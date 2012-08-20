package com.alvazan.orm.api.spi5;

import com.alvazan.orm.api.spi3.meta.IndexColumnInfo;


public interface SpiQueryAdapter {

	public void setParameter(String parameterName, byte[] value);

	public Iterable<IndexColumnInfo> getResultList();

	public void setFirstResult(int firstResult);

	public void setMaxResults(int batchSize);
	
}
