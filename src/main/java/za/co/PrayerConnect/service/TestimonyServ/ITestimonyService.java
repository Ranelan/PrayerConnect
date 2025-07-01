package za.co.PrayerConnect.service.TestimonyServ;

import za.co.PrayerConnect.domain.Testimony;
import za.co.PrayerConnect.service.IService;

import java.util.List;
import java.util.Optional;

public interface ITestimonyService extends IService<Testimony, Long> {
    List<Testimony> findAll();
    List<Testimony> findByUserId(Long userId);
    List<Testimony> findByPrayerRequest_ContentId(Long contentId);

}
