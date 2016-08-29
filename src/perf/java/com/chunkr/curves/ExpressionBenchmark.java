package com.chunkr.curves;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.chunkr.curves.Expression;
import com.chunkr.curves.Operation;
import com.chunkr.curves.expressions.StackExpression;
import com.chunkr.curves.expressions.TreeExpression;
import com.chunkr.curves.operations.binary.Add;
import com.chunkr.curves.operations.nullary.Variable;
import com.chunkr.curves.operations.unary.Sin;
import com.google.caliper.BeforeExperiment;
import com.google.caliper.Param;
import com.google.caliper.api.Macrobenchmark;
import com.google.caliper.runner.CaliperMain;

/**
 * This performance benchmark measures the runtime performance and memory
 * allocation of expression evaluation. Expression evaluation is a significant
 * performance bottleneck; therefore, expressions should be optimized for
 * runtime performance over space to minimize decompression costs.
 * 
 * @author ashwin
 * 
 */
public class ExpressionBenchmark {
	
	public static void main(String[] args) {
		CaliperMain.main(ExpressionBenchmark.class, args);
	}
	
	@Param
	private Method _method;
	
	@Param({ "5", "10", "25", "50", "100" })
	private int _length;
	
	private Expression _expression;
	private BigDecimal[] _x;
	
	public enum Method {
		
		STACK {
			@Override
			public Expression getExpression(int length) {
				Variable x = new Variable('x');
				List<Operation> oper = new ArrayList<Operation>(Arrays.asList(x));
				for(int i = 0; i < (length - 1) / 2; i++) {
					oper.add(x);
					oper.add(new Add());
				}
				
				if(length % 2 == 0) oper.add(new Sin());
				return new StackExpression(x, oper);
			}
		},
		
		TREE {
			@Override
			public Expression getExpression(int length) {
				return new TreeExpression((StackExpression) STACK.getExpression(length));
			}
		};
		
		abstract Expression getExpression(int length);
	}
	
	@BeforeExperiment
	public void setUp() throws Exception {
		_expression = _method.getExpression(_length);
		_x = new BigDecimal[1000];
		for(int i = 0; i < 1000; i++)
			_x[i] = BigDecimal.valueOf(i);
	}
	
	@Macrobenchmark
	public void timeEval() throws Exception {
		_expression.eval(_x);
	}
	
}
