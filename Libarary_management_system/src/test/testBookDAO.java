package test;

import model.book;
import dao.bookDAO;

import java.util.ArrayList;

public class testBookDAO {
    public static void main(String[] args) {
        book book = new book("11111",2,"1",12,"english","dunghkt"
                ,"NAD","testDAO","kimd222ong",2,"haikich");
        /*ArrayList<book> a = bookDAO.getInstance().getAll();
        for(book b : a) {
            System.out.println(b.getBookID());
        }*/
        book test = new book();
        test.setBookID("10112");
        book a = bookDAO.getInstance().getById(test);
        System.out.println(a.getBookTitle());
    }

}
