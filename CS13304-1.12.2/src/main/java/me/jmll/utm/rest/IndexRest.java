package me.jmll.utm.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import me.jmll.utm.model.Link;
import me.jmll.utm.model.OptionsDoc;
import me.jmll.utm.model.Resource;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Controller
public class IndexRest {
	
	@RequestMapping(value= {"","/"}, method=RequestMethod.OPTIONS)
	public ResponseEntity<?> showOptions(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Allow", "OPTIONS,GET");
		
		Map<HttpMethod,String> methods = new Hashtable<>(2);
		methods.put(HttpMethod.GET, "Downloads specified file in parameter 'path'");
		methods.put(HttpMethod.OPTIONS, "Resource documentation.");

		
		OptionsDoc options = new OptionsDoc();
		options.setMethods(methods);
		
		return new ResponseEntity<>(options,headers,HttpStatus.OK);
	}
	
	/**
	 * Genera indice con Hypermedia para JSON
	 * */
	@RequestMapping(value = { "", "/" }, 
			method = RequestMethod.GET, 
			produces = { "application/json", "text/json" })
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> indexJson() {
		ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentServletMapping();
		List<Link> links = new ArrayList<Link>();
		links.add(new Link(builder.path("/").build().toString(), "self"));
		links.add(new Link(builder.path("/user/").build().toString(),"user"));
		links.add(new Link(builder.path("/directory/").build().toString(),"directory"));
		links.add(new Link(builder.path("/file/").build().toString(),"file"));
		links.add(new Link(builder.path("/notify/").build().toString(),"notify"));
		
		Map<String, Object> response = new Hashtable<>(1);
		response.put("_links", links);
		response.put("version", "1");
		return response;
	}
	
	/**
	 * Genera indice con Hypermedia para XML
	 * */
	@RequestMapping(value = { "", "/" },
			method = RequestMethod.GET, 
			produces = { "application/xml", "text/xml" })
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Resource indexXml() {
		ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentServletMapping();
		Resource resource = new Resource();
		resource.addLink(new Link(builder.path("/").build().toString(), "self"));
		resource.addLink(new Link(builder.path("/user/").build().toString(),"user"));
		resource.addLink(new Link(builder.path("/directory/").build().toString(),"directory"));
		resource.addLink(new Link(builder.path("/file/").build().toString(),"file"));
		resource.addLink(new Link(builder.path("/notify/").build().toString(),"notify"));
		return resource;
	}

}
