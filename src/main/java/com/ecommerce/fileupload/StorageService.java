package com.ecommerce.fileupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {

	/*
	 * Create StorageService with 4 functions: – public void store(MultipartFile
	 * file): save a file – public Resource loadFile(String filename): load a file –
	 * public void deleteAll(): remove all uploaded files – public void init():
	 * create upload directory
	 */
	
	
	 Logger log = LoggerFactory.getLogger(this.getClass().getName());
	   Path rootLocation = Paths.get("upload-dir");
	  
	  public void store(MultipartFile file,String uniqueId) {
		    try {
		    	  String fileFrags = file.getOriginalFilename();
		    	      //    String extension = fileFrags[fileFrags.length-1];
		     // Files.copy(file.getInputStream(), this.rootLocation.resolve(uniqueId));
		    		byte[] bytes = file.getBytes();
				//	Path path = Paths.get(baseDirectoryPath + physicalFileName);
		    		  rootLocation = Paths.get("upload-dir",uniqueId);
					Files.write(this.rootLocation, bytes);
		    	 // File file2=new File(this.rootLocation+(uniqueId));
		    	
		    } catch (Exception e) {
		      throw new RuntimeException("FAIL!");
		    }
		  }
	  
	  public Resource loadFile(String uniqueId) {
		    try {
		      Path file = rootLocation.resolve(uniqueId);
		      Resource resource = new UrlResource(file.toUri());
		      if (resource.exists() || resource.isReadable()) {
		        return resource;
		      } else {
		        throw new RuntimeException("FAIL!");
		      }
		    } catch (MalformedURLException e) {
		      throw new RuntimeException("FAIL!");
		    }
		  }
		 
		  public void deleteAll() {
		    FileSystemUtils.deleteRecursively(rootLocation.toFile());
		  }
		  
		  public void init() {
			    try {
			      Files.createDirectory(rootLocation);
			    } catch (IOException e) {
			      throw new RuntimeException("Could not initialize storage!");
			    }
			  }
		  
		  public void deleteByUniqueId() {
			  
			  
		  }
}
