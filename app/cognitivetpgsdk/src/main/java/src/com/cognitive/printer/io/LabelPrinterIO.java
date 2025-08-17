package com.cognitive.printer.io;

import java.util.ArrayList;
import com.cognitive.util.Util;

/**
 * This class specify following feature for the Label Printer:-<br>
 * 1. Specify all the parameters list<br>
 * 2. Provides methods to print Images, Text, Barcode.<br>
 * 3. Provides methods for printer settings.<br>
 * @author Manoj
 *
 */
public class LabelPrinterIO extends PrinterIO
{
	
	/**
	 * This enumeration define Barcode types.<br>
	 * Types are :- UPCA, UPCE, UPCE1, UPCA_PLUs, EAN8,EAN13
	 * @author Manoj
	 *
	 */
	public enum BarcodeType {UPCA,UPCE,UPCE1,UPCA_PLUS,EAN8,EAN13,EAN8_PLUS,EAN13_PLUS,EAN128,ADD2,ADD5,CODE39,I2OF5,S2OF5,D2OF5,CODE128A,MSI,MSI1,CODE93,POSTNET,CODE128B,CODE128C,CODABAR,PLESSEY};
	/**
	 * This enumeration define Mode of the printer.<br>
	 * Modes are :- ASCII, GRAPHICS, ASCII_BACKGROUND, GRAPHICS_BACKGROUND, CLEAR_BACKGROUND, REUSE,AUTOMATIC
	 * @author Manoj
	 *
	 */
	public enum Mode {ASCII,GRAPHICS,ASCII_BACKGROUND,GRAPHICS_BACKGROUND,CLEAR_BACKGROUND,REUSE,AUTOMATIC};
	/**
	 * This enumeration define FontID for different sizes.<br>
	 * FontID are : - Size_6, Size_8, Size_10, Size_16, Size_24, Size_36, Size_48
	 * @author Manoj
	 *
	 */
	public enum FontID {Size_6,Size_8,Size_10,Size_16,Size_24,Size_36,Size_48};
	/**
	 * This enumeration defines Alignment.<br>
	 * Types of Alignment are : Left, Center, Right.
	 * @author Manoj
	 *
	 */
	public enum Alignment {Left,Center,Right};
	/**
	 * This enumeration Defines Graphics type.<br>
	 * Graphics Types are :- PCX, BMP
	 * @author Manoj
	 *
	 */
	public enum GraphicType {PCX,BMP};
	/**
	 * This enumeration defines Pitch value for the printers.<br>
	 * Pitch types are : - DPI_75, DPI_100, DPI_150, DPI_200, DPI_203, DPI_300
	 * @author Manoj
	 *
	 */
	public enum Pitch {DPI_75,DPI_100,DPI_150,DPI_200,DPI_203,DPI_300};
	/**
	 * This enumeration defines different font sizes.<br>
	 * Font size are :- SIZE_3X7, SIZE_5X7, SIZE_8X8, SIZE_9X12, SIZE_12X16, SIZE_18X23, SIZE_24X31
	 * @author Manoj
	 *
	 */
	public enum Font {SIZE_3X7,SIZE_5X7,SIZE_8X8,SIZE_9X12,SIZE_12X16,SIZE_18X23,SIZE_24X31};
	/**
	 * This enumeration defines speed of the Printers.<br>
	 * Values are : - NORMAL, LOWSPEED, HIGHSPEED
	 * @author Manoj
	 *
	 */
	public enum Speed {NORMAL,LOWSPEED,HIGHSPEED};
	/**
	 * This enumeration defines FeedType of the printer.<br>
	 * FeedType are :- GAP, BAR, NOTCH
	 * @author Manoj
	 *
	 */
	public enum FeedType {GAP,BAR,NOTCH};
	/**
	 * This enumeration defines IndexMode of the printer.<br>
	 * IndexMode are :- MODE_0, MODE_1, MODE_2, MODE_3, CALIBRATE
	 * @author Manoj
	 */
	public enum IndexMode {MODE_0,MODE_1,MODE_2,MODE_3,CALIBRATE};
	/**
	 * This enumeration defines ScaleType of the printer.<br>
	 * ScaleType are :- SCALE_0, SCALE_1, SCALE_2
	 * @author Manoj
	 */
	public enum ScaleType {SCALE_0,SCALE_1,SCALE_2};
	/**
	 * This enumeration defines PrintMode of the printer.<br>
	 * PrintMode are :- DT, TT, AUTO
	 * @author Manoj
	 */
	
