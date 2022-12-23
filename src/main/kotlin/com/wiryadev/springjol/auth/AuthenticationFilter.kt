package com.wiryadev.springjol.auth

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty
import com.wiryadev.springjol.BaseResponse
import com.wiryadev.springjol.Constants
import com.wiryadev.springjol.CustomException
import com.wiryadev.springjol.user.service.UserService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.endpoint.SecurityContext
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.stream.Collector
import java.util.stream.Collectors
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var userService: UserService

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            if (JwtConfig.isPermit(request)) {
                filterChain.doFilter(request, response)
            } else {
                val claims = validate(request)
                if (claims[Constants.CLAIMS] != null) {
                    setupAuth(claims)
                    filterChain.doFilter(request, response)
                } else {
                    SecurityContextHolder.clearContext()
                    throw CustomException("Token Required")
                }
            }
        } catch (e: Exception) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.contentType = "application/json"
            val errorResponse: BaseResponse<Any> = when (e) {
                is UnsupportedJwtException -> {
                    BaseResponse.failure("Unsupported JWT")
                }
                else -> {
                    BaseResponse.failure(e.message ?: "Invalid Token")
                }
            }
            val responseString = ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(errorResponse)
            response.writer.println(responseString)
        }
    }

    private fun validate(request: HttpServletRequest): Claims {
        val jwtToken = request.getHeader("Authorization")
        return Jwts.parserBuilder()
            .setSigningKey(Constants.SECRET.toByteArray())
            .build()
            .parseClaimsJws(jwtToken)
            .body
    }

    private fun setupAuth(claims: Claims) {
        val authorities = claims[Constants.CLAIMS] as List<String>
        val authStream = authorities.stream().map { username ->
            SimpleGrantedAuthority(username)
        }.collect(Collectors.toList())

        val auth = UsernamePasswordAuthenticationToken(claims.subject, null, authStream)
        SecurityContextHolder.getContext().authentication = auth
    }
}