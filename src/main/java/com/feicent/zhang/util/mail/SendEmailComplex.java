package com.feicent.zhang.util.mail;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * Java实现邮件发送--复杂版
 * @author yzuzhang
 * @date 2017年10月10日 下午2:01:25
 * http://www.marsitman.com/java/java_send_complexemail.html
 */
public class SendEmailComplex {
	
    public static String queenEmailAccount = "♦♦♦♦♦♦♦♦@163.com";

    public static String queenEmailPassword = "♦♦♦♦♦♦♦♦";

    public static String queenEmailSMTPHost = "smtp.163.com";

    // 主收件人
    public static String receiveMailAccount = "♦♦♦♦♦♦♦♦@qq.com";

    // 抄送人
    public static String ccMailAccount = "♦♦♦♦♦♦♦♦@qq.com";

    // 密送人
    public static String sccMailAccount = "♦♦♦♦♦♦♦♦@qq.com";

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        // 设置邮件传输协议
        props.setProperty("mail.transport.protocol", "smtp");
        // 设置发送人的邮件服务器
        props.setProperty("mail.smtp.host", queenEmailSMTPHost);
        // 设置请求认证
        props.setProperty("mail.smtp.auth", "true");
        // 创建会话,用于和邮件服务器交互
        Session session = Session.getDefaultInstance(props);
        // true：控制台显示调试信息;false：控制不会显示调试信息
        session.setDebug(true);
        // 创建一封邮件
        MimeMessage message = createMessage(session, queenEmailAccount, receiveMailAccount, ccMailAccount,
                sccMailAccount);
        // 从Session中获取邮件传输对象
        Transport transport = session.getTransport();
        // 使用邮箱账号 和 密码 连接邮件服务器
        transport.connect(queenEmailAccount, queenEmailPassword);
        // 发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        // 关闭连接
        transport.close();
    }

    public static MimeMessage createMessage(Session session, String sendMail, String receiveMailAccount,
            String ccMailAccount, String sccMailAccount) throws Exception {
        MimeMessage message = new MimeMessage(session);
        // 设置发信人以及显示的昵称
        message.setFrom(new InternetAddress(sendMail, "发件人昵称", "UTF-8"));
        // 设置接收人以及显示的昵称
        message.addRecipient(RecipientType.TO, new InternetAddress(receiveMailAccount, "收件人昵称", "UTF-8"));
        // 设置抄送人以及显示的昵称
        message.addRecipient(RecipientType.CC, new InternetAddress(ccMailAccount, "昵称", "UTF-8"));
        // 设置密送人以及显示的昵称
        message.addRecipient(RecipientType.BCC, new InternetAddress(sccMailAccount, "昵称", "UTF-8"));
        // 设置邮件的主题
        message.setSubject("邮件主题（文本+图片+附件）", "UTF-8");

        // 创建图片"节点"
        MimeBodyPart image = new MimeBodyPart();
        // 读取本地文件
        DataHandler dataHandlerImage = new DataHandler(new FileDataSource(
                "C:\\Users\\think\\Desktop\\timg.jpg"));
        // 将图片数据添加到"节点"
        image.setDataHandler(dataHandlerImage);
        // 为"节点"设置一个唯一编号（在文本"节点"引用该ID）
        image.setContentID("timg");

        // 创建文本"节点"
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("您好，****觉得您很符合本公司的招聘，特邀您于下周一到我司面试，如收到邮件请回复，谢谢！：<br/><img src='cid:timg'/>" , "text/html;charset=UTF-8");

        // 将 文本和 图片 "节点"合成一个混合"节点"
        MimeMultipart text_image_Multipart = new MimeMultipart();
        text_image_Multipart.addBodyPart(text);
        text_image_Multipart.addBodyPart(image);
        text_image_Multipart.setSubType("related"); // 关联关系

        // 将 文本+图片 的混合 "节点"封装成一个普通 "节点"
        MimeBodyPart text_image_MimeBodyPart = new MimeBodyPart();
        text_image_MimeBodyPart.setContent(text_image_Multipart);

        // 创建附件"节点"
        MimeBodyPart attachment = new MimeBodyPart();
        // 讀取本地文件
        DataHandler dataHandlerAttach = new DataHandler(new FileDataSource(
                "C:\\Users\\think\\Desktop\\Redis.docx"));
        // 将附件数据添加到"节点"
        attachment.setDataHandler(dataHandlerAttach);
        // 设置附件的文件名
        attachment.setFileName(MimeUtility.encodeText(dataHandlerAttach.getName()));

        // 將(文本+图片)和 附件 的关系（合成一个大的混合"节点"）
        MimeMultipart mimeMultipart = new MimeMultipart();
        mimeMultipart.addBodyPart(text_image_MimeBodyPart);
        // 如果有多个附件，可以创建多个多次添加
        mimeMultipart.addBodyPart(attachment);
        mimeMultipart.setSubType("mixed");

        // 设置邮件的正文
        message.setContent(mimeMultipart);
        // 设置发件时间
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }
    
}
