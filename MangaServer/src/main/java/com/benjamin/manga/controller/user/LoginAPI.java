package com.benjamin.manga.controller.user;

import com.benjamin.manga.constant.ResponseCode;
import com.benjamin.manga.controller.AbstractController;
import com.benjamin.manga.exception.ApplicationException;
import com.benjamin.manga.model.User;
import com.benjamin.manga.request.LoginRequest;
import com.benjamin.manga.response.RegisterResponse;
import com.benjamin.manga.response.ResponseEntity;
import com.benjamin.manga.service.base.UserService;
import com.benjamin.manga.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginAPI extends AbstractController<LoginRequest> {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request){
        return handler(request);
    }

    @Override
    protected void validateRequest(LoginRequest request) {
        if (!StringUtil.validateString(request.getEmail(), request.getPassword())){
            throw new ApplicationException(ResponseCode.WRONG_DATA_FORMAT);
        }
    }

    @Override
    protected String execute(LoginRequest request) {
        User user = userService.findUserByEmailAndPass(request.getEmail(), request.getPassword());
        RegisterResponse response = RegisterResponse.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .token(generateToken(user.getId())).build();
        return new ResponseEntity(response).toString();
    }
}
