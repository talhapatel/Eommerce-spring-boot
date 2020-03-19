package com.ecommerce.fileupload;

import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.controller.BaseController;

@RestController
@Scope("request")
@RequestMapping("files")
public class UploadCotroller extends BaseController{

	@Autowired
	StorageService storageService;
	List<String> files = new ArrayList<>();
	 @PostMapping("/post")
	  public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file ,@RequestParam("uniqueId")String uniqueId) {
		 String message = "";
		 try {
	      storageService.store(file,uniqueId);
	      files.add(uniqueId);
	 
	      message = "You successfully uploaded " + file.getOriginalFilename() + "!";
	      return ResponseEntity.status(HttpStatus.OK).body(message);
	    } catch (Exception e) {
	      message = "FAIL to upload " + file.getOriginalFilename() + "!";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
	    }
	  }
	//	@PreAuthorize("hasRole('USER')")
	  @GetMapping("/{uniqueId}")
	  @ResponseBody
	  public ResponseEntity<Resource> getFile(@PathVariable String uniqueId) {
			HttpHeaders headers = new HttpHeaders();
		
	    Resource file1 = storageService.loadFile(uniqueId);
	    String mimeType= URLConnection.guessContentTypeFromName(file1.getFilename());
	  //  headers.add("Content-Type",mimeType);
		  return ResponseEntity.ok()
				  .header(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + file1.getFilename() + "\"")
				  .contentType(MediaType.valueOf(mimeType))
				  .body(file1);
		
		//return new ResponseEntity<>(file1, headers,HttpStatus.OK);
}
}