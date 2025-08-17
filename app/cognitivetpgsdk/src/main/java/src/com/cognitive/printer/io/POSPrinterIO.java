package com.cognitive.printer.io;

import java.util.ArrayList;

import com.cognitive.util.Util;

/**
 * This class provides wrapper method for PoS printer to <br>
 * 1. Get different types parameter list<br>
 * 2. Wrapper method to print Text, Barcode, Images<br>
 * 3. Wrapper method for Printer Settings.
 * @author Srinivasan G
 *
 */
public class POSPrinterIO extends PrinterIO
{
	/**
	 * Provides values for Drawer.<br>
	 * Provided Drawer types are :- Drawer_1, Drawer_2
	 * @author Srinivasan G
	 */
	public enum Drawer {Drawer_1, Drawer_2};
	/**
	 * Provides values for Pitch.<br>
	 * Provided Pitch values are :- STANDARD and COMPRESSED.
	 * @author Srinivasan G
	 *
	 */
	public enum Pitch {STANDARD,COMPRESSED};
	/**
	 * Provides values for CodeType <br>
	 * Provided Code Types are :- Code_437,Code_850,Code_852,Code_860,Code_863,Code_865,Code_858,Code_866,Code_1252,
	 * Code_862,Code_737,Code_874,Code_857,Code_1251,Code_1255,Code_1048.
	 * @author Srinivasan G
	 */
	public enum CodeType {Code_437,Code_850,Code_852,Code_860,Code_863,Code_865,Code_858,Code_866,Code_1252,Code_862,Code_737,Code_874,Code_857,Code_1251,Code_1255,Code_1048};
	/**
	 * Provides values for HRI(Human Readable Interface)<br>
	 * Provided values are :-HRI_None, HRI_Above,HRI_Below,HRI_Both. 
	 * @author Srinivasan G
	 *
	 */
	public enum HRI {HRI_None, HRI_Above,HRI_Below,HRI_Both};
	/**
	 * Provides values for BarcodeWide <br>
	 * Provided values are :- Wide_2,Wide_3, Wide_4, Wide_5, Wide_6.
	 * @author Srinivasan G
	 *
	 */
	public enum BarcodeWide {Wide_2,Wide_3, Wide_4, Wide_5, Wide_6};
	/**
	 * Provides values for BarCodeType<br>
	 * Provided values are :- UPC_A,UPC_E,JAN13,JAN8,Code_39,CODABAR,PDF417,Code_93,Code_128,Code_EAN_128,IT.
	 * @author Srinivasan G
	 */
	public enum BarCodeType {UPC_A,UPC_E,JAN13,JAN8,Code_39,CODABAR,PDF417,Code_93,Code_128,Code_EAN_128,ITF};
	/**
	 * Provides CorrectionLevelOption<br>
	 * Provided options are :- Low, Middle, Q, High.
	 * @author Srinivasan G
	 */
	public enum CorrectionLevelOption {Low, Middle, Q, High};
	/**
	 * Provides values for Model<br>
	 * Provided values are :- Model1, Model2
	 * @author Srinivasan G
	 *
	 */
	public enum Model {Model1, Model2};
	/**
	 * Provides values for CutType<br>
	 * Provided values are FULL_CUT and PARTIAL_CUT.
	 * @author Srinivasan G
	 *
	 */
	public enum CutType {FULL_CUT, PARTIAL_CUT};
	/**
	 * Provides values for Alignment.<br>
	 * Provided values are : Left, Center and Right.
	 * @author Srinivasan G
	 *
	 */
	public enum Alignment {Left, Center, Right};
	/**
	 * Provides values for PrintMode.<br>
	 * Provided values are :-Normal, Double_Wide, Double_High and Quadruple.
	 * @author Srinivasan G
	 */
	public enum PrintMode {Normal,Double_Wide,Double_High,Quadruple};
	
	private ArrayList<Byte> buffer = new ArrayList<Byte>();
	
	/**
	 * This method clear the printer buffer.
	 */
	public void clearBuffer()
	{
		buffer.clear();
	}
	/**
	 * This method convert buffer into byte array.
	 * @return Returns byte array.
	 */
	public byte[] getByteBuffer()
	{
		return Util.convertFromListByteArrayTobyteArray(buffer);
	}
	
