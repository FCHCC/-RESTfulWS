package me.jmll.utm.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
/**
 * Implementa FileService y es marcado como servicio 
 * por org.springframework.stereotype.Service
 * */
@Service
public class FileServiceImpl implements FileService {
	private static final Logger logger = LogManager.getLogger();
	/**
	 * Obtiene la referencia al objeto tipo java.nio.file.Path
	 * por medio de un *método* la clase java.nio.file.Paths
	 * y regresa el archivo
	 * */
	@Override
	public Path getFile(String fileName) {
		Path file = Paths.get(fileName);
		return file;
	}
	
	public List<Path> walkDir(Path path, List<Path> paths){		
		try(DirectoryStream<Path> stream = Files.newDirectoryStream(path)){
			for(Path p: stream){
				if (Files.isDirectory(p)){
					walkDir(p, paths);
				}
				paths.add(p);
			}
		} catch (IOException | DirectoryIteratorException ex){
			logger.error("{}: {}", ex.getClass(), ex.getMessage());
		}
		return paths;
	}
	/**
	 * Valida que el path destino con java.nio.file.Files
	 * si no existe crea el directorio
     * Utiliza FileCopyUtils de spring para escribir el contenido
     * en el path indicado
     * 
	 * Contiene la misma lógica para subir archivos que 
	 *  en UploadController. 
	 * */
	@Override
	public boolean uploadFile(MultipartFile file, String name, String path) {
		try {
        	Path filePath = Paths.get(path);
        	if (Files.notExists(filePath)){
        		logger.warn("Target path does not exist. Creating {}", path);
        		Files.createDirectory(filePath);
        	}
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(new File(filePath.toString() + File.separator + name)));
            FileCopyUtils.copy(file.getInputStream(), stream);
            stream.close();
            logger.info("Successfully uploaded {} ", filePath.toString() + File.separator + name);
            return true;
        }
        catch (Exception ex) {
        	logger.error("{}: {}", ex.getClass(), ex.getMessage());
        }
		return false;
	}

}
