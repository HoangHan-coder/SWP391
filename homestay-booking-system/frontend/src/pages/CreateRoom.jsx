// File: src/pages/CreateRoom.jsx (Ví dụ)
import React, { useState } from 'react';
import uploadImageToCloudinary from '../services/uploadService'; // Import hàm vừa viết

const CreateRoom = () => {
    const [file, setFile] = useState(null);
    const [previewUrl, setPreviewUrl] = useState("");
    
    // Hàm xử lý khi bấm nút "Lưu" hoặc "Upload"
    const handleSaveRoom = async () => {
        if (!file) {
            alert("Vui lòng chọn ảnh!");
            return;
        }

        // 1. Upload ảnh lên Cloudinary trước
        const imageUrl = await uploadImageToCloudinary(file);

        if (imageUrl) {
            console.log("Link ảnh đã có:", imageUrl);
            
            // 2. Gửi dữ liệu (kèm link ảnh) về Backend Java
            // const roomData = { name: "Phong 1", price: 500, image: imageUrl };
            // api.post('/rooms', roomData)...
            alert("Upload thành công! Link: " + imageUrl);
        } else {
            alert("Upload thất bại!");
        }
    };

    // Hàm hiển thị ảnh xem trước khi chọn file
    const handleFileChange = (e) => {
        const selectedFile = e.target.files[0];
        setFile(selectedFile);
        setPreviewUrl(URL.createObjectURL(selectedFile));
    };

    return (
        <div className="create-room-form">
            <h2>Thêm phòng mới</h2>
            
            <input type="file" onChange={handleFileChange} />
            
            {/* Hiển thị ảnh xem trước */}
            {previewUrl && <img src={previewUrl} alt="Preview" width="200" />}
            
            <br />
            <button onClick={handleSaveRoom}>Lưu phòng</button>
        </div>
    );
};

export default CreateRoom;