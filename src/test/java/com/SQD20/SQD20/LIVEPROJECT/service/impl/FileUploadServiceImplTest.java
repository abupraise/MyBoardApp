package com.SQD20.SQD20.LIVEPROJECT.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FileUploadServiceImplTest {
    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    @InjectMocks
    private FileUploadServiceImpl fileUploadService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(cloudinary.uploader()).thenReturn(uploader);
    }

    @Test
    void uploadFile_Success() throws IOException {
        String secureUrl = "https://example.com/image.jpg";
        when(uploader.upload(any(byte[].class), any(Map.class)))
                .thenReturn(new HashMap<String, Object>() {{
                    put("secure_url", secureUrl);
                }});

        byte[] fileContent = "test file content".getBytes();
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", fileContent);

        FileUploadServiceImpl fileUploadService = new FileUploadServiceImpl(cloudinary);
        String uploadedUrl = fileUploadService.uploadFile(multipartFile);
        assertEquals(secureUrl, uploadedUrl);
    }

    @Test
    void uploadFile_Failure() throws IOException {

        when(uploader.upload(any(byte[].class), any(Map.class))).thenThrow(IOException.class);

        byte[] fileContent = "test file content".getBytes();
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", fileContent);

        String uploadedUrl = null;
        try {
            uploadedUrl = fileUploadService.uploadFile(multipartFile);
        } catch (IOException e) {

        }
        assertEquals(null, uploadedUrl);
    }

}