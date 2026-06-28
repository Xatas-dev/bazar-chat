package org.bazar.chat.adapter.outbound.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.bazar.authorization.sdk.AuthorizationRequest;
import org.bazar.authorization.sdk.BazarAuthorizationClient;
import org.bazar.chat.adapter.inbound.rest.Authorize;
import org.bazar.chat.app.api.exception.BusinessException;
import org.bazar.chat.app.service.AuthorizationService;
import org.springframework.stereotype.Component;

import static org.bazar.chat.app.api.exception.ErrorCode.AUTH_ERROR;
import static org.bazar.chat.app.api.exception.ErrorCode.FORBIDDEN;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorizationAspect {
    private final BazarAuthorizationClient bazarAuthorizationClient;
    private final AuthorizationService authorizationService;

    @Before("@annotation(authorize)")
    public void checkAuthorization(JoinPoint joinPoint, Authorize authorize) {
        Long spaceId = getSpaceId(joinPoint, authorize.spaceIdParam());
        AuthorizationRequest request = AuthorizationRequest.builder()
                .spaceId(spaceId)
                .permission(authorize.permission())
                .bearerToken(authorizationService.getCurrentJwtToken())
                .build();

        boolean isAllowed;
        try {
            isAllowed = bazarAuthorizationClient.authorize(request);
        } catch (Exception e) {
            log.error("Error while calling auth service for spaceId: {}", spaceId, e);
            throw new BusinessException(AUTH_ERROR);
        }

        if (!isAllowed) {
            log.error("Auth denied: permission {}, user {}, space {}", authorize.permission(),
                    authorizationService.getAuthenticatedUserId(),
                    spaceId);
            throw new BusinessException(FORBIDDEN, authorize.permission(), authorizationService.getAuthenticatedUserId());
        }
    }

    // =================================================================================================================
    // = Implementation
    // =================================================================================================================

    private Long getSpaceId(JoinPoint joinPoint, String spaceIdParam) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] paramNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        Long spaceId;
        for (int i = 0; i < paramNames.length; i++) {
            if (paramNames[i].equals(spaceIdParam)) {
                spaceId = (Long) args[i];
                return spaceId;
            }
        }

        throw new IllegalArgumentException(String.format("%s param is not found!", spaceIdParam));
    }
}
