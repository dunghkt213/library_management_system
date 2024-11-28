package database;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ImageStorage {

    // Lưu ảnh vào CSDL
    public static void saveStudentImage(String studentId) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif"));

        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try (Connection conn = JDBCUtil.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("UPDATE students SET image = ? WHERE studentID = ?")) {

                // Đọc file ảnh
                FileInputStream fis = new FileInputStream(selectedFile);
                stmt.setBinaryStream(1, fis, (int) selectedFile.length());
                stmt.setString(2, studentId);

                // Thực thi câu lệnh
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Image successfully saved for student ID: " + studentId);
                } else {
                    System.out.println("Failed to save image for student ID: " + studentId);
                }

                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No file selected.");
        }
    }

    // Lấy ảnh từ CSDL
    public static void loadStudentImage(String studentId, ImageView studentImageView) {
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT image FROM students WHERE studentID = ?")) {

            stmt.setString(1, studentId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                byte[] imageBytes = rs.getBytes("image");

                if (imageBytes != null) {
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);

                    Image image = new Image(bis);

                    studentImageView.setImage(image);
                    System.out.println("Image successfully loaded for student ID: " + studentId);
                } else {
                    System.out.println("No image found for student ID: " + studentId);
                }
            } else {
                System.out.println("No student found with ID: " + studentId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