	/**
	 * Clears the print line buffer and resets the printer to the default settings.
	 *			    
	 */
	public void addInitializePrinter()
	{
		Byte[] cmd={0x1B,0x40};
		AddRange(buffer, cmd);
	}
	
	/**
	 * Clears the print line buffer without printing.
	 *	    
	 */
	public void addResetPrinter()
	{
		Byte[] cmd={0x10};
		AddRange(buffer, cmd);
	}
	
	/**
	 * Rotates characters 90 degrees counter-clockwise.
	 */
	public void addTextCounterClockwise()
	{
		Byte[] cmd={0x1B,0x12};
		AddRange(buffer, cmd);
	}
	
	/**
	 * Generates an audible tone. Performed by the printer to signal certain conditions.
	 *			    
	 */
	public void addTone()
	{
		Byte[] cmd={0x1B,0x07};
		AddRange(buffer, cmd);
	}
	
	/**
	 * This method causes the printer to cut the receipt.
	 * @param type - This should be either FULL_CUT or PARTIAL_CUT.
	 *			    
	 */
	public void addCut(CutType type)
	{
		Byte[] cmd=new Byte[1];
		switch (type) 
		{
		case FULL_CUT:
			cmd[0]=0x19;
			break;

		case PARTIAL_CUT:
			cmd[0]=0x1A;
			break;
		}
		AddRange(buffer, cmd);
	}
	
	/**
	 * Prints the current printer configuration settings on the receipt. 
	 * Disabled in page mode.
	 */
	public void addTestForm()
	{
		Byte[] cmd={0x1F,0x74};
		AddRange(buffer, cmd);
	}

	/**
	 * This function opens the cash drawer connected to the printer.
	 * @param type 
	 * 		This parameter should be Drawer_1 or Drawer_2. 
	 */
	public void addCashDrawer(Drawer type)
	{
		Byte[] cmd={0x1B,0x70,0x00,0x00,0x00};
		switch (type) 
		{
		case Drawer_1:
			cmd[2]=0x00;
			break;
		case Drawer_2:
			cmd[2]=0x01;
			break;
		}
		
		cmd[3]=0x00;
		cmd[4]=(byte) 0xFF;
		
		AddRange(buffer, cmd);
	}
	
	/**
	 * Prints one line from the buffer and feeds paper one line. 
	 */
	public void addFeedLine()
	{
		Byte[] cmd={0x0A};
		AddRange(buffer, cmd);
	}
	
	/**
	 * Prints one line from the buffer and feeds paper one line. 
	 *			    
	 */
	public void addCarriageReturn()
	{
		Byte[] cmd={0x0D};
		AddRange(buffer, cmd);
	}
	
	/**
	 * Feeds the paper n lines at the current line height without printing. Ignored on receipt if current line is not empty.
	 * @param line - Number of line to be feed. This range  should be from 0 to 255.
	 *			    
	 */
	public void addFeedLines(int line) throws Exception
	{
		if(0<=line && line<=255)
		{
			Byte[] cmd={0x14,0x00};
			cmd[1]=(byte) line;
			AddRange(buffer, cmd);
		}
		else
		{
			throw new Exception("Out Of Range(0-255): "+line);
		}
	}
	
	/**
	 * Feeds the paper n dot rows (n/8 mm, n/203 inch), without printing. Receipt moves n rows if the print buffer is empty.
	 * @param row - Range of value should be from 0 to 255.
	 *			    
	 */
	public void addDotRows(int row) throws Exception
	{
		if(0<=row && row<=255)
		{
			Byte[] cmd={0x15,0x00};
			cmd[1]=(byte) row;
			AddRange(buffer, cmd);
		}
		else
		{
			throw new Exception("Range Should Be Between (0-255): "+row);
		}
	}
	
