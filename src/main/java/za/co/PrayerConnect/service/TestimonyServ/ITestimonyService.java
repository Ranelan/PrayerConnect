package za.co.PrayerConnect.service.TestimonyServ;

import za.co.PrayerConnect.domain.Testimony;
import za.co.PrayerConnect.service.IService;

import java.util.List;
import java.util.Optional;

public interface ITestimonyService extends IService<Testimony, Long> {
    List<Testimony> findAll();
    Optional<Testimony> findByUserId(Long userId);
    Optional<Testimony> findByContentId(Long contentId);
}
