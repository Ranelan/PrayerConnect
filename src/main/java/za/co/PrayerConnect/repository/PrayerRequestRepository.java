package za.co.PrayerConnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.PrayerConnect.domain.PrayerRequest;

import java.util.List;
import java.util.Optional;

public interface PrayerRequestRepository extends JpaRepository<PrayerRequest, Long> {

    Optional<PrayerRequest> findByContentId(Long contentId);
    List<PrayerRequest> findByTitle(String title);
    List<PrayerRequest> findAll();
}
