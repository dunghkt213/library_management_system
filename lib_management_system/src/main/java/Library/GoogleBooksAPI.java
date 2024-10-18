
package Library;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class GoogleBooksAPI {
    private static final String API_KEY = "AIzaSyDn1wri6Lc7M7xCTLGLrWcftBZeUfGVfrs";

    public static void main(String[] args) {
        String[] queries = {"Doraemon", "To Kill a Mockingbird", "1984", "Pride and Prejudice", "Java Programing", "Harry Potter", "The Hobbit"};

        for(String query : queries) {
            System.out.println("Tim kiem sach voi tu khoa: " + query);
            searchBooks(query);
        }
    }

    public static void searchBooks(String query) {
        String encodedQuery = query.replace(" ", "%20");
        String urlString = "https://www.googleapis.com/books/v1/volumes?q=" + encodedQuery + "&key=" + API_KEY;

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int response = conn.getResponseCode();

            if(response == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                in.close();

                JsonElement jsonElement = JsonParser.parseString(content.toString());
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                if(jsonObject.has("items")) {
                    JsonArray items = jsonObject.getAsJsonArray("items");

                    for(JsonElement item : items) {
                        JsonObject volumeInfo = item.getAsJsonObject().getAsJsonObject("volumeInfo");

                        String bookID = item.getAsJsonObject().get("id").getAsString();
                        String title = volumeInfo.get("title").getAsString();
                        String author = volumeInfo.has("authors") ? volumeInfo.getAsJsonArray("authors").toString() : "";
                        String publisher = volumeInfo.has("publisher") ? volumeInfo.get("publisher").getAsString() : "";
                        String edition = volumeInfo.has("publishedDate") ? volumeInfo.get("publishedDate").getAsString() : "";
                        String language = volumeInfo.has("language") ? volumeInfo.get("language").getAsString() : "";
                        int quantity = 1;
                        int remainingBooks = quantity;
                        String availability = remainingBooks > 0 ? "Available" : "Not Available";

                        MySQLConnection.insertBook(bookID, title, author, publisher, edition, language, quantity, remainingBooks, availability);
                    }
                } else {
                    System.out.println("Yeu cau that bai!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

