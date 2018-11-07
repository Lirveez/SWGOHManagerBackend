package services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.*;

public class TokenManager{
    public String createToken(String login, int allycode) {
        Date date = new Date();
        Map<String, String> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 100);
        JWTCreator.Builder jwtbuilder = JWT.create();
        jwtbuilder.withClaim("login", login)
                .withClaim("allycode", allycode)
                .withClaim("created", date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR,15);
        jwtbuilder.withExpiresAt(cal.getTime());
        String token =jwtbuilder.sign(Algorithm.HMAC256("rubrics.integration.token-secret"));
        return token;
    }
    public int getAllycode(String tokenStr){
        DecodedJWT token = JWT.decode(tokenStr);
        Map<String, Claim> claims = token.getClaims();
        return claims.get("allycode").asInt();
    }

}

