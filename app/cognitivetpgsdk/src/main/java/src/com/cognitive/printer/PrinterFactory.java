package com.cognitive.printer;

/**
 * This class return object of specific Printer.
 * @author Srinivasan G.
 *
 */
public class PrinterFactory 
{

	/**
	 * This method helps developer to get the Printer Model.
	 * @param model This parameter is the PrinterModel object.
	 * @return Return a Printer Model like A798,A799, DLXi, CSeries.
	 */
	public static Printer getPrinter(PrinterModel model)
	{
		Printer printer=null;
		if(PrinterModel.A799==model)
		{
			printer=new PoSPrinter();
		}
		else if(PrinterModel.A798==model)
		{
			printer=new PoSPrinter();
		}
		else if(PrinterModel.DLXi==model)
		{
			printer=new LabelPrinter();
		}
		else if(PrinterModel.CSeries==model)
		{
			printer=new LabelPrinter();
		}
		return printer;
	}

}
