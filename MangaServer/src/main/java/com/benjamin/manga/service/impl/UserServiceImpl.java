package com.benjamin.manga.service.impl;

import com.benjamin.manga.constant.ResponseCode;
import com.benjamin.manga.exception.ApplicationException;
import com.benjamin.manga.model.User;
import com.benjamin.manga.repository.UserRepository;
import com.benjamin.manga.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isEmailExists(String email) {
        return userRepository.findUsersByEmail(email).isPresent();
    }

    @Override
    public User findUserByEmailAndPass(String email, String pass) {
        Optional<User> user = userRepository.findUserByEmailAndHashPass(email, pass);
        if (!user.isPresent()){
            throw new ApplicationException(ResponseCode.WRONG_EMAIL_OR_PASS);
        }
        return user.get();
    }

    @Override
    public User insertEntity(User entity) {
        if (isEmailExists(entity.getEmail())){
            throw new ApplicationException(ResponseCode.EMAIL_EXISTS);
        }
        return userRepository.save(entity);
    }

    @Override
    public User updateEntity(User entity) {
        return null;
    }

    @Override
    public void deteleEntity(String id) {

    }

    @Override
    public Collection<User> getAllEntities(int skip, int take) {
        return null;
    }
}
