package test;

import dao.studentDAO;
import model.book;
import dao.bookDAO;
import model.student;

import java.util.ArrayList;

public class testBookDAO {
    public static void main(String[] args) {
        book book = new book("11111",2,"1",12,"english","dunghkt"
                ,"NAD","testDAO","kimd222ong",2,"haikich");
        /*ArrayList<book> a = bookDAO.getInstance().getAll();
        for(book b : a) {
            System.out.println(b.getBookID());
        }*/
        student newstudent = new student("","","","0984021814","","");
        ArrayList<student> student = studentDAO.getInstance().getByCondition(newstudent);
        for(student s : student){
            s.print();
        }
    }

}
