package com.chess.ws.api.security.jwt;

import com.chess.ws.api.security.model.RawAccessJwtToken;
import com.chess.ws.api.security.model.SecurityUser;

public class RefreshAuthenticationToken extends AbstractJwtAuthenticationToken {

    private static final long serialVersionUID = -1311042791508924523L;

    public RefreshAuthenticationToken(RawAccessJwtToken unsafeToken) {
        super(unsafeToken);
    }

    public RefreshAuthenticationToken(SecurityUser securityUser) {
        super(securityUser);
    }
}
