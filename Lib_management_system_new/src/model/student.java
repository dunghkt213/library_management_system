package model;

public class student extends person {
    private String studentID;
    private String major;
    private int count = 0;
    public student(String studentName, String phoneNumber, String studentEmailAddress, String birthdayDate, String major) {
        super(studentName,birthdayDate,phoneNumber,studentEmailAddress);
        this.major = major;
    }

    public student(String studentID) {
        this.studentID = studentID;
    }
    public student(String studentID, String studentName, String studentEmailAddress, String phoneNumber, String birthdayDate, String major) {
       super(studentName,birthdayDate,phoneNumber,studentEmailAddress);
        this.studentID = studentID;
        this.phoneNumber = phoneNumber;
        this.major = major;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }
    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void print(){
        System.out.println("Student ID: " + studentID);
        System.out.println("Student Name: " + name);
        System.out.println("Student Email Address: " + email);
        System.out.println("Student Phone Number: " + phoneNumber);
        System.out.println("Student Birthday Date: " + birthday);
        System.out.println("Student Major: " + major);
    }
}
