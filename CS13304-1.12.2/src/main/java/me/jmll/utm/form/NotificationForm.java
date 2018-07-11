package me.jmll.utm.form;

public class NotificationForm {

	private String subject, message, toAddress, ccAddress;
	
	public NotificationForm(String subject,String message,String toAddress,String ccAddress) {
		this.subject = subject;
		this.message = message;
		this.toAddress = toAddress;
		this.ccAddress = ccAddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getCcAddress() {
		return ccAddress;
	}

	public void setCcAddress(String ccAddress) {
		this.ccAddress = ccAddress;
	}
	
	
}
