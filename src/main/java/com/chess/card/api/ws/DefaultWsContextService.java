package com.chess.card.api.ws;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.chess.card.api.exception.UnLoginException;
import com.chess.card.api.security.model.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DefaultWsContextService {
    private final ThreadLocal<SecurityUser> securityCtxLocal = new TransmittableThreadLocal<>();

    public void setSecurityUser(SecurityUser securityUser) {
        securityCtxLocal.set(securityUser);
    }

    public SecurityUser getSecurityUser() {
        SecurityUser securityUser = securityCtxLocal.get();
        if (securityUser == null) {
            log.info("用户未登录 securityUser=null");
            throw new UnLoginException("用户未登录");
        }
        return securityUser;
    }
}
