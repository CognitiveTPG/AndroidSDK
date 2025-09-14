package com.cognitive.connection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.ArrayAdapter;

/**
 * This library class is helps developer to establish TCP connection from Wifi enabled devices to wired Ethernet printer.
 * @author Manoj
 *
 */
public class TCPConnection extends com.cognitive.connection.ConnectionManager
{
	private boolean isActive=false;
	private Socket socket;
	private InputStream inStream;
	private OutputStream outStream;


	protected TCPConnection()
	{
		
	}


	protected static ArrayList<Device> searchPrintersIP() throws Exception {
		ArrayList<Device> list = new ArrayList<Device>();

		StringBuffer echo = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new FileReader("/proc/net/arp"));
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] tokens = line.split(" +");
				echo.append(line + "\n");
//		    	 System.out.println(echo);
				/**
				 * We are Extracting IP and MAC address from ARP table
				 * ARP cache (/proc/net/arp).<br>
				 * <br>
				 * Structure of this file is : <br>
				 * <br>
				 * IP address    HW type     Flags       HW address            Mask     Device
				 * 192.168.2.2    0x1         0x2         00:04:20:06:55:1a     *        eth0
				 * 192.168.2.9    0x1         0x2         00:22:43:ab:2a:5b     *        eth0
				 * */

				/**
				 * Flags 0x2 means this IP can be Pinged
				 */
				if (tokens[2].equalsIgnoreCase("0x2")) {
					System.out.println(tokens[2] + " " + tokens[0] + " " + tokens[3]);
					String macAddOctect = tokens[3].substring(0, 8);
					/**
					 * We are checking CTPG printer by its first 3 octet of MAC Address
					 */
					if (macAddOctect.equalsIgnoreCase("00:E0:70")) {
						Device dev = new Device(tokens[0], tokens[3]);
						list.add(dev);
						continue;
					}
				}

			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	protected static ArrayList<Device> pingPrinters(Context context) throws Exception {
		ArrayList<Device> list = new ArrayList<Device>();
		WifiManager wifiMgr = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
		int ipadd = wifiInfo.getIpAddress();
		String ipAddress = Formatter.formatIpAddress(ipadd);
		System.out.println("ipAddress: " + ipAddress);
		for (int i = 1; i <= 255; i++) {
			String addr = ipAddress;
			addr = addr.substring(0, addr.lastIndexOf('.') + 1) + i;
			InetAddress pingAddr = null;
			try {

				pingAddr = InetAddress.getByName(addr);
				if (pingAddr.isReachable(500)) {

					boolean isCognitivePrinter = filterCognitivePrinter(pingAddr);
					if (isCognitivePrinter) {
						Device dev = new Device(pingAddr.toString(), "");
						list.add(dev);
						continue;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	private static boolean filterCognitivePrinter(InetAddress pingAddr) {
		if (pingAddr == null)
			return false;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("/proc/net/arp"));
			String line;
			while ((line = br.readLine()) != null) {
				String[] splitted = line.split(" +");
				if (splitted != null && splitted.length >= 4 && pingAddr.equals(splitted[0])) {
					// Basic sanity check
					String mac = splitted[3];
					String macAddOctect = mac.substring(0, 8);
					if (macAddOctect.equalsIgnoreCase("00:E0:70")) {
						return true;
					} else {
						return false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}


	@Override
	public boolean openConnection(String address) throws Exception 
	{
		boolean isConnected=false;
		try 
		{
			Log.d("tag", "openConnection :"+address);
			socket=new Socket(InetAddress.getByName(address), 9001);
			inStream=socket.getInputStream();
			outStream=socket.getOutputStream();
			Log.i("openConnection", "openConnection");
			isActive=true;
			isConnected=true;
			if(listener!=null)
			{
				listener.onConnected();
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			isActive=false;
			if(listener!=null)
	        {
	        	listener.onError(e.getMessage());
	        }
			throw new Exception(e.getMessage());
		}
		return isConnected;
	}

	@Override
	public void writeData(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws Exception 
	{
		Log.d("Checkmytag","In Output Stream writedata");
		try
		{
			this.outStream.write(paramArrayOfByte, paramInt1, paramInt2);
			this.outStream.flush();
	    }
	    catch (Exception e)
	    {
	    	Log.d("Checkmytag","In Output Stream writedata Exception");
	    	e.printStackTrace();
	    	isActive=false;
	    	if(listener!=null)
	    	{
	    		listener.onError(e.getMessage());
	    	}
	    	throw new Exception("Failed to Write");
	    }
	}

	@Override
	public int readData(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws Exception 
	{
		try
	    {
	    	Log.d("Checkmytag","In input Stream readdata");
	    	if (this.inStream.available() == 0)
	    		return 0;
	    	int i = this.inStream.read(paramArrayOfByte, paramInt1, paramInt2);
	    	if (i == -1)
	    		i = 0;
	    	
	    	return i;
	    }
	    catch (Exception e)
	    {
	    	Log.d("Checkmytag","In input Stream readdata Exception");
	    	e.printStackTrace();
	    	isActive=false;
	    	if(listener!=null)
		    {
		       listener.onError(e.getMessage());
		    }
	    }
	    throw new Exception("Failed to Read");
	}

	@Override
	public void closeConnection() throws Exception 
	{
		try
	    {
			isActive=false;
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
	    	e.printStackTrace();
	    	this.socket = null;
	    	if(listener!=null)
	    	{
	    		listener.onError(e.getMessage());
	    	}
	    	throw new Exception(e.getMessage());
	    }
	}
	
	public boolean isActive()
	{
		return isActive;
	}


	@Override
	public Socket getSocket() {
		// TODO Auto-generated method stub
		return null;
	}

}