package org.zen.report.excel;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelStyles {


	public static Map<String, CellStyle> createStyles(Workbook wb){
	        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
	        DataFormat df = wb.createDataFormat();
     
	        CellStyle style;
	        Font headerFont = wb.createFont();
	        headerFont.setFontHeightInPoints((short) 10);
	        headerFont.setBold(false);
	        
	        style = createBorderedStyle(wb);
	        style.setAlignment(HorizontalAlignment.CENTER);
	        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
	        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        style.setWrapText(true);
	        style.setFont(headerFont);
	        styles.put("header", style);
	        
	        style = createBorderedStyle(wb);
	        style.setAlignment(HorizontalAlignment.CENTER);
	        style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
	        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        style.setWrapText(true);
	        style.setFont(headerFont);
	        styles.put("header2", style);
	        
	        style = createBorderedStyle(wb);
	        style.setAlignment(HorizontalAlignment.CENTER);
	        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
	        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        style.setWrapText(true);
	        style.setFont(headerFont);
	        styles.put("header3", style);
	        
	        style = createBorderedStyle(wb);
	        style.setAlignment(HorizontalAlignment.CENTER);
	        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
	        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        style.setWrapText(true);
	        style.setFont(headerFont);
	        styles.put("header4", style);

	        style = createBorderedStyle(wb);
	        style.setAlignment(HorizontalAlignment.CENTER);
	        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
	        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        style.setFont(headerFont);
	        style.setWrapText(true);
	        style.setDataFormat(df.getFormat("d-mmm-yy"));
	        styles.put("header_date", style);

	        Font font1 = wb.createFont();
	        font1.setFontHeightInPoints((short) 10);
	        font1.setBold(false);

	        style = createBorderedStyle(wb);
	        style.setAlignment(HorizontalAlignment.LEFT);
	        style.setFont(font1);
	        style.setWrapText(true);
	        styles.put("cell_b", style);

	        style = createBorderedStyle(wb);
	        style.setAlignment(HorizontalAlignment.CENTER);
	        style.setFont(font1);
	        style.setWrapText(true);
	        styles.put("cell_b_centered", style);

	        style = createBorderedStyle(wb);
	        style.setAlignment(HorizontalAlignment.RIGHT);
	        style.setFont(font1);
	        style.setWrapText(true);
	        style.setDataFormat(df.getFormat("d-mmm"));
	        styles.put("cell_b_date", style);

	        style = createBorderedStyle(wb);
	        style.setAlignment(HorizontalAlignment.RIGHT);
	        style.setFont(font1);
	        style.setWrapText(true);
	        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        style.setDataFormat(df.getFormat("d-mmm"));
	        styles.put("cell_g", style);

	        Font font2 = wb.createFont();
	        font2.setFontHeightInPoints((short) 12);
	        font2.setBold(true);

	        font2.setColor(IndexedColors.BLUE.getIndex());
	        style = createBorderedStyle(wb);
	        style.setWrapText(true);
	        style.setAlignment(HorizontalAlignment.LEFT);
	        style.setFont(font2);
	        styles.put("cell_bb", style);

	        style = createBorderedStyle(wb);
	        style.setAlignment(HorizontalAlignment.RIGHT);
	        style.setFont(font1);
	        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        style.setDataFormat(df.getFormat("d-mmm"));
	        styles.put("cell_bg", style);

	        Font font3 = wb.createFont();
	        font3.setFontHeightInPoints((short)14);
	        font3.setColor(IndexedColors.DARK_BLUE.getIndex());
	        style.setAlignment(HorizontalAlignment.LEFT);
	        style.setFont(font3);
	        styles.put("cell_h", style);

	        style = createBorderedStyle(wb);
	        style.setAlignment(HorizontalAlignment.LEFT);
	        style.setFont(font1);
	        style.setWrapText(true);
	        styles.put("cell_normal", style);

	        style = createBorderedStyle(wb);
	        style.setFont(font1);
	        style.setAlignment(HorizontalAlignment.CENTER);
	        style.setWrapText(true);
	        styles.put("cell_normal_centered", style);
	        
	        style = createBorderedStyle(wb);
	        style.setFont(font1);
	        style.setAlignment(HorizontalAlignment.RIGHT);
	        style.setWrapText(true);
	        styles.put("cell_normal_right", style);


	        style = createBorderedStyle(wb);
	        style.setFont(font1);
	        style.setAlignment(HorizontalAlignment.RIGHT);
	        style.setDataFormat(df.getFormat("dd-mmm-yy"));
	        styles.put("cell_normal_date", style);
	        
	        style = createBorderedStyle(wb);
	        style.setFont(font1);
	        style.setAlignment(HorizontalAlignment.LEFT);
	        style.setDataFormat(df.getFormat("dd-mmm-yy hh:mm"));
	        styles.put("cell_time_date", style);
	        
	        style = createBorderedStyle(wb);
	        style.setFont(font1);
	        style.setAlignment(HorizontalAlignment.LEFT);
	        style.setDataFormat(df.getFormat("dd-mm-yy hh:mm:ss"));
	        styles.put("cell_time_date_ss", style);

	        style = createBorderedStyle(wb);
	        style.setFont(font1);
	        style.setAlignment(HorizontalAlignment.RIGHT);
	        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
	        styles.put("cell_score", style);
	        
	        style = createBorderedStyle(wb);
	        style.setFont(font1);
	        style.setAlignment(HorizontalAlignment.RIGHT);
	        style.setDataFormat(wb.createDataFormat().getFormat("0"));
	        styles.put("cell_num", style);
	        
	        style = createBorderedStyle(wb);
	        style.setFont(font1);
	        style.setAlignment(HorizontalAlignment.LEFT);
	        style.setIndention((short)1);
	        style.setWrapText(true);
	        styles.put("cell_indented", style);

	        style = createBorderedStyle(wb);
	        style.setFont(font1);
	        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
	        styles.put("cell_blue", style);

	        return styles;
	    }

	    private static CellStyle createBorderedStyle(Workbook wb){
	        CellStyle style = wb.createCellStyle();
	        style.setBorderRight(BorderStyle.THIN);
	        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderBottom(BorderStyle.THIN);
	        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderLeft(BorderStyle.THIN);
	        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderTop(BorderStyle.THIN);
	        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
	        return style;
	    }
}
