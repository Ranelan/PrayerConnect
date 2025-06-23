package za.co.PrayerConnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.PrayerConnect.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // You can add custom methods here if needed, for example:
    User findByEmail(String email);
}
