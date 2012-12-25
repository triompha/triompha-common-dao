package com.triompha.cache;

import java.io.Serializable;

/**
 * common cache inteface
 * 
 */
public interface Cache {
	public void put(String key,Serializable value);
	public void put(String key,Serializable value,int ttl);
	public Object get(String key);
	public void remove(String key);
}
