package com.hhgg.hhggbe.user.controller;

import com.hhgg.hhggbe.user.dto.AuthMessage;
import com.hhgg.hhggbe.user.dto.LoginErrorMessage;
import com.hhgg.hhggbe.user.dto.LoginRequestDto;
import com.hhgg.hhggbe.user.dto.SignupRequestDto;
import com.hhgg.hhggbe.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")  //@RequestBody
    public ResponseEntity<AuthMessage> signup(@RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        AuthMessage authMessage = new AuthMessage("회원가입 성공");
        return new ResponseEntity<>(authMessage, OK);
    }

    @ResponseBody
    @PostMapping("/login") //@RequestBody
    public LoginErrorMessage login(@RequestBody LoginRequestDto loginRequestDto,
                                   HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }
}
