package za.co.PrayerConnect.service.UserServ;

import za.co.PrayerConnect.domain.User;

import java.util.Optional;

public interface IUserService {
    Optional<? extends User> findByEmail(String email);
}
