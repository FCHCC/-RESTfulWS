package me.jmll.utm.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import me.jmll.utm.model.OptionsDoc;
import me.jmll.utm.rest.exception.ResourceNotFoundException;
import me.jmll.utm.service.FileService;
import me.jmll.utm.view.DownloadView;

@Controller
public class FileRest {
	@Autowired	
	FileService fileService;
	
	@RequestMapping(value = "file", method = RequestMethod.OPTIONS)
	public ResponseEntity<?> showOptions(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Allow", "OPTIONS,GET,POST,DELETE");
		
		Map<HttpMethod,String> methods = new Hashtable<>(4);
		methods.put(HttpMethod.DELETE,"Deletes specified file in parameter'path'");
		methods.put(HttpMethod.GET, "Downloads specified file in parameter 'path'");
		methods.put(HttpMethod.OPTIONS, "Resource documentation.");
		methods.put(HttpMethod.POST, "Uploads specified fine in parameter 'path'");
		
		OptionsDoc options = new OptionsDoc();
		options.setMethods(methods);
		
		return new ResponseEntity<>(options,headers,HttpStatus.OK);
	}
	
	@RequestMapping(value = "file", params= {"path"}, method= RequestMethod.GET)
	@ResponseBody
	public View downloadFile(@RequestParam(value="path")String path)throws IOException {
		
		if(!Files.exists(Paths.get(path))) 
			
			throw new ResourceNotFoundException(path +"does not exist.");
			
			Path file = fileService.getFile(path);
			
			return new DownloadView(file.getFileName().toString(), Files.probeContentType(file),Files.readAllBytes(file));

	}
	
	@RequestMapping(value = "file", params= {"path"},method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String deleteFile(@RequestParam(value="path")String path) {
		
		if(!Files.exists(Paths.get(path)))
			
			throw new ResourceNotFoundException(path+"does not exist");
		
		return fileService.delete(path);
				
	}
	
	@RequestMapping(value="file", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> uploadFile(@RequestParam(value="file")MultipartFile file,@RequestParam(value="name") String name,@RequestParam(value="dir")String dir){
		
		if(file.isEmpty()) {
			
			return new ResponseEntity<>(null,null,HttpStatus.BAD_REQUEST);
		}
		else {
			
			fileService.uploadFile(file, name, dir);
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Location", ServletUriComponentsBuilder.fromCurrentServletMapping().path("file/?path="+dir+"/"+name).build().toString());
		
			return new ResponseEntity<>(null,headers,HttpStatus.CREATED);
		}
	}
	
	

}
