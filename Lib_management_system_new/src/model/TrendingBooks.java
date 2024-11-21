package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TrendingBooks {


     public static List<book> getTopTrendingBooks(List<book> allBooks, int topN) {

             allBooks.sort(Comparator.comparingInt(book::getCountOfBorrow).reversed());


             List<book> topTrending = new ArrayList<>();
             for (int i = 0; i < topN && i < allBooks.size(); i++) {
                 topTrending.add(allBooks.get(i));
             }
             return topTrending;
         }
}
