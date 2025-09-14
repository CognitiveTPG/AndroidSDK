package com.cognitive.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.cognitive.connection.ConnectionManager;
import com.cognitive.connection.DevType;
import com.cognitive.connection.Device;
import com.cognitive.printer.R;

public class WiFiTestActivity extends Activity
{
	private TextView textView;
	private Handler handler=new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		try 
		{
			ArrayList<Device> list=ConnectionManager.searchPrinters(DevType.TCP, this);
	    } 
		catch (Exception e) 
		{
			e.printStackTrace();
			textView.setText(getResources().getString(R.string.network_error,e.getMessage()));
		}
	}
	
	private void pingAll()
	{
		Thread thread=new Thread(new Runnable() 
		{
			public void run() 
			{
				for(int i=1;i<=255;i++)
				{
					try 
					{
						Runtime.getRuntime().exec("ping -b 192.168.2."+i);
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
				
				ArrayList<String> list=getPrinterIPAddresses();
				if(list.size()>0)
				{
					for(String s:list)
					{
						updateTextView(s);
					}
				}
			}
		});
		thread.start();
	}
	
	private void updateTextView(final String msg)
	{
		handler.post(new Runnable() 
		{
			public void run() 
			{
				textView.append(msg+"\r\n");
			}
		});
	}
	
	/**
	 * Try to extract a hardware MAC address from a given IP address using the
	 * ARP cache (/proc/net/arp).<br>
	 * <br>
	 * We assume that the file has this structure:<br>
	 * <br>
	 * IP address       HW type     Flags       HW address            Mask     Device
	 * 192.168.18.11    0x1         0x2         00:04:20:06:55:1a     *        eth0
	 * 192.168.18.36    0x1         0x2         00:22:43:ab:2a:5b     *        eth0
	 *
	 * @param ip
	 * @return the MAC from the ARP cache
	 */
	public String getMacFromArpCache(String ip) 
	{
	    if (ip == null)
	        return null;
	    BufferedReader br = null;
	    try 
	    {
	        br = new BufferedReader(new FileReader("/proc/net/arp"));
	        String line;
	        while ((line = br.readLine()) != null) 
	        {
	        	//textView.append(line+"\r\n");
	            String[] splitted = line.split(" +");
	            if (splitted != null && splitted.length >= 4 && ip.equals(splitted[0])) 
	            {
	                // Basic sanity check
	                String mac = splitted[3];
	                if (mac.matches("..:..:..:..:..:..")) 
	                {
	                    return mac;
	                } 
	                else 
	                {
	                    return null;
	                }
	            }
	        }
	    } 
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	    } 
	    finally 
	    {
	        try 
	        {
	            br.close();
	        } 
	        catch (IOException e) 
	        {
	            e.printStackTrace();
	        }
	    }
	    return null;
	}
	                                                            
	public ArrayList<String> getPrinterIPAddresses()
	{
		ArrayList<String> list=new ArrayList<String>();
		BufferedReader br = null;
	    try 
	    {
	        br = new BufferedReader(new FileReader("/proc/net/arp"));
	        String line;
	        while ((line = br.readLine()) != null) 
	        {
	        	String[] splitted = line.split(" +");
	        	if(splitted!=null && splitted.length >= 4 && splitted[3].matches("..:..:..:..:..:..") && splitted[3].contains("00:E0:70"))
	        	{
	        		list.add(splitted[0]);
	        	}
	        }
	    } 
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	    } 
	    finally 
	    {
	        try 
	        {
	            br.close();
	        } 
	        catch (IOException e) 
	        {
	            e.printStackTrace();
	        }
	    }
	    return list;
	}
	
}
