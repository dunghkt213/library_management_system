package model;

public class bookloan {
    private String bookID;
    private String bookTitle;
    private String bookAuthor;
    private String status;
    private String categoryName;
    private String loanDate;
    private String dueDate;

    public bookloan(String bookID, String bookTitle, String bookAuthor, String status, String categoryName, String loanDate, String dueDate) {
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.status = status;
        this.categoryName = categoryName;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
    }

    public String getBookID() {
        return bookID;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
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
}
