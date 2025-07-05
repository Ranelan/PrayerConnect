package za.co.PrayerConnect.service.PrayerInteractionServ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.PrayerConnect.domain.PrayerInteraction;
import za.co.PrayerConnect.repository.PrayerInteractionRepository;

import java.util.List;

@Service
public class PrayerInteractionService implements IPrayerInteractionService{

    @Autowired
    private PrayerInteractionRepository prayerInteractionRepository;


    @Override
    public List<PrayerInteraction> findByContentId(Long contentId) {
        return prayerInteractionRepository.findByContentId(contentId)
                .orElseThrow(() -> new RuntimeException("No Prayer Interactions found for content ID: " + contentId));
    }

    @Override
    public List<PrayerInteraction> findByUserId(Long userId) {
        return List.of();
    }

    @Override
    public boolean hasUserPrayedForContent(Long userId, Long contentId) {
        return false;
    }

    @Override
    public PrayerInteraction save(PrayerInteraction entity) {
        return prayerInteractionRepository.save(entity);
    }

    @Override
    public PrayerInteraction findById(Long aLong) {
        return prayerInteractionRepository.findById(aLong)
                .orElseThrow(() -> new RuntimeException("Prayer Interaction not found with id: " + aLong));
    }

    @Override
    public PrayerInteraction update(PrayerInteraction entity) {
        return prayerInteractionRepository.findById(entity.getId())
                .map(existing -> {
                    PrayerInteraction updated = new PrayerInteraction.PrayerInteractionBuilder()
                            .copy(existing)
                            .setUser(entity.getUser())
                            .setId(entity.getId())
                            .setDatePrayed(entity.getDatePrayed())
                            .setPrayerRequest(entity.getPrayerRequest())
                            .build();
                    return prayerInteractionRepository.save(updated);
                })
                .orElseThrow(() -> new RuntimeException("Prayer Interaction not found with id: " + entity.getId()));
    }

    @Override
    public void deleteById(Long aLong) {
        if (prayerInteractionRepository.existsById(aLong)) {
            prayerInteractionRepository.deleteById(aLong);
        } else {
            throw new RuntimeException("Prayer Interaction not found with id: " + aLong);
        }
    }
}
