package dao;

import database.JDBCUtil;
import model.likes;

import java.sql.*;
import java.util.ArrayList;

public class likeDAO implements DAOInterface<likes> {

    // Singleton pattern
    public static likeDAO getInstance() {
        likeDAO dao = new likeDAO();
        return  dao;
    }

    public boolean hasLiked(int studentID, int commentID) {
        String sql = "SELECT COUNT(*) AS count FROM likes WHERE studentID = ? AND commentID = ?";
        boolean liked = false;

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentID);
            pstmt.setInt(2, commentID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    liked = rs.getInt("count") > 0; // Trả về true nếu đã like
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return liked;
    }

    @Override
    public int insert(likes like) {
        int result = 0;
        String sql = "INSERT INTO likes (studentID, commentID) VALUES (?, ?)";

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, like.getStudentID());
            pstmt.setInt(2, like.getCommentID());

            result = pstmt.executeUpdate();

            System.out.println("Câu lệnh INSERT đã được thực thi thành công.");
            System.out.println("Có " + result + " dòng đã bị thay đổi.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int update(likes like) {
        // Không hỗ trợ update cho bảng likes
        return 0;
    }

    @Override
    public int delete(likes like) {
        int result = 0;
        String sql = "DELETE FROM likes WHERE studentID = ? AND commentID = ?";

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, like.getStudentID());
            pstmt.setInt(2, like.getCommentID());

            result = pstmt.executeUpdate();

            System.out.println("Câu lệnh DELETE đã được thực thi thành công.");
            System.out.println("Có " + result + " dòng đã bị xóa.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public ArrayList<likes> getAll() {
        ArrayList<likes> likesList = new ArrayList<>();
        String sql = "SELECT * FROM likes";

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int studentID = rs.getInt("studentID");
                int commentID = rs.getInt("commentID");

                likes likeObj = new likes(id, studentID, commentID);
                likesList.add(likeObj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return likesList;
    }

    @Override
    public likes getById(likes like) {
        likes likeObj = null;
        String sql = "SELECT * FROM likes WHERE id = ?";

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, like.getId());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int studentID = rs.getInt("studentID");
                    int commentID = rs.getInt("commentID");

                    likeObj = new likes(rs.getInt("id"), studentID, commentID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return likeObj;
    }

    @Override
    public ArrayList<likes> getByCondition(likes searchCriteria) {
        ArrayList<likes> likesList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM likes WHERE 1=1");

        if (searchCriteria.getStudentID() != 0) {
            sql.append(" AND studentID = ?");
        }
        if (searchCriteria.getCommentID() != 0) {
            sql.append(" AND commentID = ?");
        }

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            int index = 1;

            if (searchCriteria.getStudentID() != 0) {
                pstmt.setInt(index++, searchCriteria.getStudentID());
            }
            if (searchCriteria.getCommentID() != 0) {
                pstmt.setInt(index++, searchCriteria.getCommentID());
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int studentID = rs.getInt("studentID");
                    int commentID = rs.getInt("commentID");

                    likes likeObj = new likes(id, studentID, commentID);
                    likesList.add(likeObj);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return likesList;
    }

    @Override
    public String getStatusbyId(likes like) {
        return ""; // Không cần thiết cho bảng likes
    }
}
