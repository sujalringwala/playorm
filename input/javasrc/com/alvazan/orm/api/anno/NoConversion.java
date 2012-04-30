package com.alvazan.orm.api.anno;

import com.alvazan.orm.api.Converter;

public class NoConversion implements Converter {
	@Override
	public byte[] convertToNoSql(Object value) {
		return null;
	}
	@Override
	public Object convertFromNoSql(byte[] value) {
		return null;
	}
	@Override
	public boolean isIndexingSupported() {
		return false;
	}
	@Override
	public String convertToIndexFormat(Object value) {
		throw new UnsupportedOperationException();
	}
}