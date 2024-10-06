package nguye.postcrunch.backend.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtService {

  static final long EXPIRATION_TIME = 30 * 60 * 1000;

  private final Algorithm algorithm = Algorithm.HMAC256(System.getenv("JWT_SECRET_KEY"));

  public JwtService() {}

  // Generate a signed JWT token
  public String generateToken(String username)  {

    return JWT.create()
        .withIssuer("Postcrunch social media platform")
        .withSubject(username)
        .withClaim("des", "user_credit")
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .sign(algorithm);
  }

  // Get a token from the request, verify the token, and get the username
  public String verifyAndGetUser(String header) {

    String token = header.substring(7);

    try {
      JWTVerifier verifier = JWT.require(algorithm)
          .withIssuer("Postcrunch social media platform")
          .withClaim("des", "user_credit")
          .build();

      DecodedJWT decoded = verifier.verify(token);
      return decoded.getSubject();

    } catch (JWTVerificationException e) {
      System.out.println("Invalid JWT: " + e.getMessage());
    }
    return null;
  }
}
