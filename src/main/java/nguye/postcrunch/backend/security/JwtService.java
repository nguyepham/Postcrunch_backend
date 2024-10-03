package nguye.postcrunch.backend.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nguye.postcrunch.backend.util.VaultClient;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class JwtService {

  static final long EXPIRATION_TIME = 30 * 60 * 1000;

  private final Algorithm algorithm = Algorithm.HMAC256(Objects.requireNonNull(JwtService.getVaultSecret("jwt_key")));

  public JwtService() throws Exception {}

  // Get the JWT secret key from HCP Vault Secrets using API
  public static String getVaultSecret(String name) throws Exception {

    String resBodyString = VaultClient.makeApiGetRequest(name);
    ObjectMapper mapper = new ObjectMapper();

    try {
      JsonNode rootNode = mapper.readTree(resBodyString);
      JsonNode valueNode = rootNode.path("secret").path("version").path("value");
      return valueNode.asText();

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

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
