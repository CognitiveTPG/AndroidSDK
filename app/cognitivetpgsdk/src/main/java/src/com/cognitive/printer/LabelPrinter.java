package com.cognitive.printer;

import android.util.Log;

import com.cognitive.printer.io.CommandSet;
import com.cognitive.printer.io.LabelPrinterIO;
import com.cognitive.printer.io.PrinterIO;
import com.cognitive.status.StatusMessage;
/**
 * This class is used for DLXi printer and is used to get all info about the printer like model number, serial number, firmware version etc
 * and send command of DLXi Printer Model.  
 * @author Srinivasan G 
 *
 */
public class LabelPrinter extends Printer
{
	
	/**
	 * To be filled 
	 */
	LabelPrinter() 
	{
		super(PrinterModel.DLXi, CommandSet.DLXi_F);
	}
	/**
	 * This command helps developer to get the Model number.
	 */
	public PrinterModel getModel()
	{
		return model;
	}
	
	/**
	 * This command returns all the command for DLXi printers.
	 */
	public CommandSet getCommandSet()
	{
		return commandset;
	}
	/**
	 * This method sends the commands to the printer buffer.
	 */
	@Override
	public void sendCommand(PrinterIO buffer) throws Exception 
	{
		LabelPrinterIO label=(LabelPrinterIO) buffer;
		byte[] data=label.getByteBuffer();
		String format=new String(data);
		Log.e("Label", format);
		connection.writeData(data, 0, data.length);
	}
	/**
	 * This method send command as byte data to the printer.
	 * @param data This parameter is the byte form of the commands.
	 * @throws Exception
	 */
	public void sendCommand(byte[] data) throws Exception 
	{
		int length=data.length;
		if(length<=256)
		{
			connection.writeData(data, 0, data.length);
			return;
		}
		else
		{
			
		}
	}
	
