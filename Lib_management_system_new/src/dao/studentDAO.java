package dao;

import database.JDBCUtil;
import model.book;
import model.student;

import java.sql.*;
import java.util.ArrayList;

public class studentDAO implements DAOInterface<student> {

    public static DAOInterface<student> getInstance() {
        return new studentDAO();
    }

    @Override
    public int insert(student student) {
        int result = 0;
        String sql = "INSERT INTO students(studentName, phoneNumber, studentEmailAddress, birthdayDate, major, password, studentID) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Gán giá trị cho từng dấu ?
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getPhoneNumber());
            pstmt.setString(3, student.getEmail());
            pstmt.setString(4, student.getBirthday());
            pstmt.setString(5, student.getMajor());
            pstmt.setString(6, student.getPassword());
            pstmt.setString(7, student.getStudentID());
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
    public int update(student student) {
        int result = 0;
        String sql = "UPDATE students SET studentName = ?, phoneNumber = ?, " +
                "studentEmailAddress = ?, birthdayDate = ?, major = ?, password = ? WHERE studentID = ?";
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Gán giá trị cho từng dấu ?
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getPhoneNumber());
            pstmt.setString(3, student.getEmail());
            pstmt.setString(4, student.getBirthday());
            pstmt.setString(5, student.getMajor());
            pstmt.setString(6, student.getPassword());
            pstmt.setString(7, student.getStudentID());
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
    public int delete(student student) {
        int KetQua = 0;
        String sql = "DELETE FROM students WHERE studentID = ?";

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getStudentID());
            KetQua = pstmt.executeUpdate();

            System.out.println("Câu lệnh đã được thực thi thành công.");
            System.out.println("Có " + KetQua + " dòng đã bị xóa.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return KetQua;
    }

    @Override
    public ArrayList<student> getAll() {
        ArrayList<student> studentsList= new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Lấy các giá trị của các cột từ ResultSet
                String studentID = rs.getString("studentID");
                String StudentName = rs.getString("studentName");
                String Phone = rs.getString("phoneNumber");
                String Email = rs.getString("studentEmailAddress");
                String birthDate = rs.getString("birthdayDate");
                String major = rs.getString("major");
                String password = rs.getString("password");
                student studentObj = new student(studentID, StudentName, Email, Phone, birthDate, major, password);

                studentsList.add(studentObj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentsList;
    }

    @Override
    public student getById(student student) {
        student studentObj = null;

        String sql = "SELECT * FROM students WHERE studentID = ?";

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getStudentID());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String StudentName = rs.getString("studentName");
                    String Phone = rs.getString("phoneNumber");
                    String Email = rs.getString("studentEmailAddress");
                    String birthDate = rs.getString("birthdayDate");
                    String major = rs.getString("major");
                    String password = rs.getString("password");
                    studentObj = new student(student.getStudentID(), StudentName, Email, Phone, birthDate, major, password);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentObj;
    }
    @Override
    public String getStatusbyId(student student) {
        String status = "";

        String sql = "SELECT * FROM students WHERE studentID = ?";

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getStudentID());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    status = rs.getString("role");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public ArrayList<student> getByCondition(student searchCriteria) {
        ArrayList<student> studentList = new ArrayList<>();


        StringBuilder sql = new StringBuilder("SELECT * FROM students WHERE 1=1");

        if (searchCriteria.getStudentID() != null && !searchCriteria.getStudentID().isEmpty()) {
            sql.append(" AND studentID = ?");
        }
        if (searchCriteria.getName() != null && !searchCriteria.getName().isEmpty()) {
            sql.append(" AND studentName LIKE ?");
        }
        if (searchCriteria.getPhoneNumber() != null && !searchCriteria.getPhoneNumber().isEmpty()) {
            sql.append(" AND phoneNumber LIKE ?");

        }

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (searchCriteria.getStudentID() != null && !searchCriteria.getStudentID().isEmpty()) {
                pstmt.setString(index++, searchCriteria.getStudentID());
            }
            if (searchCriteria.getName() != null && !searchCriteria.getName().isEmpty()) {
                pstmt.setString(index++, "%" + searchCriteria.getName() + "%");
            }
            if (searchCriteria.getPhoneNumber() != null && !searchCriteria.getPhoneNumber().isEmpty()) {
                pstmt.setString(index++, "%" + searchCriteria.getPhoneNumber() + "%");
            }
            System.out.println(pstmt);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String studentID = rs.getString("studentID");
                    String StudentName = rs.getString("studentName");
                    String Phone = rs.getString("phoneNumber");
                    String Email = rs.getString("studentEmailAddress");
                    String birthDate = rs.getString("birthdayDate");
                    String major = rs.getString("major");
                    String password = rs.getString("password");
                    student studentObj = new student(studentID, StudentName, birthDate, Email, Phone, major,password);
                    studentList.add(studentObj);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }
}
