package com.chess.card.api.security.model;

import com.chess.card.api.game.entity.UserInfo;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class SecurityUser extends UserInfo implements Serializable {
    /**
     *
     */
    private Collection<GrantedAuthority> authorities;

    /**
     *
     */
    private boolean enabled=false;

    /**
     *
     */
    private UserPrincipal userPrincipal;

    private String sessionId;

    /**
     * 房间号
     */
    private String roomId;


    /**
     * 坐位号为空时是观看用户
     */
    private String seatId;


    public SecurityUser() {
        super();
    }
    public SecurityUser(UserInfo user, UserPrincipal userPrincipal) {
        super(user);
        this.userPrincipal = userPrincipal;
    }

    public SecurityUser(String id) {
        super(id);
    }

    public Collection<GrantedAuthority> getAuthorities() {
        if (authorities == null) {
            authorities = Stream.of(SecurityUser.this.getAuthority())
                    .map(authority -> new SimpleGrantedAuthority(authority.name()))
                    .collect(Collectors.toList());
        }
        return authorities;
    }
}
