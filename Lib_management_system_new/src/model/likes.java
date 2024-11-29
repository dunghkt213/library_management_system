package model;

public class likes {
    private int id;               // ID của lượt thích
    private int studentID;        // ID của sinh viên
    private int commentID;        // ID của bình luận

    // Constructor không tham số
    public likes() {
    }

    // Constructor đầy đủ
    public likes(int id, int studentID, int commentID) {
        this.id = id;
        this.studentID = studentID;
        this.commentID = commentID;
    }

    // Constructor không cần ID (dùng khi thêm mới)
    public likes(int studentID, int commentID) {
        this.studentID = studentID;
        this.commentID = commentID;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    // Ghi đè phương thức toString
    @Override
    public String toString() {
        return "likes{" +
                "id=" + id +
                ", studentID=" + studentID +
                ", commentID=" + commentID +
                '}';
    }
}
