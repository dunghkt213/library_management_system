package dao;

import database.JDBCUtil;
import model.book;

import java.sql.*;
import java.util.ArrayList;

public class bookDAO implements DAOInterface<book> {


    public static DAOInterface<book> getInstance() {
        return new bookDAO();
    }

    @Override
    public int insert(book book) {
        int result = 0;
        String sql = "INSERT INTO Books (bookID, bookTitle, bookAuthor, bookPublisher, edition, language, quantity, remainingBooks, categoryName) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Gán giá trị cho từng dấu ?
            pstmt.setString(1, book.getBookID());
            pstmt.setString(2, book.getBookTitle());
            pstmt.setString(3, book.getBookAuthor());
            pstmt.setString(4, book.getBookPublisher());
            pstmt.setString(5, book.getEdition());
            pstmt.setString(6, book.getLanguage());
            pstmt.setInt(7, book.getQuantity());
            pstmt.setInt(8, book.getRemainingBooks());
            pstmt.setString(9, book.getCategoryName());

            // Thực thi câu lệnh INSERT
            result = pstmt.executeUpdate();

            System.out.println("Câu lệnh đã được thực thi thành công.");
            System.out.println("Có " + result + " dòng đã bị thay đổi.");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int update(book book) {
        int result = 0;
        String sql = "UPDATE books SET bookTitle = ?, bookAuthor = ?, bookPublisher = ?, edition = ?, " + "`language` = ?, quantity = ?, remainingBooks = ?, availability = ?, " + "categoryName = ? WHERE bookID = ?";

        try (PreparedStatement updateStatement = JDBCUtil.getConnection().prepareStatement(sql)) {
            updateStatement.setString(1, book.getBookTitle());
            updateStatement.setString(2, book.getBookAuthor());
            updateStatement.setString(3, book.getBookPublisher());
            updateStatement.setString(4, book.getEdition());
            updateStatement.setString(5, book.getLanguage());
            updateStatement.setInt(6, book.getQuantity());
            updateStatement.setInt(7, book.getRemainingBooks());
            updateStatement.setString(8, book.getAvailability());
            updateStatement.setString(9, book.getCategoryName());
            updateStatement.setString(10, book.getBookID());

            result = updateStatement.executeUpdate();
            System.out.println("so dong thay doi: " + result);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int delete(book book) {
        int KetQua = 0;
        String sql = "DELETE FROM Books WHERE bookID = ?";

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getBookID());

            KetQua = pstmt.executeUpdate();

            System.out.println("Câu lệnh đã được thực thi thành công.");
            System.out.println("Có " + KetQua + " dòng đã bị xóa.");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return KetQua;

    }

    @Override
    public ArrayList<book> getAll() {
        ArrayList<book> bookList = new ArrayList<>();

        String sql = "SELECT * FROM Books";

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Lấy các giá trị của các cột từ ResultSet
                String bookID = rs.getString("bookID");
                String bookTitle = rs.getString("bookTitle");
                String bookAuthor = rs.getString("bookAuthor");
                String bookPublisher = rs.getString("bookPublisher");
                String edition = rs.getString("edition");
                String language = rs.getString("language");
                int quantity = rs.getInt("quantity");
                int remainingBooks = rs.getInt("remainingBooks");
                String availability = rs.getString("availability");
                int categoryID = rs.getInt("categoryID");
                String categoryName = rs.getString("categoryName");

                book bookObj = new book(bookID, categoryID, availability, remainingBooks, language, edition, bookAuthor, bookTitle, bookPublisher, quantity, categoryName);

                bookList.add(bookObj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookList;
    }

    @Override
    public book getById(book book) {
        book bookObj = null;

        String sql = "SELECT * FROM Books WHERE bookID = ?";

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getBookID());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String bookTitle = rs.getString("bookTitle");
                    String bookAuthor = rs.getString("bookAuthor");
                    String bookPublisher = rs.getString("bookPublisher");
                    String edition = rs.getString("edition");
                    String language = rs.getString("language");
                    int quantity = rs.getInt("quantity");
                    int remainingBooks = rs.getInt("remainingBooks");
                    String availability = rs.getString("availability");
                    int categoryID = rs.getInt("categoryID");
                    String categoryName = rs.getString("categoryName");

                    bookObj = new book(book.getBookID(), categoryID, availability, remainingBooks, language, edition, bookAuthor, bookTitle, bookPublisher, quantity, categoryName);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookObj;
    }

    @Override
    public ArrayList<book> getByCondition(book searchCriteria) {
        ArrayList<book> bookList = new ArrayList<>();


        StringBuilder sql = new StringBuilder("SELECT * FROM Books WHERE 1=1");

        if (searchCriteria.getBookID() != null && !searchCriteria.getBookID().isEmpty()) {
            sql.append(" AND bookID = ?");
        }
        if (searchCriteria.getBookTitle() != null && !searchCriteria.getBookTitle().isEmpty()) {
            sql.append(" AND bookTitle LIKE ?");
        }
        if (searchCriteria.getBookAuthor() != null && !searchCriteria.getBookAuthor().isEmpty()) {
            sql.append(" AND bookAuthor LIKE ?");
        }

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int index = 1;

            if (searchCriteria.getBookID() != null && !searchCriteria.getBookID().isEmpty()) {
                pstmt.setString(index++, searchCriteria.getBookID());
            }
            if (searchCriteria.getBookTitle() != null && !searchCriteria.getBookTitle().isEmpty()) {
                pstmt.setString(index++, "%" + searchCriteria.getBookTitle() + "%");
            }
            if (searchCriteria.getBookAuthor() != null && !searchCriteria.getBookAuthor().isEmpty()) {
                pstmt.setString(index++, "%" + searchCriteria.getBookAuthor() + "%");
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String bookID = rs.getString("bookID");
                    String bookTitle = rs.getString("bookTitle");
                    String bookAuthor = rs.getString("bookAuthor");
                    String bookPublisher = rs.getString("bookPublisher");
                    String edition = rs.getString("edition");
                    String language = rs.getString("language");
                    int quantity = rs.getInt("quantity");
                    int remainingBooks = rs.getInt("remainingBooks");
                    String availability = rs.getString("availability");
                    int categoryID = rs.getInt("categoryID");
                    String categoryName = rs.getString("categoryName");

                    book bookObj = new book(bookID, categoryID, availability, remainingBooks, language, edition, bookAuthor, bookTitle, bookPublisher, quantity, categoryName);
                    bookList.add(bookObj);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookList;
    }
}
