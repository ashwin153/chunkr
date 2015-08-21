package com.chunkr.compress.evaluators;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.chunkr.expressions.Expression;

/**
 * SerialEvaluators sequentially evaluate the specified expression at each point
 * in the file. While sequential evaluation is significantly slower than
 * parallel evaluation, it is useful in case parallel evaluation is not
 * possible.
 * 
 * @author ashwin
 * @see Evaluator
 */
public class SerialEvaluator implements Evaluator {

	@Override
	public int[] eval(int length, Expression expression) {
		int[] chunks = new int[length];
		for(int i = 0; i < chunks.length; i++) {
			BigDecimal value = expression.eval(BigDecimal.valueOf(i));
			BigDecimal round = value.setScale(0, RoundingMode.HALF_UP);
			chunks[i] = round.intValue();
		}
		return chunks;
	}

}
