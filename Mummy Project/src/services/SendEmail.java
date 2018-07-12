/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import javax.mail.Session;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
 /**
 *
 * @author syntel
 */
public class SendEmail {
    
    String messageText;
    String toAddress;
    String subject;

    public SendEmail(String subject, String messageText, String toAddress) {
        this.subject = subject;
        this.messageText = messageText;
        this.toAddress = toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }
    
    public void sendMail(){
        try{
            String username = "<USERNAME>@gmail.com";
            String password = "<PASSWORD>";
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port","587");
            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication(){
                            return new PasswordAuthentication(username,password);
                        }
                    });
            
            Address target = new InternetAddress(toAddress);
            Address from = new InternetAddress(username);
            Message msg = new MimeMessage(session);
            msg.setFrom(from);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(messageText);
            Address [] addresses = {target};
            Transport.send(msg, addresses);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.err.println("Error sending email!");
            System.exit(0);
        }
    }
}
