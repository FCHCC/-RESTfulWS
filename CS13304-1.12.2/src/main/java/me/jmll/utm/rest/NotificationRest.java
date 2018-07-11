package me.jmll.utm.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import me.jmll.utm.form.NotificationForm;
import me.jmll.utm.model.Link;
import me.jmll.utm.model.NotificationLinkListResource;
import me.jmll.utm.model.OptionsDoc;
import me.jmll.utm.service.NotificationService;

@Controller
public class NotificationRest {
	
	private static final Logger logger = LogManager.getLogger();
	NotificationService notifications;
	
	@RequestMapping(value = "notify", method = RequestMethod.OPTIONS)
	public ResponseEntity<?> showOptions(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Allow", "OPTIONS,GET,POST");
		
		Map<HttpMethod,String> methods = new Hashtable<>(3);
		methods.put(HttpMethod.GET, "Lists notifications submited.");
		methods.put(HttpMethod.OPTIONS, "Resource documentation.");
		methods.put(HttpMethod.POST, "Submits notification to send.");
	
		OptionsDoc options = new OptionsDoc();
		options.setMethods(methods);
		
		return new ResponseEntity<>(options,headers,HttpStatus.OK);
	}
	
	@RequestMapping(value="notify",
					method= RequestMethod.GET,
					produces = {"application/json", "text/json"})
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Map<String,Object> GetNotificationsJSON() {
		
		List<Link> _links = new ArrayList<Link>();
		
		_links.add(new Link(ServletUriComponentsBuilder
		 .fromCurrentServletMapping().path("/")
		 .build().toString(), "api"));
		
		_links.add(new Link(ServletUriComponentsBuilder
		 .fromCurrentServletMapping().path("/notify/")
		 .build().toString(), "self"));
		
		Map<String, Object> response = new Hashtable<>(2);
		response.put("_links", _links);
		response.put("data", notifications);
		return response;

	}
	
	@RequestMapping(value="notify",
					method= RequestMethod.GET,
					produces= {"application/xml", "text/xml"})
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public NotificationLinkListResource getNotificationsXML() {
		
		NotificationLinkListResource notificationLinksResource = new NotificationLinkListResource();
		List<Link> _links = new ArrayList<Link>();
		
		_links.add(new Link(ServletUriComponentsBuilder
		 .fromCurrentServletMapping().path("/")
		 .build().toString(), "api"));
		
		_links.add(new Link(ServletUriComponentsBuilder
		 .fromCurrentServletMapping().path("/notify/")
		 .build().toString(), "self"));
		
		notificationLinksResource.setLinks(_links);
		notificationLinksResource.setNotifications(notifications.getNotifications());
		
		return notificationLinksResource;

	
	}
	
	@RequestMapping(value ="notify",
					method= RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> Notify(NotificationForm notification){
		
		try {
				List<String> toAddress = Arrays.asList(notification.getToAddress().split(";"));
				List<String> ccAddress = new ArrayList<>();
				
				if(notification.getCcAddress() != null) {
					ccAddress = Arrays.asList(notification.getCcAddress().split(";"));
				}
				
				notifications.notify(notification.getSubject(), notification.getMessage(), toAddress, ccAddress);
				
				return new ResponseEntity<>(null,null,HttpStatus.ACCEPTED);
				
		}catch(Exception e){
			
			logger.error(e.getMessage());
			return new ResponseEntity<>(null,null,HttpStatus.EXPECTATION_FAILED);
		}
		
		
	}
	
	
	
	
	
	
}
