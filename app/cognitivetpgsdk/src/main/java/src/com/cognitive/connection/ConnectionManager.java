package com.cognitive.connection;

import java.net.Socket;
import java.util.ArrayList;

import android.content.Context;

/**
 * This is a abstract class which helps developer to connect printer with their Bluetooth enabled device.
 * @author G Srinivasan
 *
 */
public abstract class ConnectionManager 
{
	protected ConnectionListener listener;
	
	/**
	 * This method search the nearby printers.
	 * @param type :- This parameter is the type of interface like Bluetooth, WiFi, TCP, other
	 * @return This method return list of printers.
	 * @throws Exception This method throws a custom exception "Dev type unknown". For current implementation this will throws in all interface type other than Bluetooth.
	 * 
	 */
	public static ArrayList<Device>  searchPrinters(DevType type,Context context) throws Exception
	{
		ArrayList<Device> list=new ArrayList<Device>();
		if(DevType.BLUETOOTH==type)
		{
			list=BTConnection.searchPrinters();
		}
		else if(DevType.TCP==type)
		{
			list=TCPConnection.searchPrintersIP();
		}
		else
		{
			throw new Exception("Dev Type Unknown");
		}
		return list;
	}
	public static ArrayList<Device>  pingPrinters(DevType type,Context context) throws Exception
	{
		ArrayList<Device> list=new ArrayList<Device>();
		if(DevType.TCP==type)
		{
			list=TCPConnection.pingPrinters(context);
		}
		else
		{
			throw new Exception("Dev Type Unknown");
		}
		return list;
	}
	
	/**
	 * This method establish connection from printer to devices.
	 * @param type 
	 * @return This method returns connection object.
	 * @throws Exception This method throw "Dev type unknown" if device's interface type is not Bluetooth.
	 */
	public static ConnectionManager getConnection(DevType type) throws Exception
	{
		ConnectionManager con=null;
		if(DevType.BLUETOOTH==type)
		{
			con=new BTConnection();
		}
		else if(DevType.TCP==type)
		{
			con=new TCPConnection();
		}
		else
		{
			throw new Exception("Dev Type Unknown");
		}
		return con;
	}
	
	/**
	 * Abstract method to establish connection.
	 * @param address This parameter is MAC Address of the printer. 
	 * @return  This method return true if connection is successful false otherwise.
	 * @throws Exception 
	 */
	public abstract boolean openConnection(final String address) throws Exception;
	
	/**
	 * 
	 * @param paramArrayOfByte
	 * @param paramInt1
	 * @param paramInt2
	 * @throws Exception
	 */
	public abstract void writeData(byte[] paramArrayOfByte,int paramInt1,int paramInt2) throws Exception;
	/**
	 * 
	 * @param paramArrayOfByte
	 * @param paramInt1
	 * @param paramInt2
	 * @return
	 * @throws Exception
	 */
	public abstract int readData(byte[] paramArrayOfByte,int paramInt1,int paramInt2) throws Exception;
	/**
	 * This method close the connection.
	 * @throws Exception
	 */
	public abstract void closeConnection() throws Exception;
	/**
	 * 
	 * @param listener
	 */
	public void setConnectionListener(ConnectionListener listener)
	{
		this.listener=listener;
	}
	
	public abstract Socket getSocket();
	
}
