package com.cassendra.betterreads.codec;

import java.nio.ByteBuffer;

import com.cassendra.betterreads.model.AuthorTemp;
import com.datastax.oss.driver.api.core.ProtocolVersion;
import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.datastax.oss.driver.api.core.type.codec.MappingCodec;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;

public class AuthorFormatCodec extends MappingCodec<UdtValue, AuthorTemp>
		implements
			TypeCodec<AuthorTemp> {

	final TypeCodec<UdtValue> innerCodec;

	final UserDefinedType authorTempUdt;
	protected AuthorFormatCodec(TypeCodec<UdtValue> pInnerCodec,
			GenericType<AuthorTemp> outerJavaType) {
		super(pInnerCodec, outerJavaType);
		this.innerCodec = pInnerCodec;
		this.authorTempUdt = (UserDefinedType) innerCodec.getCqlType();
	}

	@Override
	public AuthorTemp decode(ByteBuffer bytes,
			ProtocolVersion protocolVersion) {
		return toAuthorTemp(innerCodec.decode(bytes, protocolVersion));
	}

	@Override
	public ByteBuffer encode(AuthorTemp value,
			ProtocolVersion protocolVersion) {
		return innerCodec.encode(toUDTValue(value), protocolVersion);
	}

	@Override
	public String format(AuthorTemp value) {
		return value == null ? "NULL" : innerCodec.format(toUDTValue(value));
	}

	@Override
	public DataType getCqlType() {
		return authorTempUdt;
	}

	@Override
	public GenericType<AuthorTemp> getJavaType() {
		return GenericType.of(AuthorTemp.class);
	}

	@Override
	public AuthorTemp parse(String value) {
		return value == null || value.isEmpty()
				|| value.equalsIgnoreCase("NULL")
						? null
						: toAuthorTemp(innerCodec.parse(value));
	}

	protected AuthorTemp toAuthorTemp(UdtValue value) {
		return value == null
				? null
				: new AuthorTemp(value.getString("id"),
						value.getString("name"));
	}

	protected UdtValue toUDTValue(AuthorTemp value) {
		return value == null
				? null
				: authorTempUdt.newValue().setString("id", value.getId())
						.setString("name", value.getName());
	}

	@Override
	protected AuthorTemp innerToOuter(UdtValue value) {
		return toAuthorTemp(value);
	}

	@Override
	protected UdtValue outerToInner(AuthorTemp value) {
		return toUDTValue(value);
	}

}
