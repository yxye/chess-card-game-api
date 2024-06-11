package com.chess.card.api.security.rest;

import com.chess.card.api.game.entity.UserInfo;
import com.chess.card.api.game.service.IUserInfoService;
import com.chess.card.api.security.jwt.RefreshAuthenticationToken;
import com.chess.card.api.security.jwt.TokenOutdatingService;
import com.chess.card.api.security.model.RawAccessJwtToken;
import com.chess.card.api.security.model.SecurityUser;
import com.chess.card.api.security.model.UserPrincipal;
import com.chess.card.api.security.model.token.JwtTokenFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenAuthenticationProvider implements AuthenticationProvider {

    private final JwtTokenFactory tokenFactory;

    private final IUserInfoService userInfoService;

    private final TokenOutdatingService tokenOutdatingService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");
        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();
        SecurityUser unsafeUser = tokenFactory.parseRefreshToken(rawAccessToken);
        UserPrincipal principal = unsafeUser.getUserPrincipal();

        SecurityUser  securityUser = authenticateByUserId(unsafeUser.getId());

        securityUser.setSessionId(unsafeUser.getSessionId());
        if (tokenOutdatingService.isOutdated(rawAccessToken, securityUser.getId())) {
            throw new CredentialsExpiredException("Token is outdated");
        }

        return new RefreshAuthenticationToken(securityUser);
    }

    private SecurityUser authenticateByUserId(String userId) {
        UserInfo user = userInfoService.findByUserId( userId);
        if (user == null) {
            throw new UsernameNotFoundException("User not found by refresh token");
        }

        UserPrincipal userPrincipal = new UserPrincipal(UserPrincipal.Type.USER_NAME, user.getAccount());
        SecurityUser securityUser = new SecurityUser(user, userPrincipal);
        return securityUser;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (RefreshAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
