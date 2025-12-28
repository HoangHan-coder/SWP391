// import { useState } from 'react'
// import reactLogo from './assets/react.svg'
// import viteLogo from '/vite.svg'
// import './App.css'

// function App() {
//   const [count, setCount] = useState(0)

//   return (
//     <>
//       <div>
//         <a href="https://vite.dev" target="_blank">
//           <img src={viteLogo} className="logo" alt="Vite logo" />
//         </a>
//         <a href="https://react.dev" target="_blank">
//           <img src={reactLogo} className="logo react" alt="React logo" />
//         </a>
//       </div>
//       <h1>Vite + React</h1>
//       <div className="card">
//         <button onClick={() => setCount((count) => count + 1)}>
//           count is {count}
//         </button>
//         <p>
//           Edit <code>src/App.jsx</code> and save to test HMR
//         </p>
//       </div>
//       <p className="read-the-docs">
//         Click on the Vite and React logos to learn more
//       </p>
//     </>
//   )
// }

// export default App

import { useState } from 'react';
import uploadImageToCloudinary from './services/uploadService'; // Đảm bảo đường dẫn đúng

function App() {
  const [imageUrl, setImageUrl] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleTestUpload = async (event) => {
    const file = event.target.files[0];
    if (!file) return;

    setLoading(true);
    console.log("Đang bắt đầu upload...");

    // Gọi hàm upload
    const url = await uploadImageToCloudinary(file);

    setLoading(false);

    if (url) {
      console.log("Thành công! URL:", url);
      setImageUrl(url); // Lưu link ảnh để hiển thị
    } else {
      console.log("Thất bại. Kiểm tra lại Console log để xem lỗi.");
      alert("Upload lỗi! F12 để xem chi tiết.");
    }
  };

  return (
    <div style={{ padding: "50px", textAlign: "center" }}>
      <h1>TEST UPLOAD CLOUDINARY</h1>
      
      {/* Input chọn file */}
      <input type="file" onChange={handleTestUpload} />

      {/* Trạng thái loading */}
      {loading && <p style={{color: "blue"}}>Đang upload lên mây...</p>}

      {/* Hiển thị kết quả */}
      {imageUrl && (
        <div style={{ marginTop: "20px" }}>
          <h3 style={{color: "green"}}>Upload thành công!</h3>
          <p>Link ảnh: <a href={imageUrl} target="_blank">{imageUrl}</a></p>
          <img src={imageUrl} alt="Uploaded" width="300" style={{ border: "2px solid green" }} />
        </div>
      )}
    </div>
  );
}

export default App;
