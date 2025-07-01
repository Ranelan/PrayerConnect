package za.co.PrayerConnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.PrayerConnect.domain.Testimony;

import java.util.List;

public interface TestimonyRepository extends JpaRepository<Testimony, Long> {
    // Additional query methods can be defined here if needed
    // For example, you could add methods to find testimonies by user or by approval status
    List<Testimony> findAll();
}
