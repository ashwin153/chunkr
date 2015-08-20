package com.chunkr.compress.evaluators;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.chunkr.expressions.Expression;

public class ParallelEvaluator implements Evaluator {

	@Override
	public int[] eval(int length, final Expression expression) {
		ExecutorService executor = Executors.newCachedThreadPool();
		List<Callable<Integer>> tasks = new ArrayList<Callable<Integer>>();
		for(int i = 0; i < length; i++) {
			final BigDecimal x = BigDecimal.valueOf(i);
			tasks.add(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					BigDecimal value = expression.eval(x);
					BigDecimal round = value.setScale(0, RoundingMode.HALF_UP);
					return round.intValue();
				}
			});
		}
		
		try {
			// Evaluate the fitness of all chromosomes and retrieve the results
			List<Future<Integer>> results = executor.invokeAll(tasks);
			int[] chunks = new int[length];
			for(int i = 0; i < chunks.length; i++)
				chunks[i] = results.get(i).get();
			return chunks;
		} catch(Exception e) {
			// If we are unable to parallelize the task, then fall back on
			// serial evaluation; this prevents the evaluation from failing.
			return new SerialEvaluator().eval(length, expression);
		} finally {
			// Ensure the executor is shutdown to force garbage collection
			executor.shutdownNow();
		}
	}

}
