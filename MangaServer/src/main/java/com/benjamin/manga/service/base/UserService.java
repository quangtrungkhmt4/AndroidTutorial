package com.benjamin.manga.service.base;

import com.benjamin.manga.model.User;

public interface UserService extends Service<User> {

    boolean isEmailExists(String email);

    User findUserByEmailAndPass(String email, String pass);

}
