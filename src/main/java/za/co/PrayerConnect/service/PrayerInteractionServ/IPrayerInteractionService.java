package za.co.PrayerConnect.service.PrayerInteractionServ;

import za.co.PrayerConnect.domain.PrayerInteraction;
import za.co.PrayerConnect.service.IService;

import java.util.List;

public interface IPrayerInteractionService extends IService<PrayerInteraction, Long> {
     List<PrayerInteraction> findByPrayerRequest_ContentId(Long contentId);
        List<PrayerInteraction> findByUser_Id(Long userId);
        List<PrayerInteraction> findAll();
        boolean hasUserPrayedForContent(Long userId, String contentId);


}
