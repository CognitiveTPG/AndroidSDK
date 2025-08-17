package com.cognitive.util;

import java.util.List;

public class Util 
{

	public static void CopyArray(byte[] srcArray, Byte[] cpyArray) 
	{
    	for (int index = 0; index < cpyArray.length; index++) 
    	{
    		cpyArray[index] = srcArray[index];
    	}
    }

	public static byte[] convertFromListByteArrayTobyteArray(List<Byte> ByteArray)
	{
		byte[] byteArray = new byte[ByteArray.size()];
		for(int index = 0; index < byteArray.length; index++)
		{
			byteArray[index] = ByteArray.get(index);
		}
		return byteArray;
	}
	
	public static boolean[] getBits(final byte b) 
	{
		return new boolean[]{
			    (b &    1) != 0,
			    (b &    2) != 0,
			    (b &    4) != 0,
			    (b &    8) != 0,
			    (b & 0x10) != 0,
			    (b & 0x20) != 0,
			    (b & 0x40) != 0,
			    (b & 0x80) != 0
			  };	  
	}
	
	
}
