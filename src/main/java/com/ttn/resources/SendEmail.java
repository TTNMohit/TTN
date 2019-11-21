package com.ttn.resources;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class SendEmail {

	private static String USER_NAME = FetchData.readConfig("gmailUsername");
	private static String PASSWORD = FetchData.readConfig("gmailPassword");

	public void createZip1(String module, String path) {
		try {
			String OUTPUT_ZIP_FILE = Variables.extinctReportsPath + "\\"
					+ module + ".zip";
			String SOURCE_FOLDER = path;
			Zip.zipDir(SOURCE_FOLDER, OUTPUT_ZIP_FILE);
		} catch (Exception e) {
		}
	}

	public void sendFromGMail(String clas, String path, String email) {
		createZip1(clas, path);
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", USER_NAME);
		props.put("mail.smtp.password", PASSWORD);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(USER_NAME));
			message.addRecipients(Message.RecipientType.TO, email);
			message.setSubject(clas.toUpperCase() + " : Execution Logs");
			StringBuilder sb = new StringBuilder();
			sb.append("SHIKSHA EXECUTION LOGS FOR BELOW SCRIPTS :");
			sb.append("\n");
			sb.append(System.getProperty("line.separator"));
			int i = 1;

			sb.append(i + "." + clas);
			sb.append("\n");
			sb.append(System.getProperty("line.separator"));

			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setText(sb.toString());
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			String filename = Variables.extinctReportsPath + "\\" + clas
					+ ".zip";

			DataSource source = new FileDataSource(filename);
			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName(filename);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			multipart.addBodyPart(messageBodyPart2);
			message.setContent(multipart);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, USER_NAME, PASSWORD);
			//transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			System.out.println("Mail excep" + e.toString());
			// // Do noting
		}
	}
}
