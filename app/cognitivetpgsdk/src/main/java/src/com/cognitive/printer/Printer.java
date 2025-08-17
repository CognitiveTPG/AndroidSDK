package com.cognitive.printer;

import com.cognitive.connection.ConnectionListener;
import com.cognitive.connection.ConnectionManager;
import com.cognitive.connection.DevType;
import com.cognitive.printer.io.CommandSet;
import com.cognitive.printer.io.PrinterIO;
import com.cognitive.printer.io.PrinterIOFactory;

/**
 * This class is abstract and developer should extends by A798, A799 , DLXi and CSeries. 
 * @author Srinivasan G
 *
 */
public abstract class Printer 
{
	/**
	 * Set Memory type of the printer.
	 * @author Srinivasan G
	 *
	 */
	public enum MemoryType {ALL,USER_STORAGE}; 
	protected PrinterModel model;
	protected CommandSet commandset;
	protected PrinterIO buffer;
	protected ConnectionManager connection;
	protected ConnectionListener listener;
	/**
	 * This is Constructor which initialize printer model and command set.
	 * @param model This parameter is the model of the printer(A798, A799, DLXi, CSeries).
	 * @param commandset This parameter is the commandset for the printer model.
	 */
	Printer(PrinterModel model,CommandSet commandset)
	{
		model=model;
		commandset=commandset;
		buffer=PrinterIOFactory.getCommandBuffer(commandset);
	}
	/**
	 * This method helps developer to get the printer model.
	 * @return  Returns printer model(A798, A799, DLXi, CSeries)
	 */
	public PrinterModel getModel()
	{
		return model;
	}
	/**
	 * This method helps developer to get all the commands.
	 * @return  Returns all the commands for a printer.
	 */
	public CommandSet getCommandSet()
	{
		return commandset;
	}
	/**
	 * To be filled
	 * @return
	 */
	public PrinterIO getCommandBuffer()
	{
		return buffer;
	}
	/**
	 * This method set connection with the printer.
	 * @param connection Returns connection object.
	 */
	public void setConnection(ConnectionManager connection)
	{
		this.connection=connection;
	}
	
	/**
	 * This function should be called before openPrinter() method.
	 * @param listener - Specifies Connection Listener Interface.
	 */
	public void setConnectionListener(ConnectionListener listener)
	{
		this.listener=listener;
	}
	/**
	 * This method should be called after setConnection() method. This will open a printer to communicate with the device.
	 * @param type This parameter is the device type (Blutooth, TCP, None).
	 * @param address This parameter is the MAC address of the device.
	 * @throws Exception "Dev Type Unknown" will throw if dev is not Bluetooth.
	 */
	public void openPrinter(DevType type,String address) throws Exception
	{
		if(DevType.BLUETOOTH==type)
		{
			connection=ConnectionManager.getConnection(DevType.BLUETOOTH);
			connection.setConnectionListener(listener);
		}
		else if(DevType.TCP==type)
		{
			connection=ConnectionManager.getConnection(DevType.TCP);
			connection.setConnectionListener(listener);
		}
		else
		{
			throw new Exception("Dev Type Unknown");
		}
		connection.openConnection(address);
	}
	/**
	 * This abstract method helps developer to send the command to the printer through buffer.
	 * @param buffer This parameter is the PrinterIO object.
	 * @throws Exception
	 */
	public abstract void sendCommand(PrinterIO buffer) throws Exception;
	
	public void closePrinter() throws Exception
	{
		connection.closeConnection();
	}
	
}
