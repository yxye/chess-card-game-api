package com.chess.ws.api.security.model;

import java.io.Serializable;

public interface JwtToken extends Serializable {
    String getToken();
}
