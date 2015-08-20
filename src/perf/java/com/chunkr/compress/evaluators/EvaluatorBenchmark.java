package com.chunkr.compress.evaluators;

import java.util.Arrays;

import com.chunkr.expressions.Expression;
import com.chunkr.expressions.operations.Operation;
import com.chunkr.expressions.operations.nullary.Variable;
import com.google.caliper.BeforeExperiment;
import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.api.VmOptions;
import com.google.caliper.runner.CaliperMain;

@VmOptions("-server")
public class EvaluatorBenchmark {
	
	public static void main(String[] args) {
		CaliperMain.main(EvaluatorBenchmark.class, args);
	}
	
	@Param({ "10", "100", "10000", "100000" })
	private int _length;
	
	@Param
	private Method _method;

	private Evaluator _evaluator;
	private Expression _expression;
	
	public enum Method {
		
		PARALLEL {
			@Override public Evaluator getEvaluator() {
				return new ParallelEvaluator();
			}
		},
		
		SERIAL {
			@Override public Evaluator getEvaluator() {
				return new SerialEvaluator();
			}
		};
		
		abstract public Evaluator getEvaluator();
	}
	
	@BeforeExperiment
	public void setUp() throws Exception {
		_evaluator = _method.getEvaluator();
		
		Variable x = new Variable("x");
		_expression = new Expression(x, Arrays.asList((Operation) x));
	}
	
	@Benchmark
	public void timeUnchunk(int reps) {
		for(int i = 0; i < reps; i++)
			_evaluator.eval(_length, _expression);
	}
	
	@Benchmark
	public void timeChunk(int reps) {
		for(int i = 0; i < reps; i++)
			_evaluator.eval(_length, _expression);
	}
}
