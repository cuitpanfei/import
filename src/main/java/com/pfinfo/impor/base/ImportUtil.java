package com.pfinfo.impor.base;

import com.pfinfo.impor.annotation.ImportModel;
import com.pfinfo.impor.bean.ImportModelBean;
import com.pfinfo.impor.context.ImportModelBeanCatch;
import com.pfinfo.impor.exception.ImportExcelBaseException;
import com.pfinfo.impor.util.ExcelUtil;
import com.pfinfo.impor.util.HttpDownLoad;
import com.pfinfo.impor.util.NullCheckUtil;
import com.pfinfo.impor.util.StringUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static com.pfinfo.impor.util.ConstantConfig.SAVEPATH;

/**
 * 批量导入数据转换工具
 * 
 * @author pys1714
 *
 */
@Component
public class ImportUtil {

	/**
	 * 将指定url对应的文件数据转换成指定的对象,如果转换过程出错，抛出自定义异常。默认会存储excel到linux环境的 /tmp目录下，如果开发人员想自定义存储路径，
	 * 建议开发人员使用{@link ImportUtil#getData(java.lang.Class, java.lang.String, java.lang.String)}。
	 * 以下原因可能导致转换过程出错：
	 * <ol>
     * <li>类对象没有使用{@link ImportModel com.pfinfo.impor.annotation.ImportModel}注解</li>
     * <li>类对象为空</li>
     * <li>错误的网络路径</li>
	 * </ol>
	 * @param clazz 类对象
	 * @param url 文件http地址
	 * @return list集合
	 * @throws ImportExcelBaseException
	 */
	public <T> List<T> getData(Class<T> clazz, String url)
			throws ImportExcelBaseException {
		return getData(clazz,url, SAVEPATH);
	}
	
	/**
	 * 将指定url对应的文件数据转换成指定的对象,如果转换过程出错，抛出自定义异常。如果开发人员想自定义存储路径，建议开发人员使用此方法，不建议使用{@link ImportUtil#getData(java.lang.Class, java.lang.String)}。
	 * 以下原因可能导致转换过程出错：
	 * <ol>
	 * <li>类对象没有使用{@link ImportModel com.pfinfo.impor.annotation.ImportModel}注解</li>
	 * <li>类对象为空</li>
	 * <li>错误的网络路径</li>
	 * </ol>
	 * @param clazz 类对象
	 * @param url 文件http地址
	 * @param path 文件下载后本地文件夹
	 * @return list集合
	 * @throws ImportExcelBaseException
	 */
	public <T> List<T> getData(Class<T> clazz, String url,String path)
			throws ImportExcelBaseException {
		if (NullCheckUtil.isEmpty(clazz)) {
			throw new ImportExcelBaseException("clazz is empty");
		}
		ImportModelBean importModelBean = ImportModelBeanCatch.getInstance()
				.getCatch(clazz);
		if(NullCheckUtil.isEmpty(importModelBean)){
			throw new ImportExcelBaseException(" 类 "+clazz.getName()+"可能没有使用ImportModel注解");
		}
		String filePath = HttpDownLoad.downLoadFormUrl(url,path);
		Workbook workbook = ExcelUtil.getWorkbook(filePath);
		if (NullCheckUtil.isEmpty(workbook)) {
			throw new ImportExcelBaseException("解析Excel失败");
		}
		Map<String, Row> headers = ExcelUtil.getHeads(workbook);
        //根据类对象检查表头，如果检查通过，返回true，检查不通过，抛出异常。
		checkHeader(clazz, headers, importModelBean);
		Sheet sheet = ExcelUtil.getSheet(workbook,
				importModelBean.getSheetName());
		List<T> data = getData(clazz, importModelBean, sheet);
		return data;
	}

