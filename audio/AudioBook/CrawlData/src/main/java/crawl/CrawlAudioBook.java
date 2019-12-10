package crawl;

import model.Accent;
import model.AudioBook;
import model.Author;
import model.Part;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import repository.AccentRepository;
import repository.AudioBookRepository;
import repository.AuthorRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CrawlAudioBook {

    public static void main(String[] args) throws IOException {
//        Document doc = Jsoup.connect("https://truyenaudio.org/truyen-ma/loi-nguyen-ngai-den.html").get();
//
//        Elements lis = doc.select("ul[class=dropdown-menu]").get(0)
//                .select("li");
//
//        for (int i=1; i< lis.size(); i++){
//            CategoryRepository.createAccent(new Categoty(lis.get(i).selectFirst("a").text()));
//        }
//
//        checkAuthorAndAccent();

//        crawlTruyenMa();
//        crawlTruyenNgan();
//        crawlTruyenDai();
//        crawlTruyenTinhYeu();
//        crawlTruyenNgonTinh();
//        crawlTruyenTrinhTham();
//        crawlTruyenKiemHiep();
//        crawlTruyenLichSu();
//        crawlTruyenKinhDien();
//        crawlTruyenThanhCong();
//        crawlTruyenNguyenNgocNgan();
//        crawlTruyenDemKhuya();
//        crawlTruyenVanHoc();
        crawlTruyenRadio();
    }


    private static void checkAuthorAndAccent() throws IOException {
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


        URLConnection openConnection = new URL("https://truyenaudio.org/radio/?page=1").openConnection();
        openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        Document doc = Jsoup.parse(openConnection.getURL(), 5000);

        Elements panigation = doc.selectFirst("ul[class=pagination]").select("li");

//        int numberOfPage = Integer.parseInt(panigation.get(panigation.size() - 1).selectFirst("a").attr("href").split("=")[1]);

        int numberOfPage = 1;

        for (int i = 1; i <= numberOfPage; i++) {
            System.out.println("page: " + i);
            URLConnection childConnect = new URL("https://truyenaudio.org/radio/?page=".concat(String.valueOf(i))).openConnection();
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
                }catch (Exception ex){
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
                            if (attribute.selectFirst("p") != null){
                                accent = attribute.selectFirst("p").selectFirst("a").text();
                            }
                        } else {
                            if (attribute.selectFirst("p") != null){
                                author = attribute.selectFirst("p").selectFirst("a").text();
                            }
                        }
                    }


                    if (accent != null) {
//                        for (Accent a : accents){
//                            if (a.getName().equals(accent)){
//                                accent = a.getId();
//                                break;
//                            }
//                        }
//                        audioBook.setIdAccent(accent);

                        setAccent.add(accent);

                    }
                    if (author != null) {
//                        for (Author a : authors){
//                            if (a.getName().equals(author)){
//                                author = a.getId();
//                                break;
//                            }
//                        }
//                        audioBook.setIdAuthor(author);

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


    private static void crawlTruyenMa() throws IOException {

//        Set<String> setAuthor = new HashSet<String>();
//        Set<String> setAccent = new HashSet<String>();

        List<Accent> accents = AccentRepository.getAccents();
        List<Author> authors = AuthorRepository.getAuthors();

        URLConnection openConnection = new URL("https://truyenaudio.org/truyen-ma/?page=1").openConnection();
        openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        Document doc = Jsoup.parse(openConnection.getURL(), 5000);

        Elements panigation = doc.selectFirst("ul[class=pagination]").select("li");

        int numberOfPage = Integer.parseInt(panigation.get(panigation.size() - 1).selectFirst("a").attr("href").split("=")[1]);

        for (int i = 1; i <= numberOfPage; i++) {
            URLConnection childConnect = new URL("https://truyenaudio.org/truyen-ma/?page=".concat(String.valueOf(i))).openConnection();
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
                Document itemDoc = Jsoup.parse(itemConnect.getURL(), 5000);

                String name = itemDoc.selectFirst("div[class=col-sm-5]").selectFirst("h1").text();
                String image = itemDoc.selectFirst("figure[class=media-wrap]").selectFirst("img").attr("src");
                List<Part> chaps = new ArrayList<Part>();
                Elements listPart = itemDoc.selectFirst("div[class=download-link]").selectFirst("ul").select("li");

                for (Element chap : listPart) {
                    String chapName = chap.selectFirst("a").text();
                    String chapUrl = chap.selectFirst("a").attr("href");
                    chaps.add(new Part(chapName, chapUrl));
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
                audioBook.setIdCategory("5dbbe15ba5382b7227a9e040");

                if (attribute != null) {

                    if (attribute.select("p").size() > 1) {
                        accent = attribute.select("p").get(0).selectFirst("a").text();
                        author = attribute.select("p").get(1).select("a").text();
                        System.out.println("\tname: " + name + " - accent: " + accent + " - author: " + author);
                    } else {
                        if (attribute.toString().contains("Giọng đọc:")) {
                            accent = attribute.selectFirst("p").selectFirst("a").text();
                            System.out.println("\tname: " + name + " - accent: " + accent);
                        } else {
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

//                        setAccent.add(accent);

                    }
                    if (author != null) {
                        for (Author a : authors) {
                            if (a.getName().equals(author)) {
                                author = a.getId();
                                break;
                            }
                        }
                        audioBook.setIdAuthor(author);

//                        setAuthor.add(author);
                    }
                }

                AudioBookRepository.createBook(audioBook);


            }

        }
//        for (String au : setAuthor){
//            AuthorRepository.createAuthor(new Author(au));
//        }
//
//        for (String ac : setAccent){
//            AccentRepository.createAccent(new Accent(ac));
//        }
    }

    private static void crawlTruyenNgan() throws IOException {

//        Set<String> setAuthor = new HashSet<String>();
//        Set<String> setAccent = new HashSet<String>();

        List<Accent> accents = AccentRepository.getAccents();
        List<Author> authors = AuthorRepository.getAuthors();

        URLConnection openConnection = new URL("https://truyenaudio.org/truyen-ngan/?page=1").openConnection();
        openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        Document doc = Jsoup.parse(openConnection.getURL(), 5000);

        Elements panigation = doc.selectFirst("ul[class=pagination]").select("li");

        int numberOfPage = Integer.parseInt(panigation.get(panigation.size() - 1).selectFirst("a").attr("href").split("=")[1]);

        for (int i = 1; i <= numberOfPage; i++) {
            URLConnection childConnect = new URL("https://truyenaudio.org/truyen-ngan/?page=".concat(String.valueOf(i))).openConnection();
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
                audioBook.setIdCategory("5dbbe15ba5382b7227a9e041");

                if (attribute != null) {

                    if (attribute.select("p").size() > 1) {
                        accent = attribute.select("p").get(0).selectFirst("a").text();
                        author = attribute.select("p").get(1).select("a").text();
                    } else {
                        if (attribute.toString().contains("Giọng đọc:")) {
                            if (attribute.selectFirst("p") != null){
                                accent = attribute.selectFirst("p").selectFirst("a").text();
                            }
                        } else {
                            if (attribute.selectFirst("p") != null){
                                author = attribute.selectFirst("p").selectFirst("a").text();
                            }
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

//                        setAccent.add(accent);

                    }
                    if (author != null) {
                        for (Author a : authors) {
                            if (a.getName().equals(author)) {
                                author = a.getId();
                                break;
                            }
                        }
                        audioBook.setIdAuthor(author);

//                        setAuthor.add(author);
                    }
                }

                AudioBookRepository.createBook(audioBook);


            }

        }
//        for (String au : setAuthor){
//            AuthorRepository.createAuthor(new Author(au));
//        }
//
//        for (String ac : setAccent){
//            AccentRepository.createAccent(new Accent(ac));
//        }
    }

    private static void crawlTruyenDai() throws IOException {

//        Set<String> setAuthor = new HashSet<String>();
//        Set<String> setAccent = new HashSet<String>();

        List<Accent> accents = AccentRepository.getAccents();
        List<Author> authors = AuthorRepository.getAuthors();

        URLConnection openConnection = new URL("https://truyenaudio.org/truyen-dai/?page=1").openConnection();
        openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        Document doc = Jsoup.parse(openConnection.getURL(), 5000);

        Elements panigation = doc.selectFirst("ul[class=pagination]").select("li");

        int numberOfPage = Integer.parseInt(panigation.get(panigation.size() - 1).selectFirst("a").attr("href").split("=")[1]);

        for (int i = 1; i <= numberOfPage; i++) {
            System.out.println("page: "  +i);
            URLConnection childConnect = new URL("https://truyenaudio.org/truyen-dai/?page=".concat(String.valueOf(i))).openConnection();
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
                audioBook.setIdCategory("5dbbe15ba5382b7227a9e042");

                if (attribute != null) {

                    if (attribute.select("p").size() > 1) {
                        accent = attribute.select("p").get(0).selectFirst("a").text();
                        author = attribute.select("p").get(1).select("a").text();
                    } else {
                        if (attribute.toString().contains("Giọng đọc:")) {
                            if (attribute.selectFirst("p") != null){
                                accent = attribute.selectFirst("p").selectFirst("a").text();
                            }
                        } else {
                            if (attribute.selectFirst("p") != null){
                                author = attribute.selectFirst("p").selectFirst("a").text();
                            }
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

//                        setAccent.add(accent);

                    }
                    if (author != null) {
                        for (Author a : authors) {
                            if (a.getName().equals(author)) {
                                author = a.getId();
                                break;
                            }
                        }
                        audioBook.setIdAuthor(author);

//                        setAuthor.add(author);
                    }
                }

                AudioBookRepository.createBook(audioBook);


            }

        }
//        for (String au : setAuthor){
//            AuthorRepository.createAuthor(new Author(au));
//        }
//
//        for (String ac : setAccent){
//            AccentRepository.createAccent(new Accent(ac));
//        }
    }

    private static void crawlTruyenTinhYeu() throws IOException {

//        Set<String> setAuthor = new HashSet<String>();
//        Set<String> setAccent = new HashSet<String>();

        List<Accent> accents = AccentRepository.getAccents();
        List<Author> authors = AuthorRepository.getAuthors();

        URLConnection openConnection = new URL("https://truyenaudio.org/tinh-yeu/?page=1").openConnection();
        openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        Document doc = Jsoup.parse(openConnection.getURL(), 5000);

        Elements panigation = doc.selectFirst("ul[class=pagination]").select("li");

        int numberOfPage = Integer.parseInt(panigation.get(panigation.size() - 1).selectFirst("a").attr("href").split("=")[1]);

        for (int i = 1; i <= numberOfPage; i++) {
            System.out.println("page: "  +i);
            URLConnection childConnect = new URL("https://truyenaudio.org/tinh-yeu/?page=".concat(String.valueOf(i))).openConnection();
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
                audioBook.setIdCategory("5dbbe15ba5382b7227a9e043");

                if (attribute != null) {

                    if (attribute.select("p").size() > 1) {
                        accent = attribute.select("p").get(0).selectFirst("a").text();
                        author = attribute.select("p").get(1).select("a").text();
                    } else {
                        if (attribute.toString().contains("Giọng đọc:")) {
                            if (attribute.selectFirst("p") != null){
                                accent = attribute.selectFirst("p").selectFirst("a").text();
                            }
                        } else {
                            if (attribute.selectFirst("p") != null){
                                author = attribute.selectFirst("p").selectFirst("a").text();
                            }
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

//                        setAccent.add(accent);

                    }
                    if (author != null) {
                        for (Author a : authors) {
                            if (a.getName().equals(author)) {
                                author = a.getId();
                                break;
                            }
                        }
                        audioBook.setIdAuthor(author);

//                        setAuthor.add(author);
                    }
                }

                AudioBookRepository.createBook(audioBook);


            }

        }
//        for (String au : setAuthor){
//            AuthorRepository.createAuthor(new Author(au));
//        }
//
//        for (String ac : setAccent){
//            AccentRepository.createAccent(new Accent(ac));
//        }
    }

    private static void crawlTruyenNgonTinh() throws IOException {

//        Set<String> setAuthor = new HashSet<String>();
//        Set<String> setAccent = new HashSet<String>();

        List<Accent> accents = AccentRepository.getAccents();
        List<Author> authors = AuthorRepository.getAuthors();

        URLConnection openConnection = new URL("https://truyenaudio.org/ngon-tinh/?page=1").openConnection();
        openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        Document doc = Jsoup.parse(openConnection.getURL(), 5000);

        Elements panigation = doc.selectFirst("ul[class=pagination]").select("li");

        int numberOfPage = Integer.parseInt(panigation.get(panigation.size() - 1).selectFirst("a").attr("href").split("=")[1]);

        for (int i = 1; i <= numberOfPage; i++) {
            System.out.println("page: "  +i);
            URLConnection childConnect = new URL("https://truyenaudio.org/ngon-tinh/?page=".concat(String.valueOf(i))).openConnection();
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
                audioBook.setIdCategory("5dbbe15ba5382b7227a9e044");

                if (attribute != null) {

                    if (attribute.select("p").size() > 1) {
                        accent = attribute.select("p").get(0).selectFirst("a").text();
                        author = attribute.select("p").get(1).select("a").text();
                    } else {
                        if (attribute.toString().contains("Giọng đọc:")) {
                            if (attribute.selectFirst("p") != null){
                                accent = attribute.selectFirst("p").selectFirst("a").text();
                            }
                        } else {
                            if (attribute.selectFirst("p") != null){
                                author = attribute.selectFirst("p").selectFirst("a").text();
                            }
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

//                        setAccent.add(accent);

                    }
                    if (author != null) {
                        for (Author a : authors) {
                            if (a.getName().equals(author)) {
                                author = a.getId();
                                break;
                            }
                        }
                        audioBook.setIdAuthor(author);

//                        setAuthor.add(author);
                    }
                }

                AudioBookRepository.createBook(audioBook);


            }

        }
//        for (String au : setAuthor){
//            AuthorRepository.createAuthor(new Author(au));
//        }
//
//        for (String ac : setAccent){
//            AccentRepository.createAccent(new Accent(ac));
//        }
    }

    private static void crawlTruyenTrinhTham() throws IOException {

//        Set<String> setAuthor = new HashSet<String>();
//        Set<String> setAccent = new HashSet<String>();

        List<Accent> accents = AccentRepository.getAccents();
        List<Author> authors = AuthorRepository.getAuthors();

        URLConnection openConnection = new URL("https://truyenaudio.org/trinh-tham/?page=1").openConnection();
        openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        Document doc = Jsoup.parse(openConnection.getURL(), 5000);

        Elements panigation = doc.selectFirst("ul[class=pagination]").select("li");

        int numberOfPage = Integer.parseInt(panigation.get(panigation.size() - 1).selectFirst("a").attr("href").split("=")[1]);

        for (int i = 1; i <= numberOfPage; i++) {
            System.out.println("page: "  +i);
            URLConnection childConnect = new URL("https://truyenaudio.org/trinh-tham/?page=".concat(String.valueOf(i))).openConnection();
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
                audioBook.setIdCategory("5dbbe15ba5382b7227a9e045");

                if (attribute != null) {

                    if (attribute.select("p").size() > 1) {
                        accent = attribute.select("p").get(0).selectFirst("a").text();
                        author = attribute.select("p").get(1).select("a").text();
                    } else {
                        if (attribute.toString().contains("Giọng đọc:")) {
                            if (attribute.selectFirst("p") != null){
                                accent = attribute.selectFirst("p").selectFirst("a").text();
                            }
                        } else {
                            if (attribute.selectFirst("p") != null){
                                author = attribute.selectFirst("p").selectFirst("a").text();
                            }
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

//                        setAccent.add(accent);

                    }
                    if (author != null) {
                        for (Author a : authors) {
                            if (a.getName().equals(author)) {
                                author = a.getId();
                                break;
                            }
                        }
                        audioBook.setIdAuthor(author);

//                        setAuthor.add(author);
                    }
                }

                AudioBookRepository.createBook(audioBook);


            }

        }
//        for (String au : setAuthor){
//            AuthorRepository.createAuthor(new Author(au));
//        }
//
//        for (String ac : setAccent){
//            AccentRepository.createAccent(new Accent(ac));
//        }
    }

    private static void crawlTruyenKiemHiep() throws IOException {

//        Set<String> setAuthor = new HashSet<String>();
//        Set<String> setAccent = new HashSet<String>();

        List<Accent> accents = AccentRepository.getAccents();
        List<Author> authors = AuthorRepository.getAuthors();

        URLConnection openConnection = new URL("https://truyenaudio.org/kiem-hiep/?page=1").openConnection();
        openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        Document doc = Jsoup.parse(openConnection.getURL(), 5000);

        Elements panigation = doc.selectFirst("ul[class=pagination]").select("li");

        int numberOfPage = Integer.parseInt(panigation.get(panigation.size() - 1).selectFirst("a").attr("href").split("=")[1]);

        for (int i = 1; i <= numberOfPage; i++) {
            System.out.println("page: "  +i);
            URLConnection childConnect = new URL("https://truyenaudio.org/kiem-hiep/?page=".concat(String.valueOf(i))).openConnection();
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
                audioBook.setIdCategory("5dbbe15ba5382b7227a9e046");

                if (attribute != null) {

                    if (attribute.select("p").size() > 1) {
                        accent = attribute.select("p").get(0).selectFirst("a").text();
                        author = attribute.select("p").get(1).select("a").text();
                    } else {
                        if (attribute.toString().contains("Giọng đọc:")) {
                            if (attribute.selectFirst("p") != null){
                                accent = attribute.selectFirst("p").selectFirst("a").text();
                            }
                        } else {
                            if (attribute.selectFirst("p") != null){
                                author = attribute.selectFirst("p").selectFirst("a").text();
                            }
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

//                        setAccent.add(accent);

                    }
                    if (author != null) {
                        for (Author a : authors) {
                            if (a.getName().equals(author)) {
                                author = a.getId();
                                break;
                            }
                        }
                        audioBook.setIdAuthor(author);

//                        setAuthor.add(author);
                    }
                }

                AudioBookRepository.createBook(audioBook);


            }

        }
//        for (String au : setAuthor){
//            AuthorRepository.createAuthor(new Author(au));
//        }
//
//        for (String ac : setAccent){
//            AccentRepository.createAccent(new Accent(ac));
//        }
    }

    private static void crawlTruyenLichSu() throws IOException {

//        Set<String> setAuthor = new HashSet<String>();
//        Set<String> setAccent = new HashSet<String>();

        List<Accent> accents = AccentRepository.getAccents();
        List<Author> authors = AuthorRepository.getAuthors();

        URLConnection openConnection = new URL("https://truyenaudio.org/lich-su/?page=1").openConnection();
        openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        Document doc = Jsoup.parse(openConnection.getURL(), 5000);

        Elements panigation = doc.selectFirst("ul[class=pagination]").select("li");

        int numberOfPage = 1;

        for (int i = 1; i <= numberOfPage; i++) {
            System.out.println("page: "  +i);
            URLConnection childConnect = new URL("https://truyenaudio.org/lich-su/?page=".concat(String.valueOf(i))).openConnection();
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
                audioBook.setIdCategory("5dbbe15ba5382b7227a9e047");

                if (attribute != null) {

                    if (attribute.select("p").size() > 1) {
                        accent = attribute.select("p").get(0).selectFirst("a").text();
                        author = attribute.select("p").get(1).select("a").text();
                    } else {
                        if (attribute.toString().contains("Giọng đọc:")) {
                            if (attribute.selectFirst("p") != null){
                                accent = attribute.selectFirst("p").selectFirst("a").text();
                            }
                        } else {
                            if (attribute.selectFirst("p") != null){
                                author = attribute.selectFirst("p").selectFirst("a").text();
                            }
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

//                        setAccent.add(accent);

                    }
                    if (author != null) {
                        for (Author a : authors) {
                            if (a.getName().equals(author)) {
                                author = a.getId();
                                break;
                            }
                        }
                        audioBook.setIdAuthor(author);

//                        setAuthor.add(author);
                    }
                }

                AudioBookRepository.createBook(audioBook);


            }

        }
//        for (String au : setAuthor){
//            AuthorRepository.createAuthor(new Author(au));
//        }
//
//        for (String ac : setAccent){
//            AccentRepository.createAccent(new Accent(ac));
//        }
    }

    private static void crawlTruyenKinhDien() throws IOException {

//        Set<String> setAuthor = new HashSet<String>();
//        Set<String> setAccent = new HashSet<String>();

        List<Accent> accents = AccentRepository.getAccents();
        List<Author> authors = AuthorRepository.getAuthors();

        URLConnection openConnection = new URL("https://truyenaudio.org/truyen-kinh-dien/?page=1").openConnection();
        openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        Document doc = Jsoup.parse(openConnection.getURL(), 5000);

        Elements panigation = doc.selectFirst("ul[class=pagination]").select("li");

        int numberOfPage = Integer.parseInt(panigation.get(panigation.size() - 1).selectFirst("a").attr("href").split("=")[1]);

        for (int i = 1; i <= numberOfPage; i++) {
            System.out.println("page: "  +i);
            URLConnection childConnect = new URL("https://truyenaudio.org/truyen-kinh-dien/?page=".concat(String.valueOf(i))).openConnection();
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
                audioBook.setIdCategory("5dbbe15ba5382b7227a9e048");

                if (attribute != null) {

                    if (attribute.select("p").size() > 1) {
                        accent = attribute.select("p").get(0).selectFirst("a").text();
                        author = attribute.select("p").get(1).select("a").text();
                    } else {
                        if (attribute.toString().contains("Giọng đọc:")) {
                            if (attribute.selectFirst("p") != null){
                                accent = attribute.selectFirst("p").selectFirst("a").text();
                            }
                        } else {
                            if (attribute.selectFirst("p") != null){
                                author = attribute.selectFirst("p").selectFirst("a").text();
                            }
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

//                        setAccent.add(accent);

                    }
                    if (author != null) {
                        for (Author a : authors) {
                            if (a.getName().equals(author)) {
                                author = a.getId();
                                break;
                            }
                        }
                        audioBook.setIdAuthor(author);

//                        setAuthor.add(author);
                    }
                }

                AudioBookRepository.createBook(audioBook);


            }

        }
//        for (String au : setAuthor){
//            AuthorRepository.createAuthor(new Author(au));
//        }
//
//        for (String ac : setAccent){
//            AccentRepository.createAccent(new Accent(ac));
//        }
    }

    private static void crawlTruyenThanhCong() throws IOException {

//        Set<String> setAuthor = new HashSet<String>();
//        Set<String> setAccent = new HashSet<String>();

        List<Accent> accents = AccentRepository.getAccents();
        List<Author> authors = AuthorRepository.getAuthors();

        URLConnection openConnection = new URL("https://truyenaudio.org/thanh-cong/?page=1").openConnection();
        openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        Document doc = Jsoup.parse(openConnection.getURL(), 5000);

        Elements panigation = doc.selectFirst("ul[class=pagination]").select("li");

//        int numberOfPage = Integer.parseInt(panigation.get(panigation.size() - 1).selectFirst("a").attr("href").split("=")[1]);

        int numberOfPage = 1;

        for (int i = 1; i <= numberOfPage; i++) {
            System.out.println("page: "  +i);
            URLConnection childConnect = new URL("https://truyenaudio.org/thanh-cong/?page=".concat(String.valueOf(i))).openConnection();
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
                audioBook.setIdCategory("5dbbe15ba5382b7227a9e049");

                if (attribute != null) {

                    if (attribute.select("p").size() > 1) {
                        accent = attribute.select("p").get(0).selectFirst("a").text();
                        author = attribute.select("p").get(1).select("a").text();
                    } else {
                        if (attribute.toString().contains("Giọng đọc:")) {
                            if (attribute.selectFirst("p") != null){
                                accent = attribute.selectFirst("p").selectFirst("a").text();
                            }
                        } else {
                            if (attribute.selectFirst("p") != null){
                                author = attribute.selectFirst("p").selectFirst("a").text();
                            }
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

//                        setAccent.add(accent);

                    }
                    if (author != null) {
                        for (Author a : authors) {
                            if (a.getName().equals(author)) {
                                author = a.getId();
                                break;
                            }
                        }
                        audioBook.setIdAuthor(author);

//                        setAuthor.add(author);
                    }
                }

                AudioBookRepository.createBook(audioBook);


            }

        }
//        for (String au : setAuthor){
//            AuthorRepository.createAuthor(new Author(au));
//        }
//
//        for (String ac : setAccent){
//            AccentRepository.createAccent(new Accent(ac));
//        }
    }

    private static void crawlTruyenNguyenNgocNgan() throws IOException {

//        Set<String> setAuthor = new HashSet<String>();
//        Set<String> setAccent = new HashSet<String>();

        List<Accent> accents = AccentRepository.getAccents();
        List<Author> authors = AuthorRepository.getAuthors();

        URLConnection openConnection = new URL("https://truyenaudio.org/tac-gia/nha-van-nguyen-ngoc-ngan.html?page=1").openConnection();
        openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        Document doc = Jsoup.parse(openConnection.getURL(), 5000);

        Elements panigation = doc.selectFirst("ul[class=pagination]").select("li");

        int numberOfPage = Integer.parseInt(panigation.get(panigation.size() - 1).selectFirst("a").attr("href").split("=")[1]);

        for (int i = 1; i <= numberOfPage; i++) {
            System.out.println("page: "  +i);
            URLConnection childConnect = new URL("https://truyenaudio.org/tac-gia/nha-van-nguyen-ngoc-ngan.html?page=".concat(String.valueOf(i))).openConnection();
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
                audioBook.setIdCategory("5dbbe15ba5382b7227a9e04a");

                if (attribute != null) {

                    if (attribute.select("p").size() > 1) {
                        accent = attribute.select("p").get(0).selectFirst("a").text();
                        author = attribute.select("p").get(1).select("a").text();
                    } else {
                        if (attribute.toString().contains("Giọng đọc:")) {
                            if (attribute.selectFirst("p") != null){
                                accent = attribute.selectFirst("p").selectFirst("a").text();
                            }
                        } else {
                            if (attribute.selectFirst("p") != null){
                                author = attribute.selectFirst("p").selectFirst("a").text();
                            }
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

//                        setAccent.add(accent);

                    }
                    if (author != null) {
                        for (Author a : authors) {
                            if (a.getName().equals(author)) {
                                author = a.getId();
                                break;
                            }
                        }
                        audioBook.setIdAuthor(author);

//                        setAuthor.add(author);
                    }
                }

                AudioBookRepository.createBook(audioBook);


            }

        }
//        for (String au : setAuthor){
//            AuthorRepository.createAuthor(new Author(au));
//        }
//
//        for (String ac : setAccent){
//            AccentRepository.createAccent(new Accent(ac));
//        }
    }

    private static void crawlTruyenDemKhuya() throws IOException {

//        Set<String> setAuthor = new HashSet<String>();
//        Set<String> setAccent = new HashSet<String>();

        List<Accent> accents = AccentRepository.getAccents();
        List<Author> authors = AuthorRepository.getAuthors();

        URLConnection openConnection = new URL("https://truyenaudio.org/truyen-dem-khuya/?page=1").openConnection();
        openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        Document doc = Jsoup.parse(openConnection.getURL(), 5000);

        Elements panigation = doc.selectFirst("ul[class=pagination]").select("li");

        int numberOfPage = Integer.parseInt(panigation.get(panigation.size() - 1).selectFirst("a").attr("href").split("=")[1]);

        for (int i = 1; i <= numberOfPage; i++) {
            System.out.println("page: "  +i);
            URLConnection childConnect = new URL("https://truyenaudio.org/truyen-dem-khuya/?page=".concat(String.valueOf(i))).openConnection();
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
                audioBook.setIdCategory("5dbbe15ba5382b7227a9e04b");

                if (attribute != null) {

                    if (attribute.select("p").size() > 1) {
                        accent = attribute.select("p").get(0).selectFirst("a").text();
                        author = attribute.select("p").get(1).select("a").text();
                    } else {
                        if (attribute.toString().contains("Giọng đọc:")) {
                            if (attribute.selectFirst("p") != null){
                                accent = attribute.selectFirst("p").selectFirst("a").text();
                            }
                        } else {
                            if (attribute.selectFirst("p") != null){
                                author = attribute.selectFirst("p").selectFirst("a").text();
                            }
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

//                        setAccent.add(accent);

                    }
                    if (author != null) {
                        for (Author a : authors) {
                            if (a.getName().equals(author)) {
                                author = a.getId();
                                break;
                            }
                        }
                        audioBook.setIdAuthor(author);

//                        setAuthor.add(author);
                    }
                }

                AudioBookRepository.createBook(audioBook);


            }

        }
//        for (String au : setAuthor){
//            AuthorRepository.createAuthor(new Author(au));
//        }
//
//        for (String ac : setAccent){
//            AccentRepository.createAccent(new Accent(ac));
//        }
    }

    private static void crawlTruyenVanHoc() throws IOException {

//        Set<String> setAuthor = new HashSet<String>();
//        Set<String> setAccent = new HashSet<String>();

        List<Accent> accents = AccentRepository.getAccents();
        List<Author> authors = AuthorRepository.getAuthors();

        URLConnection openConnection = new URL("https://truyenaudio.org/van-hoc/?page=1").openConnection();
        openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        Document doc = Jsoup.parse(openConnection.getURL(), 5000);

        Elements panigation = doc.selectFirst("ul[class=pagination]").select("li");

        int numberOfPage = 1;

        for (int i = 1; i <= numberOfPage; i++) {
            System.out.println("page: "  +i);
            URLConnection childConnect = new URL("https://truyenaudio.org/van-hoc/?page=".concat(String.valueOf(i))).openConnection();
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
                audioBook.setIdCategory("5dbbe15ba5382b7227a9e04c");

                if (attribute != null) {

                    if (attribute.select("p").size() > 1) {
                        accent = attribute.select("p").get(0).selectFirst("a").text();
                        author = attribute.select("p").get(1).select("a").text();
                    } else {
                        if (attribute.toString().contains("Giọng đọc:")) {
                            if (attribute.selectFirst("p") != null){
                                accent = attribute.selectFirst("p").selectFirst("a").text();
                            }
                        } else {
                            if (attribute.selectFirst("p") != null){
                                author = attribute.selectFirst("p").selectFirst("a").text();
                            }
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

//                        setAccent.add(accent);

                    }
                    if (author != null) {
                        for (Author a : authors) {
                            if (a.getName().equals(author)) {
                                author = a.getId();
                                break;
                            }
                        }
                        audioBook.setIdAuthor(author);

//                        setAuthor.add(author);
                    }
                }

                AudioBookRepository.createBook(audioBook);


            }

        }
//        for (String au : setAuthor){
//            AuthorRepository.createAuthor(new Author(au));
//        }
//
//        for (String ac : setAccent){
//            AccentRepository.createAccent(new Accent(ac));
//        }
    }

    private static void crawlTruyenRadio() throws IOException {

//        Set<String> setAuthor = new HashSet<String>();
//        Set<String> setAccent = new HashSet<String>();

        List<Accent> accents = AccentRepository.getAccents();
        List<Author> authors = AuthorRepository.getAuthors();

        URLConnection openConnection = new URL("https://truyenaudio.org/radio/?page=1").openConnection();
        openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        Document doc = Jsoup.parse(openConnection.getURL(), 5000);

        Elements panigation = doc.selectFirst("ul[class=pagination]").select("li");

        int numberOfPage = 1;

        for (int i = 1; i <= numberOfPage; i++) {
            System.out.println("page: "  +i);
            URLConnection childConnect = new URL("https://truyenaudio.org/radio/?page=".concat(String.valueOf(i))).openConnection();
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
                audioBook.setIdCategory("5dbbe15ba5382b7227a9e04d");

                if (attribute != null) {

                    if (attribute.select("p").size() > 1) {
                        accent = attribute.select("p").get(0).selectFirst("a").text();
                        author = attribute.select("p").get(1).select("a").text();
                    } else {
                        if (attribute.toString().contains("Giọng đọc:")) {
                            if (attribute.selectFirst("p") != null){
                                accent = attribute.selectFirst("p").selectFirst("a").text();
                            }
                        } else {
                            if (attribute.selectFirst("p") != null){
                                author = attribute.selectFirst("p").selectFirst("a").text();
                            }
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

//                        setAccent.add(accent);

                    }
                    if (author != null) {
                        for (Author a : authors) {
                            if (a.getName().equals(author)) {
                                author = a.getId();
                                break;
                            }
                        }
                        audioBook.setIdAuthor(author);

//                        setAuthor.add(author);
                    }
                }

                AudioBookRepository.createBook(audioBook);


            }

        }
//        for (String au : setAuthor){
//            AuthorRepository.createAuthor(new Author(au));
//        }
//
//        for (String ac : setAccent){
//            AccentRepository.createAccent(new Accent(ac));
//        }
    }
}
