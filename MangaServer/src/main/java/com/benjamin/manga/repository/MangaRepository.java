package com.benjamin.manga.repository;

import com.benjamin.manga.model.Manga;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MangaRepository extends MongoRepository<Manga, String> {
}
