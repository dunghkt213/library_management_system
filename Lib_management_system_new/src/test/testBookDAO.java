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

        student newstudent = new student("230214933","dung","23021493@vnu.en.vn","0984021814","2005-01-31","tec","1211");
/*        ArrayList<student> student = studentDAO.getInstance().getByCondition(newstudent);
        for(student s : student){
            s.print();
        }*/

        loan loan = new loan("2");
        loan loan2 = loanDAO.getInstance().getById(loan);
        loan2.setStatus("Returned");
        loanDAO.getInstance().update(loan2);
    }

}
