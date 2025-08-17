package com.cognitive.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.cognitive.connection.ConnectionListener;
import com.cognitive.connection.DevType;
import com.cognitive.printer.PoSPrinter;
import com.cognitive.printer.PrinterFactory;
import com.cognitive.printer.PrinterModel;
import com.cognitive.status.PrinterStatus;


public class MainActivity extends Activity implements ConnectionListener
{
	public final String Addr_A798="00:80:E1:B1:6F:7A";
	public final String Addr_A799="00:06:66:66:F0:89";
	public final String Addr_DLXi="00:06:66:66:F8:4F";
	
	private TextView textView;
	private PoSPrinter printer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		try 
		{
			printer=(PoSPrinter) PrinterFactory.getPrinter(PrinterModel.A798);
			printer.setConnectionListener(this);
			printer.openPrinter(DevType.BLUETOOTH, Addr_A798);
			Thread.sleep(100L);
			
//			byte[] data=getBinaryDataFromFile();
//			POSPrinterIO buffer=new POSPrinterIO();
//			buffer.addData(data);
			
			//textView.setText("Connected");
//			POSPrinterIO pos=new POSPrinterIO();
//			pos.addInitializePrinter();
//			pos.addAlignment(Alignment.Center);
//			pos.addPrintMode(Pitch.STANDARD, false, true, false, false);
//			pos.addTextData("Double-High".getBytes());
//			pos.addFeedLine();
//			
//			pos.addAlignment(Alignment.Center);
//			pos.addPrintMode(Pitch.STANDARD, false, false, true, false);
//			pos.addTextData("Double-Wide".getBytes());
//			pos.addFeedLine();
//			
//			pos.addAlignment(Alignment.Center);
//			pos.addPrintMode(Pitch.STANDARD, false, true, true, false);
//			pos.addTextData("Both High and Wide".getBytes());
//			pos.addFeedLine();
//			pos.addFeedLine();
//			pos.addFeedLine();
			
//			POSPrinterIO buffer=new POSPrinterIO();
//			buffer.addInitializePrinter();
//			buffer.addAlignment(Alignment.Center);
//			buffer.addQRCode(Model.Model2,5,CorrectionLevelOption.Low,"QR Code by CognitiveTPG".getBytes());
//			Byte[] cmd={0x0D,0x0A};
//			buffer.addCommand(cmd);
//			buffer.addData("For more information please visit us at:".getBytes());
//			buffer.addCommand(cmd);
//			buffer.addData("http://www.cognitivetpg.com/gettheinsidestory".getBytes());
//			buffer.printQRCode();
//			buffer.addFeedLine();
//			buffer.addFeedLine();
			
//			buffer.addData("QR Code by CognitiveTPG".getBytes());
//			buffer.addCommand(cmd);
//			buffer.addData("For more information please visit us at:".getBytes());
//			buffer.addCommand(cmd);
//			
//			Byte[] mode1={0x1B,0x21,0x01};
//			buffer.addCommand(mode1);
//			buffer.addData("http://www.cognitivetpg.com/gettheinsidestory".getBytes());
//			buffer.addData("QR Code by CognitiveTPG".getBytes());
//			buffer.addCommand(cmd);
//			buffer.addData("For more information please visit us at:".getBytes());
//			buffer.addCommand(cmd);
//			buffer.addData("http://www.cognitivetpg.com/gettheinsidestory".getBytes());
//			buffer.printQRCode();
//			buffer.addFeedLine();
//			buffer.addFeedLine();
//			
//			buffer.addData("QR Code by CognitiveTPG".getBytes());
//			buffer.addCommand(cmd);
//			buffer.addData("For more information please visit us at:".getBytes());
//			buffer.addCommand(cmd);
//			buffer.addCommand(mode1);
//			buffer.addData("http://www.cognitivetpg.com/gettheinsidestory".getBytes());
//			Byte[] mode0={0x1B,0x21,0x00};
//			buffer.addCommand(mode0);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
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

	private byte[] getBinaryDataFromFile()
	{
		try 
		{
			InputStream is=getAssets().open("Pagemode.BIN");
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
	
}

