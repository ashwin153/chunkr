package com.chunkr.compress.encoders;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import org.objenesis.strategy.StdInstantiatorStrategy;

import com.chunkr.compress.Encoder;
import com.chunkr.compress.Expression;
import com.chunkr.compress.expressions.operations.binary.Add;
import com.chunkr.compress.expressions.operations.binary.Mul;
import com.chunkr.compress.expressions.operations.nullary.Constant;
import com.chunkr.compress.expressions.operations.nullary.Variable;
import com.chunkr.compress.expressions.operations.unary.Abs;
import com.chunkr.compress.expressions.operations.unary.Cos;
import com.chunkr.compress.expressions.operations.unary.Neg;
import com.chunkr.compress.expressions.operations.unary.Pow;
import com.chunkr.compress.expressions.operations.unary.Sin;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Kryo.DefaultInstantiatorStrategy;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.CollectionSerializer;
import com.esotericsoftware.kryo.serializers.FieldSerializer;

/**
 * This class makes use of the powerful Kryo serialization framework
 * (https://github.com/EsotericSoftware/kryo) to translate expressions to and
 * from binary. The Kryo framework is a faster and more space efficient
 * alternative to Java serialization that enables a wide range of customizations
 * to increase performance and allocation.
 * 
 * @author ashwin
 * 
 */
public class KryoEncoder implements Encoder {

	private Kryo _kryo;
	
	public KryoEncoder() {
		_kryo = new Kryo();
		
		// Register all the different types of operations; this makes it so that
		// kryo does not have to serialize the entire class name saving
		// considerable amount of space.
		_kryo.setRegistrationRequired(true);
		_kryo.register(BigDecimal.class, 0);
		_kryo.register(Constant.class, 1);
		_kryo.register(Variable.class, 2);
		_kryo.register(Abs.class, 3);
		_kryo.register(Cos.class, 4);
		_kryo.register(Neg.class, 5);
		_kryo.register(Pow.class, 6);
		_kryo.register(Sin.class, 7);
		_kryo.register(Add.class, 8);
		_kryo.register(Mul.class, 9);
		_kryo.register(ArrayList.class, 10);
		
		// Additional serialization properties can be specified to space extra
		// space. By informing kryo that list elements cannot be null we can
		// save a few bytes per element!
		FieldSerializer<Expression> serializer = new FieldSerializer<>(_kryo, Expression.class);
		CollectionSerializer list = new CollectionSerializer();
		list.setElementsCanBeNull(false);
		serializer.getField("_operations").setClass(ArrayList.class, list);
		_kryo.register(Expression.class, serializer, 11);
		
		// Because expression do not have a no-arg constructor, we have to use
		// the StdInstantiatorStrategy which creates an object instance without
		// ever calling the constructor using JVM specific APIs. Note: This will
		// not work on all JVMs.
		_kryo.setInstantiatorStrategy(new DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
	}
	
	@Override
	public Expression read(InputStream stream) {
		// Uncompress the input stream before piping it through kryo's
		// deserialization engine.
		Input input = new Input(new InflaterInputStream(stream));
		Expression expression = _kryo.readObject(input, Expression.class);
		input.close();
		return expression;
	}
	
	@Override
	public void write(Expression expression, OutputStream stream) {
		// Wraps the output stream inside a compressor to further reduce the
		// size of the serialized output.
		Output output = new Output(new DeflaterOutputStream(stream));
		_kryo.writeObject(output, expression);
		output.close();
	}

}
