package com.jeong;

import com.jeong.data.BmpData;
import com.jeong.service.ImageProcessingService;
import com.jeong.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageProcessingService imageProcessingService;
    private final ImageRepository imageRepository;

    @GetMapping("/api/image/{name}/{partN}")
    public ResponseEntity<byte[]> getBmpPartN(@PathVariable String name, @PathVariable String partN) {
        BmpData bmpData = imageProcessingService.splitBMP(name);
        return ResponseEntity.ok().body(bmpData.getPartNBmp(partN));
    }

    @GetMapping("/api/image/{name}/all")
    public ResponseEntity<byte[]> getBmpOrigin(@PathVariable String name) {

        ImageEntity imageEntity = imageRepository.findByName(name)
                .orElseThrow();
        return ResponseEntity.ok().body(imageEntity.getImageDate());
    }
}
