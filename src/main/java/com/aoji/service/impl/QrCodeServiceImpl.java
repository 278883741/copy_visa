package com.aoji.service.impl;

import com.aoji.service.QrCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Service
public class QrCodeServiceImpl implements QrCodeService{
    //生成二维码的url
    @Value("${pdf.code.url}")
    private String pdfCodeUrl;
    @Override
    public void test(String filePath,String studentNo) throws WriterException {


        String url =pdfCodeUrl+studentNo;

        String logoPath =QrCodeServiceImpl.class.getResource("/static/imgs/logo.png").getPath();

        try {
            File file = new File(filePath);

            QrCodeServiceImpl zp = new QrCodeServiceImpl();

            BufferedImage bim = zp.getQR_CODEBufferedImage(url,
                    BarcodeFormat.QR_CODE, 300, 300, zp.getDecodeHintType());

            ImageIO.write(bim, "jpeg", file);

            zp.addLogo_QRCode(file,logoPath,new LogoConfig1());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 给二维码图片添加Logo
     *
     * @param qrPic
     * @param
     */
    public void addLogo_QRCode(File qrPic, String logoPath, LogoConfig1 logoConfig) {
        try {
            /**
             * 读取二维码图片，并构建绘图对象
             */
            BufferedImage image = ImageIO.read(qrPic);
            if(StringUtils.hasText(logoPath)){
                drawLogo(logoPath, image, logoConfig);
            }



            ImageIO.write(image, "png", qrPic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绘制logo
     * @param
     * @param image
     * @param logoConfig
     * @throws IOException
     */
    private static void drawLogo(String logoPath, BufferedImage image,
                                 LogoConfig1 logoConfig) throws IOException {
        Graphics2D g = image.createGraphics();
        /**
         * 读取Logo图片
         */
        BufferedImage logo = ImageIO.read(new File(logoPath));
        /**
         * 设置logo的大小,本人设置为二维码图片的20%,因为过大会盖掉二维码
         */
        int widthLogo = logo.getWidth(null) > image.getWidth() * 3 / 10 ? (image
                .getWidth() * 3 / 10) : logo.getWidth(null), heightLogo = logo
                .getHeight(null) > image.getHeight() * 3 / 10 ? (image
                .getHeight() * 3 / 10) : logo.getHeight(null);

        // 计算图片放置位置
        // logo放在中心
        int x = (image.getWidth() - widthLogo) / 2;
        int y = (image.getHeight() - heightLogo) / 2;
        // logo放在右下角
		/*
		 * int x = (image.getWidth() - widthLogo); int y = (image.getHeight() -
		 * heightLogo);
		 */

        // 开始绘制图片
        g.drawImage(logo, x, y, widthLogo, heightLogo, null);
        g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
        g.setStroke(new BasicStroke(logoConfig.getBorder()));
        g.setColor(logoConfig.getBorderColor());
        g.drawRect(x, y, widthLogo, heightLogo);
        g.dispose();

    }

    /**
     * 生成二维码bufferedImage图片
     *
     * @param content       编码内容
     * @param barcodeFormat 编码类型
     * @param width         图片宽度
     * @param height        图片高度
     * @param hints         设置参数
     * @return
     */
    public BufferedImage getQR_CODEBufferedImage(String content,
                                                 BarcodeFormat barcodeFormat, int width, int height,
                                                 Map<EncodeHintType, ?> hints) {
        MultiFormatWriter multiFormatWriter = null;
        BitMatrix bm = null;
        BufferedImage image = null;
        try {
            multiFormatWriter = new MultiFormatWriter();
            // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            bm = multiFormatWriter.encode(content, barcodeFormat, width,
                    height, hints);

            int w = bm.getWidth();
            int h = bm.getHeight();
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

            // 开始利用二维码数据创建Bitmap图片，分别设为白（0xFFFFFFFF）黑（0xFF000000）两色
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    // image.setRGB(x, y, bm.get(x, y) ? 0xFF000000 :
                    // 0xFFCCDDEE);//
                    image.setRGB(x, y, bm.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);

                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 设置二维码的格式参数
     *
     * @return
     */
    public Map<EncodeHintType, Object> getDecodeHintType() {
        // 用于设置QR二维码参数
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        // 设置QR二维码的纠错级别（H为最高级别）具体级别信息
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 设置编码方式
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.MAX_SIZE, 350);
        hints.put(EncodeHintType.MIN_SIZE, 100);

        return hints;
    }
}

class LogoConfig1 {
    // logo默认边框颜色
    public static final Color DEFAULT_BORDERCOLOR = Color.WHITE;
    // logo默认边框宽度
    public static final int DEFAULT_BORDER = 2;
    // logo大小默认为照片的1/5
    public static final int DEFAULT_LOGOPART = 5;

    private final int border = DEFAULT_BORDER;
    private final Color borderColor;
    private final int logoPart;


    public LogoConfig1() {
        this(DEFAULT_BORDERCOLOR, DEFAULT_LOGOPART);
    }

    public LogoConfig1(Color borderColor, int logoPart) {
        this.borderColor = borderColor;
        this.logoPart = logoPart;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public int getBorder() {
        return border;
    }

    public int getLogoPart() {
        return logoPart;
    }
}

