package com.chess.card.api.security.model;

import java.io.Serializable;

public interface JwtToken extends Serializable {
    String getToken();
}
