package com.fuusb.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {
	
	@Value("${location}")
	String location;

	//Use for save data
	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {

		// Check file is empty or not
		if (file.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");

		} 
		else {
		
			try {
				//Get accurate path
				String filePath = location + File.separator + file.getOriginalFilename();
				File destination = new File(filePath);
				//Save file to destination
				file.transferTo(destination);
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body("File save succussfully- " + file.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File failed to save");
			}
		}
	}
}
