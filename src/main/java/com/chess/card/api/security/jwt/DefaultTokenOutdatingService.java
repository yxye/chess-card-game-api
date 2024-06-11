package com.chess.card.api.security.jwt;

import com.chess.card.api.security.model.JwtToken;
import com.chess.card.api.security.model.token.JwtTokenFactory;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

@Service
public class DefaultTokenOutdatingService implements TokenOutdatingService{

    private final JwtTokenFactory tokenFactory;

    public DefaultTokenOutdatingService(JwtTokenFactory tokenFactory) {
        this.tokenFactory = tokenFactory;
    }


    @Override
    public boolean isOutdated(JwtToken token, String userId) {
        Claims claims = tokenFactory.parseTokenClaims(token).getBody();
        long issueTime = claims.getIssuedAt().getTime();
        String sessionId = claims.get("sessionId", String.class);
        if (isTokenOutdated(issueTime, userId.toString())){
            return true;
        } else {
            return sessionId != null && isTokenOutdated(issueTime, sessionId);
        }
    }


    private Boolean isTokenOutdated(long issueTime, String sessionId) {
        return false;
    }


}
