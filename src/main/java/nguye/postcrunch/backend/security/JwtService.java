package nguye.postcrunch.backend.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import nguye.postcrunch.backend.model.AuthCredential;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class JwtService {

  static final long EXPIRATION_TIME = 30 * 60 * 1000;

  private final Algorithm algorithm = Algorithm.HMAC256(System.getenv("JWT_SECRET_KEY"));

  public JwtService() {}

  // Generate a signed JWT token
  public String generateToken(AuthCredential principal)  {

    return JWT.create()
        .withIssuer("Postcrunch social media platform")
        .withSubject("Postcrunch access token")
        .withClaim("des", "user's authentication principal")
        .withClaim("username", principal.getUsername())
        .withClaim("password", principal.getPassword())
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .sign(algorithm);
  }

  // Get a token from the request, verify the token, and get the username
  public AuthCredential verifyAuthPrincipal(HttpServletRequest request) {

    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (Objects.isNull(authHeader)) {
      return null;
    }

    String token = authHeader.substring(7);
    try {

      JWTVerifier verifier = JWT.require(algorithm)
          .withIssuer("Postcrunch social media platform")
          .withSubject("Postcrunch access token")
          .withClaim("des", "user's authentication principal")
          .build();
      DecodedJWT decoded = verifier.verify(token);

      return new AuthCredential(
          decoded.getClaims().get("username").asString(),
          decoded.getClaims().get("password").asString()
      );

    } catch (JWTVerificationException e) {
      System.out.println("Invalid JWT: " + e.getMessage());
    }
    return null;
  }
}
