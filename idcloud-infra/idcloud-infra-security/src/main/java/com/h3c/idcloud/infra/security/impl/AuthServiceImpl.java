package com.h3c.idcloud.infra.security.impl;

import com.h3c.idcloud.core.pojo.dto.security.UserToken;
import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.infra.security.AuthService;
import com.h3c.idcloud.infrastructure.common.constants.AuthConstants;

import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Created by qct on 2016/2/3.
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public String createJWT(String id, String issuer, String subject, long ttlMillis) {
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with user secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(AuthConstants.SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(signatureAlgorithm, signingKey)
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer);

        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    @Override
    public UserToken createJWT(User user) {
        UserToken userToken = new UserToken();
        userToken.setUserSid(user.getUserSid());
        //if it has been specified, let's add the expiration
        if (AuthConstants.TTL_MILLIS >= 0) {
            userToken.setAccessExpire(System.currentTimeMillis() + AuthConstants.TTL_MILLIS);
        }
        String uuid = UUID.randomUUID().toString();
        userToken.setUuid(uuid);
        String token = createJWT(uuid, user.getAccount(), AuthConstants.SUBJECT, System.currentTimeMillis(), userToken.getAccessExpire());
        userToken.setAccessToken(token);
        return userToken;
    }

    @Override
    public boolean verifyToken(String token) {

        return true;
    }

    private String createJWT(String id, String issuer, String subject, long now, Long accessExpire) {
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //We will sign our JWT with user secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(AuthConstants.SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(signatureAlgorithm, signingKey)
                .setId(id)
                .setIssuedAt(new Date(now))
                .setSubject(subject)
                .setIssuer(issuer);

        //if it has been specified, let's add the expiration
        if (accessExpire >= 0) {
            Date exp = new Date(accessExpire);
            builder.setExpiration(exp);
        }
        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }
}