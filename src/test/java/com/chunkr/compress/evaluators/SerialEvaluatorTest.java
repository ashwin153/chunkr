package com.chunkr.compress.evaluators;

import static org.junit.Assert.assertArrayEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.mockito.Mockito;

import com.chunkr.expressions.Expression;

public class SerialEvaluatorTest {

	@Test
	public void testEval() {
		Expression expression = Mockito.mock(Expression.class);
		int[] chunks = new int[] { 0, 1, 2, 3 };
		
		for(int i = 0; i < chunks.length; i++)
			Mockito.when(expression.eval(BigDecimal.valueOf(i))).thenReturn(BigDecimal.valueOf(i));

		Evaluator evaluator = new SerialEvaluator();
		assertArrayEquals(chunks, evaluator.eval(4, expression));
	}
	
}
