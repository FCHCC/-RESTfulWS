package me.jmll.utm.rest;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

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

import me.jmll.utm.model.Link;
import me.jmll.utm.model.OptionsDoc;
import me.jmll.utm.service.NotificationService;

@Controller
public class NotificationRest {

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
	
	
	
	
	
	
}
