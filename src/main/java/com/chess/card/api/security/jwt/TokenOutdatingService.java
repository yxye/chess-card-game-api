
package com.chess.ws.api.security.jwt;


import com.chess.ws.api.security.model.JwtToken;

public interface TokenOutdatingService {

    boolean isOutdated(JwtToken token, String userId);

}
