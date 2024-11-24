package dao;

import database.JDBCUtil;
import model.account;
import model.student;

import java.sql.*;
import java.util.ArrayList;

public class accountDAO implements DAOInterface<account> {

    public static DAOInterface<account> getInstance() {
        return new accountDAO();
    }

    @Override
    public int insert(account acc) {
        int result = 0;
        String sql = "INSERT INTO accounts (accountName, password, status, studentID) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, acc.getAcountName());
            pstmt.setString(2, acc.getPassword());
            pstmt.setString(3, acc.getStatus());
            pstmt.setString(4, acc.getStudentID());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int update(account acc) {
        int result = 0;
        String sql = "UPDATE accounts SET password = ?, status = ?, studentID = ? WHERE accountName = ?";

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, acc.getPassword());
            pstmt.setString(2, acc.getStatus());
            pstmt.setString(3, acc.getStudentID());
            pstmt.setString(4, acc.getAcountName());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int delete(account acc) {
        int result = 0;
        String sql = "DELETE FROM accounts WHERE accountName = ?";

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, acc.getAcountName());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public ArrayList<account> getAll() {
        ArrayList<account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts";

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String accountName = rs.getString("accountName");
                String password = rs.getString("password");
                String status = rs.getString("status");
                String studentID = rs.getString("studentID");

                account acc = new account(accountName, password, status,studentID);
                acc.setStudentID(String.valueOf(studentID));
                accounts.add(acc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    @Override
    public account getById(account acc) {
        account accObj = null;
        String sql = "SELECT * FROM accounts WHERE accountName = ?";

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, acc.getAcountName());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String accountName = rs.getString("accountName");
                    String password = rs.getString("password");
                    String status = rs.getString("status");
                    String studentID = rs.getString("studentID");

                    accObj = new account(accountName, password, status,studentID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accObj;
    }

    @Override
    public ArrayList<account> getByCondition(account acc) {
        ArrayList<account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE status = ? AND studentID = ?";

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, acc.getStatus());
            pstmt.setString(2, acc.getAcountName());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String accountName = rs.getString("accountName");
                    String password = rs.getString("password");
                    String status = rs.getString("status");
                    String studentID = rs.getString("studentID");

                    account accObj = new account(accountName, password, status,studentID);
                    accObj.setStudentID(String.valueOf(studentID));
                    accounts.add(accObj);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    @Override
    public String getStatusbyId(account account) {
        return "";
    }
}
