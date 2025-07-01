package za.co.PrayerConnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.PrayerConnect.domain.PrayerInteraction;

import java.util.List;

public interface PrayerInteractionRepository extends JpaRepository <PrayerInteraction, Long> {
    // Additional query methods can be defined here if needed
    // For example, you could add methods to find interactions by user or by prayer request
     List<PrayerInteraction> findByUserId(Long userId);
     List<PrayerInteraction> findByPrayerRequest_ContentId(Long contentId);

}
