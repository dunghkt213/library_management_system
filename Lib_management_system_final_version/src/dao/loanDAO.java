package dao;

import database.JDBCUtil;
import model.loan;

import java.sql.*;
import java.util.ArrayList;

public class loanDAO implements DAOInterface<loan> {

    public static DAOInterface<loan> getInstance() {
        return new loanDAO();
    }

    @Override
    public int insert(loan loan) {
        int result = 0;
        String sql = "INSERT INTO loans(loansID, bookID, studentID, loanDate, returnDate, dueDate, status) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loan.getLoansID());
            pstmt.setString(2, loan.getBookID());
            pstmt.setString(3, loan.getStudentID());
            pstmt.setString(4, loan.getLoanDate());
            pstmt.setString(5, loan.getReturnDate());
            pstmt.setString(6, loan.getDueDate());
            pstmt.setString(7, loan.getStatus());
            result = pstmt.executeUpdate();
            System.out.println("Câu lệnh đã được thực thi thành công. Có " + result + " dòng đã bị thay đổi.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int update(loan loan) {
        int result = 0;
        String sql = "UPDATE loans SET bookID = ?, studentID = ?, loanDate = ?, returnDate = ?, dueDate = ?, status = ? WHERE loansID = ?";
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loan.getBookID());
            pstmt.setString(2, loan.getStudentID());
            pstmt.setString(3, loan.getLoanDate());
            pstmt.setString(4, loan.getReturnDate());
            pstmt.setString(5, loan.getDueDate());
            pstmt.setString(6, loan.getStatus());
            pstmt.setString(7, loan.getLoansID());
            result = pstmt.executeUpdate();
            System.out.println("Câu lệnh đã được thực thi thành công. Có " + result + " dòng đã bị thay đổi.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(loan loan) {
        int result = 0;
        String sql = "DELETE FROM loans WHERE loansID = ?";
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loan.getLoansID());
            result = pstmt.executeUpdate();
            System.out.println("Câu lệnh đã được thực thi thành công. Có " + result + " dòng đã bị xóa.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<loan> getAll() {
        ArrayList<loan> loanList = new ArrayList<>();
        String sql = "SELECT * FROM loans";
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                loan loanObj = new loan(
                        rs.getString("status"),
                        rs.getString("dueDate"),
                        rs.getString("loanDate"),
                        rs.getString("returnDate"),
                        rs.getString("studentID"),
                        rs.getString("bookID"),
                        rs.getString("loansID")
                );
                loanList.add(loanObj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loanList;
    }

    @Override
    public loan getById(loan loan) {
        loan loanObj = null;
        String sql = "SELECT * FROM loans WHERE loansID = ?";
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loan.getLoansID());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    loanObj = new loan(
                            rs.getString("status"),
                            rs.getString("dueDate"),
                            rs.getString("loanDate"),
                            rs.getString("returnDate"),
                            rs.getString("studentID"),
                            rs.getString("bookID"),
                            rs.getString("loansID")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loanObj;
    }

    @Override
    public ArrayList<loan> getByCondition(loan searchCriteria) {
        ArrayList<loan> loanList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM loans WHERE 1=1");

        if (searchCriteria.getLoansID() != null) {
            sql.append(" AND loansID = ?");
        }
        if (searchCriteria.getBookID() != null && !searchCriteria.getBookID().isEmpty()) {
            sql.append(" AND bookID = ?");
        }
        if (searchCriteria.getStudentID() != null) {
            sql.append(" AND studentID = ?");
        }
        // Add more conditions as needed...

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            int index = 1;
            if (searchCriteria.getLoansID() != null) {
                pstmt.setString(index++, searchCriteria.getLoansID());
            }
            if (searchCriteria.getBookID() != null && !searchCriteria.getBookID().isEmpty()) {
                pstmt.setString(index++, searchCriteria.getBookID());
            }
            if (searchCriteria.getStudentID() != null) {
                pstmt.setString(index++, searchCriteria.getStudentID());
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    loan loanObj = new loan(
                            rs.getString("status"),
                            rs.getString("dueDate"),
                            rs.getString("loanDate"),
                            rs.getString("returnDate"),
                            rs.getString("studentID"),
                            rs.getString("bookID"),
                            rs.getString("loansID")
                    );
                    loanList.add(loanObj);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loanList;
    }
}
