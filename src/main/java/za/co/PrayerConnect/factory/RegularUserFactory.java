package za.co.PrayerConnect.factory;

import za.co.PrayerConnect.domain.RegularUser;
import za.co.PrayerConnect.util.Helper;

public class RegularUserFactory {

    public RegularUser createRegularUser(Long id, String fullName, String email, String password, int age, boolean isBlocked) {

        if(Helper.isNullOrEmpty(fullName)||
            !Helper.isValidEmail(email)||
                !Helper.isValidPassword(password)||
                !Helper.isValidAge(age)||
                !Helper.isBlocked(isBlocked)) {
            return null;
    }

        return new RegularUser.RegularUserBuilder()
                .setId(id)
                .setFullName(fullName)
                .setEmail(email)
                .setPassword(password)
                .setAge(age)
                .setIsBlocked(isBlocked)
                .build();
    }
}
