package API;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import model.book;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GoogleBooksCache {
    private final Cache<String, ArrayList<book>> bookCache;

    public GoogleBooksCache() {
        this.bookCache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();
    }

    public ArrayList<book> getBooks(String query, int pageNumber, int pageSize) {
        String key = generateKey(query, pageNumber, pageSize);
        return bookCache.getIfPresent(key);
    }

    public void putBooks(String query, int pageNumber, int pageSize, ArrayList<book> books) {
        String key = generateKey(query, pageNumber, pageSize);
        bookCache.put(key, books);
    }

    private String generateKey(String query, int pageNumber, int pageSize) {
        return query + "_" + pageNumber + "_" + pageSize;
    }
}
