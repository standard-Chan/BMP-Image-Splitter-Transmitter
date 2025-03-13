package com.jeong.service;

import com.jeong.ImageEntity;
import com.jeong.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    // static 경로에 있는 파일 저장
    public ImageEntity saveImage(String fileName) throws IOException {
        ClassPathResource imgFile = new ClassPathResource(fileName);
        Path path = imgFile.getFile().toPath();
        // get image bytes
        byte[] imageBytes = Files.readAllBytes(path);

        ImageEntity image = new ImageEntity(fileName, imageBytes);

        return imageRepository.save(image);
    }

    public byte[] getImageByName(String fileName){
        Optional<ImageEntity> image = imageRepository.findByName(fileName);
        return image.map(imageEntity -> imageEntity.getImageDate()).orElseGet(() -> null);
    }

}
