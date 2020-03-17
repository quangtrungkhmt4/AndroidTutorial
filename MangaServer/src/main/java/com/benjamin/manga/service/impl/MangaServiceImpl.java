package com.benjamin.manga.service.impl;

import com.benjamin.manga.constant.ResponseCode;
import com.benjamin.manga.model.Manga;
import com.benjamin.manga.repository.MangaRepository;
import com.benjamin.manga.service.base.MangaService;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MangaServiceImpl implements MangaService {

    @Autowired
    private MangaRepository mangaRepository;

    @Override
    public Manga insertEntity(Manga entity) {
        return mangaRepository.save(entity);
    }

    @Override
    public Manga updateEntity(Manga entity) {
        return mangaRepository.save(entity);
    }

    @Override
    public void deteleEntity(String id) {
        mangaRepository.deleteById(id);
    }

    @Override
    public Collection<Manga> getAllEntities(int page, int take) {
        return mangaRepository.findAll(PageRequest.of(page, take)).getContent();
    }
}
