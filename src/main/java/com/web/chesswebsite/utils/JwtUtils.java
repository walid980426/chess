package com.web.chesswebsite.utils;


import com.web.chesswebsite.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
//    private static final String secret = "walid";

    public String generateJwt(User user) {
        Date issuedAt = new Date();
        Claims claims = Jwts.claims().setIssuer(String.valueOf(user.id)).setIssuedAt(issuedAt);
        return Jwts.builder().setClaims(claims).compact();
    }

}
