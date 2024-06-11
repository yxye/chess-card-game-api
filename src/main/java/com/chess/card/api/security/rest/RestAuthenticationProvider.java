package com.chess.ws.api.security.rest;


import com.chess.ws.api.game.entity.UserInfo;
import com.chess.ws.api.game.service.IUserInfoService;
import com.chess.ws.api.security.model.UserPrincipal;
import com.chess.ws.api.security.model.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@Slf4j
public class RestAuthenticationProvider implements AuthenticationProvider {



    private final IUserInfoService userInfoService;

    @Autowired
    public RestAuthenticationProvider(final IUserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserPrincipal)) {
            throw new BadCredentialsException("Authentication Failed. Bad user principal.");
        }
        UserPrincipal userPrincipal = (UserPrincipal) principal;


        String username = userPrincipal.getValue();
        String password = (String) authentication.getCredentials();
        SecurityUser securityUser = authenticateByUsernameAndPassword(username, password,userPrincipal);


        return new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
    }

    private SecurityUser authenticateByUsernameAndPassword(String account, String password,UserPrincipal userPrincipal) {
        UserInfo userInfo = this.userInfoService.userLogin(account);
        if (userInfo == null) {
            throw new UsernameNotFoundException("User not found: " + account);
        }

        this.userInfoService.validateUserCredentials(password, userInfo);

        return new SecurityUser(userInfo,userPrincipal);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
