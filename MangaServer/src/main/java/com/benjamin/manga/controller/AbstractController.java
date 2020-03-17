package com.benjamin.manga.controller;

import com.benjamin.manga.cache.Session;
import com.benjamin.manga.cache.SessionManager;
import com.benjamin.manga.constant.ResponseCode;
import com.benjamin.manga.exception.ApplicationException;
import com.benjamin.manga.request.AbstractRequest;
import com.benjamin.manga.response.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class AbstractController<T extends AbstractRequest> {

    protected String handler(T request) {
        try {
            validateRequest(request);
            return execute(request);
        } catch (ApplicationException ex) {
            return new ResponseEntity(ex.getCode()).toString();
        } catch (Exception ex) {
            return new ResponseEntity(ResponseCode.UNKNOW_ERROR).toString();
        }
    }

    protected abstract void validateRequest(T request);

    protected abstract String execute(T request);

    protected String getUserIdByToken(String token){
        Session session = SessionManager.getSession(token);
        if (session == null){
            throw new ApplicationException(ResponseCode.TOKEN_EXPIRED);
        }
        return session.getUserId();
    }

    protected String generateToken(String userId){
        return SessionManager.putSession(new Session(userId));
    }

}
