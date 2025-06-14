package za.co.PrayerConnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.PrayerConnect.domain.Admin;
import za.co.PrayerConnect.domain.User;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByAge(int age);
}
