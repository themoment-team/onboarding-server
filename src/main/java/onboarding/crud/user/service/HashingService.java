package onboarding.crud.user.service;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class HashingService {
    public String hashPasswordWithId(String password, Long userId) {
        try {
            // 패스워드와 ID 결합
            String saltedPassword = password + userId;

            // SHA-256 메시지 다이제스트 인스턴스 얻기
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            // 해시 계산
            byte[] hashBytes = messageDigest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));

            // 바이트 배열을 16진수 문자열로 변환
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 알고리즘을 찾을 수 없습니다.", e);
        }
    }
}
