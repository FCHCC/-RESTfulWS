package me.jmll.utm.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import me.jmll.utm.service.FileService;

@Controller
public class UploadController {
	private static final Logger logger = LogManager.getLogger();
	@Autowired
	private FileService fileService;

	@RequestMapping(value = "upload", 
				    method = RequestMethod.GET)
	public String upload(Map<String, Object> model){
		logger.info("Upload requested file.");
		return "upload/file";
	}
	/**
	 * valida que el archivo MultiPartFile no sea vacío.
	 * Si no lo es, llama al método uploadFile del 
	 * servicio fileService enviando los atributos necesarios.
	 * El resultado se guarda en una variable boolean llamada
	 * status y si es verdadero se agrega un mensaje a la lista
	 * warnings.
	 * */
	@RequestMapping(value = "upload",
					method = RequestMethod.POST)
	public String fileUpload(Map<String, Object> model, 
			HttpSession session, HttpServletRequest request,
			@RequestParam("name") String name,
			@RequestParam("path") String path,
            @RequestParam("file") MultipartFile file) throws IOException {
		logger.debug("Uploading attachment");
		List<String> warnings = new ArrayList<String>();
		List<String> errors = new ArrayList<String>();
		if (!file.isEmpty()) {
            boolean status = fileService.uploadFile(file, name, path);
            if (status){
            	warnings.add(String.format("Successfully uploaded %s to %s", name, path));
            }
        }
        else {
        	String message = String.format("Failed to upload file %s to %s. File was empty.", name, path);
        	errors.add(message);
        	logger.info(message);
        }
		
        model.put("warnings", warnings);
        model.put("errors", errors);
		return "upload/done";
	}
}
