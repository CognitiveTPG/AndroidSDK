package com.cognitive.status;
/**
 * This class sets status message of the printer.
 * @author Srinivasan G.
 *
 */
public class StatusMessage 
{
	private String Code="";
	private String Message="";
	/**
	 * Initialize the Code.
	 * @param code
	 * 		Code return by the printer.
	 */
	public StatusMessage(String code) 
	{
		this.Code=code;
	}
	/**
	 * Returns code.
	 * @return 
	 * 		Status code of the printer.
	 */
	public String getCode()
	{
		return Code;
	}
	/**
	 * Returns message according to the Code.
	 * @return 
	 * 		Message according to the code which are - <br>
	 * 		B - Background image is present<br>
	 * 		C - Charging <br>
	 * 		R - Ready <br>
	 * 		C - Cutter Error<br>
	 * 		D - Download Error<br>
	 * 		E - EEPROM Error<br>
	 * 		H - Hot PrintHead <br>
	 * 		I - Nonvolatile memory failure <br>
	 * 		L - Low Battery<br>
	 * 		O - Out of Paper<br>
	 * 		o - Out of ribbon<br>
	 * 		P - Printing in process<br>
	 * 		S - Serial port communications error<br>
	 * 		U - Printhead up<br>
	 * 		W - Waiting<br>
	 * 	
	 */
	public String getStatusMessage()
	{
		String message="";
		if(Code.startsWith("B"))
		{
			message="Background image is present";
		}
		if(Code.startsWith("C"))
		{
			message="Charging";
		}
		if(Code.startsWith("R"))
		{
			message="Ready";
		}
		
		if(Code.startsWith("c"))
		{
			message="Cutter error";
		}
		if(Code.startsWith("D"))
		{
			message="Download error";
		}
		if(Code.startsWith("E"))
		{
			message="EEPROM error";
		}
		if(Code.startsWith("H"))
		{
			message="Hot printhead";
		}
		if(Code.startsWith("I"))
		{
			message="Nonvolatile memory failure";
		}
		if(Code.startsWith("L"))
		{
			message="Low battery";
		}
		if(Code.startsWith("O"))
		{
			message="Out of paper";
		}
		if(Code.startsWith("o"))
		{
			message="Out of ribbon";
		}
		if(Code.startsWith("P"))
		{
			message="Printing in process";
		}
		if(Code.startsWith("S"))
		{
			message="Serial port communications error";
		}
		if(Code.startsWith("U"))
		{
			message="Printhead Up";
		}
		if(Code.startsWith("W"))
		{
			message="Waiting";
		}
		return message;
	}
}
