package com.ssm.demo.service;

import com.ssm.demo.rabbitmq.MailProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Properties;

/**
 * @author o_0sky
 * @date 2019/5/28 15:33
 */
@Service
public class MailService {

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private Environment env;

    public void sendEmail()throws Exception{
        Properties properties = new Properties();
        properties.setProperty("mail.host",mailProperties.getHost());
        properties.setProperty("mail.transport.protocol",mailProperties.getProtocol());
        properties.setProperty("mail.smtp.auth",mailProperties.getNeedAuth());
        properties.setProperty("mail.smtp.socketFactory.class", mailProperties.getSslClass());
        properties.setProperty("mail.smtp.port", mailProperties.getPort()+"");

        /*Session session = Session.getDefaultInstance(properties);
        session.setDebug(true);*/ //第一种写法

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailProperties.getUserName(),mailProperties.getPassword());
            }
        };

        Session session = Session.getInstance(properties,auth); //第二种写法

        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(env.getProperty("mail.from"));
        mimeMessage.setSubject(MimeUtility.encodeText(env.getProperty("mail.subject"),"UTF-8","B"));
        mimeMessage.setContent(env.getProperty("mail.content"), "text/plain ;charset=UTF-8");

        //TODO：批量发送多个收件人
        String arr[] = env.getProperty("mail.to").split(",");
        Address[] addresses = new Address[arr.length];
        for(int i=0;i<arr.length;i++){
            addresses[i] = new InternetAddress(arr[i]);
        }
        mimeMessage.addRecipients(Message.RecipientType.CC,env.getProperty("mail.from"));
        mimeMessage.addRecipients(Message.RecipientType.TO,addresses);

        //TODO：只发送一个收件人
//        mimeMessage.addRecipients(Message.RecipientType.TO, "1132676182@qq.com");
//        mimeMessage.addRecipients(Message.RecipientType.CC, "shenjiayu951023@163.com");

        Transport transport = session.getTransport();
        transport.connect(mailProperties.getHost(),mailProperties.getUserName(),mailProperties.getPassword());
        transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());
        transport.close();
    }
}
