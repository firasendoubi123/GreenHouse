package smartgreen.Security.Oauth;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import smartgreen.Repository.UserRepository;
import smartgreen.Repository.UserTokenRepository;
import smartgreen.Entity.User;
import smartgreen.Security.UserNotAuthorized;
import smartgreen.Security.SecurityService;
@ApplicationScoped
public class OauthService {
    static final int EXPIRE_IN = 3600;
    static final Duration EXPIRES = Duration.ofSeconds(EXPIRE_IN);
    @Inject
    private SecurityService securityService;
    @Inject

    private UserTokenRepository repository;

    @Inject
    private UserRepository user_repository;
    @Inject
    private Validator validator;
    public Map<String, Object> token(OauthRequest request) {

        final Set<ConstraintViolation<OauthRequest>> violations = validator.validate(request, OauthRequest
                .GenerateToken.class);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        final User user = securityService.findBy(request.getEmail(), request.getPassword());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        user_repository.save(user);
        final UserToken userToken = repository.findById(request.getEmail()).orElse(new UserToken(user.getEmail()));

        final Token token = Token.generate();

        final String jwt = UserJWT.createToken(user, token, EXPIRES);

        AccessToken accessToken = new AccessToken(jwt, token.get(), EXPIRES);
        RefreshToken refreshToken = new RefreshToken(Token.generate(), accessToken);
        userToken.add(refreshToken);
        repository.save(userToken);
        HashMap<String, Object> map = new HashMap<>();
        map.put("accessToken", accessToken.getToken());
        map.put("refreshToken", refreshToken.getToken());
        map.put("phone_number", user.getTelephone());
        map.put("email", user.getEmail());
        map.put("role", user.getRoles());
        map.put("username", user.getUsername());
        return map;
        // return Oauth2Response.of(accessToken, refreshToken, EXPIRE_IN);
    }

    public Map<String, Object> refreshToken(OauthRequest request) {
        final Set<ConstraintViolation<OauthRequest>> violations = validator.validate(request, OauthRequest
                .RefreshToken.class);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        final UserToken userToken = repository.findByRefreshToken(request.getRefreshToken())
                .orElseThrow(() -> new UserNotAuthorized("Invalid Token"));
        final User user = securityService.findBy(userToken.getEmail());
        final Token token = Token.generate();
        final String jwt = UserJWT.createToken(user, token, EXPIRES);
        AccessToken accessToken = new AccessToken(token.get(), jwt, EXPIRES);
        RefreshToken refreshToken = userToken.update(accessToken, request.getRefreshToken(), repository);
        HashMap<String, Object> map = new HashMap<>();
        map.put("accessToken", accessToken.getToken());
        map.put("refreshToken", refreshToken.getToken());
        return map;
    }

}
