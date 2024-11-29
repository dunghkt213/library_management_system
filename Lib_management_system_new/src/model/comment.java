package model;

import java.time.LocalDateTime;

public class comment {

    private int id;
    private String bookID;
    private int studentID;
    private double rating;
    private String comment;
    private LocalDateTime createdAt;
    private int countoflike;
    // Constructor
    public comment(int id, String bookID, int studentID, double rating, String comment, LocalDateTime createdAt) {
        this.id = id;
        this.bookID = bookID;
        this.studentID = studentID;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }
    public comment(int id, String bookID, int studentID, double rating, String comment, LocalDateTime createdAt, int countoflike) {
        this.id = id;
        this.bookID = bookID;
        this.studentID = studentID;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
        this.countoflike = countoflike;
    }
    public comment(String bookID, int studentID, double rating, String comment, LocalDateTime createdAt) {
        this.bookID = bookID;
        this.studentID = studentID;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public comment() {

    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "comment{" +
                "id=" + id +
                ", bookID='" + bookID + '\'' +
                ", studentID=" + studentID +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public int getCountoflike() {
        return countoflike;
    }

    public void setCountoflike(int count) {
        this.countoflike = count;
    }
}
