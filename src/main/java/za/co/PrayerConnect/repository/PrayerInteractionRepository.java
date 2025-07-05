package za.co.PrayerConnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.PrayerConnect.domain.PrayerInteraction;

import java.util.List;

public interface PrayerInteractionRepository extends JpaRepository <PrayerInteraction, Long> {
    List<PrayerInteraction> findAll();
     List<PrayerInteraction> findByUser_Id(Long userId);
     List<PrayerInteraction> findByPrayerRequest_ContentId(Long contentId);
    boolean existsByUser_IdAndPrayerRequest_ContentId(Long userId, String contentId);
}