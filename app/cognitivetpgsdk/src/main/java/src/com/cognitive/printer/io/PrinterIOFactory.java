package com.cognitive.printer.io;

/**
 * This class puts the command in buffer according to the printer.
 * @author Manoj
 *
 */
public class PrinterIOFactory 
{
	/**
	 * This method helps developer to put commands in buffer according to the printer type.
	 * @param command 
	 * 		CommandSet type. Values should be A799_F and DLXi_F.
	 * @return
	 * 		Buffer stored either PoS and Label printer's command. 
	 */
	public static PrinterIO getCommandBuffer(CommandSet command)
	{
		PrinterIO buffer=null;
		if(CommandSet.A799_F==command)
		{
			buffer=new POSPrinterIO();
		}
		else if(CommandSet.DLXi_F==command)
		{
			buffer=new LabelPrinterIO();
		}
		return buffer;
	}
}
