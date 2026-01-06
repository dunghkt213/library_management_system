package model;

public class account extends person {
    private String acountName;
    private String password;
    private String status;
    private String studentID;

    public account(String acountName, String password, String status, String studentID) {
        this.acountName = acountName;
        this.password = password;
        this.status = status;
        this.studentID = studentID;
    }
    public account() {}

    public String getAcountName() {
        return acountName;
    }

    public void setAcountName(String acountName) {
        this.acountName = acountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void printInfo(){
        System.out.println(acountName);
        System.out.println(password);
        System.out.println(status);
        System.out.println(studentID);
    }
}
