package com.cognitive.status;

import com.cognitive.util.Util;

public class PrinterStatus1 
{
	private boolean isOnline=false;
	private boolean isPaperLow=false;
	private boolean isCoverOpen=false;
	private boolean isBusy=false;
	private boolean isDrawerOpen=false;
	private boolean isError=false;
	
	/**
	 * Constructor
	 */
	public PrinterStatus1()
	{
		
	}
	/**
	 * To be filled.
	 * @param status
	 */
	public PrinterStatus1(final byte status)
	{
		isOnline=true;
		boolean[] bit=Util.getBits(status);
		isPaperLow=bit[0];
		isPaperLow=bit[1];
		isCoverOpen=bit[2];
		isBusy=bit[3];
		isDrawerOpen=bit[4];
		isError=bit[6];
	}
	/**
	 * Checks if printer is connected.
	 * @return True or false.
	 */
	public boolean isOnline()
	{
		return isOnline;
	}
	/**
	 * Checks if paper is Low.
	 * @return Either True or False.
	 */
	public boolean isPaperLow()
	{
		return isPaperLow;
	}
	
	/**
	 * Checks if cover is open.
	 * @return Either True or False.
	 */
	public boolean isCoverOpen()
	{
		return isCoverOpen;
	}
	/**
	 * Checks if Printer is Busy.
	 * @return Either True or False.
	 */
	public boolean isBusy()
	{
		return isBusy;
	}
	/**
	 * Check if Drawer is Open.
	 * @return Either True or False.
	 */
	public boolean isDrawerOpen()
	{
		return isDrawerOpen;
	}
	/**
	 * Check if there is any error.
	 * @return Either True or False.
	 */
	public boolean isError()
	{
		return isError;
	}

}
