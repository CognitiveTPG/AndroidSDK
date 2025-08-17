package com.cognitive.connection;

/**
 * This interface define scan Listener and provide callback methods.
 * @author Manoj
 *
 */
public interface ScanListener 
{
	/**
	 * This callback method will be called when scan has started.
	 */
	public void onScanStarted();
	/**
	 * This callback method will be called when a device found.
	 * @param name This parameter is the device name. 
	 * @param address This parameter is the MAC Address of the device.
	 */
	public void onDeviceFound(final String name,final String address);
	/**
	 * Called when scan has been finished.
	 */
	public void onScanFinished();
	/**
	 * This callback method will be called when there is any error in scanning.
	 * @param msg This parameter is the Error message.
	 */
	public void onError(final String msg);
}
