package com.benjamin.manga.controller.user;

import com.benjamin.manga.controller.AbstractController;
import com.benjamin.manga.model.User;
import com.benjamin.manga.request.LoginByGuestRequest;
import com.benjamin.manga.response.RegisterResponse;
import com.benjamin.manga.response.ResponseEntity;
import com.benjamin.manga.service.base.UserService;
import com.benjamin.manga.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class LoginByGuestAPI extends AbstractController<LoginByGuestRequest> {

    @Autowired
    private UserService userService;

    @GetMapping("/login/guest")
    public String loginByGuest(@Valid LoginByGuestRequest request){
        return handler(request);
    }

    @Override
    protected void validateRequest(LoginByGuestRequest request) {

    }

    @Override
    protected String execute(LoginByGuestRequest request) {
        User requestData = User.builder()
                .userName("Guest")
                .email(StringUtil.randomString())
                .hashPass(StringUtil.randomString())
                .isVerify(false).build();
        User result = userService.insertEntity(requestData);
        RegisterResponse response = RegisterResponse.builder()
                .userId(result.getId())
                .userName(result.getUserName())
                .token(generateToken(result.getId())).build();
        return new ResponseEntity(response).toString();
    }
}
