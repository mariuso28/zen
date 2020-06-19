package org.zen.report.excel;

import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelHelper {

	private Workbook workbook;
	private Map<String, CellStyle> styles;
	
	public ExcelHelper(Workbook workbook)
	{
		setWorkbook(workbook);
		styles = ExcelStyles.createStyles(workbook);
	}
	
	public void addComment(Sheet sheet, String author, String commentText, Cell cell) {
        CreationHelper factory = workbook.getCreationHelper();
      
        ClientAnchor anchor = factory.createClientAnchor();
        //i found it useful to show the comment box at the bottom right corner
        anchor.setCol1(cell.getColumnIndex() + 1); //the box of the comment starts at this given column...
        anchor.setCol2(cell.getColumnIndex() + 3); //...and ends at that given column
        anchor.setRow1(cell.getRowIndex() + 1); //one row below the cell...
        anchor.setRow2(cell.getRowIndex() + 5); //...and 4 rows high

        @SuppressWarnings("rawtypes")
		Drawing drawing = sheet.createDrawingPatriarch();
        Comment comment = drawing.createCellComment(anchor);
        //set the comment text and author
        comment.setString(factory.createRichTextString(commentText));
        comment.setAuthor(author);

        cell.setCellComment(comment);
        
    }
	
	public void initializeSheet(Sheet sheet)
	{
		//turn off gridlines
        sheet.setDisplayGridlines(false);
        sheet.setPrintGridlines(false);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);

        //the following three statements are required only for HSSF
        sheet.setAutobreaks(true);
        printSetup.setFitHeight((short)1);
        printSetup.setFitWidth((short)1);
        //freeze the first 1 row
	      //  sheet.createFreezePane(0, 1);
	 }

	public int createDataCell(Sheet sheet,Row dataRow,int col,String data,int width)
	{
		Cell cell = dataRow.createCell(col);
	    cell.setCellValue(data);
	    cell.setCellStyle(styles.get("cell_normal"));
	    sheet.setColumnWidth(col++, 256*width);
	    return col;
	}
	
	public int createHeaderCell(Sheet sheet,Row headerRow,int col,String header,int width)
	{
		Cell cell = headerRow.createCell(col);
	    cell.setCellValue(header);
	    cell.setCellStyle(styles.get("header"));
	    sheet.setColumnWidth(col++, 256*width);
	    return col;
	}
	
	public int createHeaderCell2(Sheet sheet,Row headerRow,int col,String header,int width)
	{
		Cell cell = headerRow.createCell(col);
	    cell.setCellValue(header);
	    cell.setCellStyle(styles.get("header2"));
	    sheet.setColumnWidth(col++, 256*width);
	    return col;
	}
	
	public int createHeaderCell3(Sheet sheet,Row headerRow,int col,String header,int width)
	{
		Cell cell = headerRow.createCell(col);
	    cell.setCellValue(header);
	    cell.setCellStyle(styles.get("header3"));
	    sheet.setColumnWidth(col++, 256*width);
	    return col;
	}
	
	public int createHeaderCell4(Sheet sheet,Row headerRow,int col,String header,int width)
	{
		Cell cell = headerRow.createCell(col);
	    cell.setCellValue(header);
	    cell.setCellStyle(styles.get("header4"));
	    sheet.setColumnWidth(col++, 256*width);
	    return col;
	}
	
	public int createDataCell(Sheet sheet,Row dataRow,int col,String data)
	{
		 return createDataCell(sheet,dataRow,col,data,16);
	}
	
	public int createHeaderCell(Sheet sheet,Row headerRow,int col,String header)
	{
	    return createHeaderCell(sheet,headerRow,col,header,16);
	}
	
	public int createHeaderCell2(Sheet sheet,Row headerRow,int col,String header)
	{
		return createHeaderCell2(sheet,headerRow,col,header,16);
	}
	
	public int createHeaderCell3(Sheet sheet,Row headerRow,int col,String header)
	{
		return createHeaderCell3(sheet,headerRow,col,header,16);
	}
	
	public int createHeaderCell4(Sheet sheet,Row headerRow,int col,String header)
	{
		return createHeaderCell4(sheet,headerRow,col,header,16);
	}

	public Workbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	public Map<String, CellStyle> getStyles() {
		return styles;
	}

	public void setStyles(Map<String, CellStyle> styles) {
		this.styles = styles;
	}

	
	
}