	/**
	 * This function is used to specify the alignment of characters, graphics, logos, and bar codes on the receipt station.
	 * @param align - Alignment should be Left, Center, Right.
	 *			    
	 */
	public void addAlignment(Alignment align)
	{
		Byte[] cmd={0x1B,0x61,0x00};
		switch (align) 
		{
		case Left:
			cmd[2]=0x00;
			break;

		case Center:
			cmd[2]=0x01;
			break;
			
		case Right:
			cmd[2]=0x02;
			break;
		}
		AddRange(buffer, cmd);
	}
	
	/**
	 * Selects the character pitch for a print line.
	 * @param pitch
	 * 		This parameter is pitch value and it should be either STANDARD or COMPRESSED.
	 */
	public void addPitch(Pitch pitch)
	{
		Byte[] cmd={0x1B,0x16,0x00};
		switch (pitch) 
		{
		case STANDARD:
			cmd[2]=0;
			break;
		case COMPRESSED:
			cmd[2]=1;
			break;
		}
		AddRange(buffer, cmd);
	}
	
	/**
	 * Prints the first character of the next print line in column n. It must be
	 * sent for each line not printed at column one. The value of n is set to
	 * one after each line.
	 * 
	 * @param n - Value of n: 1 44 = Standard pitch
	 * 						  1 56 = Compressed pitch
	 */
	public void addColumn(int n)
	{
		Byte[] cmd={0x1B,0x14,(byte) n};
		AddRange(buffer, cmd);
	}
	
	/**
	 * This function is used for Pitch selection (standard, compressed, double high, or double wide)
	 * 
	 * @param pitch - It should be either Standard or Compressed
	 * @param emphasized - Specifies emphasized mode
	 * @param double_high - Specifies double_high mode
	 * @param double_wide - Specifies double_wide mode
	 * @param underline - Specifies underline mode
	 */
	public void addPrintMode(Pitch pitch,boolean emphasized,boolean double_high,boolean double_wide,boolean underline)
	{
		String binary=new String();
		
		if(underline)
			binary=binary+"1";
		else
			binary=binary+"0";
		
		binary=binary+"0";//Not Used
		
		if(double_wide)
			binary=binary+"1";
		else
			binary=binary+"0";
		
		if(double_high)
			binary=binary+"1";
		else
			binary=binary+"0";
		
		if(emphasized)
			binary=binary+"1";
		else
			binary=binary+"0";
		
		binary=binary+"0";//Not Used
		binary=binary+"0";//Not used
		
		switch (pitch) 
		{
		case STANDARD:
			binary=binary+"0";
			break;
		case COMPRESSED:
			binary=binary+"1";
			break;
		}
		
		String hex=Long.toHexString(Long.parseLong(binary,2));
		byte b=Byte.parseByte(hex, 16);
		Byte[] cmd={0x1B,0x21,b};
		AddRange(buffer, cmd);
		
		System.out.println("Pitch Selection: "+binary+" "+hex);
	}

	/**
	 * Add text to printer buffer to print.
	 * @param data Parameter is byte data.
	 */
	public void addTextData(byte[] data)
	{
		Byte[] tempList = new Byte[data.length];
		Util.CopyArray(data, tempList);
		AddRange(buffer, tempList);
	}
	
	/**
	 * This method turn Underline mode on or off.
	 * @param set Either true or false.
	 */
	public void addTextUnderline(boolean set)
	{
		Byte[] cmd={0x1B,0x2D,0x00};
		if(set)
		{
			cmd[2]=0x31;
		}
		else
		{
			cmd[2]=0x30;
		}
		AddRange(buffer, cmd);
	}
	
	/**
	 * This method turn Italic mode on or off.
	 * @param set Either true or false.
	 */
	public void addTextItalic(boolean set)
	{
		Byte[] cmd={0x1B,0x49,0x00};
		if(set)
		{
			cmd[2]=1;
		}
		else
		{
			cmd[2]=0;
		}
		AddRange(buffer, cmd);
	}
	
	/**
	 *  Rotates characters 90 degrees clockwise.
	 *  @param set  True or False
	 *  	
	 */
	public void addTextRotation(boolean set)
	{
		Byte[] cmd={0x1B,0x56,0x00};
		if(set)
		{
			cmd[2]=1;
		}
		else
		{
			cmd[2]=0;
		}
		AddRange(buffer, cmd);
	}
	
