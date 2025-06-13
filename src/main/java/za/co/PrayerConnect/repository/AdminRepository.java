package za.co.PrayerConnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.PrayerConnect.domain.User;

import java.util.List;

public interface AdminRepository extends JpaRepository<User, Long> {

    List<User> findByEmail(String email);
    List<User> findByAge(int age);
}
