async function fetchBMPPart(imageName, part) {
  const url = `http://localhost:8080/api/image/${imageName}/${part}`;

  try {
      const response = await fetch(url);
      if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
      }

      const arrayBuffer = await response.arrayBuffer(); // BMP 데이터를 바이너리 데이터로 변환
      console.log(`BMP Part ${part} Loaded, Size: ${arrayBuffer.byteLength} bytes`);
      return new Uint8Array(arrayBuffer); // Uint8Array로 변환해서 리턴
  } catch (error) {
      console.error(`Error fetching BMP part (${part}):`, error);
      return null;
  }
}

Promise.all([
  fetchBMPPart("testImage.bmp", "part1"),
  fetchBMPPart("testImage.bmp", "part2")
]).then(([oddPartData, evenPartData]) => {
  if (oddPartData && evenPartData) {
      const newWindow = window.open();
      newWindow.document.write(`
          <html>
              <body>
                  <canvas id="evenCanvas"></canvas>
                  <canvas id="oddCanvas"></canvas>
              </body>
          </html>
      `);

      const oddCanvas = newWindow.document.getElementById("oddCanvas");
      const evenCanvas = newWindow.document.getElementById("evenCanvas");

      renderBMPToCanvas(oddPartData, oddCanvas);
      renderBMPToCanvas(evenPartData, evenCanvas);
  }
});
