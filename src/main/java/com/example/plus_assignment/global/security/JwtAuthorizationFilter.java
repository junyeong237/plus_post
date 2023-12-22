package com.example.plus_assignment.global.security;


import static com.example.plus_assignment.global.jwt.JwtUtil.ACCESS_TOKEN_HEADER;
import static com.example.plus_assignment.global.jwt.JwtUtil.BEARER_PREFIX;
import static com.example.plus_assignment.global.jwt.JwtUtil.REFRESH_TOKEN_HEADER;

import com.example.plus_assignment.global.jwt.JwtUtil;
import com.example.plus_assignment.global.redis.RedisUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Redis;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
        FilterChain filterChain) throws ServletException, IOException {
        log.error(req.getRequestURI()); //url 확인용 에러
        //String accessToken = jwtUtil.getTokenFromHeader(req, ACCESS_TOKEN_HEADER);
        String accessToken = jwtUtil.getTokenFromRequest(req,ACCESS_TOKEN_HEADER);

//        if (StringUtils.hasText(accessToken)) {
//            accessToken = jwtUtil.substringToken(accessToken);
//            log.info(accessToken);
//
//            if (!jwtUtil.validateToken(accessToken)) {
//                log.error("Token Error");
//                return;
//            }
//
//            Claims info = jwtUtil.getUserInfoFromToken(accessToken);
//            try {
//                setAuthentication(info.getSubject());
//            } catch (Exception e) {
//                log.error(e.getMessage());
//                return;
//            }
//        }
        if (StringUtils.hasText(accessToken)) {
            accessToken = jwtUtil.substringToken(accessToken);
            if (StringUtils.hasText(accessToken) && redisUtil.containBlackList(accessToken)) {
                accessToken = null;
            }
        }

        if (StringUtils.hasText(accessToken) && !jwtUtil.validateToken(accessToken)) {
            log.info("토큰이 유효하지않은경우");
            //String refreshToken = jwtUtil.getTokenFromHeader(req, REFRESH_TOKEN_HEADER);
            String refreshToken = jwtUtil.getTokenFromRequest(req,REFRESH_TOKEN_HEADER);
            String subRefreshToken = jwtUtil.substringToken(refreshToken);
            if (StringUtils.hasText(subRefreshToken) && jwtUtil.validateToken(subRefreshToken)
                && redisUtil.hasKey(subRefreshToken)) {

                Long userId = (Long) redisUtil.get(subRefreshToken);

                UserDetailsImpl userDetails = userDetailsService.loadUserById(userId);
                accessToken = jwtUtil.createAccessToken(
                        userDetails.getUsername(),
                        userDetails.getUser().getRole().getAuthority()
                    );
                //res.addHeader("AccessToken", BEARER_PREFIX + accessToken);
                jwtUtil.addAccessJwtToCookie(accessToken, res);
                accessToken = accessToken.split(" ")[1].trim();
            }
        }

        if (StringUtils.hasText(accessToken)) {
            Claims info = jwtUtil.getUserInfoFromToken(accessToken);
            try {
                setAuthentication(info.getSubject());
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
        }



        filterChain.doFilter(req, res);
    }

    public void setAuthentication(String nickname) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(nickname);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String nickname) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(nickname);
        return new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
    }
}