package com.benjamin.manga.repository;

import com.benjamin.manga.model.MangaImage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MangaImageRepository extends MongoRepository<MangaImage, String> {
}