	/**
	 * Causes the printer to send a status message.
	 */
	public StatusMessage getPrinterStatus()
	{
		String response=new String();
		try 
		{
			String cmd="!QS\r\n";
			byte[] data=cmd.getBytes();
			connection.writeData(data, 0, data.length);
			Thread.sleep(200L);
			
			data=new byte[1024];
			int size=connection.readData(data, 0, data.length);
			if(size>0)
			{
				response=new String(data, 0, size);
			}
			System.out.println("Status Code "+response);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return new StatusMessage(response);
	}
	
	/**
	 * 
	 * This function is used to query the printers MAC address.
	 */
	public String getMacAddress()
	{
		String response=null;
		try 
		{
			String cmd="!SHOW MAC\r\n";
			byte[] data=cmd.getBytes();
			connection.writeData(data, 0, data.length);
			Thread.sleep(200L);
			
			data=new byte[1024];
			int size=connection.readData(data, 0, data.length);
			if(size>0)
			{
				response=new String(data, 0, size);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 
	 * This function is used to query the printer�s model number.
	 */
	public String getModelNumber()
	{
		String response=null;
		try 
		{
			String cmd="!SHOW MODELNUMBER\r\n";
			byte[] data=cmd.getBytes();
			connection.writeData(data, 0, data.length);
			Thread.sleep(200L);
			
			data=new byte[1024];
			int size=connection.readData(data, 0, data.length);
			if(size>0)
			{
				response=new String(data, 0, size);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 
	 * This function is used to query the printer�s current print head setting.
	 */
	public String getPrintHead()
	{
		String response=null;
		try 
		{
			String cmd="!SHOW PRINTHEAD\r\n";
			byte[] data=cmd.getBytes();
			connection.writeData(data, 0, data.length);
			Thread.sleep(200L);
			
			data=new byte[1024];
			int size=connection.readData(data, 0, data.length);
			if(size>0)
			{
				response=new String(data, 0, size);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 
	 * This function is used to query the printer�s serial number.
	 */
	public String getSerialNumber()
	{
		String response=null;
		try 
		{
			String cmd="!SHOW SERIALNUMBER\r\n";
			byte[] data=cmd.getBytes();
			connection.writeData(data, 0, data.length);
			Thread.sleep(200L);
			
			data=new byte[1024];
			int size=connection.readData(data, 0, data.length);
			if(size>0)
			{
				response=new String(data, 0, size);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * Gets the default print width.
	 */
	public String getWidth()
	{
		String response=null;
		try 
		{
			String cmd="! 0 0 0 0\r\nVARIABLE WIDTH ?\r\nEND";
			byte[] data=cmd.getBytes();
			connection.writeData(data, 0, data.length);
			Thread.sleep(200L);
			
			data=new byte[1024];
			int size=connection.readData(data, 0, data.length);
			if(size>0)
			{
				response=new String(data, 0, size);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * Returns current user feedback settings
	 */
	public String getUserFeedback()
	{
		String response=null;
		try 
		{
			String cmd="! 0 0 0 0\r\nVARIABLE USER_FEEDBACK ?\r\nEND";
			byte[] data=cmd.getBytes();
			connection.writeData(data, 0, data.length);
			Thread.sleep(200L);
			
			data=new byte[1024];
			int size=connection.readData(data, 0, data.length);
			if(size>0)
			{
				response=new String(data, 0, size);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * Returns current sprint speed setting.
	 */
	public String getPrintSpeed()
	{
		String response=null;
		try 
		{
			String cmd="! 0 0 0 0\r\nVARIABLE PRINT_SPEED ?\r\nEND";
			byte[] data=cmd.getBytes();
			connection.writeData(data, 0, data.length);
			Thread.sleep(200L);
			
			data=new byte[1024];
			int size=connection.readData(data, 0, data.length);
			if(size>0)
			{
				response=new String(data, 0, size);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * Returns the current printing mode setting status.
	 */
	public String getPrintMode()
	{
		String response=null;
		try 
		{
			String cmd="! 0 0 0 0\r\nVARIABLE PRINT_MODE ?\r\nEND";
			byte[] data=cmd.getBytes();
			connection.writeData(data, 0, data.length);
			Thread.sleep(200L);
			
			data=new byte[1024];
			int size=connection.readData(data, 0, data.length);
			if(size>0)
			{
				response=new String(data, 0, size);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * Returns the current print pitch setting.
	 */
	public String getPitch()
	{
		String response=null;
		try 
		{
			String cmd="! 0 0 0 0\r\nVARIABLE PITCH ?\r\nEND";
			byte[] data=cmd.getBytes();
			connection.writeData(data, 0, data.length);
			Thread.sleep(200L);
			
			data=new byte[1024];
			int size=connection.readData(data, 0, data.length);
			if(size>0)
			{
				response=new String(data, 0, size);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * Returns the current paper feed type setting.
	 */
	public String getFeedType()
	{
		String response=null;
		try 
		{
			String cmd="! 0 0 0 0\r\nVARIABLE FEED_TYPE ?\r\nEND";
			byte[] data=cmd.getBytes();
			connection.writeData(data, 0, data.length);
			Thread.sleep(200L);
			
			data=new byte[1024];
			int size=connection.readData(data, 0, data.length);
			if(size>0)
			{
				response=new String(data, 0, size);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * Returns the current paper feed setting.
	 */
	public String getFeed()
	{
		String response=null;
		try 
		{
			String cmd="! 0 0 0 0\r\nVARIABLE FEED ?\r\nEND";
			byte[] data=cmd.getBytes();
			connection.writeData(data, 0, data.length);
			Thread.sleep(200L);
			
			data=new byte[1024];
			int size=connection.readData(data, 0, data.length);
			if(size>0)
			{
				response=new String(data, 0, size);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * Returns the current darkness setting.
	 */
	public String getDarkness()
	{
		String response=null;
		try 
		{
			String cmd="! 0 0 0 0\r\nVARIABLE DARKNESS ?\r\nEND";
			byte[] data=cmd.getBytes();
			connection.writeData(data, 0, data.length);
			Thread.sleep(200L);
			
			data=new byte[1024];
			int size=connection.readData(data, 0, data.length);
			if(size>0)
			{
				response=new String(data, 0, size);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return response;
	}
}
