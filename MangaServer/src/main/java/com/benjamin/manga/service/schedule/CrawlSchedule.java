package com.benjamin.manga.service.schedule;

import com.benjamin.manga.constant.Constant;
import com.benjamin.manga.model.Category;
import com.benjamin.manga.model.Chap;
import com.benjamin.manga.model.Manga;
import com.benjamin.manga.model.MangaImage;
import com.benjamin.manga.repository.CategoryRepository;
import com.benjamin.manga.repository.MangaImageRepository;
import com.benjamin.manga.repository.MangaRepository;
import com.benjamin.manga.response.ResponseEntity;
import com.benjamin.manga.util.JsonUtil;
import org.bson.types.ObjectId;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CrawlSchedule {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MangaRepository mangaRepository;

    @Autowired
    private MangaImageRepository mangaImageRepository;

    @Scheduled(fixedDelay = 3600000)
    private void crawlData() throws IOException {
        System.out.println("-------Started crawl-------");
//        crawlCategories();
//        crawlManga();
//        crawlMangaAdachi();
//        crawlChaps();
//        crawlImage();

//        Page<Manga> mangas = mangaRepository.findAll(PageRequest.of(0, 4));
//        int size = mangas.getSize();
//        String jsonObj = new ResponseEntity(mangas.getContent()).toString();
//        String json = JsonUtil.castFromEntity(mangas.getContent().get(0), Arrays.asList("chaps"));
        System.out.println("-------Crawl done-------");
    }

    private void crawlImage() throws IOException {
        List<Manga> mangas = mangaRepository.findAll();
        for (Manga manga : mangas){
            List<Chap> chaps = manga.getChaps();
            for (Chap chap : chaps){
                List<MangaImage> mangaImages = new ArrayList<>();
                String idChap = chap.getId();
                String link = chap.getLink();
                Document document = getDocument(link);
                Elements pageImages = document.selectFirst("div[class=reading-detail box_doc]").select("div[class=page-chapter]");
                for (Element page : pageImages){
                    int index = Integer.parseInt(page.selectFirst("img").attr("data-index"));
                    String urlImage;
                    if (page.selectFirst("img").attr("src").contains("http")){
                        urlImage = page.selectFirst("img").attr("src");
                    }else {
                        urlImage = page.selectFirst("img").attr("data-cdn");
                    }
                    MangaImage mangaImage = MangaImage.builder()
                            .imageIndex(index)
                            .chapId(idChap)
                            .link(urlImage).build();
                    mangaImages.add(mangaImage);

                }
                mangaImageRepository.saveAll(mangaImages);
            }
        }
    }


    private void crawlMangaAdachi() throws IOException {
        mangaRepository.deleteAll();
        List<Manga> mangas = new ArrayList<>();
        int count = 0;
        Document doc = getDocument(Constant.TRUYEN_CHON_ADACHI_MITSURU);

        Elements items = doc.selectFirst("div[class=Module Module-194]")
                .selectFirst("div[class=ModuleContent]")
                .selectFirst("div[class=items]")
                .selectFirst("div[class=row]")
                .select("div[class=item]");

        for (Element item : items){
            String image = item.selectFirst("div[class=image]").selectFirst("a").selectFirst("img").attr("data-original");
            String link = item.selectFirst("div[class=image]").selectFirst("a").attr("href");
            String name = item.selectFirst("figcaption").selectFirst("h3").selectFirst("a").text();
            String views = item.selectFirst("div[class=view]").selectFirst("span").after("i[class=fa fa-eye]")
                    .text().split(" ")[0].replace(".","");
            Manga manga = Manga.builder()
                    .name(name)
                    .imageUrl(image)
                    .link(link)
                    .views(Long.parseLong(views)).build();
            mangas.add(manga);
        }

        mangaRepository.saveAll(mangas);
    }

    private void crawlChaps() throws IOException {
        List<Manga> mangas = mangaRepository.findAll();
        int count = 0;
        for (Manga manga : mangas){
            Document doc = getDocument(manga.getLink());
            String author = doc.selectFirst("ul[class=list-info]").selectFirst("li[class=author row]").selectFirst("p[class=col-xs-8]").text();
            String status = doc.selectFirst("ul[class=list-info]").selectFirst("li[class=status row]").selectFirst("p[class=col-xs-8]").text();
            String category = doc.selectFirst("ul[class=list-info]").selectFirst("li[class=kind row]").selectFirst("p[class=col-xs-8]").text();
            String views = doc.selectFirst("ul[class=list-info]").selectFirst("li[class=row]").selectFirst("p[class=col-xs-8]").text().replace(".", "");
            String updateTime = doc.selectFirst("time[class=small]").text().replace("[", "").replace("]", "").split("lúc: ")[1];
            String ranking = doc.selectFirst("div[class=row rating]").selectFirst("div[class=col-sm-6]").selectFirst("div[class=star]").attr("data-rating");
            String desc = "";
            if (doc.selectFirst("div[class=detail-content]").selectFirst("p") != null){
                desc = doc.selectFirst("div[class=detail-content]").selectFirst("p").text();
            }
            List<Chap> chaps = new ArrayList<>();
            Elements eleChaps = doc.selectFirst("div[class=list-chapter]").selectFirst("ul").select("li");
            for (int i=1; i<eleChaps.size(); i++){
                String chapLink = eleChaps.get(i).selectFirst("a").attr("href");
                String chapName = eleChaps.get(i).selectFirst("a").text();
                String chapUpdateTime = eleChaps.get(i).selectFirst("div[class=col-xs-4 text-center small]").text();
                String chapViews = eleChaps.get(i).selectFirst("div[class=col-xs-3 text-center small]").text().replace(".", "");
                Chap chap = Chap.builder()
                        .link(chapLink)
                        .name(chapName)
                        .updateTime(chapUpdateTime)
                        .views(Long.parseLong(chapViews)).build();
                chap.setId(new ObjectId().toString());
                chaps.add(chap);
            }

            manga.setAuthor(author);
            manga.setStatus(status);
            manga.setCategory(category);
            manga.setViews(Long.parseLong(views));
            manga.setUpdateTime(updateTime);
            manga.setRanking(Double.parseDouble(ranking));
            manga.setDesc(desc);
            manga.setChaps(chaps);
            mangaRepository.save(manga);
            count++;
            System.out.println("Done " + count);
        }

    }

    private void crawlCategories() throws IOException {
        categoryRepository.deleteAll();
        Document doc = getDocument(Constant.SOURCE_CRAWL);
        Elements eleCategory = doc.select("ul[class=dropdown-menu megamenu]");
        Elements listGroupCategory = eleCategory.get(0).selectFirst("li").selectFirst("div").select("div[class=col-sm-3]");
        for (Element groupCategory : listGroupCategory){
            Elements eleCategories = groupCategory.selectFirst("ul").select("li");
            for (Element category : eleCategories){
                String categoryName = category.selectFirst("a").text();
                if (categoryName.contains("Tất cả")){
                    continue;
                }
                Category categoryModel = Category.builder()
                        .name(category.selectFirst("a").text())
                        .info(category.selectFirst("a").attr("data-title"))
                        .link(category.selectFirst("a").attr("href"))
                        .count(0)
                        .build();
                categoryRepository.save(categoryModel);
            }
        }
    }

    private List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    private void crawlManga() throws IOException {
        mangaRepository.deleteAll();
        List<Category> categories = getCategories();
        List<Manga> mangas = new ArrayList<>();
        int count = 0;

        for (Category category : categories){
            System.out.println("---------------" + category.getName() + "----------------");
            Document doc = getDocument(category.getLink());

            Element pagination = doc.selectFirst("ul[class=pagination]");

            String lastPage = "1";
            if (pagination != null && pagination.select("li") != null){
                if (pagination.select("li").last().selectFirst("a").attr("href").split("page=")[1] != null){
                    lastPage = pagination.select("li").last().selectFirst("a").attr("href").split("page=")[1];
                }
            }

            for (int i=1; i<=Integer.parseInt(lastPage); i++){
                String linkPage = category.getLink().concat("?page=") + i;
                Document docPage = getDocument(linkPage);
                Elements items = docPage.selectFirst("div[class=items]").selectFirst("div[class=row]").select("div[class=item]");

                for (int j=items.size() - 1; j>=0; j--){
                    String mangaName = items.get(j).selectFirst("a[class=jtip]").text();
                    String imageUrl = items.get(j).selectFirst("div[class=image]").selectFirst("a").selectFirst("img").attr("data-original");
                    String linkManga = items.get(j).selectFirst("div[class=image]").selectFirst("a").attr("href");
                    Manga manga = Manga.builder()
                            .name(mangaName)
                            .link(linkManga)
                            .imageUrl(imageUrl).build();
                    mangas.add(manga);
                    count++;
                    System.out.println(count + " - " + mangaName);
                }
            }
        }
        mangaRepository.saveAll(mangas);
    }

    private Document getDocument(String url) throws IOException {
        URLConnection openConnection = new URL(url).openConnection();
        openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        return Jsoup.parse(openConnection.getURL(), 5000);
    }
}
