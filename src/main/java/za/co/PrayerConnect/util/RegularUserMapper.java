package za.co.PrayerConnect.util;

import za.co.PrayerConnect.domain.RegularUser;
import za.co.PrayerConnect.dto.RegularUserDto;

public class RegularUserMapper {

    public static RegularUserDto toDTO(RegularUser user) {
        RegularUserDto dto = new RegularUserDto();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setAge(user.getAge());
        dto.setBlocked(user.isBlocked());
        return dto;
    }

    public static RegularUser toEntity(RegularUserDto dto, String password) {
        return new RegularUser.RegularUserBuilder()
                .setId(dto.getId())
                .setFullName(dto.getFullName())
                .setEmail(dto.getEmail())
                .setPassword(password) // Handle securely in real apps!
                .setAge(dto.getAge())
                .setIsBlocked(dto.isBlocked())
                .build();
    }
}
