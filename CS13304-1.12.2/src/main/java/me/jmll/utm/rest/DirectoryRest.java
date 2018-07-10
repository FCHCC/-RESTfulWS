package me.jmll.utm.rest;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import me.jmll.utm.model.File;
import me.jmll.utm.model.FileLinkListResource;
import me.jmll.utm.model.Link;
import me.jmll.utm.model.OptionsDoc;
import me.jmll.utm.rest.exception.ResourceNotFoundException;
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
	
	@RequestMapping(value ="directory",params= "dir", method=RequestMethod.GET,produces = { "application/json", "text/json" })
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Map<String,Object> getFilesJSON(@RequestParam(value="dir") String dir){
		
		Path path = Paths.get(dir);
		if(!Files.exists(path)) {
			throw new ResourceNotFoundException(path +"does not exist.");
		}
		
		List<Path> paths = new ArrayList<Path>();
		
		List<File> files = new ArrayList<File>();
		paths = fileService.walkDir(path, paths);
		
		paths.forEach(file ->{
			
			files.add(new File(file.getFileName().toString(),
					path.toAbsolutePath().toString().replaceAll("\\\\", "/"),
					file.toAbsolutePath().toString().replaceAll("\\\\", "/"),
					file.toFile().length(),
					new Link(ServletUriComponentsBuilder.fromCurrentServletMapping()
							.path("/file/?path="+file.toAbsolutePath()).build().toString().replaceAll("\\\\", "/"),
							"download")));		
		});
		
		List<Link> _links = new ArrayList<Link>();
		
		 _links.add(new Link(ServletUriComponentsBuilder
				 .fromCurrentServletMapping().path("/")
				 .build().toString(), "api"));
				 _links.add(new Link(ServletUriComponentsBuilder
				 .fromCurrentServletMapping().path("/directory")
				 .build().toString(), "self"));
				 
				 Map<String, Object> response = new Hashtable<>(2);
				 response.put("_links", _links);
				 response.put("data", files);
				 
				 return response;
	}
	
	@RequestMapping(value = "directory", 
			params= "dir",
			method = RequestMethod.GET,
			produces = { "application/xml", "text/xml" })
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public FileLinkListResource getFilesXML(@RequestParam(value="dir") String dir) {
				
		Path path = Paths.get(dir);
		if(!Files.exists(path)) {
			throw new ResourceNotFoundException(path +"does not exist.");
		}
	
		List<Path> paths = new ArrayList<Path>();
		
		List<File> files = new ArrayList<File>();

		paths.forEach(file ->{
			
			files.add(new File(file.getFileName().toString(),
					path.toAbsolutePath().toString().replaceAll("\\\\", "/"),
					file.toAbsolutePath().toString().replaceAll("\\\\", "/"),
					file.toFile().length(),
					new Link(ServletUriComponentsBuilder.fromCurrentServletMapping()
							.path("/file/?path="+file.toAbsolutePath()).build().toString().replaceAll("\\\\", "/"),
							"download")));		
		});		

		FileLinkListResource filesLinksResource = new FileLinkListResource();
		List<Link> _links = new ArrayList<Link>();
		
		_links.add(new Link(ServletUriComponentsBuilder
		 .fromCurrentServletMapping().path("/")
		 .build().toString(), "api"));
		
		_links.add(new Link(ServletUriComponentsBuilder
		 .fromCurrentServletMapping().path("/directory")
		.build().toString(), "self"));
		
		filesLinksResource.setLinks(_links);
		filesLinksResource.setFiles(files);
		
		return filesLinksResource;

	}
	
	
}
