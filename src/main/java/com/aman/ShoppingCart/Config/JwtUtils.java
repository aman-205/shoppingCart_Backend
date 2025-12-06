package com.aman.ShoppingCart.Config;

import com.aman.ShoppingCart.Security.UserDetail;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {

    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;
    @Value(("${auth.token.expirationInMils}"))
    private  int expirationTime;

    public String generateToken(Authentication authentication){

        UserDetail userDetail=(UserDetail) authentication.getPrincipal();
        List<String> roles= userDetail.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).toList();
        return Jwts.builder()
                .setSubject(userDetail.getEmail())
                .claim("id",userDetail.getId())
                .claim("roles",roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime()+expirationTime))
                .signWith(key(), SignatureAlgorithm.HS256).compact();

    }
    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecret));
    }

    public String getUserNameFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validate(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException |MalformedJwtException | SignatureException |IllegalArgumentException e) {
            throw new JwtException(e.getMessage());
        }

    }

}
