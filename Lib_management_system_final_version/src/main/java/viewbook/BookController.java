package viewbook;

import API.GoogleBooksService;
import dao.bookDAO;
import model.book;

import java.util.ArrayList;

public class BookController {
    private final bookDAO bookDao;

    public BookController() {
        this.bookDao = new bookDAO();
    }

    //Tìm kiếm trên API
    public ArrayList<book> searchBooksOnline(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Từ khóa tìm kiếm không hợp lệ!");
        }
        return GoogleBooksService.searchBooks(keyword);
    }

    //Tìm kiếm trên Database
    public ArrayList<book> searchBooksInDatabase(book searchCriteria) {
        return bookDao.getByCondition(searchCriteria);
    }

    //Tìm kiếm chủ động
    public ArrayList<book> searchBooks(book searchCriteria, boolean isOnline) {
        ArrayList<book> result = new ArrayList<>();

        if (isOnline) {
            result = searchBooksOnline(searchCriteria.getBookTitle());
        } else {
            result = searchBooksInDatabase(searchCriteria);
        }

        if (result.isEmpty()) {
            System.out.println("Không tìm thấy sách với tiêu chí đã chọn.");
        }
        return result;
    }
}