	/**
	 *  Turns on white/black reverse print mode. 
	 *  In white/black reverse print mode, print dots and non-print dots are reversed, which means that white characters are printed on a black background.
	 *  @param set Either true or false.
	 */
	public void addTextInvertColor(boolean set)
	{
		Byte[] cmd={0x1D,0x42,0x00};
		if(set)
		{
			cmd[2]=1;
		}
		else
		{
			cmd[2]=0;
		}
		AddRange(buffer, cmd);
	}
	
	/**
	 *  This method causes printer to enable/disable upside-down character printing.
	 *  @param set Either true or false.
	 */
	public void addTextUpsideDown(boolean set)
	{
		Byte[] cmd={0x1B,0x7B,0x00};
		if(set)
		{
			cmd[2]=1;
		}
		else
		{
			cmd[2]=0;
		}
		AddRange(buffer, cmd);
	}
	
	/**
	 *  Starts or stops emphasized printing.
	 *  @param set Either true or false.
	 */
	public void addTextEmphasized(boolean set)
	{
		Byte[] cmd={0x1B,0x45,0x00};
		if(set)
		{
			cmd[2]=1;
		}
		else
		{
			cmd[2]=0;
		}
		AddRange(buffer, cmd);
	}
	
	/**
	 * This function is used to select PDF417 Parameters.
	 * @param height -This limit should be from 1 to 10.
	 * @param width - This limit should be from 1 to 100.
	 * @param rows - This limit should be from 3 to 90.
	 * @param columns - This limit should be from 7 to 30.
	 * @param x_dim - This limit should be from 1 to 7.
	 * @param y_dim - This limit should be from 2 to 25.
	 */
	public void addPDF417Parameters(int height,int width,int rows,int columns,int x_dim,int y_dim) throws Exception
	{
		if(!(height>=1 && height<=10))
		{
			throw new Exception("Range Should Be Between (1-10): "+height);
		}
		if(!(width>=1 && width<=100))
		{
			throw new Exception("Range Should Be Between (1-100): "+width);
		}
		if(!(rows>=3 && rows<=90))
		{
			throw new Exception("Range Should Be Between (3-90): "+rows);
		}
		if(!(columns>=7 && columns<=30))
		{
			throw new Exception("Range Should Be Between (7-30): "+columns);
		}
		if(!(x_dim>=1 && x_dim<=7))
		{
			throw new Exception("Range Should Be Between (1-7): "+x_dim);
		}
		if(!(y_dim>=2 && y_dim<=25))
		{
			throw new Exception("Range Should Be Between (2-25): "+y_dim);
		}
		
		Byte[] cmd={0x1D,0x70,(byte) height,(byte) width,(byte) rows,(byte) columns,(byte) x_dim,(byte) y_dim};
		AddRange(buffer, cmd);
	}
	
