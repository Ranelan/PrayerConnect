package za.co.PrayerConnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.PrayerConnect.domain.RegularUser;
import za.co.PrayerConnect.domain.User;

import java.util.List;
import java.util.Optional;

public interface RegularUserRepository extends JpaRepository<RegularUser, Long> {

    List<RegularUser> findByFullName (String fillName);
    Optional<RegularUser> findByEmail(String email);
    List<RegularUser> findByAge(int age);

}
