package com.feicent.zhang.io.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.DocumentFactoryHelper;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;

/**
 * Java使用POI读EXCEl
 * http://www.toutiao.com/i6456842642306105869/
 * @author yzuzhang
 * @date 2017年8月22日
 */
public class ExcelUtils {
	
	private ExcelUtils(){
		
	}
	
	public static List<Object[]> readExcel(String file) {
		return readExcel(new File(file));
	}
	
	/**
	 * 读取Excel
	 * @param file
	 * @return
	 */
	public static List<Object[]> readExcel(File file) {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			List<Object[]> list = readExcel(inputStream);
			return list;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
		
		return new ArrayList<Object[]>();

	}
	
	/**
	 * 读取Excel
	 * @param inputStream
	 * @return
	 */
	public static List<Object[]> readExcel(InputStream inputStream) {
		Workbook workbook = null;
		try {
			workbook = getWorkbook(inputStream);
			return readWorkbook(workbook);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ArrayList<Object[]>();
	}

	private static Workbook getWorkbook(InputStream inputStream)
			throws IOException, InvalidFormatException {
		
		Workbook book = null;
		if (!(inputStream.markSupported())) {
			inputStream = new PushbackInputStream(inputStream, 8);
		}
		if (POIFSFileSystem.hasPOIFSHeader(inputStream)) {
			book = new HSSFWorkbook(inputStream);
		} 
		else if (DocumentFactoryHelper.hasOOXMLHeader(inputStream)) {
			book = new XSSFWorkbook(OPCPackage.open(inputStream));
		}
		
		return book;
	}

	/**
	 * 读取workbook
	 * @param workbook
	 * @return
	 */
	public static List<Object[]> readWorkbook(Workbook workbook) {
		List<Object[]> list = new ArrayList<Object[]>();
		Iterator<Sheet> sheetIterator = workbook.sheetIterator();
		
		while (sheetIterator.hasNext()) {
			Sheet sheet = sheetIterator.next();
			int firstRowNum = sheet.getFirstRowNum();
			int lastRowNum = sheet.getLastRowNum();
			for (int i = firstRowNum; i <= lastRowNum; i++) {
				Row row = sheet.getRow(i);
				if ( null != row ) {
					short firstCellNum = row.getFirstCellNum();
					short lastCellNum = row.getLastCellNum();
					Object[] obj = new Object[lastCellNum];
					for (short s = firstCellNum; s < lastCellNum; s++) {
						Cell cell = row.getCell(s);
						obj[s] = getCellValue(cell);
					}
					list.add(obj);
				}
			}
		}
		
		return list;
	}

	/**
	 * 判断是否位日期 抛弃
	 * @param cell
	 * @return
	 */
	protected static boolean isCellDateFormatted(Cell cell) {
		if (cell == null)
			return false;
		
		boolean isDate = false;
		double d = cell.getNumericCellValue();

		if (DateUtil.isValidExcelDate(d)) {
			CellStyle style = cell.getCellStyle();
			if (style == null)
				return false;

			int i = style.getDataFormat();
			String f = style.getDataFormatString();
			isDate = DateUtil.isADateFormat(i, f);
		}
		
		return isDate;
	}

	@SuppressWarnings("deprecation")
	private static Object getCellValue(Cell cell) {
		Object obj = null;
		switch (cell.getCellType()) {
		case 0:
			double d = cell.getNumericCellValue();
			if (DateUtil.isCellDateFormatted(cell)) {
				obj = cell.getDateCellValue();
			} else {
				obj = d;
			}
			break;
		case 1:
			obj = cell.getStringCellValue().trim();
			break;
		case 2:
			try {
				obj = cell.getNumericCellValue();
			} catch (Exception e) {
				try {
					obj = cell.getRichStringCellValue() == null ? "" : cell
							.getRichStringCellValue().getString();
				} catch (Exception e2) {
					throw new RuntimeException("获取公式类型的单元格失败", e2);
				}
			}
			break;
		case 3:
			obj = "";
			break;
		case 4:
			obj = cell.getBooleanCellValue();
			break;
		default:
			return null;
		}
		
		return obj;
	}
	
	public static void main(String[] args) {
		List<Object[]> list = readExcel("D:/zhang/zhanglei.xlsx");
		for(Object[] line : list) {
			System.out.println(JSON.toJSONString(line));	
		}
	}
}
