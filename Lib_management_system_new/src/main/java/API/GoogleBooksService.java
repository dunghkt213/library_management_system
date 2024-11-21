package API;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class GoogleBooksService {
    private static final String API_KEY = "AIzaSyAgPQrYLC2kJzbT4Lh9PpUxUWgkpzlxrHw";

    private static String encodeValue(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    public static CompletableFuture<ArrayList<book>> searchBooksAsync(String searchOption, String query, int pageNumber, int pageSize) {
        return CompletableFuture.supplyAsync(() -> searchBooks(searchOption, query, pageNumber, pageSize));
    }

    public static ArrayList<book> searchBooks(String searchOption, String query, int pageNumber, int pageSize) {
        try {
            query = query.trim();
            String encodedQuery = encodeValue(query);
            String url;
            if ("ISBN".equals(searchOption)) {
                url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + encodedQuery + "&key=" + API_KEY;
            } else {
                int startIndex = pageNumber * pageSize;
                url = "https://www.googleapis.com/books/v1/volumes?q=" + encodedQuery
                        + "&startIndex=" + startIndex + "&maxResults=" + pageSize + "&key=" + API_KEY;
            }

            URL apiURL = URI.create(url).toURL();
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();
            connection.setConnectTimeout(5000); // 5 seconds to connect
            connection.setReadTimeout(5000); // 5 seconds to read data
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                System.err.println("HTTP GET request not successful. Response Code: " + responseCode);
                return new ArrayList<>();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return processBookSearchResponse(response.toString());

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IOException while searching books: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    private static ArrayList<book> processBookSearchResponse(String jsonResponse) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
        JsonArray items = jsonObject.getAsJsonArray("items");
        ArrayList<book> bookList = new ArrayList<>();
        if (items != null) {
            for (JsonElement item : items) {
                JsonObject bookInfo = item.getAsJsonObject().getAsJsonObject("volumeInfo");
                if (bookInfo != null) {
                    bookList.add(extractBookFromJson(bookInfo));
                }
            }
        }
        return bookList;
    }

    private static book extractBookFromJson(JsonObject bookInfo) {
        book newBook = new book();
        newBook.setBookTitle(getJsonString(bookInfo, "title", "Unknown Title"));
        newBook.setBookAuthor(getJsonArrayAsString(bookInfo, "authors", "Unknown Authors"));
        newBook.setBookPublisher(getJsonString(bookInfo, "publisher", "Unknown Publisher"));
        newBook.setEdition(getJsonString(bookInfo, "edition", "Unknown Edition"));
        newBook.setLanguage(getJsonString(bookInfo, "language", "Unknown Language"));
        newBook.setCategoryName(getJsonArrayAsString(bookInfo, "categories", "Unknown Category"));
        newBook.setDescription(getJsonString(bookInfo, "description", "No description available."));
        newBook.setPageCount(getJsonInt(bookInfo, "pageCount", 0));

        // Generate bookID if ISBN is not available
        String isbn = getIsbn(bookInfo);
        if ("ISBN Not Available".equals(isbn)) {
            newBook.setBookID(generateUniqueBookID(newBook));
        } else {
            newBook.setBookID(isbn);
        }

        newBook.setPreviewLink(getJsonString(bookInfo, "previewLink", "No preview available."));

        JsonObject imageLinks = bookInfo.getAsJsonObject("imageLinks");
        newBook.setImageUrl(imageLinks != null ? getJsonString(imageLinks, "thumbnail", "") : "");

        return newBook;
    }

    private static String getJsonString(JsonObject jsonObject, String memberName, String defaultValue) {
        return jsonObject != null && jsonObject.has(memberName) ? jsonObject.get(memberName).getAsString() : defaultValue;
    }

    private static String getJsonArrayAsString(JsonObject jsonObject, String memberName, String defaultValue) {
        if (jsonObject != null && jsonObject.has(memberName)) {
            JsonArray jsonArray = jsonObject.getAsJsonArray(memberName);
            StringBuilder result = new StringBuilder();
            for (JsonElement element : jsonArray) {
                result.append(element.getAsString()).append(", ");
            }
            return result.substring(0, result.length() - 2); // Remove trailing comma and space
        }
        return defaultValue;
    }

    private static int getJsonInt(JsonObject jsonObject, String memberName, int defaultValue) {
        return jsonObject != null && jsonObject.has(memberName) ? jsonObject.get(memberName).getAsInt() : defaultValue;
    }

    private static String getIsbn(JsonObject bookInfo) {
        if (bookInfo != null && bookInfo.has("industryIdentifiers")) {
            JsonArray identifiers = bookInfo.getAsJsonArray("industryIdentifiers");
            for (JsonElement identifier : identifiers) {
                JsonObject id = identifier.getAsJsonObject();
                if (id.has("type") && (id.get("type").getAsString().equals("ISBN_13") ||
                        id.get("type").getAsString().equals("ISBN_10"))) {
                    return id.get("identifier").getAsString();
                }
            }
        }
        return "ISBN Not Available";
    }

    public static String generateUniqueBookID(book book) {
        String baseInfo = (book.getBookTitle() + book.getBookAuthor() + book.getBookPublisher()).toLowerCase().replaceAll("\\s+", "");
        return UUID.nameUUIDFromBytes(baseInfo.getBytes()).toString();
    }
}