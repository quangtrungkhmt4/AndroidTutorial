package crawl;

import model.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import repository.AccentRepository;
import repository.AudioBookRepository;
import repository.AuthorRepository;
import repository.CategoryRepository;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Crawl {

    public static void main(String[] args) throws IOException {
        List<Categoty> categoties = CategoryRepository.getCategories();

        for (Categoty categoty : categoties){
            String linkCategory = categoty.getLink();

            checkAuthorAndAccent(linkCategory);

            crawlData(linkCategory, categoty.getId());

        }



    }



    private static void checkAuthorAndAccent(String url) throws IOException {
        List<Accent> accents = AccentRepository.getAccents();
        List<Author> authors = AuthorRepository.getAuthors();

        Set<String> setAuthor = new HashSet<String>();
        Set<String> setAccent = new HashSet<String>();

        for (Accent ac : accents) {
            setAccent.add(ac.getName());
        }
        for (Author au : authors) {
            setAuthor.add(au.getName());
        }


        URLConnection openConnection = new URL(url + "?page=1").openConnection();
        openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        Document doc = Jsoup.parse(openConnection.getURL(), 5000);

        Elements panigation = doc.selectFirst("ul[class=pagination]").select("li");

        int numberOfPage;
        if (panigation.size() > 5) {
            numberOfPage = Integer.parseInt(panigation.get(panigation.size() - 1).selectFirst("a").attr("href").split("=")[1]);
        } else {
            numberOfPage = 1;
        }

        for (int i = 1; i <= numberOfPage; i++) {
            System.out.println("page: " + i);
            URLConnection childConnect = new URL(url + "?page=".concat(String.valueOf(i))).openConnection();
            childConnect.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            Document childDoc = Jsoup.parse(childConnect.getURL(), 5000);

            Elements items = childDoc.selectFirst("div[class=category-product]").select("div[class=col-md-3 col-xs-6]");

            for (Element item : items) {
                String link = item.selectFirst("h4").selectFirst("a").attr("href");

                URLConnection itemConnect = new URL(link).openConnection();
                itemConnect.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                Document itemDoc;
                try {
                    itemDoc = Jsoup.parse(itemConnect.getURL(), 5000);
                } catch (Exception ex) {
                    continue;
                }


                String accent = null, author = null;
//                System.out.println(duration);

                Element attribute = itemDoc.selectFirst("div[class=attribute]");

                if (attribute != null) {

                    if (attribute.select("p").size() > 1) {
                        accent = attribute.select("p").get(0).selectFirst("a").text();
                        author = attribute.select("p").get(1).select("a").text();
                    } else {
                        if (attribute.toString().contains("Giọng đọc:")) {
                            if (attribute.selectFirst("p") != null) {
                                accent = attribute.selectFirst("p").selectFirst("a").text();
                            }
                        } else {
                            if (attribute.selectFirst("p") != null) {
                                author = attribute.selectFirst("p").selectFirst("a").text();
                            }
                        }
                    }

                    if (accent != null) {
                        setAccent.add(accent);

                    }
                    if (author != null) {
                        setAuthor.add(author);
                    }
                }

            }

        }

        for (String au : setAuthor) {
            AuthorRepository.createAuthorCheckExists(new Author(au));
        }

        for (String ac : setAccent) {
            AccentRepository.createAccentCheckExists(new Accent(ac));
        }
    }

    private static void crawlData(String urlTruyen, String idCategory) throws IOException {

        List<Accent> accents = AccentRepository.getAccents();
        List<Author> authors = AuthorRepository.getAuthors();

        URLConnection openConnection = new URL(urlTruyen + "?page=1").openConnection();
        openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        Document doc = Jsoup.parse(openConnection.getURL(), 5000);

        Elements panigation = doc.selectFirst("ul[class=pagination]").select("li");

        int numberOfPage;
        if (panigation.size() > 5){
            numberOfPage = Integer.parseInt(panigation.get(panigation.size() - 1).selectFirst("a").attr("href").split("=")[1]);
        }else {
            numberOfPage = 1;
        }

        for (int i = 1; i <= numberOfPage; i++) {
            URLConnection childConnect = new URL(urlTruyen + "?page=".concat(String.valueOf(i))).openConnection();
            childConnect.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            Document childDoc = Jsoup.parse(childConnect.getURL(), 5000);

            Elements items = childDoc.selectFirst("div[class=category-product]").select("div[class=col-md-3 col-xs-6]");

            for (Element item : items) {
                String link = item.selectFirst("h4").selectFirst("a").attr("href");
                String info = item.selectFirst("p[class=audio-info]").text();
                String duration = info.substring(0, 8);
                String views = info.split("Lượt nghe: ")[1].replace(".", "");
                int parts;
                try {
                    parts = Integer.parseInt(info.split(" ")[1]);
                } catch (Exception ex) {
                    parts = 0;
                }

                System.out.println(views);

                URLConnection itemConnect = new URL(link).openConnection();
                itemConnect.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                Document itemDoc;
                try {
                    itemDoc = Jsoup.parse(itemConnect.getURL(), 5000);
                }catch (Exception ex){
                    continue;
                }

                String name = itemDoc.selectFirst("div[class=col-sm-5]").selectFirst("h1").text();
                String image = itemDoc.selectFirst("figure[class=media-wrap]").selectFirst("img").attr("src");
                List<Part> chaps = new ArrayList<Part>();
                Elements listPart = itemDoc.selectFirst("div[class=download-link]").selectFirst("ul").select("li");

                for (Element chap : listPart) {
                    String chapName = chap.selectFirst("a").text();
                    String chapUrl = chap.selectFirst("a").attr("href");
                    chaps.add(new Part(chapName, chapUrl));
                }

                Element lienquan = itemDoc.selectFirst("ul[class=related-pro]");

                String lienQuanStr = null;
                if (lienquan != null){
                    Elements lienQuanChild = lienquan.select("li");
                    List<String> lienQuanArr = new ArrayList<String>();
                    for (Element ele : lienQuanChild){
                        lienQuanArr.add(ele.select("a").text());
                    }
                    lienQuanStr = String.join(",", lienQuanArr);
                }

                String accent = null, author = null;
//                System.out.println(duration);

                Element attribute = itemDoc.selectFirst("div[class=attribute]");

                AudioBook audioBook = new AudioBook();
                audioBook.setName(name);
                audioBook.setParts(chaps);
                audioBook.setDuration(duration);
                audioBook.setImage(image);
                audioBook.setNumberOfPart(parts);
                audioBook.setViews(Long.parseLong(views));
                audioBook.setCreatedAt(System.currentTimeMillis());
                audioBook.setIdCategory(idCategory);
                audioBook.setRelatedAudio(lienQuanStr);

                if (attribute != null) {

                    if (attribute.select("p").size() > 1) {
                        accent = attribute.select("p").get(0).selectFirst("a").text();
                        author = attribute.select("p").get(1).select("a").text();
                        System.out.println("\tname: " + name + " - accent: " + accent + " - author: " + author);
                    } else {
                        if (attribute.toString().contains("Giọng đọc:")) {
                            accent = attribute.selectFirst("p").selectFirst("a").text();
                            System.out.println("\tname: " + name + " - accent: " + accent);
                        } else if (attribute.toString().contains("Tác giả:")){
                            author = attribute.selectFirst("p").select("a").text();
                            System.out.println("\tname: " + name + " - author: " + author);
                        }
                    }
                    if (accent != null) {
                        for (Accent a : accents) {
                            if (a.getName().equals(accent)) {
                                accent = a.getId();
                                break;
                            }
                        }
                        audioBook.setIdAccent(accent);

                    }
                    if (author != null) {
                        for (Author a : authors) {
                            if (a.getName().equals(author)) {
                                author = a.getId();
                                break;
                            }
                        }
                        audioBook.setIdAuthor(author);
                    }
                }

                AudioBookRepository.createBook(audioBook);
            }

        }


    }



}
