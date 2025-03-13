package com.jeong;

import com.jeong.service.ImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional // 테스트 후 데이터 롤백 방지하려면 제거
class ImageServiceTest {

    @Autowired
    private ImageService imageService;

    @Test
    void testSaveImage() throws IOException {
        // BMP 이미지를 저장
        ImageEntity savedImage = imageService.saveImage("testImage.bmp");

        // 저장된 데이터 검증
        assertNotNull(savedImage);
        assertNotNull(savedImage.getId());

        System.out.println("✅ BMP 이미지가 local.db에 저장되었습니다!");
    }
}
