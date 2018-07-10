package me.jmll.utm.rest;

import java.util.Hashtable;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import me.jmll.utm.model.OptionsDoc;
import me.jmll.utm.service.FileService;

@Controller
public class NotificationRest {

	FileService fileService;
	
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
}
