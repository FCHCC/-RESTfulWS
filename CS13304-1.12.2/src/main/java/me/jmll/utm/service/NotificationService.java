package me.jmll.utm.service;

import java.util.List;

public interface NotificationService {
	public void notify(String subject, String message, List<String> toAddress, List<String> ccAddress);
}
