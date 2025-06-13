package za.co.PrayerConnect.factory;

import za.co.PrayerConnect.domain.Permissions;
import za.co.PrayerConnect.util.Helper;

import java.time.LocalDateTime;
import java.util.List;

public class Admin {

    public static Admin createAdmin(Long id, String fullName, String email, String password, int age, String adminCode, LocalDateTime lastLogin, List<Permissions> permissions) {

        if (Helper.isNullOrEmpty(fullName)||
            !Helper.isValidEmail(email)||
                !Helper.isValidPassword(password)||
                !Helper.isValidAge(age)||
                Helper.isNullOrEmpty(adminCode)||
                !Helper.isValidDateTime(lastLogin)||
                permissions == null || permissions.isEmpty()) {
            return null;
        }



        return new Admin.AdminBuilder()
                .setId(id)
                .setFullName(fullName)
                .setEmail(email)
                .setPassword(password)
                .setAge(age)
                .setUserType("ADMIN")
                .setAdminCode(adminCode)
                .setLastLogin(lastLogin)
                .setPermissions(permissions)
                .build();

    }
}
