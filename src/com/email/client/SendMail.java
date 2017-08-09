package com.email.client;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.util.ArrayList;
import java.util.Properties;

public class SendMail {

	String host = "smtp.gmail.com";
	Properties props;

	public SendMail() {
		props = System.getProperties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtps.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
	}

	public boolean sendMail(String from, String Password,ArrayList<String> mailList, String sub, String body, String attachmentFile) {
		Session session = Session.getInstance(props, null);
		java.io.File file = new java.io.File(attachmentFile);
		long filesize = file.length();
		long filesizeInKB = filesize / 1024;
		double megabytes = (filesizeInKB / 1024);
		System.out.println("Size of File is: " + filesizeInKB + " KB");
		System.out.println("Size of File is: " + megabytes + " MB");
		double size1 = 20.1;
		if (megabytes > size1)
			System.out.println("File Size Must Below 20 MB");
		else {
			try {
				javax.mail.internet.MimeMessage message = new javax.mail.internet.MimeMessage(session);
				message.setFrom(new InternetAddress(from));

				String address = new String();
				for (String temp : mailList)
					address = address + "," + temp;

				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address));

				message.setSubject(sub);
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setText(body);
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);
				messageBodyPart = new MimeBodyPart();
				if (file.isFile()) {
					DataSource source = new FileDataSource(attachmentFile);
					messageBodyPart.setDataHandler(new DataHandler(source));
					
					messageBodyPart.setFileName(attachmentFile.substring(attachmentFile.lastIndexOf("\\")+1));
				}
				multipart.addBodyPart(messageBodyPart);
				message.setContent(multipart);

				javax.mail.Transport tr = session.getTransport("smtps");
				tr.connect(host, from, Password);
				System.out.println("Connected to Server......");
				System.out.println("Now Sending " + attachmentFile);
				tr.sendMessage(message, message.getAllRecipients());
				tr.close();

			} catch (Exception ex) {
				return false;
			}

			System.out.println("Mail Sent Successfully........");

		}
		return true;
	}
	
	public boolean ifNotAttachmentSendMail(String from,String Password,ArrayList<String> mailList, String sub, String body) {
		Session session = Session.getDefaultInstance(props);  

			try {
				String address = new String();
				for (String temp : mailList)
					address = address + "," + temp;

				MimeMessage message = new MimeMessage(session);  
		         message.setFrom(new InternetAddress(from));  
		         message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(address));  
		         message.setSubject(sub);  
		         message.setText(body);  
					javax.mail.Transport tr = session.getTransport("smtps");
					tr.connect(host, from, Password);
					System.out.println("Connected to Server{}......");

					tr.sendMessage(message, message.getAllRecipients());
					tr.close();

		         System.out.println("message sent successfully...."); 

			} catch (Exception ex) {
				System.out.println("ERROR" + ex.toString());
				return false;
			}

			System.out.println("Mail Sent Successfully........");


		return true;
	}

	

}
