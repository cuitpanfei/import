package com.pfinfo.impor.util;

import com.pfinfo.impor.exception.ImportExcelBaseException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.pfinfo.impor.util.ConstantConfig.XLS;
import static com.pfinfo.impor.util.ConstantConfig.XLSX;

public class ExcelUtil {

    /**
     * 根据文件路径获取文件中的工作簿，如果文件不是{@link ConstantConfig#XLS}或者{@link ConstantConfig#XLSX}格式的，会返回{@code null}对象
     * @param localFilePath 文件路径
     * @return 如果文件格式是excel格式，返回工作簿对象，否则，返回null对象
     * @throws ImportExcelBaseException 文件如果受损导致不能打开，将抛出自定义异常。
     */
    public static Workbook getWorkbook(String localFilePath)
            throws ImportExcelBaseException {
        try (FileInputStream fileInputStream = new FileInputStream(
                ResourceUtils.getFile(localFilePath))) {
            if (localFilePath.endsWith(XLS)) {
                return new HSSFWorkbook(fileInputStream);
            } else if (localFilePath.endsWith(XLSX)) {
                return new XSSFWorkbook(fileInputStream);
            }
            return null;
        } catch (IOException e) {
            throw new ImportExcelBaseException(e);
        }
    }

    /**
     * 获取工作簿的所有表头（第一行）
     *
     * @param workbook
     * @return Map集合。（key->表名,value->表头）
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
     * @param cell
     * @return 文本值
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
     *
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
