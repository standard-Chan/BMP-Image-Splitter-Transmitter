async function fetchBMP() {
  const imageName = "testImage2.bmp"; // BMP 이미지 이름
  const url = `http://localhost:8080/api/image/${imageName}/all`; // 한 번에 가져오기

  try {
      const response = await fetch(url);
      if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
      }

      const arrayBuffer = await response.arrayBuffer(); // BMP 데이터를 바이너리로 변환
      console.log(`BMP Loaded, Size: ${arrayBuffer.byteLength} bytes`);
      return new Uint8Array(arrayBuffer); // Uint8Array로 변환하여 반환
  } catch (error) {
      console.error(`Error fetching BMP:`, error);
      return null;
  }
}

// BMP 로드 후 렌더링 실행
fetchBMP().then((bmpData) => {
  if (bmpData) {
      const newWindow = window.open();
      newWindow.document.write(`
          <html>
              <body>
                  <canvas id="bmpCanvas"></canvas>
              </body>
          </html>
      `);

      const canvas = newWindow.document.getElementById("bmpCanvas");
      renderBMPToCanvas(bmpData, canvas);
  }
});
