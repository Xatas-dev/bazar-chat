package org.bazar.chat.utils.extensions

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import java.util.UUID

fun getUserIdFromSecurityContext(): UUID {
    val token = SecurityContextHolder.getContext().authentication as JwtAuthenticationToken
    return UUID.fromString(token.token.subject)
}