package dao;

import database.JDBCUtil;
import model.comment;

import java.sql.*;
import java.util.ArrayList;

public class commentDAO implements DAOInterface<comment> {

    // Singleton pattern
    public static DAOInterface<comment> getInstance() {
        return new commentDAO();
    }

    @Override
    public int insert(comment comment) {
        int result = 0;
        String sql = "INSERT INTO comments (bookID, studentID, rating, comment, created_at) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, comment.getBookID());
            pstmt.setInt(2, comment.getStudentID());
            pstmt.setDouble(3, comment.getRating());
            pstmt.setString(4, comment.getComment());
            pstmt.setTimestamp(5, Timestamp.valueOf(comment.getCreatedAt())); // created_at là loại DATETIME

            result = pstmt.executeUpdate();

            System.out.println("Câu lệnh đã được thực thi thành công.");
            System.out.println("Có " + result + " dòng đã bị thay đổi.");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int update(comment comment) {
        int result = 0;
        String sql = "UPDATE comments SET rating = ?, comment = ?, created_at = ?, countoflike = ? WHERE id = ?";

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, comment.getRating());
            pstmt.setString(2, comment.getComment());
            pstmt.setTimestamp(3, Timestamp.valueOf(comment.getCreatedAt()));
            pstmt.setInt(4, comment.getCountoflike());
            pstmt.setInt(5, comment.getId());
            result = pstmt.executeUpdate();
            System.out.println("Số dòng thay đổi: " + result);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int delete(comment comment) {
        int result = 0;
        String sql = "DELETE FROM comments WHERE id = ?";

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, comment.getId());

            result = pstmt.executeUpdate();

            System.out.println("Câu lệnh đã được thực thi thành công.");
            System.out.println("Có " + result + " dòng đã bị xóa.");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public ArrayList<comment> getAll() {
        ArrayList<comment> commentList = new ArrayList<>();
        String sql = "SELECT * FROM comments";

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String bookID = rs.getString("bookID");
                int studentID = rs.getInt("studentID");
                double rating = rs.getDouble("rating");
                String commentText = rs.getString("comment");
                Timestamp createdAt = rs.getTimestamp("created_at");
                int countoflike = rs.getInt("countoflike");
                comment commentObj = new comment(id, bookID, studentID, rating, commentText, createdAt.toLocalDateTime(), countoflike);
                commentList.add(commentObj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commentList;
    }

    @Override
    public comment getById(comment comment) {
        comment commentObj = null;
        String sql = "SELECT * FROM comments WHERE id = ?";

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, comment.getId());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String bookID = rs.getString("bookID");
                    int studentID = rs.getInt("studentID");
                    double rating = rs.getDouble("rating");
                    String commentText = rs.getString("comment");
                    Timestamp createdAt = rs.getTimestamp("created_at");
                    int countoflike = rs.getInt("countoflike");
                    commentObj = new comment(studentID, bookID, studentID, rating, commentText, createdAt.toLocalDateTime(), countoflike);
                    commentObj.setId(id);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commentObj;
    }

    @Override
    public ArrayList<comment> getByCondition(comment searchCriteria) {
        ArrayList<comment> commentList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM comments WHERE 1=1");

        if (searchCriteria.getBookID() != null && !searchCriteria.getBookID().isEmpty()) {
            sql.append(" AND bookID = ?");
        }
        if (searchCriteria.getStudentID() != 0) {
            sql.append(" AND studentID = ?");
        }
        if (searchCriteria.getRating() != 0) {
            sql.append(" AND rating = ?");
        }
        if (searchCriteria.getCreatedAt() != null) {
            sql.append(" AND created_at = ?");
        }

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int index = 1;

            if (searchCriteria.getBookID() != null && !searchCriteria.getBookID().isEmpty()) {
                pstmt.setString(index++, searchCriteria.getBookID());
            }
            if (searchCriteria.getStudentID() != 0) {
                pstmt.setInt(index++, searchCriteria.getStudentID());
            }
            if (searchCriteria.getRating() != 0) {
                pstmt.setDouble(index++, searchCriteria.getRating());
            }
            if (searchCriteria.getCreatedAt() != null) {
                pstmt.setTimestamp(index++, Timestamp.valueOf(searchCriteria.getCreatedAt()));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String bookID = rs.getString("bookID");
                    int studentID = rs.getInt("studentID");
                    double rating = rs.getDouble("rating");
                    String commentText = rs.getString("comment");
                    Timestamp createdAt = rs.getTimestamp("created_at");
                    int countoflike = rs.getInt("countoflike");
                    comment commentObj = new comment(studentID, bookID, studentID, rating, commentText, createdAt.toLocalDateTime(), countoflike);
                    commentObj.setId(id);
                    commentList.add(commentObj);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commentList;
    }

    @Override
    public String getStatusbyId(comment comment) {
        return "";
    }
}
