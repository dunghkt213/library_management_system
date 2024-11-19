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

public class GoogleBooksService {
    private static final String API_KEY = "AIzaSyAgPQrYLC2kJzbT4Lh9PpUxUWgkpzlxrHw";

    private static String encodeValue(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    public static ArrayList<book> searchBooks(String query, int pageNumber, int pageSize) {
        try {
            query = query.trim();
            int startIndex = pageNumber * pageSize;

            // Ensure that we do not request more than 12 results in total
            if (startIndex >= 12) {
                return new ArrayList<>();
            }

            String url = "https://www.googleapis.com/books/v1/volumes?q=" + encodeValue(query)
                    +  "&startIndex=" + startIndex + "&maxResults=" + pageSize
                    + "&key=" + API_KEY;
            URL apiUrl = URI.create(url).toURL();
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            connection.disconnect();

            return processBookSearchResponse(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
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
                JsonObject book = item.getAsJsonObject().getAsJsonObject("volumeInfo");
                String title = book.has("title") ? book.get("title").getAsString() : "Unknown Title";
                String authors = book.has("authors") ? book.get("authors").toString() : "Unknown Authors";
                String publisher = book.has("publisher") ? book.get("publisher").getAsString() : "Unknown Publisher";
                String edition = book.has("edition") ? book.get("edition").getAsString() : "Unknown Edition";
                String language = book.has("language") ? book.get("language").getAsString() : "Unknown Language";
                String categoryName = book.has("categories") ? book.getAsJsonArray("categories").get(0).getAsString() : "Unknown Category";

                // Lấy thêm các thông tin mới
                String description = book.has("description") ? book.get("description").getAsString() : "No description available.";
                int pageCount = book.has("pageCount") ? book.get("pageCount").getAsInt() : 0;
                //float averageRating = book.has("averageRating") ? book.get("averageRating").getAsFloat() : 0.0f;
                //String maturityRating = book.has("maturityRating") ? book.get("maturityRating").getAsString() : "UNKNOWN";

                book newBook = new book();
                newBook.setBookTitle(title);
                newBook.setBookAuthor(authors);
                newBook.setBookPublisher(publisher);
                newBook.setEdition(edition);
                newBook.setLanguage(language);
                newBook.setCategoryName(categoryName);
                //newBook.setQuantity(1);
                //newBook.setRemainingBooks(1);
                //newBook.setAvailability("Available");
                //newBook.setCategoryID(1);

                // Set các thông tin mới vào đối tượng book
                newBook.setDescription(description);
                newBook.setPageCount(pageCount);
                //newBook.setAverageRating(averageRating);
                //newBook.setMaturityRating(maturityRating);

                if (book.has("imageLinks")) {
                    JsonObject imageLinks = book.getAsJsonObject("imageLinks");
                    String thumbnailUrl = imageLinks.has("thumbnail") ? imageLinks.get("thumbnail").getAsString() : "";
                    newBook.setImageUrl(thumbnailUrl);
                }

                bookList.add(newBook);
            }
        }

        return bookList;
    }
}
