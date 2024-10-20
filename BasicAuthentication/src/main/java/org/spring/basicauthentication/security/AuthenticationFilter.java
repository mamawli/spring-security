package org.spring.basicauthentication.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.spring.basicauthentication.domain.User;
import org.spring.basicauthentication.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization.isEmpty() || authorization.startsWith("Basic")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            byte[] decodeAuth = Base64.getDecoder().decode(authorization.getBytes());
            String auth = new String(decodeAuth);
            String[] splitAuth = auth.split(",");
            String username = splitAuth[0];
            String password = splitAuth[1];

            if (!authenticated(username, password))
                response.sendError(HttpServletResponse.SC_FORBIDDEN);

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, password));
        }
    }

    private boolean authenticated(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null)
            return false;

        return passwordEncoder.matches(password, user.getPassword());
    }
}
