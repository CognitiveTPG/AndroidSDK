package com.cognitive.printer;

import android.util.Log;

import com.cognitive.printer.io.CommandSet;
import com.cognitive.printer.io.POSPrinterIO;
import com.cognitive.printer.io.PrinterIO;
import com.cognitive.status.DrawerStatus;
import com.cognitive.status.PrinterStatus;
/**
 * This class is used for A799 printer and is used to get all info about the printer like model number, serial number, firmware version etc
 * and send command of A799 Printer Model.  
 * @author Srinivasan G 
 *
 */

public class PoSPrinter extends Printer
{
	
	/**
	 * To be Filled.
	 */
	PoSPrinter() 
	{
		super(PrinterModel.A799,CommandSet.A799_F);
	}

	/**
	 * This command helps developer to get the Model number.
	 */
	public PrinterModel getModel()
	{
		return model;
	}
	
	/**
	 * This command returns all the command for A799 printers.
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
		POSPrinterIO pos=(POSPrinterIO) buffer;
		byte[] data=pos.getByteBuffer();
		connection.writeData(data, 0, data.length);
	}

	/**
	 * Transmits status of the printer in real time.
	 * @throws Exception 
	 */
//	public PrinterStatus1 getStatus()
//	{
//		PrinterStatus1 status1=new PrinterStatus1();
//		try 
//		{
//			byte[] statusCommand={0x1D,0x05};
//			connection.writeData(statusCommand, 0, statusCommand.length);
//			Thread.sleep(200L);
//			
//			byte[] data=new byte[1];
//			int size=connection.readData(data, 0, data.length);
//			if(size>0)
//			{
//				byte statusByte=data[0];
//				status1=new PrinterStatus1(statusByte);
//				
//				String str = String.format("%8s", Integer.toBinaryString(statusByte & 0xFF)).replace(' ', '0');
//				System.out.println("Printer (RT) Response: "+str);
//			}
//		} 
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//		}
//		return status1;
//	}
	
