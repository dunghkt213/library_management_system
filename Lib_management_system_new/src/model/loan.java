package model;

public class loan {
    int loansID;
    String bookID;
    int studentID;
    String loanDate;
    String returnDate;
    String dueDate;
    String status;

    public int getLoansID() {
        return loansID;
    }

    public void setLoansID(int loansID) {
        this.loansID = loansID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public loan(String status, String dueDate, String loanDate, String returnDate, int studentID, String bookID, int loansID) {
        this.status = status;
        this.dueDate = dueDate;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.studentID = studentID;
        this.bookID = bookID;
        this.loansID = loansID;
    }
}