    /**
     * 讲sheet表内的数据转化为对应的实体类集合，映射规则用 importModelBean 传入。
     *
     * @param clazz           实体类
     * @param importModelBean 映射规则
     * @param sheet           表数据
     * @param <T>             泛型
     * @return 泛型对象集合
     */
    private <T> List<T> getData(Class<T> clazz,
                                ImportModelBean importModelBean, Sheet sheet) {
        List<T> list = new ArrayList<>();
        Iterator<Row> rows = sheet.iterator();
        // excel 表头（第一）行
        Row headerRow = rows.next();
        Map<String, Integer> header = ExcelUtil.getHeaderInfo(headerRow);
        Map<String, String> colsMap = importModelBean.getColsMap();
        Map<String, Field> allField = Arrays.stream(clazz.getDeclaredFields())
                .collect(Collectors.toMap(Field::getName, t -> t));
        while (rows.hasNext()) {
            Row row = rows.next();
            T t = null;
            try {
                t = getTData(clazz, allField, header, colsMap, row);
            } catch (ImportExcelBaseException e) {
                e.printStackTrace();
            }
            if (NullCheckUtil.isNotEmpty(t)) {
                list.add(t);
            }
        }
        return list;
    }

    /**
     * 将传入的Row对象转化为List<T>集合对象
     * @param clazz 类对象，需要转化的数据对象的Class对象
     * @param allField <类对象字段名,类对象字段对象>映射关系
     * @param header <表头,位置>映射关系
     * @param colsMap <表头,类对象字段名>映射关系
     * @param row 表格行
     * @param <T> 泛型类，需要转化的数据对象
     * @return
     * @throws ImportExcelBaseException
     */
	private <T> T getTData(Class<T> clazz, Map<String, Field> allField,
			Map<String, Integer> header, Map<String, String> colsMap, Row row)
			throws ImportExcelBaseException {
		try {
			T t = clazz.newInstance();
			colsMap.forEach((nameCN, fieldName) -> {
				try {
					int index = header.get(nameCN);
					Object value = ExcelUtil.getCellValueAsString(row.getCell(index));
					Field field = allField.get(fieldName);
					// 打破封装
					field.setAccessible(true);
					Method m = clazz.getMethod("set" + StringUtil.upperCase(fieldName),field.getType());
                    m.invoke(t, value);
                } catch (Exception e) {
                    //将异常信息转化为运行时异常，抛出。
                    throw new RuntimeException(e);
				}
			});
			return t;
		} catch (Exception e) {
			throw new ImportExcelBaseException("第" + row.getRowNum() + "行转化出错,原因：" + e.getMessage(), e);
		}
	}

	/**
	 * 根据类对象检查表头，如果检查通过，返回true，检查不通过，抛出异常。
	 * 
	 * @param clazz
	 *            类对象
	 * @param headers
	 *            表头
	 * @param importModelBean
	 *            映射关系
	 * @return 检查结果
	 */
	private boolean checkHeader(Class<?> clazz, Map<String, Row> headers,
			ImportModelBean importModelBean) {
		// excel 行
		Row header = headers.get(importModelBean.getSheetName());
		// bean对象 字段与表头映射关系
		Map<String, String> colsMap = importModelBean.getColsMap();
		List<String> allExceptionMsg = new ArrayList<>();
		header.forEach(cell -> {
			if (NullCheckUtil.isNotEmpty(cell)) {
				String value = ExcelUtil.getCellValueAsString(cell);
				if (value == null) {
					allExceptionMsg.add("第" + cell.getColumnIndex()
							+ "列不能转为文本格式，需要手动设置为文本格式。如果不是数据列，请删除单元列");
				} else if (!colsMap.containsKey(value)) {
					allExceptionMsg.add(clazz.getName() + "中缺少" + value
							+ "对应的属性");
				}
			}
		});
		if (allExceptionMsg.isEmpty()) {
			return true;
		} else {
			// 只要检查不通过，就抛出异常。
			String arrStr = Arrays.toString(allExceptionMsg.stream().toArray());
			throw new ImportExcelBaseException(arrStr);
		}
	}

}
