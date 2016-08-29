package com.chunkr.curves.expressions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.chunkr.curves.operations.binary.Add;
import com.chunkr.curves.operations.binary.Sub;
import com.chunkr.curves.operations.nullary.Variable;

public class TreeExpressionTest {
	
	@Test
	public void testEquivalence() throws Exception {
		Variable v1 = new Variable('x');
		StackExpression e1 = new StackExpression(v1, v1, v1, v1, new Add(), new Sub());
		TreeExpression e2 = new TreeExpression(e1);
		assertEquals(e1, e2);
		assertEquals(e1.hashCode(), e2.hashCode());
	}
	
}
