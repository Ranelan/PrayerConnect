package za.co.PrayerConnect.service.PrayerRequestServ;

import za.co.PrayerConnect.domain.PrayerRequest;
import za.co.PrayerConnect.service.IService;

import java.util.List;
import java.util.Optional;

public interface IPrayerRequestService extends IService<PrayerRequest, Long> {

    List<PrayerRequest> findAll();
    Optional<PrayerRequest> findByTitle(String title);
    Optional<PrayerRequest> findByContentId(Long contentId);
}
