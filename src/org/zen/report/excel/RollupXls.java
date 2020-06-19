package org.zen.report.excel;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zen.report.rollup.RuFee;
import org.zen.report.rollup.RuMgr;
import org.zen.report.rollup.RuRow;
import org.zen.services.Services;

public class RollupXls {
	
	private static Logger log = Logger.getLogger(RollupXls.class);
	private XSSFWorkbook wb;
	private Sheet sheet;
	private String reportName;
	private String workbookPath;
	private ExcelHelper helper;
	private SimpleDateFormat df;
	private RuMgr rm;
	
	public RollupXls(RuMgr rm) throws Exception
	{
		setRm(rm);
		
		wb = new XSSFWorkbook();
		helper = new ExcelHelper(wb);
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		log.info("Creating RollupXls");
	
		createSS();
		
		String xlsFolder = "/home/pmk/zen-properties/reports";
		if (Services.getProp() != null)
			xlsFolder = Services.getProp().getProperty("reportPath","/home/pmk/zen-properties/reports");
		
		if (!xlsFolder.endsWith("/"))
			xlsFolder = xlsFolder + "/";
		workbookPath = xlsFolder + "rollup.xls";
       
        FileOutputStream out = new FileOutputStream(workbookPath);
        wb.write(out);
        out.close();
        
        log.info("workbook : " + workbookPath + " created..");
	}
	
	private void createSS() {
		
		createSheet();
		int rowNum = createRuRowHeader(0,rm);
		for (RuRow rr : rm.getRuMatrixTemplate().getRows())
			rowNum = createRuRow(rr,rowNum);
	}
	
	private int createRuRowHeader(int rowNum, RuMgr rm) {
		Row headerRow = sheet.createRow(rowNum++);
	    headerRow.setHeightInPoints(12.75f);
	    int col = 0;
	    col = createHeaderCell(headerRow,col,"Level");
	    col = createHeaderCell(headerRow,col,"Pot. Members");
	    col = createHeaderCell(headerRow,col,"Act. Members");
	    RuRow rr = rm.getRuMatrixTemplate().getRows().get(0);
	    for (RuFee rf : rr.getFees())
	    {
	    	String str = "R-" + rf.getRating() + "UFee";
	    	col = createHeaderCell(headerRow,col,str);
	    	str = "R-" + rf.getRating() + "UPaid";
	    	col = createHeaderCell(headerRow,col,str);
	    }
		return rowNum;
	}

	private int createRuRow(RuRow rr, int rowNum) {
		Row dataRow = sheet.createRow(rowNum++);
		dataRow.setHeightInPoints(12.75f);
	    int col = 0;
	    col = createDataCell(dataRow,col,rr.getLevel());
	    col = createDataCell(dataRow,col,rr.getPotMemb());
	    col = createDataCell(dataRow,col,rr.getActMemb());
	    for (RuFee rf : rr.getFees())
	    {
	    	col = createDataCell(dataRow,col,rf.getUpgradeFee());
	    	col = createDataCell(dataRow,col,rf.getUpgradePaid());
	    }
		return rowNum;
	}

	private void createSheet()
	{
		sheet = wb.createSheet("Rowups");
		helper.initializeSheet(sheet);
	}

	private int createDataCell(Row dataRow,int col,String data)
	{
	    return helper.createDataCell(sheet, dataRow, col, data,12);
	}
	
	private int createDataCell(Row dataRow,int col,int data)
	{
	    return helper.createDataCell(sheet, dataRow, col, Integer.toString(data),12);
	}
	
	private int createDataCell(Row dataRow,int col,double data)
	{
	    return helper.createDataCell(sheet, dataRow, col, Double.toString(data),12);
	}
	
	private int createHeaderCell(Row headerRow,int col,String header)
	{
		return helper.createHeaderCell(sheet, headerRow, col, header,12);
	}
	
	private int createHeaderCell2(Row headerRow,int col,String header)
	{
	    return helper.createHeaderCell2(sheet, headerRow, col, header,12);
	}
	
	private int createHeaderCell3(Row headerRow,int col,String header)
	{
		return helper.createHeaderCell4(sheet, headerRow, col, header);
	}
	/*
	private int createHeaderCell4(Row headerRow,int col,String header)
	{
	    return helper.createHeaderCell4(sheet, headerRow, col, header);
	}
	*/

	public String getWorkbookPath() {
		return workbookPath;
	}

	public void setWorkbookPath(String workbookPath) {
		this.workbookPath = workbookPath;
	}
	

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public XSSFWorkbook getWb() {
		return wb;
	}

	public void setWb(XSSFWorkbook wb) {
		this.wb = wb;
	}

	public SimpleDateFormat getDf() {
		return df;
	}

	public void setDf(SimpleDateFormat df) {
		this.df = df;
	}

	public RuMgr getRm() {
		return rm;
	}

	public void setRm(RuMgr rm) {
		this.rm = rm;
	}
	
	
	
}
