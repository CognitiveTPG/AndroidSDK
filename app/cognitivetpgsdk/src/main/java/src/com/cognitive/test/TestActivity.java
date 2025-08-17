package com.cognitive.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.cognitive.connection.ConnectionListener;
import com.cognitive.connection.DevType;
import com.cognitive.printer.LabelPrinter;
import com.cognitive.printer.PrinterFactory;
import com.cognitive.printer.PrinterModel;
import com.cognitive.printer.io.LabelPrinterIO;
import com.cognitive.printer.io.LabelPrinterIO.Font;
import com.cognitive.printer.io.LabelPrinterIO.Mode;


public class TestActivity extends Activity implements ConnectionListener
{
	
	public final String Addr_A798="00:06:66:66:F8:54";
	public final String Addr_A799="00:06:66:66:F0:89";
	public final String Addr_DLXi="00:80:E1:B1:74:EF";
	
	//public final String Addr_DLX="00:06:66:66:F8:4F";
	
	private TextView textView;
	private LabelPrinter printer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		try 
		{
			printer=(LabelPrinter) PrinterFactory.getPrinter(PrinterModel.DLXi);
			printer.setConnectionListener(this);
			printer.openPrinter(DevType.BLUETOOTH, Addr_DLXi);
			Thread.sleep(100L);
			
			LabelPrinterIO label=new LabelPrinterIO();
			label.addHeader(Mode.ASCII, 0, 100, 400, 1);
			//label.addPitch(Pitch.DPI_100);
			String data="NAME:JOHN SMITH\nADDRESS:116 WILBUR\nBOHEMIA, NY 11716\nPHONE:516-555-4907\nPHYSICIAN:DR.HARRY KLINE\nINSURANCE:AETNA\nPOLICY NO:918-1287345\nSPOUSE:JENNIFER SMITH\nHT:5i9\"\nWT:165\nHAIR COLOR:BROWN\nEYE COLOR: BROWN\nALLERGIES:NONE\nDISABILITIES:NONE\nBLOOD:A\nSS#051-98-2374\nDOB:5/24/60";
			label.addPDF417(50, 10, 2, 6, 1, 0, 7, 309, data);
			label.addString(Font.SIZE_18X23, 1,1,2,2,100, 340, "PDF417");
			label.addEnd();
			
			//System.out.println(label.t);
			printer.sendCommand(label);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		
	}
	
	private byte[] getBinaryImage()
	{
		try 
		{
			InputStream is=getAssets().open("image.bmp");
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			int nRead;
			byte[] data = new byte[16384];

			while ((nRead = is.read(data, 0, data.length)) != -1) {
			  buffer.write(data, 0, nRead);
			}

			buffer.flush();

			return buffer.toByteArray();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
	}
	
	private void showToast(String msg)
	{
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		if(printer!=null)
		{
			try {
				printer.closePrinter();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onConnected() 
	{
		textView.append("Printer Connected\r\n");
		showToast("Printer Connected");
		System.out.println("Printer Connected");
	}

	@Override
	public void onError(String msg) 
	{
		textView.append("Printer Error\r\n");
		showToast("Printer Error");
		System.out.println("Printer Error: "+msg);
	}

	@Override
	public void onDisconnected() 
	{
		showToast("Printer Disconnected");
		System.out.println("Printer Disconnected");
	}
	
	//pos.addBarcode(80, BarcodeWide.Wide_2, HRI.HRI_Above, BarCodeType.UPC_A, "036000241457".getBytes());
	//pos.addBarcode(80, BarcodeWide.Wide_2, HRI.HRI_Above, BarCodeType.UPC_E, "065100004327".getBytes());
	//pos.addBarcode(80, BarcodeWide.Wide_2, HRI.HRI_Above, BarCodeType.JAN8, "7351353".getBytes());
	//pos.addBarcode(80, BarcodeWide.Wide_2, HRI.HRI_Above, BarCodeType.JAN13, "4006381333931".getBytes());
}

