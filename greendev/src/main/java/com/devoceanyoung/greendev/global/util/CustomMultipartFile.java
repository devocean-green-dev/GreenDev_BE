package com.devoceanyoung.greendev.global.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public class CustomMultipartFile implements MultipartFile {
	private byte[] input;
	private String originalFilename;
	private String contentType;

	public CustomMultipartFile(byte[] input, String originalFilename, String contentType) {
		this.input = input;
		this.originalFilename = originalFilename;
		this.contentType = contentType;
	}

	@Override
	public String getName() {
		return originalFilename;  // Modify this as per your needs
	}

	@Override
	public String getOriginalFilename() {
		return originalFilename;
	}

	@Override
	public String getContentType() {
		return contentType;
	}


	//previous methods
	@Override
	public boolean isEmpty() {
		return input == null || input.length == 0;
	}

	@Override
	public long getSize() {
		return input.length;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return input;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(input);
	}

	@Override
	public void transferTo(File destination) throws IOException, IllegalStateException {
		try(FileOutputStream fos = new FileOutputStream(destination)) {
			fos.write(input);
		}
	}



}
