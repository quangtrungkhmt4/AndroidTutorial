package com.benjamin.manga.controller.user;

import com.benjamin.manga.constant.ResponseCode;
import com.benjamin.manga.controller.AbstractController;
import com.benjamin.manga.exception.ApplicationException;
import com.benjamin.manga.model.User;
import com.benjamin.manga.request.RegisterRequest;
import com.benjamin.manga.response.RegisterResponse;
import com.benjamin.manga.response.ResponseEntity;
import com.benjamin.manga.service.base.UserService;
import com.benjamin.manga.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterAPI extends AbstractController<RegisterRequest> {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterRequest request){
        return handler(request);
    }


    @Override
    protected void validateRequest(RegisterRequest request) {
        if (!StringUtil.isEmailValid(request.getEmail())){
            throw new ApplicationException(ResponseCode.EMAIL_INVALID);
        }
    }

    @Override
    protected String execute(RegisterRequest request) {
        User requestData = User.builder()
                .userName(request.getName())
                .email(request.getEmail())
                .hashPass(request.getPassword())
                .isVerify(true).build();
        User result = userService.insertEntity(requestData);
        RegisterResponse response = RegisterResponse.builder()
                .userId(result.getId())
                .userName(result.getUserName())
                .token(generateToken(result.getId())).build();
        return new ResponseEntity(response).toString();
    }
}
