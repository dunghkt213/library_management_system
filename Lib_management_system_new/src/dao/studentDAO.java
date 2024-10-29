package dao;

import database.JDBCUtil;
import model.book;
import model.student;

import java.sql.*;
import java.util.ArrayList;

public class studentDAO implements dao.DAOInterface<student> {

    public static DAOInterface<student> getInstance() {
        return new studentDAO();
    }

    @Override
    public int insert(student student) {
        int result = 0;
        String sql = "INSERT INTO students(studentName,phoneNumber,studentEmailAddress,birthdayDate,major)"
                +  "VALUES(?,?,?,?,?)";
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Gán giá trị cho từng dấu ?
            pstmt.setString(1, student.getStudentName());
            pstmt.setString(2, student.getPhoneNumber());
            pstmt.setString(3, student.getStudentEmailAddress());
            pstmt.setString(4, student.getBirthdayDate());
            pstmt.setString(5, student.getMajor());
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
        String sql = "UPDATE  students SET studentName = ? ,phoneNumber=? ,studentEmailAddress=? ,birthdayDate= ?,major=?"
                + "WHERE studentId = ?";
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Gán giá trị cho từng dấu ?
            pstmt.setString(1, student.getStudentName());
            pstmt.setString(2, student.getPhoneNumber());
            pstmt.setString(3, student.getStudentEmailAddress());
            pstmt.setString(4, student.getBirthdayDate());
            pstmt.setString(5, student.getMajor());
            pstmt.setString(6, student.getStudentID());
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
        String sql = "DELETE FROM students WHERE studentID=  ?";
        String sql2= "UPDATE students " +
                "SET studentID = (" +
                "    SELECT COUNT(*) " +
                "    FROM students AS inner_students " +
                "    WHERE inner_students.studentID <= students.studentID" +
                ");";

        String sql3 = "ALTER TABLE students AUTO_INCREMENT = ?";

        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getStudentID());
            KetQua = pstmt.executeUpdate();

            System.out.println("Câu lệnh đã được thực thi thành công.");
            System.out.println("Có " + KetQua + " dòng đã bị xóa.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection conn = JDBCUtil.getConnection(); Statement st = conn.createStatement()) {

            st.execute(sql2);
            System.out.println("đã xét lại các thứ tự sau khi xóa.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        student.setCount(student.getCount()-1);
        try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql3)) {

            pstmt.setInt(1, student.getCount()+1);
            KetQua = pstmt.executeUpdate();

            System.out.println("Đã cập nhật lại AUTO_INCREMENT");

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
                student studentObj = new student(studentID, StudentName, Phone, Email, birthDate, major);

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
                    studentObj = new student(student.getStudentID(), StudentName, Phone, Email, birthDate, major);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentObj;
    }

    @Override
    public ArrayList<student> getByCondition(student searchCriteria) {
        ArrayList<student> studentList = new ArrayList<>();


        StringBuilder sql = new StringBuilder("SELECT * FROM students WHERE 1=1");

        if (searchCriteria.getStudentID() != null && !searchCriteria.getStudentID().isEmpty()) {
            sql.append(" AND studentID = ?");
        }
        if (searchCriteria.getStudentName() != null && !searchCriteria.getStudentName().isEmpty()) {
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
            if (searchCriteria.getStudentName() != null && !searchCriteria.getStudentName().isEmpty()) {
                pstmt.setString(index++, "%" + searchCriteria.getStudentName() + "%");
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
                    student studentObj = new student(studentID, StudentName, Phone, Email, birthDate, major);
                    studentList.add(studentObj);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }
}