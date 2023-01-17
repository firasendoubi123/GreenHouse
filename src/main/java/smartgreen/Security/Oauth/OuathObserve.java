package smartgreen.Security.Oauth;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import smartgreen.Entity.User;
import smartgreen.Repository.UserTokenRepository;
@ApplicationScoped
class OauthObserves {


    @Inject
    private UserTokenRepository repository;

    public void observe(@Observes RemoveToken removeToken) {
        final User user = removeToken.getUser();
        final String token = removeToken.getToken();
        UserToken userToken = repository.findById(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User was not found: " + user.getEmail()));
        userToken.remove(token);
        repository.save(userToken);
    }

}
