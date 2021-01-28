package com.utt.jwt;



import java.util.Date;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.utt.security.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class JwtTokenProvider {
	 // Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
    private final String JWT_SECRET = "lodaaaaaa";

    //Thời gian có hiệu lực của chuỗi jwt
    private final long JWT_EXPIRATION = 604800000L;
    
    //Tạo ra jwt từ thông tin user
    public String generateToken(CustomUserDetails userDetails) {
    	Date now = new Date();
    	Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
    	 // Tạo chuỗi json web token từ id của user.
    	  return Jwts.builder()
                  .setSubject(userDetails.getUsername())
                  .setIssuedAt(now)
                  .setExpiration(expiryDate)
                  .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                  .compact();
    }
    // Lấy thông tin user từ jwt
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                            .setSigningKey(JWT_SECRET)
                            .parseClaimsJws(token)
                            .getBody();

        return Long.parseLong(claims.getSubject());
    }
    
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
        	Logger.getLogger("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
        	 Logger.getLogger("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
        	 Logger.getLogger("Unsupported JWT token.");
        } catch (IllegalArgumentException ex) {
            Logger.getLogger("JWT claims string is empty.");
        }
        return false;
    }
     
}
