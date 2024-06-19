
package com.chess.card.api.security.model;


import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class JwtPair {

    private String token;
    private String refreshToken;

    private Authority scope;

    /**
     * 有房间号说明在房间内
     */
    private String roomId;


    public JwtPair(String token,String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

}