	/**
	 * This method add Barcode to printer's buffer to print.
	 * @param height 
	 * 		Height of the Barcode. Range of Height should be from 1 to 255.
	 * @param width
	 * 		Width of the Barcode. Values should be Wide_2, Wide_3, Wide_4, Wide_5 and Wide_6.
	 * @param hri
	 * 		Place of HRI Characters. Values should be HRI_NONE, HRI_ABOVE, HRI_BELOW, HRI_BOTH.
	 * @param type
	 * 		Types of the Barcode. Values should be  UPC_A, UPC_E, JAN13, JAN8, Code_39, CODABAR, PDF417, Code_93, Code_128, Code_EAN_128 and ITF.
	 * @param data
	 * 		Data to be printed in Barcode.
	 * @throws Exception Throw custom exception Height out of Range.
	 */
	public void addBarcode(int height,BarcodeWide width,HRI hri,BarCodeType type,byte[] data) throws Exception
	{
		if(1<=height && height<=255)
		{
			Byte[] heightCommand={0x1D,0x68,(byte) height};
			AddRange(buffer, heightCommand);
			
			Byte[] widthCommand={0x1D,0x77,0x00};
			switch (width) 
			{
			case Wide_2:
				widthCommand[2]=0x02;
				break;

			case Wide_3:
				widthCommand[2]=0x03;
				break;
				
			case Wide_4:
				widthCommand[2]=0x04;
				break;
				
			case Wide_5:
				widthCommand[2]=0x05;
				break;
				
			case Wide_6:
				widthCommand[2]=0x06;
				break;
			}
			AddRange(buffer, widthCommand);
			
			Byte[] hriCommand={0x1D,0x48,0x00};
			switch (hri) 
			{
			case HRI_None:
				hriCommand[2]=0x00;
				break;

			case HRI_Above:
				hriCommand[2]=0x01;
				break;
				
			case HRI_Below:
				hriCommand[2]=0x02;
				break;
				
			case HRI_Both:
				hriCommand[2]=0x03;
				break;
			}
			AddRange(buffer, hriCommand);
			
			int m=0;
			switch (type) 
			{
				case UPC_A:
					m=65;
					break;
				case UPC_E:
					m=66;
					break;
				case JAN13:
					m=67;
					break;
				case JAN8:
					m=68;
					break;
				case Code_39:
					m=69;
					break;
				case ITF:
					m=70;
					break;
				case CODABAR:
					m=71;
					break;
				case Code_93:
					m=72;
					break;
				case Code_128:
					m=73;
					break;
				case PDF417:
					m=75;
					break;
				case Code_EAN_128:
					m=78;
					break;
			}
			Byte[] printCommand=new Byte[4+data.length];
			printCommand[0]=0x1D;
			printCommand[1]=0x6B;
			printCommand[2]=(byte) m;
			printCommand[3]=(byte) data.length;
			
			for(int index=0; index<data.length; index++)
			{
				printCommand[index + 4] = data[index];
			}
			AddRange(buffer, printCommand);
		}
		else
		{
			throw new Exception("Out Of Range(1-255): "+height);
		}
	}
	
	/**
	 * This function is used to print QR Code.
	 * 
	 * @param model 
	 * 		Specifies QR code model.
	 * @param size 
	 * 		Size of the QR code. The Size should be between 1 to 10.
	 * @param option 
	 * 		Specifies QR Code option.
	 * @param data 
	 * 		Specifies barcode data.
	 * @throws Exception
	 */
	public void addQRCode(Model model,int size,CorrectionLevelOption option,byte[] data) throws Exception
	{
		if(!(size>=1 && size<=10))
		{
			throw new Exception("Range Should Be Between (1-10): "+size);
		}
		
		Byte[] modelCommand={0x1D,0x28,0x6B,0x04,0x00,0x31,0x41,0x00,0x00};
		switch (model) 
		{
		case Model1:
			modelCommand[7]=0x31;
			break;

		case Model2:
			modelCommand[7]=0x32;
			break;
		}
		AddRange(buffer, modelCommand);
		
		Byte[] sizeCommand={0x1D,0x28,0x6B,0x03,0x00,0x31,0x43,(byte) size};
		AddRange(buffer, sizeCommand);
		
		Byte[] errorCommand={0x1D,0x28,0x6B,0x03,0x00,0x31,0x45,0x30};
		switch (option) 
		{
		case Low:
			errorCommand[7]=0x30;
			break;

		case Middle:
			errorCommand[7]=0x31;
			break;
			
		case Q:
			errorCommand[7]=0x32;
			break;
			
		case High:
			errorCommand[7]=0x33;
			break;
		}
		AddRange(buffer, errorCommand);
		
		Byte[] symbolCommand=new Byte[8+data.length];
		symbolCommand[0]=0x1D;
		symbolCommand[1]=0x28;
		symbolCommand[2]=0x6B;
		symbolCommand[3]=0x73;
		symbolCommand[4]=0x00;
		//symbolCommand[3]=(byte) (data.length % 256);
		//symbolCommand[4]=(byte) (data.length / 256);
		symbolCommand[5]=0x31;
		symbolCommand[6]=0x50;
		symbolCommand[7]=0x30;
		
		for(int i=0;i<data.length;i++)
		{
			symbolCommand[8+i]=data[i];
		}
		AddRange(buffer, symbolCommand);
	}
	
