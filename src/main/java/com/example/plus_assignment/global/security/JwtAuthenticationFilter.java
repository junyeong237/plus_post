package com.example.plus_assignment.global.security;


import com.example.plus_assignment.domain.user.dto.request.UserLoginRequestDto;
import com.example.plus_assignment.domain.user.entity.User;
import com.example.plus_assignment.global.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    //private final RedisUtil redisUtil;


    public final Integer REFRESH_TOKEN_TIME = 60 * 24 * 14;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        //setFilterProcessesUrl("/api/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {
        try {
            UserLoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(),
                UserLoginRequestDto.class);

//            if (userService.userIsBlocked(requestDto.getEmail())) {
//                throw new BlockedUserException(UserErrorCode.BLOCK_USER);
//            }

            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                    requestDto.getNickname(),
                    requestDto.getPassword(),
                    null
                )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult) {

        User user = ((UserDetailsImpl) authResult.getPrincipal()).getUser();

        String accessToken = jwtUtil.createAccessToken(user.getNickname(),user.getRole().getAuthority());
       // String refreshToken = jwtUtil.createRefreshToken();

        //response.addHeader(JwtUtil.ACCESS_TOKEN_HEADER, accessToken);
        jwtUtil.addJwtToCookie(accessToken, response);
        //response.addHeader(JwtUtil.REFRESH_TOKEN_HEADER, refreshToken);

        //refreshToken = refreshToken.split(" ")[1].trim();

       //redisUtil.set(refreshToken, user.getId(), REFRESH_TOKEN_TIME);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
        //필터단에서는 글로벌 exception handler 적용x



    }

}