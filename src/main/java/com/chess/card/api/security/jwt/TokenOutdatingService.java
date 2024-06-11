
package com.chess.card.api.security.jwt;


import com.chess.card.api.security.model.JwtToken;

public interface TokenOutdatingService {

    boolean isOutdated(JwtToken token, String userId);

}
