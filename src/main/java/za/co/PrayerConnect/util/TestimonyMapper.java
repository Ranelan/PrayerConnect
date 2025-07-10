package za.co.PrayerConnect.util;

import za.co.PrayerConnect.domain.PrayerRequest;
import za.co.PrayerConnect.domain.RegularUser;
import za.co.PrayerConnect.domain.Testimony;
import za.co.PrayerConnect.dto.TestimonyDto;
import za.co.PrayerConnect.service.PrayerRequestServ.PrayerRequestService;
import za.co.PrayerConnect.service.RegularUserServ.RegularUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestimonyMapper {

    private final PrayerRequestService prayerRequestService;
    private final RegularUserService regularUserService;

    @Autowired
    public TestimonyMapper(PrayerRequestService prayerRequestService, RegularUserService regularUserService) {
        this.prayerRequestService = prayerRequestService;
        this.regularUserService = regularUserService;
    }

    public TestimonyDto toDto(Testimony testimony) {
        if (testimony == null) return null;

        TestimonyDto dto = new TestimonyDto();
        dto.setTestimonyId(testimony.getTestimonyId());
        dto.setMessage(testimony.getMessage());
        dto.setDateSubmitted(testimony.getDateSubmitted());

        if (testimony.getPrayerRequest() != null) {
            dto.setPrayerRequestId(testimony.getPrayerRequest().getContentId());
        }

        if (testimony.getUser() != null) {
            dto.setUserId(testimony.getUser().getId());
        }

        return dto;
    }

    public Testimony toEntity(TestimonyDto dto) {
        if (dto == null) return null;

        PrayerRequest prayerRequest = null;
        if (dto.getPrayerRequestId() != null) {
            prayerRequest = prayerRequestService.findById(dto.getPrayerRequestId());
        }

        RegularUser user = null;
        if (dto.getUserId() != null) {
            user = regularUserService.findById(dto.getUserId().toString());
        }

        return new Testimony.TestimonyBuilder()
                .setTestimonyId(dto.getTestimonyId())
                .setMessage(dto.getMessage())
                .setDateSubmitted(dto.getDateSubmitted())
                .setPrayerRequest(prayerRequest)
                .setUser(user)
                .build();
    }
}
