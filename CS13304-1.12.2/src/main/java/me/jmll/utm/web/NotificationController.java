package me.jmll.utm.web;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import me.jmll.utm.service.NotificationService;
import me.jmll.utm.view.NotificationView;

@Controller
public class NotificationController {
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	NotificationService notificationService;
	
	@RequestMapping(value = "notify",
			method = RequestMethod.POST)
	@ResponseBody
	public View notify(@RequestParam("subject") String subject,
					   @RequestParam("message") String message,
					   @RequestParam("toAddress") String toAddress,
					   @RequestParam("ccAddress") String ccAddress){
		logger.info("Notification URI requested");
		notificationService.notify(subject, message,
				Arrays.asList(toAddress.split(";")), 
				Arrays.asList(ccAddress.split(";")));
		return new NotificationView("text/html");
	}

}
