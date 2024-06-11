package com.chess.card.api.security.rest;

import com.chess.card.api.security.jwt.TokenOutdatingService;
import com.chess.card.api.exception.JwtExpiredTokenException;
import com.chess.card.api.security.jwt.JwtAuthenticationToken;
import com.chess.card.api.security.model.RawAccessJwtToken;
import com.chess.card.api.security.model.SecurityUser;
import com.chess.card.api.security.model.token.JwtTokenFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtTokenFactory tokenFactory;

    private final TokenOutdatingService tokenOutdatingService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();
        SecurityUser securityUser = tokenFactory.parseAccessJwtToken(rawAccessToken);

        if (tokenOutdatingService.isOutdated(rawAccessToken, securityUser.getId())) {
            throw new JwtExpiredTokenException("Token is outdated");
        }

        return new JwtAuthenticationToken(securityUser);
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
