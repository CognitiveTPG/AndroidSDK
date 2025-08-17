package com.cognitive.status;

import android.util.Log;

import com.cognitive.util.Util;

/**
 * This class check Status of the Printer.
 * @author Manoj
 *
 */
public class PrinterStatus 
{
	private boolean isOnline=false;
	
	private boolean isPaperLow=false;
	private boolean isCoverOpen=false;
	private boolean isPaperPresent=false;
	private boolean isKnifeInHome=false;
	private boolean isTemperatureOk=false;
	private boolean isVoltageOk=false;
	
	private String bitString;
	
	/**
	 * Constructor
	 */
	public PrinterStatus()
	{
		
	}
	
	/**
	 * To be filled.
	 * @param status
	 */
	public PrinterStatus(final byte status)
	{
		isOnline=true;
		boolean[] bit=Util.getBits(status);
		isPaperLow=bit[0];
		isCoverOpen=bit[1];
		isPaperPresent=!bit[2];
		isKnifeInHome=!bit[3];
		isTemperatureOk=!bit[5];
		isVoltageOk=!bit[6];
		
		int i=0;
		for(boolean b:bit)
		{
			Log.i(""+i++, b+"");
		}
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
	
	public boolean isPaperPresent()
	{
		return isPaperPresent; 
	}
	
	public boolean isKnifeHomePosition()
	{
		return isKnifeInHome;
	}
	
	public boolean isTemperatureOK()
	{
		return isTemperatureOk;
	}
	
	public boolean isVoltageOK()
	{
		return isVoltageOk;
	}
	
	public void setBitString(String bitString)
	{
		this.bitString=bitString;
	}
	
	public String getBitString()
	{
		return bitString;
	}
}
