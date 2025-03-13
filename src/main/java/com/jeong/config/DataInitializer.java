package com.jeong.config;

import com.jeong.service.ImageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

//// bmp 이미지 파일 저장 클래스
//@Configuration
//public class DataInitializer {
//
//    @Bean
//    CommandLineRunner initDatabase(ImageService imageService) {
//        return args -> {
//            try {
//                imageService.saveImage("testImage2.bmp");
//                System.out.println("✅ BMP 이미지가 local.db에 자동 저장되었습니다!");
//            } catch (IOException e) {
//                System.err.println("❌ BMP 이미지 저장 실패: " + e.getMessage());
//            }
//        };
//    }
//}
