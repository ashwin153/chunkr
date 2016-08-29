package com.chunkr.curves.expressions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.chunkr.curves.operations.binary.Add;
import com.chunkr.curves.operations.nullary.Variable;
import com.chunkr.curves.operations.unary.Neg;

public class StackExpressionTest {
	
	@Test
	public void testSubexpr() {
		Variable v1 = new Variable('x');
		StackExpression e1 = new StackExpression(v1, v1, v1, new Add());		
		
		assertEquals(new StackExpression(v1, v1), e1.subexpr(0));
		assertEquals(new StackExpression(v1, v1), e1.subexpr(1));
		assertEquals(e1, e1.subexpr(2));
	}
	
	@Test
	public void testReplace() {
		Variable v1 = new Variable('x');
		Variable v2 = new Variable('y');
		StackExpression e1 = new StackExpression(v1, v1, v1, new Add());
		StackExpression e2 = new StackExpression(v2, v2, new Neg());
		
		assertEquals(new StackExpression(v1, v2, new Neg(), v1, new Add()), e1.replace(0, e2));
		assertEquals(new StackExpression(v1, v1, v2, new Neg(), new Add()), e1.replace(1, e2));
		assertEquals(new StackExpression(v1, v2, new Neg()), e1.replace(2, e2));
	}
	
}
