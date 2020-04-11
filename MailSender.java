package pl.edu.pwr.s241223;

import org.springframework.stereotype.Controller;

import com.sun.mail.smtp.SMTPTransport;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Date;
import java.util.Properties;

@Controller
public class MailSender
{
    private  String ServerSMTP = "smtp.gmail.com";
    private  String Login = "testydlaprojektu@gmail.com";
    private  String Password = "testy123";
    private  String SenderAddress = "testydlaprojektu@gmail.com";

    private  String ReceiverAddress = "testydlaprojektu@gmail.com"; // dla testów mail do samego siebie
    private  String Topic = "Test maila z apki - temat"; // temat
    private  String MessageContent = "Testuję po raz kolejny"; // tresc

    public void SendMail()
    {
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", ServerSMTP);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "587"); // 587 to port
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        try
        {
            msg.setFrom(new InternetAddress(SenderAddress));
            msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(ReceiverAddress, false));
            msg.setSubject(Topic);
            msg.setText(MessageContent);
            msg.setSentDate(new Date());
            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
            t.connect(ServerSMTP, Login, Password);
            t.sendMessage(msg, msg.getAllRecipients());
            t.close();

            System.out.println("Mail poszedl !!!");
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        }
    }

    public void SendNewBorrowMail(String userData, String itemName)
    {
        Topic = "Nowe wypozyczenie dla Uzytkownika "+ userData;
        MessageContent = "Szanowny Uzytkowniku " + userData + ", \n";
        MessageContent += "Wypożyczyłeś przedmiot: " + itemName + "\n";
        MessageContent += "Pamietaj zwrócić go w wyznaczonym terminie. \n";
        MessageContent += "Pozdrawiamy serdecznie, \n";
        MessageContent += "Zespół Toyota";
        System.out.println(Topic);
        System.out.println(MessageContent);
        SendMail();
    }

    public void SendReturnMail(String userData, String itemName)
    {
        Topic = "Nowy zwrot dla Uzytkownika "+ userData;
        MessageContent = "Szanowny Uzytkowniku " + userData + ", \n";
        MessageContent += "Zwróciłeś przedmiot: " + itemName + "\n";
        MessageContent += "Dziękujemy. \n";
        MessageContent += "Pozdrawiamy serdecznie, \n";
        MessageContent += "Zespół Toyota";
        System.out.println(Topic);
        System.out.println(MessageContent);
        SendMail();
    }

    public void SendReminderMail(String userData, String itemName)
    {
        Topic = "Przypomnienie dla Uzytkownika "+ userData;
        MessageContent = "Szanowny Uzytkowniku " + userData + ", \n";
        MessageContent += "Przypominamy konieczności zwrotu przedmiotu: " + itemName + "\n";
        MessageContent += "Pozdrawiamy serdecznie, \n";
        MessageContent += "Zespół Toyota";
        System.out.println(Topic);
        System.out.println(MessageContent);
        SendMail();
    }
}