	/**
	 * Transmits status of the printer in real time.
	 * @throws Exception 
	 */
	public PrinterStatus getStatus()
	{
		PrinterStatus status=new PrinterStatus();
		try 
		{
			byte[] statusCommand={0x1B,0x76};
			connection.writeData(statusCommand, 0, statusCommand.length);
			Thread.sleep(200L);
			
			byte[] data=new byte[1];
			int size=connection.readData(data, 0, data.length);
			if(size>0)
			{
				byte statusByte=data[0];
				status=new PrinterStatus(statusByte);
				
				String str = String.format("%8s", Integer.toBinaryString(statusByte & 0xFF)).replace(' ', '0');
				status.setBitString(str);
				System.out.println("Printer (RT) Response: "+str);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return status;
	}
	
	/**
	 * Used to get logo definition.
	 * @return true - If logo is downloaded in memory
	 * 		   false -If logo is not found in memory.
	 */
	public boolean isLogoDownloaded()
	{
		boolean isLogoDownloaded=false;
		try 
		{
			byte[] logoCommand={0x1D,0x49,0x04};
			connection.writeData(logoCommand, 0, logoCommand.length);
			Thread.sleep(1000L);
			byte[] data=new byte[1];
			int size=connection.readData(data, 0, data.length);
			System.out.println("Logo Response: "+size);
			if(size>0)
			{
				byte statusByte=data[0];
				isLogoDownloaded=(statusByte & 1) != 0;
				
				String str = String.format("%8s", Integer.toBinaryString(statusByte & 0xFF)).replace(' ', '0');
				System.out.println("Logo Response: "+str);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return isLogoDownloaded;
	}
	
	/**
	 * This function is used to get the cash drawer status.
	 * 
	 */
	public DrawerStatus getDrawerStatus()
	{
		DrawerStatus status=new DrawerStatus();
		try 
		{
			byte[] drawerCommand={0x1B,0x75,0x00};
			connection.writeData(drawerCommand, 0, drawerCommand.length);
			Thread.sleep(1000L);
			byte[] data=new byte[1];
			int size=connection.readData(data, 0, data.length);
			System.out.println("Drawer Response: "+size);
			if(size>0)
			{
				byte statusByte=data[0];
				status=new DrawerStatus(statusByte);
				
				String str = String.format("%8s", Integer.toBinaryString(statusByte & 0xFF)).replace(' ', '0');
				System.out.println("Drawer Response: "+str);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return status;
	}
	
	/**
	 * 
	 * This command erases all 64K flash memory sectors allocated to
	 * user-defined character and logos storage. Those sectors should be erased
	 * in two situations: when the logo definition area is full and an
	 * application is attempting to define new logos, and when an application
	 * wants to replace one user-defined character set with another. In both
	 * cases, all logos and character set definitions are erased and must be
	 * redefined.
	 * 
	 * It should wait a minimum of ten seconds after sending the erase user flash sector.
	 * 
	 * @param type - Memory Type should be either ALL or USER_STORAGE
	 * @return - True or False
	 */
	public boolean eraseMemory(MemoryType type)
	{
		boolean isSuccess=false;
		try 
		{
			byte[] cmd={0x1D,0x40,0x00};
			switch (type) 
			{
			case ALL:
				cmd[2]=0x31;
				break;
			case USER_STORAGE:
				cmd[2]=0x32;
				break;
			}
			
			connection.writeData(cmd, 0, cmd.length);
			Thread.sleep(5000L);
			byte[] data=new byte[1];
			int size=connection.readData(data, 0, data.length);
			System.out.println("Erase Memory Response: "+size);
			if(size>0)
			{
				isSuccess=true;
				byte statusByte=data[0];
				
				String str = String.format("%8s", Integer.toBinaryString(statusByte & 0xFF)).replace(' ', '0');
				System.out.println("Erase Memory Response: "+str);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			isSuccess=false;
		}
		return isSuccess;
	}
	/**
	 * This method helps developer to get Printer Serial Number.
	 * @return Serial number of the printer in String data type.
	 */
	public String getSerialNumber()
	{
		String response=null;
		try 
		{
			byte[] cmd={0x1D,0x49,0x40,0x23};
			connection.writeData(cmd, 0, cmd.length);
			Thread.sleep(1000L);
			
			byte[] data=new byte[1024];
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
	 * This method helps developer to get Printer Model.
	 * @return Returns model of the printer.
	 */
	public String getPrinterModel()
	{
		String response=null;
		try 
		{
			byte[] cmd={0x1D,0x49,0x40,0x27};
			connection.writeData(cmd, 0, cmd.length);
			Thread.sleep(1000L);
			
			byte[] data=new byte[1024];
			int size=connection.readData(data, 0, data.length);
			if(size>0)
			{
				response=new String(data, 1, size);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return response;
	}
	/**
	 * This method helps developer to get Firmware version of the printer.
	 * @return Returns Firmware version of the printer.
	 */
	public String getBootFirmwareVersion()
	{
		String response=null;
		try 
		{
			byte[] cmd={0x1D,0x49,0x40,(byte) 0x97};
			connection.writeData(cmd, 0, cmd.length);
			Thread.sleep(1000L);
			
			byte[] data=new byte[1024];
			int size=connection.readData(data, 0, data.length);
			Log.i("Data Boot Firmware Version :", ""+data);
			if(size>0)
			{
				response=new String(data, 1, size);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return response;
	}
	/**
	 * This method helps developer to get Flash Firmware version of the printer.
	 * @return Returns Flash Firmware version of the printer.
	 */
	public String getFlashFirmwareVersion()
	{
		String response=null;
		try 
		{
			byte[] cmd={0x1D,0x49,0x40,(byte) 0xA3};
			connection.writeData(cmd, 0, cmd.length);
			Thread.sleep(1000L);
			
			byte[] data=new byte[1024];
			int size=connection.readData(data, 0, data.length);
			Log.i("Data getFlashFirmwareVersion", ""+data);
			if(size>0)
			{
				response=new String(data, 1, size);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return response;
	}
	/**
	 * This method helps developer to get Software version of the printer.
	 * @return Returns Software version of the printer.
	 */
	public String getSoftwareVersion()
	{
		String response=null;
		try 
		{
			byte[] cmd={0x1F,0x56};
			connection.writeData(cmd, 0, cmd.length);
			Thread.sleep(1000L);
			
			byte[] data=new byte[1024];
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
