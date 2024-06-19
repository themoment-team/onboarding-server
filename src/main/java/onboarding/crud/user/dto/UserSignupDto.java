package onboarding.crud.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupDto {
    private Long Id;
    private String name;
    private String nickname;
    private String password;
}
