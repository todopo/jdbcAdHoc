package com.todopo.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;

public class Array extends Hashtable<String,Class<?>> implements java.sql.Array {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1130673536297601600L;
	String typeName;
	
	Hashtable<String,Class<?>> helements=new Hashtable<String, Class<?>>();
		
	public Array(String typeName, Object[] elements)
	{
		this.typeName=typeName;
		
		for (Object obj:elements) 
		{
			this.helements.put(obj.toString(), (Class<?>) obj);
		}
	}

	@Override
	public void free() throws SQLException {
		this.helements.clear();
		
	}

	@Override
	public Object getArray() throws SQLException {
		return helements;
	}

	@Override
	public Object getArray(Map<String, Class<?>> map) throws SQLException {
		 helements.putAll(map);
		 return helements;
	}

	@Override
	public Object getArray(long index, int count) throws SQLException {
		return helements;
	}

	@Override
	public Object getArray(long index, int count, Map<String, Class<?>> map)
			throws SQLException {
		 helements.putAll(map);
		 return helements;
	}

	@Override
	public int getBaseType() throws SQLException {
		try {
			return Class.forName("java.sql.Types").getField(typeName).getInt(typeName);
		} catch (IllegalArgumentException | IllegalAccessException
				| NoSuchFieldException | SecurityException
				| ClassNotFoundException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public String getBaseTypeName() throws SQLException {
		return typeName;		
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		return new com.todopo.jdbc.Resultset();
	}

	@Override
	public ResultSet getResultSet(Map<String, Class<?>> map)
			throws SQLException {
		return new com.todopo.jdbc.Resultset(map);
	}

	@Override
	public ResultSet getResultSet(long index, int count) throws SQLException {
		return new Resultset(index, count);
	}

	@Override
	public ResultSet getResultSet(long index, int count,
			Map<String, Class<?>> map) throws SQLException {
		return null;
	}

}
