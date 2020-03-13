package com.benjamin.manga.repository;

import com.benjamin.manga.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findUsersByEmail(String email);
    Optional<User> findUserByEmailAndHashPass(String email, String password);
}
