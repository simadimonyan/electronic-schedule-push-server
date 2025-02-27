package pushserver.infrastructure.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pushserver.infrastructure.configuration.SecurityConfiguration;

import javax.crypto.SecretKey;
import java.util.Random;

@Component
public class JwtUtil {

    private final SecretKey secret;

    @Autowired
    public JwtUtil(SecurityConfiguration properties) {
        this.secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(properties.getSecret()));
    }

    public String createAccessToken() {
        return Jwts.builder()
                .setSubject("access " + new Random().longs())
                .signWith(secret)
                .compact();
    }

    public Boolean validateToken(String toValidate) {
        try {
            Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(toValidate);
            return true;
        }
        catch (Exception ignored) {}
        return false;
    }

}
