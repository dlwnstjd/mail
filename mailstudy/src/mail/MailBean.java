package mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailBean {
	private String sendid;	//보내는 사람 메일 주소
	private String sendpw;	//보내는 사람 인증 비밀번호
	private String recipient;	//받는사람	
	private String title;		//제목
	private String contents;	//내용
	private String mtype;		//문서 형식
	private String err_msg;
	
	public boolean sendMail() {
		//Properties: Map 구현 클래스
		//			  Hashtable 클래스의 하위 클래스
		//			  (key:String, value:String) Map 클래스
		Properties prop = new Properties();
		//메일 전송 서버에 설정 값
		prop.put("mail.smtp.host", "smtp.naver.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.auth", "true");	//인증받은메일만가능
		prop.put("mail.debug", "true");	//개발모드
		prop.put("mail.smtp.user", "sendid");	//네이버아이디
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", 
						"javax.net.ssl.SSLSocketFactory");
		prop.put("mail.smtp.socketFactory.fallback", "false");
		MyAuthenticator auth =  new MyAuthenticator(sendid, sendpw);
		Session session = Session.getInstance(prop,auth);
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(sendid));
			InternetAddress[] address =
				{new InternetAddress(recipient) };
			//받는 메일 정보
			//essage.RecipientType.TO: 받는메일
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(title);
			msg.setSentDate(new Date());
			msg.setText(contents);//내용
			MimeMultipart multipart = new MimeMultipart();
			MimeBodyPart body = new MimeBodyPart();
			body.setContent(contents, mtype);
			multipart.addBodyPart(body);
			msg.setContent(multipart);	//내용
			Transport.send(msg);	//메일 전송
		}catch(MessagingException me) {
			System.out.println(me.getMessage());
			me.printStackTrace();
			return false;
		}
		return true;
	}
	
	public final class MyAuthenticator extends Authenticator{
		private String id;
		private String pw;
		
		public MyAuthenticator(String id, String pw) {
			this.id = id;
			this.pw = pw;
		}
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(id, pw);
		}
	}

	public String getSendid() {
		return sendid;
	}

	public void setSendid(String sendid) {
		this.sendid = sendid;
	}

	public String getSendpw() {
		return sendpw;
	}

	public void setSendpw(String sendpw) {
		this.sendpw = sendpw;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getMtype() {
		return mtype;
	}

	public void setMtype(String mtype) {
		this.mtype = mtype;
	}

	public String getErr_msg() {
		return err_msg;
	}

	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
	
	
}
