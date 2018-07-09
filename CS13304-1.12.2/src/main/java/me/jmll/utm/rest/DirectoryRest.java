package me.jmll.utm.rest;

import java.util.Hashtable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
public class DirectoryRest {
	
	@Autowired	
	FileService fileService;

	@RequestMapping(value = "directory", method = RequestMethod.OPTIONS)
	public ResponseEntity<?> showOptions(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Allow", "OPTIONS,GET");
		
		Map<HttpMethod,String> methods = new Hashtable<>(2);
		methods.put(HttpMethod.GET, "Lists especified directory contents in parameter 'dir'");
		methods.put(HttpMethod.OPTIONS, "Resource documentation.");
	
		OptionsDoc options = new OptionsDoc();
		options.setMethods(methods);
		
		return new ResponseEntity<>(options,headers,HttpStatus.OK);
	}
	
	
	public Map<String,Object> getFilesJSON(){}
	
	
}
