package crawl;

import model.Accent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import repository.AccentRepository;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CrawlAccent {

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://truyenaudio.org/truyen-ma/loi-nguyen-ngai-den.html").get();

        Elements lis = doc.select("ul[class=dropdown-menu]").get(1)
                .select("li");

        for (Element li : lis){
            AccentRepository.createAccent(new Accent(li.selectFirst("a").text()));
        }
    }
}
