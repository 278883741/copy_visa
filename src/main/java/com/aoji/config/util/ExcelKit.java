package com.aoji.config.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author daitian
 * @date 2018/9/18
 */
public class ExcelKit {

    /**
     * 设置sheet名称
     * @param workbook
     * @param sheet
     * @return
     */
    private static HSSFRow setSheet(HSSFWorkbook workbook, String sheet) {
       return workbook.createSheet(sheet).createRow(0);
    }

    /**
     * 设置单元格
     * @param row
     * @param index
     * @param s
     */
    private static void setCell(HSSFRow row, int index, String s) {
        HSSFCell cell = row.createCell(index);
//        cell.setCellStyle(getStyle(cell,2));
        cell.setCellValue(s);
    }

    /**
     *
     * @param cell
     * @param type 1.表头 2.表格
     * @return
     */
    private static HSSFCellStyle getStyle(HSSFCell cell,int type) {
        HSSFCellStyle hssfCellStyle=cell.getCellStyle();
        return null;
    }

    /**
     * 设置表头
     * @param hssfSheet
     * @param index
     * @param s
     */
    private static void setFirstCell(HSSFSheet hssfSheet, int index, String s) {
        HSSFCell cell = hssfSheet.createRow(0).createCell(index);
//        cell.setCellStyle(getStyle(cell,1));
        cell.setCellValue(s);
    }

    /**
     * 加载数据
     * @param data
     * @param clazz
     * @return
     */
    private static HSSFWorkbook loadData(List<Map> data, Class<?> clazz){// ? class对象的泛型
        HSSFWorkbook workbook = new HSSFWorkbook();
        List<String> key=init(workbook,clazz);

        String title =clazz.getAnnotation(ApiModel.class).value();
        HSSFSheet sheet = workbook.getSheet(title);
        HSSFRow row;
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                row = sheet.createRow(sheet.getLastRowNum() + 1);
                for (int j = 0; j < key.size(); j++) {
                    //格式化数据
                    String s=fomart(data.get(i).get(key.get(j)));
                    row.createCell(j).setCellValue(s);
                }
            }
        }
        return workbook;
    }

    private static String fomart(Object o) {
        return o==null?"":o.toString();
    }

    /**
     * 模版初始化
     * @param workbook
     * @param clazz
     * @return
     */
    private static List<String> init(HSSFWorkbook workbook, Class<?> clazz) {

        //反射解析实体类
        Field[] declaredFields = clazz.getDeclaredFields();
        Field.setAccessible(declaredFields, true);
        String title =clazz.getAnnotation(ApiModel.class).value();
        Map<String,String> map = new LinkedHashMap<>();
        for (Field declaredField : declaredFields) {
            map.put(declaredField.getName(),declaredField.getAnnotation(ApiModelProperty.class).value());
        }

        //创建excel模版
        HSSFRow row=setSheet(workbook,title);
        //设置表头
        int i=0;
        for (String cell : map.values()) {
            setCell(row,i++,cell);
        }
        return new ArrayList<>(map.keySet());
    }

    public static void export(HttpServletResponse response,List<Map> data, Class clazz){
        HSSFWorkbook workbook = loadData(data,clazz);

        if (workbook != null) {
            try {
                String fileName = "Excel-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
                String headStr = "attachment; filename=\"" + fileName + "\"";
                response.setContentType("APPLICATION/OCTET-STREAM");
                response.setHeader("Content-Disposition", headStr);
                OutputStream out = response.getOutputStream();
                workbook.write(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
