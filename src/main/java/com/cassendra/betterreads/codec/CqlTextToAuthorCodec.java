package com.cassendra.betterreads.codec;

import java.nio.ByteBuffer;

import com.cassendra.betterreads.model.AuthorTemp;
import com.datastax.oss.driver.api.core.ProtocolVersion;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.codec.TypeCodecs;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;

public class CqlTextToAuthorCodec implements TypeCodec<AuthorTemp> {

	@Override
	public AuthorTemp decode(ByteBuffer bytes,
			ProtocolVersion protocolVersion) {
		String intValue = TypeCodecs.TEXT.decode(bytes, protocolVersion);
		return new AuthorTemp(intValue.split("^")[0], intValue.split("^")[1]);
	}

	@Override
	public ByteBuffer encode(AuthorTemp value,
			ProtocolVersion protocolVersion) {
		if (value == null) {
			return null;
		} else {
			String intValue = value.getId() + "^" + value.getName();
			return TypeCodecs.TEXT.encode(intValue, protocolVersion);
		}
	}

	@Override
	public String format(AuthorTemp value) {
		String intValue = value.getId() + "^" + value.getName();
		return TypeCodecs.TEXT.format(intValue);
	}

	@Override
	public DataType getCqlType() {
		return DataTypes.TEXT;
	}

	@Override
	public GenericType<AuthorTemp> getJavaType() {
		return GenericType.of(AuthorTemp.class);
	}

	@Override
	public AuthorTemp parse(String value) {
		String intValue = TypeCodecs.TEXT.parse(value);
		return intValue == null
				? null
				: new AuthorTemp(intValue.split("^")[0],
						intValue.split("^")[1]);
	}
}