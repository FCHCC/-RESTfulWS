package me.jmll.utm.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Notification implements Serializable{

	private static final long serialVersionUID = 1L;
	private String subject, message, messageId, status;
	private List<String> toAddress,ccAddress;

	public Notification(){
		this.messageId = UUID.randomUUID().toString();
	}
	
	public Notification(String message,String subject,List<String>toAddress, List<String>ccAddress) {
	
		this.message = message;
		this.messageId = UUID.randomUUID().toString();
		this.subject = subject;
		this.toAddress = toAddress;
		this.ccAddress = ccAddress;
	}
	
	public Notification(String message, String subject, List<String>toAddress) {
		this.message= message;
		this.subject = subject;
		this.toAddress = toAddress;
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

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getToAddress() {
		return toAddress;
	}

	public void setToAddress(List<String> toAddress) {
		this.toAddress = toAddress;
	}
	
	public List<String> getCcAddress() {
		return ccAddress;
	}

	public void setCcAddress(List<String> ccAddress) {
		this.ccAddress = ccAddress;
	}
	
	public String toString() {

		return String.format(
				"[id: %s: %s] to: %s; cc: %s; subject: %s; message: %s",
				this.getMessageId(), 
				this.getStatus(), 
				this.getToAddress(), 
				this.getCcAddress(), 
				this.getSubject(), 
				this.getMessage());

	}
	
}
