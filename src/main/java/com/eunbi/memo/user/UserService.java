package com.eunbi.memo.user;


import com.eunbi.memo.common.MD5MashingEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // final: 정적 변수로, 변수 값을 수정할 수 없음. 실수를 줄이기 위해 사용되곤 합니다
    private final UserRepository userRepository;

//    @Autowired
    // 클래스 내에 생성자가 객체 주입을 위한 생성자가 유일한 경우 오토 와이어드(의존성 주입)는 생략 가능
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public boolean createUser(
            String loginId,
            String password,
            String name,
            String email
    ) {

        String encodedPassword = MD5MashingEncoder.encode(password);
//        String encodedPassword = SHA25HashingEncoder.encode(password);
        int count = userRepository.insertUser(loginId, encodedPassword, name, email);

        if (count == 1) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isDuplicateId(String loginId) {
        int count = userRepository.countByLoginId(loginId);

        if (count == 0) {
            return false;
        } else {
            return true;
        }
    }

    public User getUser(String loginId, String password) {
        String encodedPassword = MD5MashingEncoder.encode(password);
        User user = userRepository.selectUser(loginId, encodedPassword);
        return user;
    }



}
