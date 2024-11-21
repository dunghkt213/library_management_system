package test;

import dao.accountDAO;
import dao.loanDAO;
import dao.studentDAO;
import model.account;
import model.book;
import dao.bookDAO;
import model.loan;
import model.student;

import java.util.ArrayList;

public class testBookDAO {
    public static void main(String[] args) {
        /*ArrayList<book> a = bookDAO.getInstance().getAll();
        for(book b : a) {
            System.out.println(b.getBookID());
        }*/

        //student newstudent = new student("230214933","dung","23021493@vnu.en.vn","0984021814","2005-01-31","tec","1211");
/*        ArrayList<student> student = studentDAO.getInstance().getByCondition(newstudent);
        for(student s : student){
            s.print();
        }*/
        book newbook = new book();
        newbook.setBookID("9780132119177");
        newbook.setBookTitle("The Pragmatic Programmer");
        newbook.setBookAuthor("[\"Andrew Hunt\",\"David Thomas\"]\n");
        newbook.setBookPublisher("Addison-Wesley Professional");
        newbook.setLanguage("en");
        newbook.setCategoryName("Computers");
        newbook.setImageUrl("http://books.google.com/books/content?id=5wBQEp6ruIAC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api");
        newbook.setDescription("What others in the trenches say about The Pragmatic Programmer... “The cool thing about this book is that it’s great for keeping the programming process fresh. The book helps you to continue to grow and clearly comes from people who have been there.” — Kent Beck, author of Extreme Programming Explained: Embrace Change “I found this book to be a great mix of solid advice and wonderful analogies!” — Martin Fowler");
        bookDAO.getInstance().insert(newbook);

    }

}
