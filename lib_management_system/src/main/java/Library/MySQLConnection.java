package Library;

import java.sql.*;

public class MySQLConnection {

    private static final String url = "jdbc:mysql://localhost:3306/lib";
    private static final String user = "root";
    private static final String password = "Nguyenbienuet123@";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) throws SQLException {
        try(Connection connection = getConnection()) {
            System.out.println("Ket noi thanh cong");

            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM Students";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                System.out.println("Du lieu: " + resultSet.getString("studentID"));
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Ket noi that bai: " + e.getMessage());
        }
    }

    // Phương thức chèn dữ liệu vào bảng Books
    public static void insertBook(String bookID, String title, String authors, String publisher, String edition, String language, int quantity, int remainingBooks, String availability) {
        String insertBookSQL = "INSERT INTO Books (bookID, bookTitle, bookAuthor, bookPublisher, edition, language, quantity, remainingBooks, availability) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String updateBookSQL = "UPDATE Books SET bookTitle = ?, bookAuthor = ?, bookPublisher = ?, edition = ?, language = ?, quantity = ?, remainingBooks = ?, availability = ? "
                + "WHERE bookID = ?";

        try (Connection connection = getConnection()) {

            PreparedStatement checkStatement = connection.prepareStatement("SELECT * FROM Books WHERE bookID = ?");
            checkStatement.setString(1, bookID);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {

                try (PreparedStatement updateStatement = connection.prepareStatement(updateBookSQL)) {
                    updateStatement.setString(1, title);
                    updateStatement.setString(2, authors);
                    updateStatement.setString(3, publisher);
                    updateStatement.setString(4, edition);
                    updateStatement.setString(5, language);
                    updateStatement.setInt(6, quantity);
                    updateStatement.setInt(7, remainingBooks);
                    updateStatement.setString(8, availability);
                    updateStatement.setString(9, bookID);
                    updateStatement.executeUpdate();
                    System.out.println("Đã cập nhật sách: " + title);
                }
            } else {

                try (PreparedStatement insertStatement = connection.prepareStatement(insertBookSQL)) {
                    insertStatement.setString(1, bookID);
                    insertStatement.setString(2, title);
                    insertStatement.setString(3, authors);
                    insertStatement.setString(4, publisher);
                    insertStatement.setString(5, edition);
                    insertStatement.setString(6, language);
                    insertStatement.setInt(7, quantity);
                    insertStatement.setInt(8, remainingBooks);
                    insertStatement.setString(9, availability);
                    insertStatement.executeUpdate();
                    System.out.println("Đã chèn sách mới: " + title);
                }
            }

            // Đóng kết nối
            resultSet.close();
            checkStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Phuong thuc xoa mot cuon sach tu bang Books
    public static void deleteBook(String bookID) {
        String deleteBookSQL = "DELETE FROM Books WHERE bookID = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteBookSQL)) {

            preparedStatement.setString(1, bookID);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Đã xóa sách với ID: " + bookID);
            } else {
                System.out.println("Không tìm thấy sách với ID: " + bookID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
