package za.co.PrayerConnect.util;

import za.co.PrayerConnect.domain.Admin;
import za.co.PrayerConnect.domain.Permissions;
import za.co.PrayerConnect.dto.AdminDto;

import java.util.stream.Collectors;
 import java.util.Arrays;

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
    var permissions = dto.getPermissions();

    if (permissions == null || permissions.isEmpty()) {
        // Add all permissions from the Permissions enum
        permissions = Arrays.stream(Permissions.values())
                            .map(Enum::name)
                            .collect(Collectors.toList());
    }

    var permissionsEnum = permissions.stream()
        .map(String::toUpperCase)
        .map(Permissions::valueOf)
        .collect(Collectors.toList());

    return new Admin.AdminBuilder()
            .setId(dto.getId())
            .setFullName(dto.getFullName())
            .setEmail(dto.getEmail())
            .setPassword(password)
            .setAge(dto.getAge())
            .setAdminCode(dto.getAdminCode())
            .setLastLogin(dto.getLastLogin())
            .setPermissions(permissionsEnum)
            .build();
}

    }



