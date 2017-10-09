package com.ua.oauth.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Created on 04.04.17.
 */
@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    @Value("${app.oauth2.client.client-id}")
    private String clientId;

    @Value("${app.oauth2.client.client-secret}")
    private String clientSecret;

    @Value("${app.oauth2.client.redirect-url}")
    private String redirectUrl;

    @RequestMapping(value = "/tag")
    @ResponseBody
    public String tagHandler(String code, CsrfToken token, HttpSession session) throws UnsupportedEncodingException {

        String clientCredentials = clientId + ":" + clientSecret;
        clientCredentials = Base64.getEncoder().encodeToString(clientCredentials.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + clientCredentials);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("X-CSRF-TOKEN", token.getToken());
        headers.add("Cookie", "JSESSIONID=" + session.getId());

        String authorisationCode = URLEncoder.encode("authorization_code", StandardCharsets.UTF_8.name());
        code = URLEncoder.encode(code, StandardCharsets.UTF_8.name());
        String clientIdEncoded = URLEncoder.encode(clientId, StandardCharsets.UTF_8.name());
        String redirect_uri = "/api/authentication/tag";

        HttpEntity requestEntity = new HttpEntity(headers);

        ResponseEntity<String> response = new RestTemplate().postForEntity(
                "http://localhost:7000/oauth/token" +
                        "?grant_type=" + authorisationCode +
                        "&code=" + code +
                        "&client_id=" + clientIdEncoded +
                        "&redirect_uri=" + redirect_uri, requestEntity, String.class);
        return response.getBody();
    }

}
