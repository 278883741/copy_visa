package com.aoji.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yangsaixing
 * @description
 * @date Created in 上午10:04 2018/10/31
 */
public class ExcelUtil {

    private static Workbook workbook;

    private static CellStyle  cellStyleDate = null;
    private static CellStyle  cellStyleNumber = null;

    /**
     * 每个sheet页的row最大值
     */
    private static final Integer sheetSize = 65534;


    public ExcelUtil() {
    }
    public ExcelUtil(int size, String version)throws  FileNotFoundException,IOException{
        if (size < 65535) {
            workbook = new HSSFWorkbook();
        } else {
            workbook = new SXSSFWorkbook();
        }
    }
    public ExcelUtil(InputStream is,int size, String version) throws FileNotFoundException, IOException {

        if(StringUtils.hasText(version) && "2003".equals(version)){
            workbook = new HSSFWorkbook(is);
        }

    }

    /**
     * 公共导出方法
     * @param response
     * @param data
     * @param clazz
     */
    public static void export(HttpServletResponse response, List<Map> data, Class clazz){
        if (data.size() < sheetSize) {
            workbook = new HSSFWorkbook();
        } else {
            workbook = new SXSSFWorkbook(1000);
        }

        loadData(data,clazz);

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
    /**
     * 加载数据
     * @param data
     * @param clazz
     * @return
     */
    private static void loadData(List<Map> data, Class<?> clazz) {

        double sheetNum = Math.ceil(data.size() / new Integer(sheetSize).doubleValue());
        String title =clazz.getAnnotation(ApiModel.class).value();

        Map<String,ExportModel> map=init(data.size(),clazz);
        List<String> key= new ArrayList<>(map.keySet());

        //当数据量过大时，分不同sheet页展示
        for (int num=0;num<sheetNum;num++){

            Sheet sheet=workbook.createSheet(title+num);
            Row rowTitle=sheet.createRow(0);
            //设置表头
            int firstRow=0;
            for (ExportModel model : map.values()) {
                setCell(rowTitle,firstRow++,model.description);
            }
            Row row;
            for (int excelRow = num * sheetSize; excelRow < sheetSize*(num+1); excelRow++) {
                if(excelRow>=data.size()){
                    break;
                }
                row = sheet.createRow(sheet.getLastRowNum() + 1);
                for (int excelColumn = 0; excelColumn < key.size(); excelColumn++) {
                    //格式化数据
                    Object o=data.get(excelRow).get(key.get(excelColumn));
                    if(o==null){
                        continue;
                    }
                    HSSFCell cell= (HSSFCell) row.createCell(excelColumn);
                    ExportModel column=map.get(key.get(excelColumn));
                    if(column!=null && StringUtils.hasText(column.dataType)){
                        setStyle(cell,column.dataType);
                    }
                    setValue(cell,o);
                }
            }
        }


    }

    private static void setValue(HSSFCell cell,Object o){
        if(o instanceof Date) {
            cell.setCellValue((Date)o);
            cell.setCellStyle(cellStyleDate);
        } else if(o instanceof BigDecimal){
            cell.setCellStyle(cellStyleNumber);
            cell.setCellValue(Double.valueOf(o.toString()));
        }else{
            cell.setCellValue(o.toString());
        }

    }

    @Data
    static class ExportModel{
        String name;
        String description;
        String dataType;
    }


    /**
     * 模版初始化
     * @param clazz
     * @return
     */
    private static Map<String,ExportModel> init(int size,Class<?> clazz) {
        //反射解析实体类
        Field[] declaredFields = clazz.getDeclaredFields();
        Field.setAccessible(declaredFields, true);
        Map<String,ExportModel> map = new LinkedHashMap<>();
        for (Field declaredField : declaredFields) {
            if(declaredField.getAnnotation(ApiModelProperty.class) == null) {continue;}
            ExportModel ex=new ExportModel();
            ex.setDataType(declaredField.getAnnotation(ApiModelProperty.class).dataType());
            ex.setDescription(declaredField.getAnnotation(ApiModelProperty.class).value());
            map.put(declaredField.getName(),ex);
        }
        return map;
    }

        /**
         * 设置sheet名称
         * @param workbook
         * @param sheet
         * @return
         */
        private static Row setSheet(Workbook workbook, String sheet) {
            return workbook.createSheet(sheet).createRow(0);
        }

        /**
         * 设置单元格
         * @param row
         * @param index
         * @param s
         */
        private static void setCell(Row row, int index, String s) {
            Cell cell = row.createCell(index);
            cell.setCellValue(s);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        }

    /**
     * 设置样式
     * @param cell
     * @param dataType
     * @return
     */
        private static void setStyle(HSSFCell cell, String dataType) {
            switch (dataType){
                case "Date":
                    if(cellStyleDate==null){
                        cellStyleDate=workbook.createCellStyle();
                        DataFormat format = workbook.createDataFormat();
                        cellStyleDate.setDataFormat(format.getFormat("yyyy-m-d"));
                        cell.setCellStyle(cellStyleDate);
                    }
                    break;
                case "Double":
                    if(cellStyleNumber==null){
                        cellStyleNumber=workbook.createCellStyle();
                        cellStyleNumber.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
                        cell.setCellStyle(cellStyleNumber);
                    }
                    break;
                    default:
            }
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






}
