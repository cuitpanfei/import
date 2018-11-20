package com.pfinfo.impor.util;

import static com.pfinfo.impor.util.ConstantConfig.XLS;
import static com.pfinfo.impor.util.ConstantConfig.XLSX;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ResourceUtils;

import com.pfinfo.impor.exception.ImportExcelBaseException;

public class ExcelUtil {

	public static Workbook getWorkbook(String localFilePaht)
			throws ImportExcelBaseException {
		try (FileInputStream fileInputStream = new FileInputStream(
				ResourceUtils.getFile(localFilePaht))) {
			if (localFilePaht.endsWith(XLS)) {
				return new HSSFWorkbook(fileInputStream);
			} else if (localFilePaht.endsWith(XLSX)) {
				return new XSSFWorkbook(fileInputStream);
			}
			return null;
		} catch (IOException e) {
			throw new ImportExcelBaseException(e);
		}
	}

	/**
	 * 获取工作簿的所有表头
	 * 
	 * @param workbook
	 * @return
	 */
	public static Map<String, Row> getHeads(Workbook workbook) {
		Map<String, Row> rows = new HashMap<>(workbook.getNumberOfSheets());
		Map<String, Sheet> sheets = getSheet(workbook);
		sheets.forEach((k, v) -> {
			rows.put(k, v.getRow(0));
		});
		return rows;
	}

	/**
	 * 获取表
	 * 
	 * @param workbook
	 * @return Map集合。（key->表名,value->表）
	 */
	public static Map<String, Sheet> getSheet(Workbook workbook) {
		Map<String, Sheet> all = new HashMap<>(workbook.getNumberOfSheets());
		Iterator<Sheet> allSheet = workbook.sheetIterator();
		allSheet.forEachRemaining(sheet -> {
			all.put(sheet.getSheetName(), sheet);
		});
		return all;
	}

	/**
	 * 获取表
	 * 
	 * @param workbook
	 * @return Sheet
	 */
	public static Sheet getSheet(Workbook workbook, String sheetName) {
		Map<String, Sheet> sheets = getSheet(workbook);
		return sheets.get(sheetName);
	}

	/**
	 * 文本方式获取单元格的值
	 * 
	 * @param workbook
	 * @return Map集合。（key->表名,value->表）
	 */
	public static String getCellValueAsString(Cell cell) {
		CellType cellType = cell.getCellTypeEnum();
		String value = "";
		switch (cellType) {
		case NUMERIC:
			value += cell.getNumericCellValue();
			break;
		case STRING:
			value += cell.getStringCellValue();
			break;
		case BOOLEAN:
			value += cell.getBooleanCellValue();
			break;
		// 空的话，就用空串吧。
		case BLANK:
		case _NONE:
			break;
		// 公式我就没有办法了，先走默认处理吧
		case FORMULA:
		default:
			value = null;
			break;
		}
		return value;
	}

	/**
	 * 将表头的位置信息与表头列信息取出
	 * @param headerRow
	 * @return
	 */
	public static Map<String, Integer> getHeaderInfo(Row headerRow) {
		Map<String, Integer> headInfo = new HashMap<>();
		headerRow.forEach(cell -> {
			if (cell != null) {
				String nameCN = getCellValueAsString(cell);
				if (NullCheckUtil.isNotEmpty(nameCN)) {
					headInfo.put(getCellValueAsString(cell),
							cell.getColumnIndex());
				}
			}
		});
		return headInfo;
	}

}
