package com.chunkr.compress.expressions;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.chunkr.compress.expressions.Expression;
import com.chunkr.compress.expressions.Operation;
import com.chunkr.compress.expressions.operations.binary.Add;
import com.chunkr.compress.expressions.operations.binary.Mul;
import com.chunkr.compress.expressions.operations.nullary.Constant;
import com.chunkr.compress.expressions.operations.nullary.Variable;

public class ExpressionTest {

	@Test
	public void testSampleExpression() {
		Variable var = new Variable("x");
		
		List<Operation> operations = Arrays.asList(
				new Constant(BigDecimal.valueOf(+2)),
				new Constant(BigDecimal.valueOf(+1)),
				var,
				new Add(),
				new Mul()
		);
		
		assertEquals(BigDecimal.valueOf(+8), new Expression(var, operations).eval(BigDecimal.valueOf(+3)));
	}
}
