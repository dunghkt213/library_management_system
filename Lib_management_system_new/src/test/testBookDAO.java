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

        //student newStudent = new student("2302", "nvb", "2005/10/01", "vanbien", "0989618925", "CS",);
/*        ArrayList<student> student = studentDAO.getInstance().getByCondition(newstudent);
        for(student s : student){
            s.print();
        }*/
        student newStudent = new student("24112024", "nvb", "vanbien", "0989618925", "2005/10/05", "CS", "051005", "admin");
        studentDAO.getInstance().insert(newStudent);
    }
}
