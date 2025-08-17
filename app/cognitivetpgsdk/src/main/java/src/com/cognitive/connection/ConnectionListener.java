package com.cognitive.connection;

/**
 * This is interface to listen the connection.
 * @author Srinivasan G
 *
 */
public interface ConnectionListener 
{
	/**
	 * This is a callback method called when printer is connected with the device.
	 */
	public void onConnected();
	/**
	 * This method will be called whenever there is an Error in connection.
	 * @param msg This parameter is the Error message.
	 */
	public void onError(final String msg);
	/**
	 * This callback method will be called when printer is disconnected from the device. 
	 */
	public void onDisconnected();
}
