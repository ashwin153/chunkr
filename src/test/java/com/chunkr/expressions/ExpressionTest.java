package com.chunkr.expressions;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Test;

import com.chunkr.expressions.Expression;
import com.chunkr.expressions.operations.Operation;
import com.chunkr.expressions.operations.nullary.Variable;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;

public class ExpressionTest {
	
	/**
	 * Verifies that evaluation of expressions is thread safe. Makes use of the
	 * MultithreadedTC library to interleave threads in various ways to ensure
	 * that expressions are synchronized. This is an extremely important
	 * characteristic because the deflation and inflation algorithms make heavy
	 * use of concurrency.
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testEval() throws Throwable {
		TestFramework.runManyTimes(new MultithreadedExpressionTest(), 5);
	}
	
	@SuppressWarnings({"deprecation", "unused"})
	private class MultithreadedExpressionTest extends MultithreadedTestCase {
		
		private Expression _e1, _e2;
		
		@Override
		public void initialize() {
			Variable x = new Variable('x');
			_e1 = new Expression(x, Arrays.asList((Operation) x));
			_e2 = new Expression(x, Arrays.asList((Operation) x));
		}
		
		public void thread1() {
			assertEquals(BigDecimal.valueOf(+1), _e1.eval(BigDecimal.valueOf(+1)));
		}
		
		public void thread2() {
			assertEquals(BigDecimal.valueOf(+2), _e2.eval(BigDecimal.valueOf(+2)));
		}
		
		public void thread3() {
			assertEquals(BigDecimal.valueOf(+3), _e1.eval(BigDecimal.valueOf(+3)));
		}
		
		public void thread4() {
			assertEquals(BigDecimal.valueOf(+4), _e2.eval(BigDecimal.valueOf(+4)));
		}
	}
	
}