	public enum PrintMode {DT,TT,AUTO};
	
	/**
	 * This enumeration defines Rotation of the printed text.<br>
	 * Rotaion values are :- R0, R90, R180, R270
	 * @author Manoj
	 */
	public enum Rotation {R0,R90,R180,R270};
	public enum FontType {A,B,C};
	
	private ArrayList<Byte> buffer = new ArrayList<Byte>();
	
	/**
	 * This method buffer to byte array since in Label printer we are passing command as byte data only.
	 * @return Returns byte data.
	 */
	public byte[] getByteBuffer()
	{
		return Util.convertFromListByteArrayTobyteArray(buffer);
	}
	
	private static void AddRange(ArrayList<Byte> array, byte[] newData)
	{
		for(int index=0; index<newData.length; index++)
		{
			array.add(newData[index]);
		}
	}
	
	/**
	 * This method clears the buffer.
	 */
	public void clearBuffer()
	{
		buffer.clear();
	}
	
	/**
	 * Initializes the printer to receive a label format.
	 * 
	 * @param mode
	 *            - Encoding Mode.
	 * @param x
	 *            - X starting position for label.
	 * @param dottime
	 *            - Determines how long the printhead dots stay hot, thereby
	 *            changing the dot length. Values can range from 0 to 255, but
	 *            values less than 30 are treated as 30.
	 * @param maxY
	 *            - Specifies how many dot rows are memory-mapped for each label
	 *            max Y must be large enough to map all label components, but
	 *            not so large that it causes memory overflow or label skipping.
	 * @param numlbls
	 *            - Sets the quantity of labels to be printed by the label
	 *            format.
	 */
	public void addHeader(Mode mode,int x,int dottime,int maxY,int numlbls)
	{
		String header="";
		switch (mode) 
		{
		case ASCII:
			header="!";
			break;
		case GRAPHICS:
			header="@";
			break;
		case ASCII_BACKGROUND:
			header="!#";
			break;
		case GRAPHICS_BACKGROUND:
			header="#";
			break;
		case CLEAR_BACKGROUND:
			header="!*";
			break;
		case REUSE:
			header="!+";
			break;
		case AUTOMATIC:
			header="!A";
			break;
		}
		
		String str=header+" "+x+" "+dottime+" "+maxY+" "+numlbls+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Prints a bar code, specifying type, position, height, and characters to
	 * be coded.
	 * 
	 * @param type
	 *            - Type of Barcode
	 * @param modifier
	 *            - Optional Parameter
	 * @param x
	 *            - X Position of Barcode
	 * @param y
	 *            - Y Position of Barcode
	 * @param height
	 *            - Height of Barcode
	 * @param chars
	 *            - Barcode Data
	 */
	public void addBarcode(BarcodeType type,Modifiers modifier,int x,int y,int height,String chars)
	{
		String barcode="";
		switch (type) 
		{
		case UPCA:
			barcode="UPCA";
			break;
		case UPCE:
			barcode="UPCE";
			break;
		case UPCE1:
			barcode="UPCE1";
			break;
		case UPCA_PLUS:
			barcode="UPCA+";
			break;
		case EAN8:
			barcode="EAN8";
			break;
		case EAN13:
			barcode="EAN13";
			break;
		case EAN8_PLUS:
			barcode="EAN8+";
			break;
		case EAN13_PLUS:
			barcode="EAN13+";
			break;
		case EAN128:
			barcode="EAN128";
			break;
		case ADD2:
			barcode="ADD2";
			break;
		case ADD5:
			barcode="ADD5";
			break;
		case CODE39:
			barcode="CODE39";
			break;
		case I2OF5:
			barcode="I2OF5";
			break;
		case S2OF5:
			barcode="S2OF5";
			break;
		case D2OF5:
			barcode="D2OF5";
			break;
		case CODE128A:
			barcode="CODE128A";
			break;
		case MSI:
			barcode="MSI";
			break;
		case MSI1:
			barcode="MSI1";
			break;
		case CODE93:
			barcode="CODE93";
			break;
		case POSTNET:
			barcode="POSTNET";
			break;
		case CODE128B:
			barcode="CODE128B";
			break;
		case CODE128C:
			barcode="CODE128C";
			break;
		case CODABAR:
			barcode="CODABAR";
			break;
		case PLESSEY:
			barcode="PLESSEY";
			break;
			
		default:
			break;
		}
		
		String str="";
		if(modifier!=null)
		{
			str="BARCODE "+barcode+modifier.getModifier()+" "+x+" "+y+" "+height+" "+chars+"\r\n";
		}
		else
		{
			str="BARCODE "+barcode+" "+x+" "+y+" "+height+" "+chars+"\r\n";
		}
		
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Prints a 2-D matrix symbology consisting of an array of nominally square
	 * modules arranged in an overall square pattern using the QR symbology.
	 * 
	 * @param x
	 *            - starting position for bar code block.
	 * @param y
	 *            - starting position for bar code block.
	 * @param cellsize
	 *            - Sets the number of pixels which make a module (square) in
	 *            the barcode.
	 * @param model
	 *            - Specifies the original version (m = 1), or the enhanced form
	 *            of the symbology (m = 2). model 2 is the recommended model.
	 * @param data
	 *            - Data to be encoded.
	 */
	public void addQRCode(int x,int y,int cellsize,int model,String data)
	{
		String str="BARCODE QR "+x+" "+y+" "+cellsize+" M="+model+" A~"+"\r\n"
					+"~QA,"+data+"~"+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Causes the printer to beep for the specified duration.
	 * @param seconds - The number of seconds to beep. The range is 1 to 255 seconds.
	 */
	public void addBeep(int seconds)
	{
		String str="BEEP "+seconds+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Prints text on a label using compressed bitmapped fonts.
	 * 
	 * @param font - Specifies the font family and size.
	 * @param x - X position of the character
	 * @param y - Y position of the character
	 * @param text - ASCII text to be printed.
	 */
	public void addText(FontID font,int x,int y,String text)
	{
		String id="";
		switch (font) 
		{
		case Size_6:
			id="0";
			break;
		case Size_8:
			id="1";
			break;
		case Size_10:
			id="2";
			break;
		case Size_16:
			id="3";
			break;
		case Size_24:
			id="4";
			break;
		case Size_36:
			id="5";
			break;
		case Size_48:
			id="6";
			break;
		}
		
		String str="TEXT "+id+" "+x+" "+y+" "+text+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Prints text on a label using compressed bitmapped fonts.
	 * 
	 * @param font
	 *            - Specifies the font family and size.
	 * @param spacing
	 *            - Sets the spacing between characters. Valid entries are 0 to
	 *            255
	 * @param rotation
	 *            - Valid entries are 0, 90, 180, and 270
	 * @param xmult
	 *            - Expands the font width. Valid entries are 0 to 4
	 * @param ymult
	 *            - Expands the font height. Valid entries are 0 to 4
	 * @param x
	 *            - X position of the character string
	 * @param y
	 *            - Y position of the character string
	 * @param characters
	 *            - ASCII text to be printed.
	 */
	public void addText(FontID font,int spacing,Rotation rotation,int xmult,int ymult,int x,int y,String characters)
	{
		String id="";
		switch (font) 
		{
		case Size_6:
			id="0";
			break;
		case Size_8:
			id="1";
			break;
		case Size_10:
			id="2";
			break;
		case Size_16:
			id="3";
			break;
		case Size_24:
			id="4";
			break;
		case Size_36:
			id="5";
			break;
		case Size_48:
			id="6";
			break;
		}
		
		String r="0";
		switch (rotation) 
		{
		case R0:
			r="0";
			break;
		case R90:
			r="90";
			break;
		case R180:
			r="180";
			break;
		case R270:
			r="270";
			break;
		}
		
		String str="TEXT "+id+" ("+spacing+","+r+","+xmult+","+ymult+") "+x+" "+y+" "+characters+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Prints a two-dimensional bar code on a label, using the PDF417 symbology.
	 * 
	 * @param x
	 *            - X starting position for bar code block.
	 * @param y
	 *            - Y starting position for bar code block.
	 * @param width
	 *            - Width (x dimension) of the narrowest element (bar or space)
	 *            in the bar code.
	 * @param height
	 *            - Height (y dimension) of the shortest element (bar or space)
	 *            in the bar code.
	 * @param errorlevel
	 *            - Error correction level; 0 through 8.
	 * @param rows
	 *            - Number of bar code rows, from 3 to 90. Entering 0 will cause
	 *            the printer to calculate the number of rows.
	 * @param columns
	 *            - Number of bar code columns, from 1 to 30. Entering 0 will
	 *            cause the printer to calculate the number of columns.
	 * @param byteSize
	 *            - Number of encoded data bytes, including carriage returns and
	 *            line feeds. Macro PDF functions are invoked if this value
	 *            exceeds 3072
	 * @param data
	 *            - Data to be encoded.
	 */
	public void addPDF417(int x,int y,int width,int height,int errorlevel,int rows,int columns,int byteSize,String data)
	{
		String str="BARCODE PDF417 "+x+" "+y+" "+width+":"+height+" "+errorlevel+" "+rows+" "+columns+" "+byteSize+"\n"+data+"\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	
	/**
	 * Prints text on a label, using Ultra Fonts (stroke fonts).
	 * 
	 * @param font
	 *            - Font type, A, B, or C. Type A characters have rounded
	 *            corners.Type B characters have angled corners. Type C
	 *            resembles Helvetica print.
	 * @param size
	 *            - Font size, in dots. This immediately follows the font type,
	 *            and may specify both X and Y dimensions (A25X50, for example),
	 *            or just the Y dimension, in which case X is approximately one-
	 *            half Y (as in A50). For font types A andB, X and Y can range
	 *            from 1 to 65535. Font C size can range from 40 to 700.
	 * @param x
	 *            - X Starting position of the printed character string.
	 * @param y
	 *            - Y Starting position of the printed character string.
	 * @param characters
	 *            - ASCII characters to be printed.
	 */
	public void addUltraFont(FontType font,int size,int x,int y,String characters)
	{
		String type="";
		switch (font) 
		{
		case A:
			type="A";
			break;
		case B:
			type="B";
			break;
		case C:
			type="C";
			break;
		}
		
		String str="ULTRA_FONT "+type+size+" "+x+" "+y+" "+characters+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	public void addUltraFont(FontType font,int size,int bold,int spacing,Rotation rotation,int x,int y,String characters)
	{
		String type="";
		switch (font) 
		{
		case A:
			type="A";
			break;
		case B:
			type="B";
			break;
		case C:
			type="C";
			break;
		}
		
		String r="0";
		switch (rotation) 
		{
		case R0:
			r="0";
			break;
		case R90:
			r="90";
			break;
		case R180:
			r="180";
			break;
		case R270:
			r="270";
			break;
		}
		
		String str="ULTRA_FONT "+type+size+" ("+bold+","+spacing+","+r+") "+x+" "+y+" "+characters+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * This method specify alignment of the label.
	 * @param alignment - This should be LEFT or CENTER or RIGHT.
	 */
	public void addJustify(Alignment alignment)
	{
		String value="";
		switch (alignment) 
		{
		case Left:
			value="LEFT";
			break;
		case Center:
			value="CENTER";
			break;
		case Right:
			value="RIGHT";
			break;
		}
		
		String str="JUSTIFY "+value+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Sets the print density in dots per inch. 
	 * @param pitch - pitch in dots per inch
	 */
	public void addPitch(Pitch pitch)
	{
		int value=0;
		switch (pitch) 
		{
		case DPI_75:
			value=75;
			break;
		case DPI_100:
			value=100;
			break;
		case DPI_150:
			value=150;
			break;
		case DPI_200:
			value=200;
			break;
		case DPI_203:
			value=203;
			break;
		case DPI_300:
			value=300;
			break;
		}
		
		String str="PITCH "+value+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * This function is used for documenting label formats.
	 * @param characters - This parameter is comment.
	 */
	public void addComments(String characters)
	{
		String str="COMMENT "+characters+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Draws a hollow rectangle on the label.
	 * 
	 * @param x - X coordinate of upper left corner of box.
	 * @param y - Y coordinate of upper left corner of box.
	 * @param width - Box width, measured in dot columns. Must be greater than zero.
	 * @param height - Box height, measured in dot rows. Must be greater than zero.
	 * @param tickness - specifies line thickness in dots.
	 */
	public void addDrawBox(int x,int y,int width,int height,int tickness)
	{
		String str="DRAW_BOX "+x+" "+y+" "+width+" "+height+" "+tickness+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * This function inverts every dot in the specified rectangular area. Where
	 * the existing field is white, the FILL_BOX command fills it in black,
	 * while areas already black are flipped to white.
	 * 
	 * @param x
	 *            - X coordinate of upper left corner of box.
	 * @param y
	 *            - Y coordinate of upper left corner of box.
	 * @param width
	 *            - Box width, measured in dot columns. Must be greater than
	 *            zero.
	 * @param height
	 *            - Box height, measured in dot rows. Must be greater than zero.
	 */
	public void addFillBox(int x,int y,int width,int height)
	{
		String str="FILL_BOX "+x+" "+y+" "+width+" "+height+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Sends graphics data to printer.
	 * 
	 * NOTE: GRAPHIC must be the last command in its label format. Do not follow
	 * it with an END command. The printer waits for graphics data and a
	 * following printable label file after receiving this command.
	 * 
	 * @param type
	 *            - Graphic file type. Allowable types are PCX and BMP.
	 * @param x
	 *            - X position
	 * @param y
	 *            - Y position
	 * @param data
	 *            - Image binary data
	 */
	public void addGraphic(GraphicType type,int x,int y,byte[] data)
	{
		String gr_type="";
		switch (type) 
		{
		case BMP:
			gr_type="BMP";
			break;
		case PCX:
			gr_type="PCX";
			break;
		}
		
		String str="GRAPHIC "+gr_type+" "+x+" "+y+"\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
		
		AddRange(buffer, data);
		AddRange(buffer, "\n".getBytes());
	}
	
	/**
	 * Pauses the printer after it prints one label.
	 */
	public void addHalt()
	{
		String str="HALT "+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Enables automatic label indexing.
	 */
	public void addIndex()
	{
		String str="INDEX "+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Disables index detection. The printer will stop feeding the label after
	 * printing the last dot row.
	 */
	public void addNoIndex()
	{
		String str="NOINDEX "+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Sets the quantity of labels to be printed by the label format.
	 * 
	 * @param numlabels
	 *            - The number of labels printed by the label format.
	 */
	public void addQuantity(int numlabels)
	{
		String str="QUANTITY "+numlabels+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Causes the printer to print a test label that displays most of the
	 * current configuration settings.
	 */
	public void addPrintTestLabel()
	{
		String str="!PRINT TESTLABEL"+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Causes the printer to print duplicate labels side-by- side.
	 * 
	 * @param n
	 *            - n is the number of duplicate labels to print side-by- side.
	 *            The acceptable range is 2 to 9.
	 */
	public void addMultiple(int n)
	{
		String str="MULTIPLE "+n+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Sets the width of the printed label. Typically, this command is for
	 * printing on label stock that is narrower than the printhead.
	 * 
	 * @param n
	 *            - Print width in 100th of an inch.
	 */
	public void addWidth(int n)
	{
		String str="WIDTH "+n+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Prints text (ASCII characters) on a label using CSI fonts.
	 * 
	 * @param type
	 *            - Specifies the basic font size, in dots.
	 * @param x
	 *            - Horizontal position for the upper left corner of the first
	 *            character in a string.
	 * @param y
	 *            - Vertical position for the upper left corner of the first
	 *            character in a string.
	 * @param characters
	 *            - ASCII string to be printed.
	 */
	public void addString(Font type,int x,int y,String characters)
	{
		String size="";
		
		switch (type) 
		{
		case SIZE_3X7:
			size="3X7";
			break;
		case SIZE_5X7:
			size="5X7";
			break;
		case SIZE_8X8:
			size="8X8";
			break;
		case SIZE_9X12:
			size="9X12";
			break;
		case SIZE_12X16:
			size="12X16";
			break;
		case SIZE_18X23:
			size="18X23";
			break;
		case SIZE_24X31:
			size="24X31";
			break;
		}
		
		String str="STRING "+size+" "+x+" "+y+" "+characters+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Prints text (ASCII characters) on a label using CSI fonts. These are
	 * non-proportional, non-compressed bitmapped fonts.
	 * 
	 * @param type
	 *            - Specifies the basic font size, in dots.
	 * @param eximage
	 *            - Produces bolder forms of all character sets by printing the
	 *            character the number of times specified, offset by one dot
	 *            column.The allowable range is 1 to 9
	 * @param exspace
	 *            - Modifies the spacing between characters.The allowable range
	 *            is 1 to 9.
	 * @param xmult
	 *            - Independently expands the width of any font. The allowable
	 *            range is 0 to 9.
	 * @param ymult
	 *            - Independently expands the height of any font. Range 0 to 9
	 * @param x
	 *            - Horizontal starting position for the upper left corner of
	 *            the first character in a string.
	 * @param y
	 *            - Vertical starting position for the upper left corner of the
	 *            first character in a string.
	 * @param characters
	 *            - ASCII string to be printed.
	 */
	public void addString(Font type,int eximage,int exspace,int xmult,int ymult,int x,int y,String characters)
	{
		String size="";
		
		switch (type) 
		{
		case SIZE_3X7:
			size="3X7";
			break;
		case SIZE_5X7:
			size="5X7";
			break;
		case SIZE_8X8:
			size="8X8";
			break;
		case SIZE_9X12:
			size="9X12";
			break;
		case SIZE_12X16:
			size="12X16";
			break;
		case SIZE_18X23:
			size="18X23";
			break;
		case SIZE_24X31:
			size="24X31";
			break;
		}
		
		String str="STRING "+size+"("+eximage+","+exspace+","+xmult+","+ymult+") "+x+" "+y+" "+characters+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Prints text rotated 90 degrees clockwise from horizontal.
	 * 
	 * @param type
	 *            - Specifies the basic font size, in dots.
	 * @param x
	 *            - Horizontal position for the upper left corner of the first
	 *            character in a string.
	 * @param y
	 *            - Vertical position for the upper left corner of the first
	 *            character in a string.
	 * @param characters
	 *            - ASCII string to be printed.
	 */
	public void addR90(Font type,int x,int y,String characters)
	{
			String size="";
			
			switch (type) 
			{
			case SIZE_3X7:
				size="3X7";
				break;
			case SIZE_5X7:
				size="5X7";
				break;
			case SIZE_8X8:
				size="8X8";
				break;
			case SIZE_9X12:
				size="9X12";
				break;
			case SIZE_12X16:
				size="12X16";
				break;
			case SIZE_18X23:
				size="18X23";
				break;
			case SIZE_24X31:
				size="24X31";
				break;
			}
			
			String str="R90 "+size+" "+x+" "+y+" "+characters+"\r\n";
			byte[] cmd=str.getBytes();
			AddRange(buffer, cmd);
	}
	
	/**
	 * Prints text rotated 180 degrees clockwise from horizontal.
	 * 
	 * @param type
	 *            - Specifies the basic font size, in dots.
	 * @param x
	 *            - Horizontal position for the upper left corner of the first
	 *            character in a string.
	 * @param y
	 *            - Vertical position for the upper left corner of the first
	 *            character in a string.
	 * @param characters
	 *            - ASCII string to be printed.
	 */
	public void addR180(Font type,int x,int y,String characters)
	{
			String size="";
			
			switch (type) 
			{
			case SIZE_3X7:
				size="3X7";
				break;
			case SIZE_5X7:
				size="5X7";
				break;
			case SIZE_8X8:
				size="8X8";
				break;
			case SIZE_9X12:
				size="9X12";
				break;
			case SIZE_12X16:
				size="12X16";
				break;
			case SIZE_18X23:
				size="18X23";
				break;
			case SIZE_24X31:
				size="24X31";
				break;
			}
			
			String str="R180 "+size+" "+x+" "+y+" "+characters+"\r\n";
			byte[] cmd=str.getBytes();
			AddRange(buffer, cmd);
	}
	
	/**
	 * Prints text rotated 270 degrees clockwise from horizontal.
	 * 
	 * @param type
	 *            - Specifies the basic font size, in dots.
	 * @param x
	 *            - Horizontal position for the upper left corner of the first
	 *            character in a string.
	 * @param y
	 *            - Vertical position for the upper left corner of the first
	 *            character in a string.
	 * @param characters
	 *            - ASCII string to be printed.
	 */
	public void addR270(Font type,int x,int y,String characters)
	{
			String size="";
			
			switch (type) 
			{
			case SIZE_3X7:
				size="3X7";
				break;
			case SIZE_5X7:
				size="5X7";
				break;
			case SIZE_8X8:
				size="8X8";
				break;
			case SIZE_9X12:
				size="9X12";
				break;
			case SIZE_12X16:
				size="12X16";
				break;
			case SIZE_18X23:
				size="18X23";
				break;
			case SIZE_24X31:
				size="24X31";
				break;
			}
			
			String str="R270 "+size+" "+x+" "+y+" "+characters+"\r\n";
			byte[] cmd=str.getBytes();
			AddRange(buffer, cmd);
	}
	
	/**
	 * Sets the clock to the numeric time value
	 * 
	 * @param YYYY
	 *            - Desired year. Allowable values are 1970 to 2069.
	 * @param MM
	 *            - Desired month. Allowable values are 01 to 12.
	 * @param DD
	 *            - Desired day within the month. Allowable values are 01 to 31.
	 * @param hh
	 *            - Desired hour. Allowable values are 00 to 23.
	 * @param mm
	 *            - Desired minute. Allowable values are 00 to 59.
	 * @param ss
	 *            - Desired second. Allowable values are 00 to 59.
	 */
	public void setTime(int YYYY,int MM,int DD,int hh,int mm,int ss)
	{
		String str="TIME SET "+YYYY+" "+MM+" "+DD+" "+hh+" "+mm+" "+ss+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Adds the specified interval to the last time read by TIME GET.
	 * 
	 * @param YYYY
	 *            - Desired year. Allowable values are 1970 to 2069.
	 * @param MM
	 *            - Desired month. Allowable values are 01 to 12.
	 * @param DD
	 *            - Desired day within the month. Allowable values are 01 to 31.
	 * @param hh
	 *            - Desired hour. Allowable values are 00 to 23.
	 * @param mm
	 *            - Desired minute. Allowable values are 00 to 59.
	 * @param ss
	 *            - Desired second. Allowable values are 00 to 59.
	 */
	public void addTime(int YYYY,int MM,int DD,int hh,int mm,int ss)
	{
		String str="TIME ADD "+YYYY+" "+MM+" "+DD+" "+hh+" "+mm+" "+ss+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Changes the printhead heat, thereby adjusting the darkness at which labels are printed.
	 * @param n - Darkness value.
	 */
	public void setDarkness(int n)
	{
		String str="VARIABLE DARKNESS "+n+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Selects paper speed to use when feed switch is depressed.
	 * @param speed - Feed speed of the printer.This parameter should be "NORMAL", "LOWSPEED" or "HIGHSPEED".
	 */
	public void setFeed(Speed speed)
	{
		String value="";
		switch (speed) 
		{
		case NORMAL:
			value="NORMAL";
			break;
		case LOWSPEED:
			value="LOWSPEED";
			break;
		case HIGHSPEED:
			value="HIGHSPEED";
			break;
		}
		
		String str="VARIABLE FEED "+value+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * This method helps developer to set FeedType of the printer.
	 * @param feed FeedType should be GAP, BAR or NOTCH.
	 */
	public void setFeedType(FeedType feed)
	{
		String type="";
		switch (feed) 
		{
		case GAP:
			type="GAP";
			break;
		case BAR:
			type="BAR";
			break;
		case NOTCH:
			type="NOTCH";
			break;
		}
		
		String str="VARIABLE FEED_TYPE "+type+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Changes the print speed to its highest available setting.
	 */
	public void setHighSpeed()
	{
		String str="VARIABLE HIGHSPEED"+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Turns indexing on or off.
	 * 
	 * @param value - True or False
	 */
	public void setIndex(boolean flag)
	{
		String value="";
		if(flag)
			value="ON";
		else
			value="OFF";
		
		String str="VARIABLE INDEX "+value+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);

	}
	
	/**
	 * Adjusts the index detector for optimum gap detection through a wide range
	 * of ribbon and label densities. The command is primarily for use with
	 * thermal transfer printers in gap indexing mode. There is no need to use
	 * this command when using black bar indexing. The C Series printers use the
	 * CALIBRATE parameter, but not any of the other parameters.
	 */
	public void setIndexSettings(IndexMode mode)
	{
		String value="0";
		switch (mode) 
		{
		case MODE_0:
			value="0";
			break;
		case MODE_1:
			value="1";
			break;
		case MODE_2:
			value="2";
			break;
		case MODE_3:
			value="3";
			break;
		case CALIBRATE:
			value="CALIBRATE";
			break;
		}
		
		String str="VARIABLE INDEX SETTING "+value+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Changes the printer speed to its lowest allowable value.
	 */
	public void setLowSpeed()
	{
		String str="VARIABLE LOWSPEED "+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Selects Blazer Emulation Mode in printers that support variable dot time,
	 * or sets the default print pitch in all other printers except the Code
	 * Courier and the C Series printers which do not support the command.
	 */
	public void setMode(ScaleType scale)
	{
		String mode="";
		switch (scale) 
		{
		case SCALE_0:
			mode="0";
			break;
		case SCALE_1:
			mode="1";
			break;
		case SCALE_2:
			mode="2";
			break;
		}
		
		String str="VARIABLE MODE "+mode+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Changes the printer speed to a speed approximately halfway between the
	 * LOWSPEED and HIGHSPEED settings.
	 */
	public void setNormal()
	{
		String str="VARIABLE NORMAL "+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * This method Selects the default print pitch.
	 * @param pitch Pitch value , allowable values are DPI_75, DPI_100, DPI_150, DPI_200, DPI_203, DPI_300.
	 */
	public void setPitch(Pitch pitch)
	{
		int value=0;
		switch (pitch) 
		{
		case DPI_75:
			value=75;
			break;
		case DPI_100:
			value=100;
			break;
		case DPI_150:
			value=150;
			break;
		case DPI_200:
			value=200;
			break;
		case DPI_203:
			value=203;
			break;
		case DPI_300:
			value=300;
			break;
		}
		
		String str="VARIABLE PITCH "+value+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Controls the dispensing of labels for application. VARIABLE PRESENTLABEL
	 * ensures a second set of labels is not printed before the operator is
	 * ready, and enables the user to change the distance fed forward and
	 * backward after printing.
	 */
	public void setPresentLabel(boolean action,int advance_distance,int retract_distance,int time_delay)
	{
		String value="";
		if(action)
			value="ON";
		else
			value="OFF";
		
		String str="VARIABLE PRESENTLABEL "+value+" "+advance_distance+" "+retract_distance+" "+time_delay+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Sets the printer up for direct thermal or thermal transfer printing. The
	 * command adjusts print darkness and gap indexing parameters and enables or
	 * disables the ribbon-out detector as needed for the selected print method.
	 * @param mode This parameter is Print mode. This values should be DT, TT or AUTO.
	 */
	public void setPrintMode(PrintMode mode)
	{
		String value="";
		switch (mode) 
		{
		case DT:
			value="DT";
			break;
		case TT:
			value="TT";
			break;
		case AUTO:
			value="AUTO";
			break;
		}
		
		String str="VARIABLE PRINT_MODE "+value+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Sets the current print speed of the printer.
	 * @param speed 
	 * 		This parameter is the speed of the printer. Allowable values are NORMAL, HIGHSPEED or LOWSPEED.
	 */
	public void setPrintSpeed(Speed speed)
	{
		String value="";
		switch (speed) 
		{
		case NORMAL:
			value="NORMAL";
			break;
		case LOWSPEED:
			value="LOWSPEED";
			break;
		case HIGHSPEED:
			value="HIGHSPEED";
			break;
		}
		
		String str="VARIABLE PRINT_SPEED "+value+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * This command is used to restore the printer settings to their default
	 * factory values.
	 */
	public void setReset()
	{
		String str="VARIABLE RESET "+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	

	/**
	  * Enables or disables the transmission of certain status messages to the
	  * host computer.
	  * @param status This is status parameter. Value should be ON or OFF.
	  */
	public void setUserFeedback(boolean status)
	{
		String value="";
		if(status)
			value="ON";
		else
			value="OFF";
		
		String str="VARIABLE USER_FEEDBACK "+value+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * This method sets the default print width.
	 * @param n This parameter is width.
	 */
	public void setWidth(int n)
	{
		String str="VARIABLE WIDTH "+n+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Writes the current variable values to nonvolatile storage. Values in
	 * effect when VARIABLE WRITE is executed are retained in memory while the
	 * printer power is off.
	 */
	public void setWrite()
	{
		String str="VARIABLE WRITE "+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
	
	/**
	 * Add Binary Data to Label Buffer
	 * @param binary - Binary Data
	 */
	public void addBinaryData(byte[] binary)
	{
		AddRange(buffer, binary);
	}
	
	/**
	 * Signals the end of a label format.
	 */
	public void addEnd()
	{
		String str="END "+"\r\n";
		byte[] cmd=str.getBytes();
		AddRange(buffer, cmd);
	}
}