	public void printQRCode()
	{
		Byte[] printCommand={0x1D,0x28,0x6B,0x03,0x00,0x31,0x51,0x30};
		AddRange(buffer, printCommand);
	}
	
	/**
	 * This method causes printer to download Logo to printer's buffer to print.
	 * @param data 
	 * 		Represent image data in byte array form.
	 */
	public void addLogo(byte[] data)
	{
		Byte[] downloadLogo=new Byte[1+data.length];
		downloadLogo[0]=0x1B;
		
		for(int i=0;i<data.length;i++)
		{
			downloadLogo[1+i]=data[i];
		}
		
		AddRange(buffer, downloadLogo);
	}
	
	/**
	 * This function is used to print downloaded logo on receipt.
	 * @param mode 
	 * 		should be one of (Normal,Double-Wide,Double-High,Quadruple)
	 */
	public void printLogo(PrintMode mode)
	{
		Byte[] printLogo={0x1D,0x2F,0x00};
		switch (mode) 
		{
		case Normal:
			printLogo[2]=0x00;
			break;
		case Double_Wide:
			printLogo[2]=0x01;
			break;
		case Double_High:
			printLogo[2]=0x02;
			break;
		case Quadruple:
			printLogo[2]=0x03;
			break;
		}
		AddRange(buffer, printLogo);
	}
	
	/**
	 * Selects the character set to be used.
	 * @param type
	 * 		Character set to be used. Accepted values are  : Code_437, Code_850, Code_852, Code_860, Code_863, Code_865, Code_858, Code_866,
	 * 		Code_1252, Code_862, Code_737, Code_874, Code_857, Code_1251, Code_1255 and Code_1048.
	 */
	public void addCharactetSet(CodeType type)
	{
		Byte[] cmd={0x1B,0x74,0x00};
		switch (type) 
		{
		case Code_437:
			cmd[2]=0x00;
			break;
		case Code_850:
			cmd[2]=0x01;
			break;
		case Code_852:
			cmd[2]=0x02;
			break;
		case Code_860:
			cmd[2]=0x03;
			break;
		case Code_863:
			cmd[2]=0x04;
			break;
		case Code_865:
			cmd[2]=0x05;
			break;
		case Code_858:
			cmd[2]=0x06;
			break;
		case Code_866:
			cmd[2]=0x07;
			break;
		case Code_1252:
			cmd[2]=0x08;
			break;
		case Code_862:
			cmd[2]=0x09;
			break;
		case Code_737:
			cmd[2]=0x0A;
			break;
		case Code_874:
			cmd[2]=0x0B;
			break;
		case Code_857:
			cmd[2]=0x0C;
			break;
		case Code_1251:
			cmd[2]=0x0D;
			break;
		case Code_1255:
			cmd[2]=0x0E;
			break;
		case Code_1048:
			cmd[2]=0x0F;
			break;
		}
		AddRange(buffer, cmd);
	}
	
	/**
	 * 	This method helps to add command in the printer's  buffer.
	 * @param command 
	 * 		Byte command in hexadecimal  format.
	 */
	public void addCommand(Byte[] command)
	{
		AddRange(buffer, command);
	}
	
	/**
	 * 	This method helps to add data in the printer's  buffer.
	 * @param data 
	 * 		byte data in hexadecimal  format.
	 */
	public void addData(byte[] data)
	{
		Byte[] symbolCommand=new Byte[data.length];
		for(int i=0;i<data.length;i++)
		{
			symbolCommand[i]=data[i];
		}
		AddRange(buffer, symbolCommand);
	}
	
	/**
	 * Add Binary Data to POS Buffer
	 * @param binary - Binary Data
	 */
	public void addBinaryData(byte[] binary)
	{
		Byte[] bin=new Byte[binary.length];
		for(int i=0;i<binary.length;i++)
		{
			bin[i]=binary[i];
		}
		AddRange(buffer, bin);
	}
	
	private static void AddRange(ArrayList<Byte> array, Byte[] newData)
	{
		for(int index=0; index<newData.length; index++)
		{
			array.add(newData[index]);
		}
	}
	
}
