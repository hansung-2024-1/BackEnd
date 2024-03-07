package ahchacha.ahchacha.dto;

import ahchacha.ahchacha.domain.Item;
import ahchacha.ahchacha.domain.User;
import ahchacha.ahchacha.domain.common.enums.Category;
import ahchacha.ahchacha.domain.common.enums.PersonOrOfficial;
import ahchacha.ahchacha.domain.common.enums.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class UserDto {

    @Getter
    @Builder
    public static class UserRequestDto {
        private String nickname;
        private String track1;
        private String track2;
    }

    @Getter
    public static class LoginRequestDto {
        private String id;
        private String passwd;
    }

    @Getter
    @Setter
    @Builder
    public static class UserResponseDto {
        private Long id;
        private String nickname;
        private String track1;
        private String track2;

        public static UserDto.UserResponseDto toDto(Long id, String track1, String track2) {
            return UserResponseDto.builder()
                    .id(id)
                    .track1(track1)
                    .track2(track2)
                    .build();
        }
    }
}
