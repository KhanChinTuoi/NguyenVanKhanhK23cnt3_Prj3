package com.example.cuahang.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

    // Thay thế logic @Value bằng đường dẫn vật lý trong dự án
    // Dùng đường dẫn tương đối (current working directory)
    private final Path rootLocation = Paths.get("src/main/resources/static/uploads");

    /**
     * Lưu file và trả về tên file duy nhất đã lưu.
     */
    public String store(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        // Đảm bảo thư mục tồn tại
        Files.createDirectories(rootLocation);

        // Tạo tên file duy nhất
        String originalFilename = file.getOriginalFilename();
        String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;

        // Lưu file vật lý
        Path destinationFile = this.rootLocation.resolve(uniqueFilename).normalize();

        Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFilename;
    }

    /**
     * Phương thức xóa file vật lý (vẫn cần thiết)
     */
    public boolean delete(String filename) {
        if (filename == null || filename.isEmpty()) {
            return false;
        }
        try {
            Path fileToDelete = this.rootLocation.resolve(filename).normalize();
            return Files.deleteIfExists(fileToDelete);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
