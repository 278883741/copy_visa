package com.aoji.utils;

import com.aoji.controller.ChannelStudentController;
import com.aoji.model.MailEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author yangsaixing
 * @description 发送邮件工具类
 * @date Created in 上午9:20 2017/11/29
 */
public class MailUtils {
    private static final Logger logger = LoggerFactory.getLogger(MailUtils.class);
    // 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
    // PS: 某些邮箱服务器为了增加邮箱本身密码的安全性，给 SMTP 客户端设置了独立密码（有的邮箱称为“授权码”）,
    //     对于开启了独立密码的邮箱, 这里的邮箱密码必需使用这个独立密码（授权码）。
    public static String myEmailAccount = "zhaojianfei@aoji.cn";
    public static String myEmailPassword = "2691894sC3";//"mylife234,";

    // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
    // 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
    public static String myEmailSMTPHost = "smtp.exmail.qq.com";

    public static String transportProtocol="smtp";

    // 收件人邮箱（替换为自己知道的有效邮箱）
    public static String receiveMailAccount = "konglingji@aoji.cn";

    public static void main(String[] args) {
        MailEntity mailEntity=new MailEntity();
        mailEntity.setSubject("项目文件收集");
        mailEntity.setContent("请于收到邮件后的三日内提交");
        mailEntity.setSendAccount(myEmailAccount);
        mailEntity.setSendPassword(myEmailPassword);
        List<String> receives=new ArrayList<>(5);
        receives.add(receiveMailAccount);
        receives.add(myEmailAccount);
        mailEntity.setReceiveAccounts(receives);
        mailEntity.setSendName("杨赛星");
        new MailUtils().send(mailEntity);
    }
    public void send(MailEntity mailEntity){

        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", transportProtocol);   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证

        // PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
        //     如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
        //     打开下面 /* ... */ 之间的注释代码, 开启 SSL 安全连接。
        /*
        // SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
        //                  需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
        //                  QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
        */
        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);

        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        session.setDebug(true);                                 // 设置为debug模式, 可以查看详细的发送 log

        // 3. 创建一封邮件
        MimeMessage message = createSimpleMessage(session,mailEntity);
        try {
             //MimeMessage message = createNew(session, mailEntity.getSendAccount(), mailEntity.getReceiveAccounts());
            // 4. 根据 Session 获取邮件传输对象
            Transport transport = session.getTransport();
            transport.connect(mailEntity.getSendAccount(), mailEntity.getSendPassword());
            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());
            // 7. 关闭连接
            transport.close();
        } catch (Exception e) {
            logger.error("发送邮件失败:" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 创建简单的邮件
     * @param session
     * @param mailEntity
     * @return
     */
    private MimeMessage createSimpleMessage(Session session,MailEntity mailEntity) {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        try {
            // 2. From: 发件人
            message.setFrom(new InternetAddress(mailEntity.getSendAccount(), mailEntity.getSendName(), "UTF-8"));
            // 3. To: 收件人
            //CC 抄送 BCC 密送
           // message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMailAccount, "杨赛星", "UTF-8"));
            message.addRecipients(MimeMessage.RecipientType.TO,getAccounts(mailEntity.getReceiveAccounts()));
            // 4. Subject: 邮件主题
            message.setSubject(mailEntity.getSubject(), "UTF-8");
            // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
            message.setContent(mailEntity.getContent(), "text/html;charset=UTF-8");

            // 6. 设置发件时间
            message.setSentDate(new Date());

            // 7. 保存设置
            message.saveChanges();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return message;
    }

    /**
     * 获取账号地址
     * @param receiveAccounts
     * @return
     * @throws AddressException
     */
    private InternetAddress[] getAccounts(List<String> receiveAccounts) throws AddressException {
        List<InternetAddress> addressList=new ArrayList<>(receiveAccounts.size());
        for(String receiveStr:receiveAccounts){
           if(StringUtils.hasText(receiveStr)){
               addressList.add(new InternetAddress(receiveStr));
           }
        }
        InternetAddress[] addresses=(InternetAddress[])addressList.toArray(new InternetAddress[addressList.size()]);
        return addresses;
    }

    /**
     * 创建一封复杂邮件（文本+图片+附件）
     */
    private MimeMessage createMimeMessage(Session session, MailEntity mailEntity) throws Exception {
        // 1. 创建邮件对象
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人
        message.setFrom(new InternetAddress(mailEntity.getSendAccount(), mailEntity.getSendName(), "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.addRecipients(MimeMessage.RecipientType.TO,getAccounts(mailEntity.getReceiveAccounts()));
        // 4. Subject: 邮件主题
        message.setSubject(mailEntity.getSubject(), "UTF-8");
        //5. 设置
        //整封邮件的MINE消息体
         MimeMultipart msgMultipart = new MimeMultipart("mixed");//混合的组合关系
        //设置邮件的MINE消息体
        message.setContent(msgMultipart);


        //正文内容
        MimeBodyPart content = new MimeBodyPart();
        //将正文、附件加入到MINE消息体中
        msgMultipart.addBodyPart(content);

        if(!(mailEntity.getAttachments()==null && mailEntity.getAttachments().size()==0)){
           for(int i=0;i<mailEntity.getAttachments().size();i++){
               String attachmentUrl=mailEntity.getAttachments().get(i);
               if(StringUtils.hasText(attachmentUrl)){
                   continue;
               }
               MimeBodyPart attachment = new MimeBodyPart();
               msgMultipart.addBodyPart(attachment);
               //附件
               DataSource dataSource=new URLDataSource(new URL(attachmentUrl));
               attachment.setDataHandler(new DataHandler(dataSource));
           }
        }


        //设置正文内容
        MimeMultipart bodyMultipart  = new MimeMultipart("related");
        //设置内容为正文
        content.setContent(bodyMultipart);
        //html代码部分
        MimeBodyPart htmlPart = new MimeBodyPart();
        //正文添加图片和html代码
        bodyMultipart.addBodyPart(htmlPart);
        htmlPart.setContent(mailEntity.getContent(),"text/html;charset=utf-8");
        //生成文件邮件
        message.saveChanges();
        //设置发件时间
        message.setSentDate(new Date());
        return  message;
    }

}
