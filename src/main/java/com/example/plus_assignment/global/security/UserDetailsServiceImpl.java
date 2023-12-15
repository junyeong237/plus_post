package com.example.plus_assignment.global.security;


import com.example.plus_assignment.domain.user.entity.User;
import com.example.plus_assignment.domain.user.repository.UserRepositry;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepositry userRepository;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        User user = userRepository.findByNickname(nickname)
            .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));

        return new UserDetailsImpl(user);
    }

    public UserDetailsImpl loadUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new NullPointerException("존재하지 않는 유저 아이디입니다."));

        return new UserDetailsImpl(user);
    }

}
