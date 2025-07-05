package za.co.PrayerConnect.service.PrayerInteractionServ;

import za.co.PrayerConnect.domain.PrayerInteraction;
import za.co.PrayerConnect.service.IService;

import java.util.List;

public interface IPrayerInteractionService extends IService<PrayerInteraction, Long> {
     List<PrayerInteraction> findByContentId(Long contentId);
        List<PrayerInteraction> findByUserId(Long userId);

        boolean hasUserPrayedForContent(Long userId, Long contentId);


}
