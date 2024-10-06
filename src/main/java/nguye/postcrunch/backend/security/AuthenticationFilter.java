package nguye.postcrunch.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nguye.postcrunch.backend.exception.InvalidAccessTokenException;
import nguye.postcrunch.backend.model.AuthPrincipal;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public AuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, InvalidAccessTokenException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // If the Authorization header is null, it means that the
        if (authHeader != null) {
            // Extract the username from the JWT carried in the Authorization header of the incoming request.
            // If the token is valid, store the info about the authenticated user to the security context.
            AuthPrincipal principal = jwtService.verifyAuthPrincipal(request);

            if (Objects.isNull(principal)) {
                throw new InvalidAccessTokenException("Invalid access token.");
            }

            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

            Authentication auth = new UsernamePasswordAuthenticationToken(principal, null, List.of(authority));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
