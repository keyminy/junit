package site.metacoding.util;

import org.springframework.stereotype.Component;

//추후에 Mail클래스가 완성되면 코드를 완성하면 됨.
//@Component //Mail클래스 구현 후, MailSenderStub에 @Component는 주석
public class MailSenderAdapter implements MailSender {

//	private Mail mail;
//	
//	public MailSenderAdapter() {
//		this.mail = new Mail();
//	}
	
	@Override
	public boolean send() {
		//return mail.sendMail();
		System.out.println("mailSenderAdapter Send!!");
		return true;
	}

}