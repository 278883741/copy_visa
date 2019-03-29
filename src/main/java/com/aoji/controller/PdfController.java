package com.aoji.controller;
import java.awt.*;
import java.io.*;
import java.net.URLEncoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aoji.model.CoeApplyInfo;
import com.aoji.model.StudentInfo;
import com.aoji.model.res.StudentInfoRes;
import com.aoji.service.*;
import com.aoji.service.impl.QrCodeServiceImpl;
import com.aoji.utils.HttpUtils;
import com.lowagie.text.Chunk;
import com.lowagie.text.Font;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.aoji.controller.BaseController;
import com.aoji.model.ApplyInfo;
import com.aoji.model.StudentCostInfo;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
@RestController
public class PdfController extends BaseController {
    @Autowired
    ApplyCollegeService applyCollegeService;

    @Autowired
    private CountryAddress countryAddress;
    @Autowired
    QrCodeService qrCodeService;
    @Autowired
    CostService costService;
    @Autowired
    StudentService studentService;
    @Autowired
    CoeApplyService coeApplyService;
    //生成二维码的url
    @Value("${pdf.code.url}")
    private String pdfCodeUrl;
    /**
     * 导出pdf
     *
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     * @author huyanlong
     */
    @RequestMapping(value = {"/exportpdf"})
    public String exportPdf(HttpServletResponse response, HttpServletRequest request, ApplyInfo applyInfo , StudentCostInfo studentCostInfo,String  studentNo) throws UnsupportedEncodingException, Exception {
        // 指定解析器
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
                "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
        //pdf文件在金山云上的路径
        String filename = "1528449389187007286.pdf";
        String path = "http://aojicrp.ks3-cn-beijing.ksyun.com/data/upload/dev/image/2018/06/08";
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;fileName="
                + URLEncoder.encode(filename, "UTF-8"));

        OutputStream os = null;
        PdfStamper ps = null;
        PdfReader reader = null;
        try {
            os = response.getOutputStream();
            // 2 读入pdf表单
            reader = new PdfReader(path + "/" + filename);
            // 3 根据表单生成一个新的pdf
            ps = new PdfStamper(reader, os);
            // 4 获取pdf表单
            AcroFields form = ps.getAcroFields();
            // 5给表单添加中文字体 这里采用系统字体。不设置的话，中文可能无法显示
            BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);

            form.addSubstitutionFont(bf);

            // 6查询数据================================================
            //设置时间转换格式
            SimpleDateFormat   sdf = new SimpleDateFormat("yyyy-MM-dd");
            //设置空的map集合，以便后边的存值以及取值
            Map<String, Object> data = new HashMap<String, Object>();
            Map<String, Object>  data1 =null;
            Map<String, Object> data2=null;
            Map<String, Object> data3=null;
            Map<String, Object> data4=null;
            if(studentNo!=null&&!studentNo.equals("")){
                //查询学生的基本信息
                data1 = studentService.getPdfStudentInfo(studentNo);
                //查询课程及注册费，押金，学费相关的信息
                applyInfo.setStudentNo(studentNo);
                data2 = applyCollegeService.selectPdfByPlanCourseStatus(applyInfo);
                //查询公共费用列表的相关信息
                studentCostInfo.setDeleteStatus(false);
                studentCostInfo.setStudentNo(studentNo);
                data3 = costService.getPdfCostById(studentCostInfo);
                //调取国内地址(并且将中文转换成拼音)
                data4 = countryAddress.getCountryAddress(studentNo);
                //调取coe接口 查询coe附件信息
            data.put("cost20",sdf.format(new Date()));//出费日期中文
            data.put("cost21",sdf.format(new Date()));//出费日期英文


            data.put("cost18",(Double.parseDouble(data2.get("finallyApplyCost").toString())+Double.parseDouble(data3.get("finallyCost").toString())));
           /* data.put("cost24","北京市朝阳区");//国内地址中文*/
            //data.put("cost25","bei jing shi chao yang qu");//国内地址英文
            //貌似只能实现url为超链接模式，至于url的样式，及颜色，下边的两个属性，不好使
            Font font = new Font();
            font.setColor(Color.red);
            font.setStyle(Font.UNDERLINE);
            //pdf中url设置为超链接
            data.put("cost19",
                    new Chunk(pdfCodeUrl+studentNo, font)
                            .setAnchor("点击支付"));

            //往pdf中赋值，fille_1指的是pdf文件中文件域的名称
            // 7遍历data 给pdf表单表格赋值
                if(data3!=null){
                    data3.putAll(data4);
                    data2.putAll(data3);
                    data1.putAll(data2);
                    data.putAll(data1);
                }

            }

            for (String key : data.keySet()) {
                form.setField(key, data.get(key).toString());

            }
            ps.setFormFlattening(true);
            //-----------------------------pdf 添加图片----------------------------------
            //读取生成的二维码
            String imgpath = request.getServletContext().getRealPath("/static/imgs/test.png");
            qrCodeService.test(imgpath,studentNo);
            //img指的是pdf文件中，文件域的名称
            // 通过域名获取所在页和坐标，左下角为起点
            int pageNo = form.getFieldPositions("img").get(0).page;
            Rectangle signRect = form.getFieldPositions("img").get(0).position;
            float x = signRect.getLeft();
            float y = signRect.getBottom();
            // 读图片
            Image image = Image.getInstance(imgpath);
            // 获取操作的页面
            PdfContentByte under = ps.getOverContent(pageNo);
            // 根据域的大小缩放图片
            image.scaleToFit(signRect.getWidth(), signRect.getHeight());
            // 添加图片
            image.setAbsolutePosition(x, y);
            under.addImage(image);
            //-------------------------------------------------------------
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ps.flush();
                ps.close();
                reader.close();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 通过学生Id查询coe申请附件
     * @param
     * @return  listCoe
     * @author huyanlong
     */
    @RequestMapping(value = {"/outer/getCoeListByStudentNo"})
    public  List<CoeApplyInfo> getCoeListByStudentNo(HttpServletResponse response, HttpServletRequest request,String  studentNo){
        CoeApplyInfo coeApplyInfo = new CoeApplyInfo();
        if(studentNo!=null){
            coeApplyInfo.setStudentNo(studentNo);
            coeApplyInfo.setDeleteStatus(false);
            List<CoeApplyInfo> listCoe=coeApplyService.getListNPA(coeApplyInfo);
            if(listCoe.size()>0){
                return  listCoe;
            }
        }
       return  null;
    }

}