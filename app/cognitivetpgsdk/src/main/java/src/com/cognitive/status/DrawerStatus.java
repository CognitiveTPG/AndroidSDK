package com.cognitive.status;

import com.cognitive.util.Util;
/**
 * This class checks status of the Drawers.
 * @author Manoj
 */
public class DrawerStatus 
{
	private boolean isOnline=false;
	private boolean isDrawerOpen1=false;
	private boolean isDrawerOpen2=false;
	
	/**
	 * Constructor
	 */
	public DrawerStatus() 
	{
		
	}
	/**
	 * Check status of both the drawer, Drawer 1 and Drawer 2.
	 * @param status
	 */
	public DrawerStatus(final byte status)
	{
		isOnline=true;
		boolean[] bit=Util.getBits(status);
		isDrawerOpen1=!bit[0];
		isDrawerOpen2=!bit[1];
	}
	
	/**
	 * Checks if Drawer is connected.
	 * @return Either True or false.
	 */
	public boolean isOnline()
	{
		return isOnline;
	}
	/**
	 * Check if First Drawer is open.
	 * @return Either true or false.
	 */
	public boolean isDrawerOpen1()
	{
		return isDrawerOpen1;
	}
	
	/**
	 * Check if Second Drawer is open.
	 * @return Either true or false.
	 */
	public boolean isDrawerOpen2()
	{
		return isDrawerOpen2;
	}
}
