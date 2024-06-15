package onboarding.crud.user.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import onboarding.crud.user.dto.UpdateUserDto;
import onboarding.crud.user.dto.UserDto;
import onboarding.crud.user.dto.UserSignupDto;
import onboarding.crud.user.entity.User;
import onboarding.crud.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/users")

public class UserController{

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable long userId) {
        Optional<UserDto> userOptional = userService.getUserById(userId);
        if (userOptional.isPresent())
            return userOptional.get();
        else throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"
        );
    }

    @PostMapping("/signup")
    public User signup(@RequestBody UserSignupDto userSignupDto) {
        return userService.registerUser(userSignupDto.getName(), userSignupDto.getNickname(), userSignupDto.getPassword());
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Map<String, String> loginData, HttpServletRequest request) {

        String name;
        String password;
        try{
            name = loginData.get("name");
            password = loginData.get("password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("요청 정보가 형식에 맞지 않습니다.");
        }

        Optional<User> _user= userService.loginUser(name, password);
        if(_user.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인에 실패했습니다. 유효한 사용자 이름과 비밀번호를 입력하세요.");
        }
        User user = _user.get();
        //기존세션 만료후 새 세션 발급
        request.getSession().invalidate();
        HttpSession session = request.getSession();
        session.setAttribute("userId",user.getId());
        session.setMaxInactiveInterval(30*60);
        return ResponseEntity.ok("로그인이 성공적으로 완료되었습니다.");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그아웃할 세션을 찾을 수 없습니다.");
        session.invalidate();
        return ResponseEntity.ok("로그아웃이 성공적으로 완료되었습니다.");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(HttpSession session) {
        Object _id = session.getAttribute("userId");
        if(_id == null) return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않은 요청입니다.");
        userService.deleteUser((Long) _id);
        session.invalidate();
        return ResponseEntity.ok("회원 탈퇴가 성공적으로 완료되었습니다.");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UpdateUserDto userDto) {
        User updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok("회원 정보가 성공적으로 수정되었습니다.");
    }
}