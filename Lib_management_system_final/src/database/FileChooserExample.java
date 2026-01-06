package database;
import javax.swing.*;
import java.io.File;

public class FileChooserExample {

    public static String chooseImageFromUser() {
        // Tạo một JFileChooser
        JFileChooser fileChooser = new JFileChooser();

        // Thiết lập bộ lọc để chỉ hiển thị file ảnh
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                // Chấp nhận thư mục và file có định dạng ảnh
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".jpg")
                        || f.getName().toLowerCase().endsWith(".jpeg")
                        || f.getName().toLowerCase().endsWith(".png")
                        || f.getName().toLowerCase().endsWith(".gif");
            }

            @Override
            public String getDescription() {
                return "Image Files (*.jpg, *.jpeg, *.png, *.gif)";
            }
        });

        // Hiển thị hộp thoại để chọn file
        int result = fileChooser.showOpenDialog(null);

        // Nếu người dùng chọn file, trả về đường dẫn file
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            return selectedFile.getAbsolutePath();
        } else {
            System.out.println("No file selected.");
            return null;
        }
    }

    public static void main(String[] args) {
        String imagePath = chooseImageFromUser();
        if (imagePath != null) {
            System.out.println("Image path chosen: " + imagePath);
        }
    }
}
