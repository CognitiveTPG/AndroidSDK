package com.cognitive.printer.io;

public class Modifiers 
{
	StringBuffer buffer;
	
	public Modifiers()
	{
		buffer=new StringBuffer();
	}
	
	/**
	 * Adds a modulo 43 check digit to CODE39, or when used with S2OF5 or
	 * D2OF5, causes the intercharacter spacing to be equal to the width of
	 * the wide bar.
	 */
	public void setPlus()
	{
		buffer.append("+");
	}
	
	/**
	 * Prints the bar code without uncoded subtext (not used with CODE16K,
	 * since it never has uncoded subtext).
	 */
	public void setMinus()
	{
		buffer.append("-");
	}
	
	/**
	 * Increases the width ratio of wide to narrow bars (use only with
	 * CODE39).
	 */
	public void setW()
	{
		buffer.append("W");
	}
	
	/**
	 * Doubles the width of all bars and spaces in the bar code (use only
	 * with CODE39).
	 */
	public void setX()
	{
		buffer.append("X");
	}
	
	/**
	 * Specifications for the narrow (n) and wide (w) bars. Place these
	 * modifiers within parentheses. Allowable range is 1 to 9 for both n
	 * and w. For UPC, EAN, ADD2, ADD5, and CODE128 (A, B, and C), n
	 * specifies an integral multiplier for the bar code width. For all
	 * other codes, this option specifies the width in dots of the narrow
	 * and wide bars. The value of w must always be greater than n.
	 */
	public void setNarrowWide(int n,int w)
	{
		buffer.append("(").append(n).append(":").append(w).append(")");
	}
	
	public String getModifier()
	{
		return buffer.toString();
	}

}
