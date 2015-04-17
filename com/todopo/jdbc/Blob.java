package com.todopo.jdbc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Arrays;


public class Blob implements java.sql.Blob {

	ByteArrayOutputStream baos=new ByteArrayOutputStream();
	byte [] aByte=new byte[0];	
	
	@Override
	public void free() throws SQLException {
		baos.reset();
		
	}

	@Override
	public InputStream getBinaryStream() throws SQLException {
		return new ByteArrayInputStream(baos.toByteArray());
	}

	@Override
	public InputStream getBinaryStream(long pos, long length)
			throws SQLException {
		return new ByteArrayInputStream(baos.toByteArray(), (int)pos, (int)length);
	}

	@Override
	public byte[] getBytes(long pos, int length) throws SQLException {
		byte [] retorno=new byte[length];
		System.arraycopy(baos.toByteArray(), (int) pos, retorno, 0, length);
		return retorno;
	}

	@Override
	public long length() throws SQLException {
		return baos.size();
	}

	@Override
	public long position(byte[] pattern, long start) throws SQLException {
		if (pattern.length>length()-start)
			return -1;
		else
		{
			byte [] aux=new byte[pattern.length];
			System.arraycopy(baos.toByteArray(), (int) start, aux, 0, pattern.length);
		if (Arrays.equals(pattern, aux))
				return start;
			else 
				return position(pattern, start+1);
		}
					
	}

	@Override
	public long position(java.sql.Blob pattern, long start) throws SQLException {
		return position(pattern.getBytes(0, (int) pattern.length()),  start);
	}

	@Override
	public OutputStream setBinaryStream(final long pos) throws SQLException {
		OutputStream os=new OutputStream()
		{
			public void write(int b)
			{
				baos.write(new byte[] {(byte) b},(int)pos, 1);
			}
		};
		
		return os;
	}

	@Override
	public int setBytes(long pos, byte[] bytes) throws SQLException {
		baos.write(bytes, (int) pos, bytes.length);
		return bytes.length;
	}

	@Override
	public int setBytes(long pos, byte[] bytes, int offset, int len)
			throws SQLException {
		byte [] aux =new byte[len];
		System.arraycopy(bytes, offset, aux, 0, len);
		baos.write(aux, (int) pos, len);
		return len;
	}

	@Override
	public void truncate(long len) throws SQLException {
		ByteArrayOutputStream aux =new ByteArrayOutputStream();	
		aux.write(baos.toByteArray(), 0, (int) len);
		baos=aux;
	}

}
