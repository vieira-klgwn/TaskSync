package vector.TaskSync.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vector.TaskSync.config.JwtService;
import vector.TaskSync.models.Token;
import vector.TaskSync.models.TokenType;
import vector.TaskSync.models.User;
import vector.TaskSync.repositories.TokenRepository;
import vector.TaskSync.repositories.UserRepository;
import java.util.logging.Logger;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private static final Logger logger = Logger.getLogger(AuthenticationService.class.getName());

    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already taken: " + request.getEmail());
        }
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        var savedUser = userRepository.save(user);
        var token = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        System.out.println("Access Token Length: " + token.length());
        System.out.println("Refresh Token Length: " + refreshToken.length());
        saveUserToken(savedUser, token);
        saveUserToken(savedUser, refreshToken);

        logger.info("accesstoken: "+token);
        logger.info("refreshtoken: "+refreshToken);
        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        var user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new IllegalStateException("User not found: " + authenticationRequest.getEmail()));
        var token = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, refreshToken);
        saveUserToken(user, token);

        return AuthenticationResponse.builder()
            .accessToken(token)
            .refreshToken(refreshToken)
            .build();
}



    private void revokeAllUserTokens(User user) {
        var validTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validTokens.isEmpty()) {
            return;

        }
        validTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);

        });
        tokenRepository.saveAll(validTokens);

    }


    private void saveUserToken(User savedUser, String jwtToken) {
        var token = Token.builder()
                .token(jwtToken)
                .user(savedUser)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);

    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader("Authorization");
        final String refreshToken;
        final String email;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Missing or Invalid Authorization header");
            return;
        }

        refreshToken = authHeader.substring(7);
        email = jwtService.extractUsername(refreshToken);

        if (email == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid Refresh Token");
            return;
        }

        var user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException("User not found: " + email));

        if (!jwtService.isTokenValid(refreshToken, user)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid or Expired Refresh Token");
            return;
        }
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);





    }
}
