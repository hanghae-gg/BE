package com.hhgg.hhggbe.user.service;

import com.hhgg.hhggbe.jwt.JwtUtil;
import com.hhgg.hhggbe.user.dto.LoginErrorMessage;
import com.hhgg.hhggbe.user.dto.LoginRequestDto;
import com.hhgg.hhggbe.user.dto.SignupRequestDto;
import com.hhgg.hhggbe.user.entity.User;
import com.hhgg.hhggbe.user.entity.UserRoleEnum;
import com.hhgg.hhggbe.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;


    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        String email = signupRequestDto.getEmail();

        // 아이디 중복 확인
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("username이 중복됩니다.");
        }

        UserRoleEnum userRoleEnum = UserRoleEnum.USER;

        User user = new User(username, email, password, userRoleEnum);
        userRepository.save(user);
    }
    public boolean idCheck(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        boolean result;
        if (userRepository.findByUsername(username).isPresent()) {
            result = false;
        } else {
            result =  true;
        }
        return result;
    }

    public LoginErrorMessage login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();
        System.out.println("****************username : "+username);

        if (username == null || password == null || username.equals("") || password.equals("")) {
            return new LoginErrorMessage(400, "아이디 또는 비밀번호가 입력되지 않았습니다.", null);
        }

        // 사용자 확인
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return new LoginErrorMessage(401, "존재하지 않는 id입니다", null);
        }
        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            return new LoginErrorMessage(402, "password가 잘못되었습니다", null);
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER,
                jwtUtil.createToken(user.get().getUsername(), user.get().getRole()));
        return new LoginErrorMessage(200, "로그인 성공", jwtUtil.createToken(user.get().getUsername(), user.get().getRole()));
    }
}