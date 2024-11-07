package test;

import dao.loanDAO;
import dao.studentDAO;
import model.book;
import dao.bookDAO;
import model.loan;
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

        student newstudent = new student("2","dung","23021493@vnu.en.vn","0984021814","2005-01-31","tec");
/*        ArrayList<student> student = studentDAO.getInstance().getByCondition(newstudent);
        for(student s : student){
            s.print();
        }*/

        loan newloan = new loan("Active","2024-12-30","2024-12-30","2025-12-30","1","10112");
        loan loan1 = new loan("1");
        loanDAO.getInstance().getById(loan1).printInfo();
    }

}
