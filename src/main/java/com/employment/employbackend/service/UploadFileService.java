package com.employment.employbackend.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {

	public final static String UPLOAD_FOLDER = ".//src//main/resources//files//";

	public void saveFile(MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			byte[] bytesFile = file.getBytes();
			Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
			Files.write(path, bytesFile);
		}
	}
}
