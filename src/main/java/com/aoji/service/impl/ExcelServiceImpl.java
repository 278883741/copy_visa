package com.aoji.service.impl;

import com.aoji.service.ExcelService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangjunqiang
 * @description
 * @date Created in 2018/7/30 15:14
 */
public class ExcelServiceImpl implements ExcelService {


    /**
     * 显示的导出表的标题
     */
    private String title;
    /**
     * 导出表的列名
     */
    private String[] rowName ;

    private List<Object[]> dataList = new ArrayList<Object[]>();

    HttpServletResponse response;

    public ExcelServiceImpl(String title, String[] rowName, List<Object[]>  dataList, HttpServletResponse response){
        this.dataList = dataList;
        this.rowName = rowName;
        this.title = title;
        this.response = response;
    }
    HSSFCellStyle cellStyle=null;
    HSSFCellStyle cellStyleDate=null;
    HSSFCellStyle cellStyleNumber=null;
    @Override
    public void export() throws Exception {

        try{
            // 创建工作簿对象
            HSSFWorkbook workbook = new HSSFWorkbook();
            Integer sheetSize = 65534;
            double sheetNum = Math.ceil(dataList.size() / new Integer(sheetSize).doubleValue());
            // 2.创建相应的工作表,并向其中填充数据
            for (int i = 0; i < sheetNum; i++) {
                // 如果只有一个工作表的情况
                if (i == 0) {
                    // 向工作表中填充数据
                    HSSFSheet sheet = workbook.createSheet(title);

                    // 产生表格标题行
                    HSSFDataFormat format = workbook.createDataFormat();

                    //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
                    //获取列头样式对象
                    HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);
                    //单元格样式对象
                    cellStyle = workbook.createCellStyle();
                    cellStyleDate = workbook.createCellStyle();
                    cellStyleDate.setDataFormat(format.getFormat("yyyy-m-d"));
                    cellStyleNumber = workbook.createCellStyle();
                    cellStyleNumber.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
                    Integer lastIndex = 0;
                    if (dataList.size() >=sheetSize ){
                        lastIndex = sheetSize;
                    }else{
                        lastIndex = dataList.size();
                    }
                    fillSheet(sheet, columnTopStyle,format,0, lastIndex);
                    // 有多个工作表的情况
                } else {
                    HSSFSheet sheet = workbook.createSheet(title+i);

                    // 产生表格标题行
                    HSSFRow rowm = sheet.createRow(0);
                    HSSFCell cellTiltle = rowm.createCell(0);
                    HSSFDataFormat format = workbook.createDataFormat();

                    //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
                    //获取列头样式对象
                    HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);
                    //单元格样式对象
                    HSSFCellStyle cellStyle = workbook.createCellStyle();

                    // 获取开始索引和结束索引
                    int firstIndex = i * sheetSize;
                    int lastIndex = (i + 1) * sheetSize - 1 > dataList.size() - 1 ? dataList.size() - 1
                            : (i + 1) * sheetSize - 1;
                    // 填充工作表
                    fillSheet(sheet, columnTopStyle,format,firstIndex, lastIndex);
                }
            }
            if(workbook !=null){
                OutputStream out = null;
                try
                {
                    String fileName = "Excel-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
                    String headStr = "attachment; filename=\"" + fileName + "\"";
                    response.setContentType("APPLICATION/OCTET-STREAM");
                    response.setHeader("Content-Disposition", headStr);
                    out = response.getOutputStream();
                    workbook.write(out);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 列头单元格样式
     * @param workbook
     * @return
     */
    public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short)11);
        //字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        return style;

    }

    /**
     * 列数据信息单元格样式
     * @param workbook
     * @return
     */
    public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        return style;

    }

    public void fillSheet(HSSFSheet sheet,HSSFCellStyle columnTopStyle,HSSFDataFormat format,Integer firstIndex,Integer lastIndex){
        // 创建工作表

        // 定义所需列数
        int columnNum = rowName.length;
        // 在索引2的位置创建行(最顶端的行开始的第二行)
        HSSFRow rowRowName = sheet.createRow(0);

        // 将列头设置到sheet的单元格中
        for(int n=0;n<columnNum;n++){
            //创建列头对应个数的单元格
            HSSFCell  cellRowName = rowRowName.createCell(n);
            //设置列头单元格的数据类型
            cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);
            HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
            //设置列头单元格的值
            cellRowName.setCellValue(text);
            //设置列头单元格样式
           // cellRowName.setCellStyle(columnTopStyle);
        }

        //将查询出的数据设置到sheet对应的单元格中
        for(int i=firstIndex;i<lastIndex;i++){
            //遍历每个对象
            Object[] obj = dataList.get(i);

            //创建所需的行数
            int size = 0;
            if (firstIndex < 65534){
                size = i;
            }else{
                size = i-(i/65534)*65534;
            }
            HSSFRow row = sheet.createRow(size +1);

            for(int j=0; j<obj.length; j++){
                HSSFCell cell = null;
                if (j == 0){
                    cell = row.createCell(j);
                    cell.setCellValue(j+1);
                }
                if(obj[j] instanceof Long) {
                    cell = row.createCell(j);
                    cellStyle=cellStyleDate;
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(new Date((Long) obj[j]));
                } else if(obj[j] instanceof BigDecimal){
                    cell = row.createCell(j,HSSFCell.CELL_TYPE_NUMERIC);
                    cellStyle=cellStyleNumber;
                    cell.setCellValue(Double.valueOf(obj[j].toString()));
                }
                else{
                    cell = row.createCell(j,HSSFCell.CELL_TYPE_STRING);
                    if(!"".equals(obj[j]) && obj[j] != null){
                        cell.setCellValue(obj[j].toString());
                    }
                }
            }
        }
    }

}
