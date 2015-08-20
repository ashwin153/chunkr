package com.chunkr.compress.encoders;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.chunkr.compress.Archive;
import com.chunkr.expressions.Expression;
import com.chunkr.expressions.operations.Operation;
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
 * Defines my own encoding scheme to massively increase storage performance. By
 * defining a rigid encoding scheme, I am able to avoid all the extra space
 * serialization frameworks use to regenerate object graphs. The encoding is
 * specified in the scheme below.
 * 
 * @author ashwin
 * 
 */
public class CustomEncoder implements Encoder {
	
	/**
	 * The order that operations are declared in this enumeration will determine
	 * their operation codes. Therefore, it is imperative that these enumerated
	 * constants stay in order. OperationCode names must be the same as the
	 * associated operation class simple name.
	 * 
	 * @author ashwin
	 * 
	 */
	private enum OperationCode {
		CONSTANT, VARIABLE, ABS, COS, NEG, POW, SIN, ADD, MUL, SUB;
	}

	@Override
	public Archive read(InputStream stream) throws IOException {
		// Wrap the input stream into a data input stream; this enables to have
		// fine-grained access to the input stream.
		DataInputStream input = new DataInputStream(stream);
		
		// Unpack the archive header; the archive header consists of the
		// chunkSize (byte), length (int), weights (double[]); the size of the
		// header depends on the chunkSize, but in general it will the size of
		// the header will be 1 + 4 + 8 * chunkSize bytes
		byte chunkSize = input.readByte();
		int length = input.readInt();
		
		List<Double> weights = new ArrayList<>(chunkSize);
		for(int i = 0; i < chunkSize; i++)
			weights.add(input.readDouble());
		
		// Unpack the archive expression; each operation is assigned an
		// operation code depending on its ordinal in the OperationCode
		// enumeration. Additional operation parameters are stored adjacent to
		// the OperationCode. The variable is only stored once and it is assumed
		// that all future references object are actually references to the
		// variable object defined first.
		Variable var = new Variable(input.readChar());
		List<Operation> operations = new ArrayList<>();
		
		while(input.available() > 0) {
			switch(OperationCode.values()[input.readUnsignedByte()]) {
				case CONSTANT: 	operations.add(new Constant(input.readDouble())); break;
				case VARIABLE: 	operations.add(var); break;
				case ABS: 		operations.add(new Abs()); break;
				case COS:		operations.add(new Cos()); break;
				case NEG:		operations.add(new Neg()); break;
				case POW:		operations.add(new Pow(input.readInt())); break;
				case SIN:		operations.add(new Sin()); break;
				case ADD:		operations.add(new Add()); break;
				case MUL:		operations.add(new Mul()); break;
				case SUB:		operations.add(new Sub()); break;
			}
		}
		
		return new Archive(chunkSize, length, weights, new Expression(var, operations));
	}

	@Override
	public void write(Archive archive, OutputStream stream) throws IOException {
		DataOutputStream output = new DataOutputStream(stream);
		
		// Write the archive header; contents are described in read
		output.writeByte(archive.getChunkSize());
		output.writeInt(archive.getLength());
		
		for(Double weight : archive.getWeights())
			output.writeDouble(weight);
		
		// Write the archive expression; contents are described in read
		Expression expression = archive.getExpression();
		output.writeChar(expression.getVariable().getName());
		
		for(Operation operation : expression.getOperations()) {
			// Convert the name of the operation to a string and use that string
			// to lookup the ordinal of the operation in the OperationCode
			// enumeration.
			String name = operation.getClass().getSimpleName().toUpperCase();
			output.writeByte(OperationCode.valueOf(name).ordinal());
			
			if(operation instanceof Constant)
				output.writeDouble(((Constant) operation).getValue());
			else if(operation instanceof Pow)
				output.writeInt(((Pow) operation).getPower());
		}

	}

}
