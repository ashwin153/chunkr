package com.chunkr.expressions;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.chunkr.expressions.operations.binary.Add;
import com.chunkr.expressions.operations.binary.Mul;
import com.chunkr.expressions.operations.binary.Sub;
import com.chunkr.expressions.operations.nullary.Constant;
import com.chunkr.expressions.operations.nullary.Variable;
import com.chunkr.expressions.operations.unary.Abs;
import com.chunkr.expressions.operations.unary.Cos;
import com.chunkr.expressions.operations.unary.Neg;
import com.chunkr.expressions.operations.unary.Pow;
import com.chunkr.expressions.operations.unary.Sin;

/**
 * Expressions are functions that can be evaluated at any point. Expressions are
 * the building block of all mathematical/statistical models. Expressions store
 * operations in postfix order (reverse polish notation), and evaluate them
 * using a stack.
 * 
 * @author ashwin
 * @see Operation
 */
public class Expression implements Externalizable {
	
	private List<Operation> _operations;
	private Variable _variable;
	
	public Expression(Variable variable, List<Operation> operations) {
		_variable = variable;
		_operations = operations;
	}
	
	public Variable getVariable() {
		return _variable;
	}
	
	public List<Operation> getOperations() {
		return _operations;
	}
	
	/**
	 * Evaluates the expression at the specified coordinate. This operation is
	 * thread-safe.
	 * 
	 * @param x input
	 * @return output
	 */
	public BigDecimal eval(BigDecimal x) {
		// Because multiple expressions can reuse the same variables; we need to
		// make evaluations synchronized to ensure that evaluations are
		// thread-safe.
		synchronized(_variable) {
			_variable.setValue(x);
			
			Stack<BigDecimal> stack = new Stack<BigDecimal>();
			for(int i = 0; i < _operations.size(); i++) {
				BigDecimal[] operands = new BigDecimal[_operations.get(i).arity()];
				for(int j = 0; j < operands.length; j++)
					operands[j] = stack.pop();
				stack.push(_operations.get(i).eval(operands));
			}
			
			return stack.pop();
		}
	}
	
	public BigDecimal eval(double x) {
		return eval(BigDecimal.valueOf(x));
	}
	
	/**
	 * Evaluates the expression at each multiple of inc between start and end.
	 * 
	 * @param start start index
	 * @param end end index
	 * @param inc increment
	 * @return evaluated expression
	 */
	public BigDecimal[] eval(BigDecimal beg, BigDecimal end, BigDecimal inc) {
		BigDecimal ceil = beg.divide(inc, new MathContext(5)).setScale(0, RoundingMode.UP);
		BigDecimal flor = end.divide(inc, new MathContext(5)).setScale(0, RoundingMode.DOWN);
		BigDecimal[] results = new BigDecimal[flor.subtract(ceil).intValue()];
		
		for(int i = 0; i < results.length; i++)
			results[i] = eval(beg.add(inc.multiply(BigDecimal.valueOf(i))));
		
		return results;
	}
	
	public int[] eval(int beg, int end, int inc) {
		BigDecimal[] results = eval(BigDecimal.valueOf(beg),
				BigDecimal.valueOf(end), BigDecimal.valueOf(inc));
		
		int[] values = new int[results.length];
		for(int i = 0; i < results.length; i++)
			values[i] = results[i].setScale(0, RoundingMode.HALF_UP).intValueExact();
		return values;
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {		
		out.writeChar(_variable.getName());
		
		for(Operation operation : _operations) {
			// Convert the name of the operation to a string and use that string
			// to lookup the ordinal of the operation in the OperationCode
			// enumeration.
			String name = operation.getClass().getSimpleName().toUpperCase();
			out.writeByte(Operation.Type.valueOf(name).ordinal());
			
			if(operation instanceof Constant)
				out.writeDouble(((Constant) operation).getValue());
			else if(operation instanceof Pow)
				out.writeInt(((Pow) operation).getPower());
		}
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		_variable = new Variable(in.readChar());
		_operations = new ArrayList<>();
		
		while(in.available() > 0) {
			switch(Operation.Type.values()[in.readUnsignedByte()]) {
				case CONSTANT: 	_operations.add(new Constant(in.readDouble())); break;
				case VARIABLE: 	_operations.add(_variable); break;
				case ABS: 		_operations.add(new Abs()); break;
				case COS:		_operations.add(new Cos()); break;
				case NEG:		_operations.add(new Neg()); break;
				case POW:		_operations.add(new Pow(in.readInt())); break;
				case SIN:		_operations.add(new Sin()); break;
				case ADD:		_operations.add(new Add()); break;
				case MUL:		_operations.add(new Mul()); break;
				case SUB:		_operations.add(new Sub()); break;
				default: throw new IOException("Unrecognized Operation");
			}
		}
	}
	
	@Override
	public String toString() {
		return _operations.toString();
	}
	
}
