package com.aaronsite.server.controllers;

import com.aaronsite.response.ErrorResponse;
import com.aaronsite.response.ResponseData;
import com.aaronsite.security.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.aaronsite.server.controllers.MasterController.BASE_URL;
import static com.aaronsite.server.controllers.MasterController.DEV_URL;
import static com.aaronsite.server.controllers.MasterController.PROD_URL_A;

@CrossOrigin(origins = {DEV_URL, PROD_URL_A})
@RestController
@RequestMapping(BASE_URL)
public class AuthController {

  @PostMapping("/gettoken")
  public ResponseData getToken(
      @RequestHeader("Authorization") String authHeader) {

    try {
      return Authentication.basicAuth(authHeader);
    } catch (Throwable e) {
      return new ErrorResponse(e);
    }
  }
}
