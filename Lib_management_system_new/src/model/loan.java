package model;

public class loan {
    String loansID;
    String bookID;
    String studentID;
    String loanDate;
    String returnDate;
    String dueDate;
    String status;
    int increment = 0;

    public loan() {}
    public loan(String status, String dueDate, String loanDate, String returnDate,String studentID, String bookID) {
        this.status = status;
        this.dueDate = dueDate;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.studentID = studentID;
        this.bookID = bookID;
    }

    public loan(String loansID){
        this.loansID = loansID;
    }

    public loan(String status, String dueDate, String loanDate, String returnDate, String studentID, String bookID, String loansID) {
        this.status = status;
        this.dueDate = dueDate;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.studentID = studentID;
        this.bookID = bookID;
        this.loansID = loansID;
    }

    public String getLoansID() {
        return loansID;
    }

    public void setLoansID(String loansID) {
        this.loansID = loansID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
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

    public void printInfo(){
        System.out.println("loansID: "+loansID);
        System.out.println("bookID: "+bookID);
        System.out.println("studentID: "+studentID);
        System.out.println("loanDate: "+loanDate);
        System.out.println("returnDate: "+returnDate);
        System.out.println("dueDate: "+dueDate);
        System.out.println("status: "+status);
    }
}
