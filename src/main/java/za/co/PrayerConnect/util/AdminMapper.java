package za.co.PrayerConnect.util;

import za.co.PrayerConnect.domain.Admin;
import za.co.PrayerConnect.domain.Permissions;
import za.co.PrayerConnect.dto.AdminDto;

import java.util.stream.Collectors;

public class AdminMapper {

    public static AdminDto toDTO(Admin admin) {
        AdminDto dto = new AdminDto();
        dto.setId(admin.getId());
        dto.setFullName(admin.getFullName());
        dto.setEmail(admin.getEmail());
        dto.setAge(admin.getAge());
        dto.setAdminCode(admin.getAdminCode());
        dto.setLastLogin(admin.getLastLogin());
        dto.setPermissions(admin.getPermissions().stream()
                .map(Enum::name)
                .collect(Collectors.toList()));
        return dto;
    }


    public static Admin toEntity(AdminDto dto, String password) {
        return new Admin.AdminBuilder()
                .setId(dto.getId())
                .setFullName(dto.getFullName())
                .setEmail(dto.getEmail())
                .setPassword(password)
                .setAge(dto.getAge())
                .setAdminCode(dto.getAdminCode())
                .setLastLogin(dto.getLastLogin())
                .setPermissions(dto.getPermissions().stream()
                        .map(Permissions::valueOf)
                        .collect(Collectors.toList()))
                .build();
    }
    }



