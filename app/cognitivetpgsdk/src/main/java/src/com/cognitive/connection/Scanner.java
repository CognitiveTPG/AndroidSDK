package com.cognitive.connection;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
/**
 * This class implement scanning of the printer device. 
 * @author Manoj
 *
 */
public class Scanner 
{
	private Context context;
	private com.cognitive.connection.ScanListener listener;
	
	public Scanner(Context context)
	{
		this.context=context;
	}
	
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() 
    {
        @Override
        public void onReceive(Context context, Intent intent) 
        {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) 
            {
            	if(listener!=null)
					listener.onScanStarted();
            }
            else if (BluetoothDevice.ACTION_FOUND.equals(action)) 
            {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(device!=null && device.getAddress().startsWith("00:06:66"))
                {
                	if(listener!=null){
						try{
							listener.onDeviceFound(device.getName(),device.getAddress());
						}catch (SecurityException e){
							throw new SecurityException(e);
						}
					}

                }
            } 
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) 
            {
            	if(listener!=null)
            		listener.onScanFinished();
            }
        }
    };
    /**
     * This method ends the scanning.
     */
    public void endScan()
    {
    	context.unregisterReceiver(mReceiver);
    }
    /**
     * This method start the scanning of the device.
     * @param listener This parameter  is the ScanListener, we are passing this parameter to catch the scanning events.
     */
    public void startScan(final com.cognitive.connection.ScanListener listener)
	{
		this.listener=listener;
		IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		context.registerReceiver(mReceiver, filter);
		
		filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		context.registerReceiver(mReceiver, filter);
		
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		context.registerReceiver(mReceiver, filter);
		
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		if(adapter==null)
		{
			if(listener!=null)
				listener.onError("No Bluetooth Supported");
			 return;
		}
		if (adapter.getState() != BluetoothAdapter.STATE_ON)
		{
			if(listener!=null)
				listener.onError("Bluetooth Disabled");
			return;
		}
		try {
			if (adapter.isDiscovering()) {
				adapter.cancelDiscovery();
			}
			adapter.startDiscovery();
		} catch (SecurityException e) {
			throw new SecurityException(e);
		}
	}

}
