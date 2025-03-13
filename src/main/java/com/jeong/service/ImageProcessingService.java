package com.jeong.service;

import com.jeong.ImageEntity;
import com.jeong.ImageRepository;
import com.jeong.data.BmpData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Optional;

@Service
public class ImageProcessingService {
    private static final int PIXEL_IN_HEADER = 10; // 헤더에 있는 픽셀 데이터 시작 위치
    private static final int WIDTH_OFFSET = 18; // 헤더에 있는 너비 정보 (18, 19, 20, 21 byte 위치)
    private static final int HEIGHT_OFFSET = 22; // 헤더에 있는 높이 정보 (22, 23, 24, 25 byte 위치)
    private static final int FILE_HEADER_SIZE = 54;
    private static final int START_PIXEL_OFFSET = 54; // 픽셀 데이터 시작 offset
    private static final int RGB_BYTE = 3; // rgb 바이트 크기

    @Autowired
    private ImageRepository imageRepository;

    public BmpData splitBMP(String imageName) {
        // BMP 데이터 byte 정보로 가져오기
        Optional<ImageEntity> imageEntity = imageRepository.findByName(imageName);
        byte[] bmpData = imageEntity
                .orElseThrow(() -> new RuntimeException("imageName이 잘못되었습니다 : " + imageName))
                .getImageDate();

        //  BMP 헤더에서 높이 추출 (너비는 그대로)
        int width = ByteBuffer.wrap(bmpData, WIDTH_OFFSET, 4).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();
        int height = ByteBuffer.wrap(bmpData,  HEIGHT_OFFSET, 4).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();
        int newHeight = height / 2; // 이미지   높이 나누기
        int rowByteSize = width * RGB_BYTE; // 1row = width * RGB(3BYTE) 크기   (width는 픽셀 수)


        // 픽셀 데이터 BYTE get
        byte[] pixelData = Arrays.copyOfRange(bmpData, START_PIXEL_OFFSET, bmpData.length);

        // 파트 분할 픽셀 데이터
        byte[] part1 = Arrays.copyOfRange(pixelData, 0, rowByteSize * newHeight);
        byte[] part2 = Arrays.copyOfRange(pixelData, rowByteSize * newHeight, rowByteSize * height); // 상위 1/2 라인용

        // 분할된 BMP 파일 생성
        byte[] part1BMP = createNewBMP(bmpData, part1, rowByteSize, newHeight);
        byte[] part2BMP = createNewBMP(bmpData, part2, rowByteSize, newHeight);

        return new BmpData(part1BMP, part2BMP);
    }

    private byte[] createNewBMP(byte[] originalBMP, byte[] newPixelData, int rowByteSize, int newHeight) {
        int newImageSize = rowByteSize * newHeight;

        // 새로운 BMP 헤더 생성 및 기존 헤더 복사
        byte[] newBmp = new byte[FILE_HEADER_SIZE + newImageSize+1];
        System.arraycopy(originalBMP, 0, newBmp, 0, FILE_HEADER_SIZE);

        // 픽셀 데이터 시작 위치 수정 (54로 고정)
        ByteBuffer.wrap(newBmp, PIXEL_IN_HEADER, 4).order(java.nio.ByteOrder.LITTLE_ENDIAN).putInt(START_PIXEL_OFFSET);

        // 이미지 높이 수정
        ByteBuffer.wrap(newBmp, HEIGHT_OFFSET, 4).order(java.nio.ByteOrder.LITTLE_ENDIAN).putInt(newHeight-1);

        // 픽셀 데이터 복사
        System.arraycopy(newPixelData, 0, newBmp, START_PIXEL_OFFSET, newImageSize);

        return newBmp;
    }
}
