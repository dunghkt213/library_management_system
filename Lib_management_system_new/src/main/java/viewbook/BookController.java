package viewbook;

import API.GoogleBooksCache;
import API.GoogleBooksService;
import dao.bookDAO;
import model.book;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class BookController {
    private final bookDAO bookDao;
    private final GoogleBooksCache booksCache;
    private final ExecutorService executorService;

    public BookController() {
        this.bookDao = new bookDAO();
        this.booksCache = new GoogleBooksCache();
        this.executorService = Executors.newFixedThreadPool(10); // Tạo ExecutorService với 4 luồng
    }

    // Tìm kiếm trên API với Bộ Nhớ Đệm và Xử Lý Không Đồng Bộ
    public CompletableFuture<ArrayList<book>> searchBooksOnlineAsync(String keyword, int pageNumber, int pageSize) {
        return CompletableFuture.supplyAsync(() -> searchBooksOnline(keyword, pageNumber, pageSize), executorService);
    }

    //Tìm kiếm trên API
    public ArrayList<book> searchBooksOnline(String keyword, int pageNumber, int pageSize) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Từ khóa tìm kiếm không hợp lệ!");
        }

        // Kiểm tra trong bộ nhớ đệm
        ArrayList<book> cachedBooks = booksCache.getBooks(keyword, pageNumber, pageSize);
        if (cachedBooks != null) {
            return cachedBooks;
        }

        // Nếu không có trong đệm, truy vấn API
        ArrayList<book> books = GoogleBooksService.searchBooks(keyword, pageNumber, pageSize);

        // Lưu kết quả vào bộ nhớ đệm
        booksCache.putBooks(keyword, pageNumber, pageSize, books);

        return books;
    }

    // Tìm kiếm trên Database không đồng bộ
    public CompletableFuture<ArrayList<book>> searchBooksInDatabaseAsync(book searchCriteria) {
        return CompletableFuture.supplyAsync(() -> searchBooksInDatabase(searchCriteria), executorService);
    }

    // Tìm kiếm trên Database
    public ArrayList<book> searchBooksInDatabase(book searchCriteria) {
        return bookDao.getByCondition(searchCriteria);
    }

    // Tìm kiếm chủ động không đồng bộ
    public CompletableFuture<ArrayList<book>> searchBooksAsync(book searchCriteria, boolean isOnline, int pageNumber, int pageSize) {
        return CompletableFuture.supplyAsync(() -> searchBooks(searchCriteria, isOnline, pageNumber, pageSize), executorService);
    }

    // Tìm kiếm chủ động
    public ArrayList<book> searchBooks(book searchCriteria, boolean isOnline, int pageNumber, int pageSize) {
        ArrayList<book> result = new ArrayList<>();

        if (isOnline) {
            result = searchBooksOnline(searchCriteria.getBookTitle(), pageNumber, pageSize);
        } else {
            result = searchBooksInDatabase(searchCriteria);
        }

        if (result.isEmpty()) {
            System.out.println("Không tìm thấy sách với tiêu chí đã chọn.");
        }
        return result;
    }
}