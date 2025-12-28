// File: src/services/uploadService.js
import axios from 'axios';

// CẤU HÌNH CLOUDINARY
// Bạn thay 2 thông tin này bằng cái của bạn lấy trên trang Cloudinary nhé
const CLOUD_NAME = "dlwdnmuir"; 
const UPLOAD_PRESET = "Homestay-system-upload"; 

const uploadImageToCloudinary = async (file) => {
    try {
        const formData = new FormData();
        formData.append("file", file);
        formData.append("upload_preset", UPLOAD_PRESET);
        formData.append("folder", "homestay-system-storage"); // Tùy chọn: Gom ảnh vào 1 thư mục

        const response = await axios.post(
            `https://api.cloudinary.com/v1_1/${CLOUD_NAME}/image/upload`,
            formData
        );

        // Trả về đường dẫn ảnh (URL) để lưu vào Database
        return response.data.secure_url; 
    } catch (error) {
        console.error("Lỗi upload ảnh:", error);
        return null;
    }
};

export default uploadImageToCloudinary;