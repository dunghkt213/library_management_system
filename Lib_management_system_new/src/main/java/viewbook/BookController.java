package viewbook;

import API.GoogleBooksCache;
import API.GoogleBooksService;
import model.book;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookController {
    private final GoogleBooksCache booksCache;
    private final ExecutorService executorService;
    private final Logger logger = Logger.getLogger(BookController.class.getName());

    public BookController() {
        this.booksCache = new GoogleBooksCache();
        this.executorService = Executors.newFixedThreadPool(10); // Số Thread tối ưu
    }

    public CompletableFuture<ArrayList<book>> searchBooksOnlineOrCache(String searchOption, String keyword, int pageNumber, int pageSize) {
        String sanitizedKeyword = keyword.trim().toLowerCase();
        return CompletableFuture.supplyAsync(() -> searchBooksOnlineOrCacheImpl(searchOption, sanitizedKeyword, pageNumber, pageSize), executorService);
    }

    private ArrayList<book> searchBooksOnlineOrCacheImpl(String searchOption, String keyword, int pageNumber, int pageSize) {
        ArrayList<book> books = new ArrayList<>();
        try {
            // Kiểm tra cache trước khi gọi API
            ArrayList<book> cachedBooks = booksCache.getBooks(searchOption + ":" + keyword, pageNumber, pageSize);
            if (cachedBooks != null) {
                return cachedBooks;
            }

            // Nếu không có trong cache, gọi API và lưu vào cache không đồng bộ
            books = GoogleBooksService.searchBooks(searchOption, keyword, pageNumber, pageSize);
            if (books != null && !books.isEmpty()) {
                booksCache.putBooks(searchOption + ":" + keyword, pageNumber, pageSize, books);

                // Prefetch next page for better user experience
                if (books.size() == pageSize) {
                    prefetchNextPage(searchOption, keyword, pageNumber + 1, pageSize);
                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error searching books: ", ex);
        }
        return books;
    }

    private void prefetchNextPage(String searchOption, String keyword, int nextPageNumber, int pageSize) {
        CompletableFuture.runAsync(() -> {
            try {
                GoogleBooksService.searchBooksAsync(searchOption, keyword, nextPageNumber, pageSize)
                        .thenAccept(nextBooks -> {
                            if (nextBooks != null && !nextBooks.isEmpty()) {
                                booksCache.putBooks(searchOption + ":" + keyword, nextPageNumber, pageSize, nextBooks);
                            }
                        })
                        .exceptionally(ex -> {
                            logger.log(Level.SEVERE, "Error prefetching next page: ", ex);
                            return null;
                        });
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Exception in prefetching next page: ", ex);
            }
        }, executorService);
    }

    public void shutdown() {
        executorService.shutdown();
    }
}