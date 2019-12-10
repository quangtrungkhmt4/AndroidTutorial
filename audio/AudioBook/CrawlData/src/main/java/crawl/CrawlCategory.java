package crawl;

import model.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import repository.CategoryRepository;

import java.io.IOException;

public class CrawlCategory {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://truyenaudio.org/truyen-ma/loi-nguyen-ngai-den.html").get();

        Elements lis = doc.select("ul[class=dropdown-menu]").get(0)
                .select("li");
//
//        for (int i=1; i< lis.size(); i++){
//            System.out.println(lis.get(i).selectFirst("a").text());
//            CategoryRepository.createAccent(new Categoty(lis.get(i).selectFirst("a").text())
//                    , lis.get(i).selectFirst("a").attr("href"));
//        }
    }
}
