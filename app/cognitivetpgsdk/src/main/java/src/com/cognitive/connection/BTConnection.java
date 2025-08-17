package com.cognitive.connection;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

/**
 * This class extends Connection class and establish bluetooth connection. 
 * @author Manoj
 *
 */
public class BTConnection extends ConnectionManager
{
	private BluetoothSocket socket = null;
	private InputStream inStream;
	private OutputStream outStream;
	
	/**
	 * 
	 */
	protected BTConnection()
	{
		
	}
	
	/**
	 * This method search list of bluetooth enabled device and will filter Cognitive Printer only by checking MAC Address of the device.
	 * @return This method returns list of printer in ArrayList.
	 * @throws Exception This method will throw following exception : -
	 * 1.  No Bluetooth Adapter Found - If there is no bluetooth adapter.
	 * 2.  Bluetooth Adapter id Off	 - If Bluetooth Adapter is off. 
	 */
	protected static ArrayList<Device>  searchPrinters() throws Exception
	{
		ArrayList<Device> list=new ArrayList<Device>();
		
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		if(adapter==null)
		{
			throw new Exception("No Bluetooth Adapter Found");
		}
		if (adapter.getState() != 12)
		{
			throw new Exception("Bluetooth Adapter is Off");
		}
		Set<BluetoothDevice> devices=adapter.getBondedDevices();
		for(BluetoothDevice device:devices)
		{                  
			if(device!=null && device.getAddress().startsWith("00:80:E1")) //00:06:66 for RN Module 00:80:E1 for ST Module
            {
				String name=device.getName();
				String address=device.getAddress();
				Device dev=new Device(name, address);
				list.add(dev);
            }
		}
		return list;
	}
	
	public boolean openConnection(final String address) throws Exception
	{
		boolean isConnected=false;
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		if(adapter==null)
		{
			throw new Exception("No Bluetooth Adapter Found");
		}
		if (adapter.getState() != 12)
		{
			throw new Exception("Bluetooth Adapter is Off");
		}
		if(address==null || !BluetoothAdapter.checkBluetoothAddress(address))
		{
			throw new Exception("Invalid Bluetooth Mac Address");
		}
		try 
		{
			BluetoothDevice device=adapter.getRemoteDevice(address);
			int state=device.getBondState();
			//socket=device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
			//socket=device.createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
			//socket=device.createInsecureRfcommSocketToServiceRecord(UUID.fromString("35111C00001101-0000-1000-8000-00805F9B34FB"));
			socket=device.createRfcommSocketToServiceRecord(UUID.fromString("35111C00001101-0000-1000-8000-00805F9B34FB"));
			Log.e("Bonding State ", state+" "+device.getName());
			adapter.cancelDiscovery();
			socket.connect();
			this.outStream = this.socket.getOutputStream();
	        this.inStream = this.socket.getInputStream();
	        isConnected=true;
	        if(listener!=null)
	        {
	        	listener.onConnected();
	        }
		} 
		catch (Exception e) 
		{
			if(listener!=null)
	        {
	        	listener.onError(e.getMessage());
	        }
			throw new Exception(e.getMessage());
		}
		
		return isConnected;
	}
	
	public void writeData(byte[] paramArrayOfByte,int paramInt1,int paramInt2) throws Exception
	{
		try
		{
			this.outStream.write(paramArrayOfByte, paramInt1, paramInt2);
			this.outStream.flush();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      if(listener!=null)
	      {
	        listener.onError(e.getMessage());
	      }
	      throw new Exception("Failed to Write");
	    }
	}
	
	public int readData(byte[] paramArrayOfByte,int paramInt1,int paramInt2) throws Exception
	{
		try
	    {
	    	if (this.inStream.available() == 0)
	    		return 0;
	    	int i = this.inStream.read(paramArrayOfByte, paramInt1, paramInt2);
	    	if (i == -1)
	    		i = 0;
	    	return i;
	    }
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	    	if(listener!=null)
		    {
		       listener.onError(e.getMessage());
		    }
	    }
	    throw new Exception("Failed to Read");
	}
	
	public void closeConnection() throws Exception
	{
		try
	    {
	      this.outStream.close();
	      this.inStream.close();
	      this.socket.close();
	      Thread.sleep(500L);
	      this.socket = null;
	      if(listener!=null)
		  {
	    	  listener.onDisconnected();
		  }
	    }
	    catch (Exception e)
	    {
	      this.socket = null;
	      if(listener!=null)
		  {
	    	  listener.onError(e.getMessage());
		  }
	      throw new Exception(e.getMessage());
	    }
	}

	@Override
	public Socket getSocket() {
		// TODO Auto-generated method stub
		return null;
	}

}
