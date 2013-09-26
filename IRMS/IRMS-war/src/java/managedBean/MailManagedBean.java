/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;
import java.io.Serializable;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.mail.MessagingException;

/**
 *
 * @author WU JINGYUN
 */
@Named(value = "mailManagedBean")
@RequestScoped
public class MailManagedBean implements Serializable {

    /**
     * Creates a new instance of MailManagedBean
     */
    String to;
String from;
String message;
String subject;
String smtpServ;
    public MailManagedBean() {
    }
    
    public void sendEmail() throws MessagingException{
          System.out.println("match password ======================================0");
    Properties props = new Properties();
props.setProperty("mail.smtp.auth", "true");//必须 普通客户端
  System.out.println("match password ======================================1");
props.setProperty("mail.transport.protocol", "smtp");//必须选择协议
  System.out.println("match password ======================================2");
Session session = Session.getDefaultInstance(props);
session.setDebug(true);//设置debug模式   在控制台看到交互信息
Message msg = new MimeMessage(session);  //建立一个要发送的信息
msg.setText("li72  welcome ");//设置简单的发送内容
System.out.println("match password ======================================3");
msg.setFrom(new InternetAddress("wujingyun1128@gmail.com"));//发件人邮箱号
Transport transport = session.getTransport();//发送信息的工具
transport.connect("smtp.gmail.com", 25, "wujingyun1128@gmail.com", "Kurt1314aw");//发件人邮箱号 和密码
System.out.println("match password ======================================4");
transport.sendMessage(msg, new Address[] { new InternetAddress(
"A0092208@NUS.EDU.SG") });//对方的地址
System.out.println("match password ======================================5");
transport.close();}
    
}
