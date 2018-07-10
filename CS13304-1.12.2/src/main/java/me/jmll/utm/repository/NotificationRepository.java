package me.jmll.utm.repository;

import java.util.List;

import me.jmll.utm.model.Notification;

public interface NotificationRepository {

	List<Notification> getNotifications();

	Notification getNotification(String id);

	Notification createNotification(String message, String subject, List<String> toAddress, List<String> ccAddress);

	Notification updateNotification(String id, Notification notification);

	void addNotification(Notification notification);

	
}
