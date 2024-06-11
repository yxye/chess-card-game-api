
package com.chess.ws.api.security.jwt;


import com.chess.ws.api.security.model.RawAccessJwtToken;
import com.chess.ws.api.security.model.SecurityUser;

public class JwtAuthenticationToken extends AbstractJwtAuthenticationToken {

    private static final long serialVersionUID = -8487219769037942225L;

    public JwtAuthenticationToken(RawAccessJwtToken unsafeToken) {
        super(unsafeToken);
    }

    public JwtAuthenticationToken(SecurityUser securityUser) {
        super(securityUser);
    }
}
