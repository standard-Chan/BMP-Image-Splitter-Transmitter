function renderBMPToCanvas(bmpData, canvas) {
  const ctx = canvas.getContext("2d");

  // BMP 헤더 읽기
  const width = bmpData[18] | (bmpData[19] << 8);
  const height = Math.abs(bmpData[22] | (bmpData[23] << 8));
  const offset = bmpData[10] | (bmpData[11] << 8); // 픽셀 데이터 시작 위치
  const rowSize = Math.ceil((width * 3) / 4) * 4; // 4바이트 패딩 고려

  console.log(`Rendering BMP - Width: ${width}, Height: ${height}, Offset: ${offset}`);

  // 💡 원하는 크기로 조정 (비율 유지)
  const scaleFactor = 0.5; // 50% 크기로 축소
  const scaledWidth = Math.floor(width * scaleFactor);
  const scaledHeight = Math.floor(height * scaleFactor);

  // 💡 원본 픽셀 해상도 유지
  canvas.style.width = scaledWidth + "px";
  canvas.style.height = scaledHeight + "px";
  canvas.width = width;  // 원본 해상도 유지
  canvas.height = height;

  const imageData = ctx.createImageData(width, height);

  let dataIndex = offset; // BMP의 픽셀 데이터 시작 위치
  for (let y = height - 1; y >= 0; y--) { // BMP는 Bottom-to-Top 저장
      for (let x = 0; x < width; x++) {
          const i = (y * width + x) * 4; // RGBA 포맷
          imageData.data[i] = bmpData[dataIndex + 2]; // R
          imageData.data[i + 1] = bmpData[dataIndex + 1]; // G
          imageData.data[i + 2] = bmpData[dataIndex]; // B
          imageData.data[i + 3] = 255; // Alpha (불투명)

          dataIndex += 3;
      }
      dataIndex += rowSize - (width * 3); // 패딩 스킵
  }

  ctx.putImageData(imageData, 0, 0);

  // 💡 캔버스 크기를 조절하여 축소 효과 적용
  ctx.scale(scaleFactor, scaleFactor);
  ctx.drawImage(canvas, 0, 0);
}

export default renderBMPToCanvas;