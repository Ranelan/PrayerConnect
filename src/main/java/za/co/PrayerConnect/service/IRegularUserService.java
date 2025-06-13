package za.co.PrayerConnect.service;

import za.co.PrayerConnect.domain.RegularUser;

public interface IRegularUserService extends IService<RegularUser, String> {

    /**
     * Method to authenticate a user based on email and password.
     *
     * @param email    the user's email
     * @param password the user's password
     * @return RegularUser if authentication is successful, null otherwise
     */
    RegularUser authenticate(String email, String password);

    /**
     * Method to find a user by their email.
     *
     * @param email the user's email
     * @return RegularUser if found, null otherwise
     */
    RegularUser findByEmail(String email);
    RegularUser findByFullName(String fullName);
    RegularUser findByAge(int age);
}
