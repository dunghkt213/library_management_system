package model;

public class book {
    private String bookID ;
    private String bookTitle ;
    private String bookAuthor ;
    private String bookPublisher ;
    private String edition ;
    private String language ;
    private int quantity ;
    private int remainingBooks ;
    private String availability ;
    private int categoryID ;
    private String categoryName ;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public book(){
    }
    public book(String bookID, int categoryID, String availability, int remainingBooks
            , String language, String edition, String bookAuthor, String bookTitle, String bookPublisher, int quantity,String categoryName) {
        this.bookID = bookID;
        this.categoryID = categoryID;
        this.availability = availability;
        this.remainingBooks = remainingBooks;
        this.language = language;
        this.edition = edition;
        this.bookAuthor = bookAuthor;
        this.bookTitle = bookTitle;
        this.bookPublisher = bookPublisher;
        this.quantity = quantity;
        this.categoryName = categoryName;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getRemainingBooks() {
        return remainingBooks;
    }

    public void setRemainingBooks(int remainingBooks) {
        this.remainingBooks = remainingBooks;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }
}
