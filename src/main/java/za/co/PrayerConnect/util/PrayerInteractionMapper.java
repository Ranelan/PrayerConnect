package za.co.PrayerConnect.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.PrayerConnect.domain.PrayerInteraction;
import za.co.PrayerConnect.domain.PrayerRequest;
import za.co.PrayerConnect.domain.RegularUser;
import za.co.PrayerConnect.dto.PrayerInteractionDto;

import za.co.PrayerConnect.service.PrayerRequestServ.PrayerRequestService;
import za.co.PrayerConnect.service.RegularUserServ.RegularUserService;

@Component
public class PrayerInteractionMapper {

    @Autowired
    private RegularUserService regularUserService;

    @Autowired
    private PrayerRequestService prayerRequestService;

    public PrayerInteractionDto toDto(PrayerInteraction entity) {
        if (entity == null) return null;

        PrayerInteractionDto dto = new PrayerInteractionDto();
        dto.setId(entity.getId());
        dto.setDatePrayed(entity.getDatePrayed());

        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
        }

        if (entity.getPrayerRequest() != null) {
            dto.setPrayerRequestId(entity.getPrayerRequest().getContentId());
        }

        return dto;
    }

    public PrayerInteraction toEntity(PrayerInteractionDto dto) {
        if (dto == null) return null;

        RegularUser user = dto.getUserId() != null
                ? regularUserService.findById(dto.getUserId().toString())
                : null;

        PrayerRequest request = dto.getPrayerRequestId() != null
                ? prayerRequestService.findById(dto.getPrayerRequestId())
                : null;

        return new PrayerInteraction.PrayerInteractionBuilder()
                .setId(dto.getId())
                .setDatePrayed(dto.getDatePrayed() != null ? dto.getDatePrayed() : java.time.LocalDateTime.now())
                .setUser(user)
                .setPrayerRequest(request)
                .build();
    }
}
