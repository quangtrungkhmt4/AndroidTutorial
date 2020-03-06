package com.benjamin.manga.controller;

import com.benjamin.manga.model.Manga;
import com.benjamin.manga.response.ResponseEntity;
import com.benjamin.manga.service.base.MangaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
public class MangaController extends  AbstractController {

    @Autowired
    private MangaService mangaService;

    @GetMapping("/mangas")
    public String getListManga(@RequestParam("page") int page, @RequestParam("take") int take){
        System.out.println("page: " + page + " - take: " + take);
        List<Manga> mangas = (List<Manga>) mangaService.getAllEntities(page, take);
        return new ResponseEntity(mangas).toString();
    }

    public static void main(String[] args) {
        List<String> strings = Arrays.asList("Trung", "Quang", "Le");
        System.out.println(strings.subList(0, 1));
        System.out.println(strings.subList(1, strings.size()));
    }
}
