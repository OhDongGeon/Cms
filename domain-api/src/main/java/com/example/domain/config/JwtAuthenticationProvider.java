package com.example.domain.config;

import com.example.domain.domain.common.UserType;
import com.example.domain.domain.common.UserVo;
import com.example.domain.util.Aes256util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Objects;


public class JwtAuthenticationProvider {
    private String secretKey = "secretKey";
    private long tokenValidTime = 1000L * 60 * 60 * 24;


    public String createToken(String userPk, Long id, UserType userType) {
        // Aes256util 은 userPk 에 대하여 암호화가 필요해서 사용
        Claims claims = Jwts.claims().setSubject(Aes256util.encrypt(userPk)).setId(Aes256util.encrypt(id.toString()));
        claims.put("roles", userType);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }


    public UserVo getUserVo(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

        return new UserVo(Long.valueOf(Objects.requireNonNull(Aes256util.decrypt(claims.getId()))), Aes256util.decrypt(claims.getSubject()));
    }
}
