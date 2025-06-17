package za.co.PrayerConnect.service.PrayerRequestServ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.PrayerConnect.domain.PrayerRequest;
import za.co.PrayerConnect.repository.PrayerRequestRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PrayerRequestService implements IPrayerRequestService {

    @Autowired
    private PrayerRequestRepository prayerRequestRepository;

    @Override
    public PrayerRequest save(PrayerRequest entity) {
            if (entity.getDateSubmitted() == null) {
                entity = new PrayerRequest.PrayerRequestBuilder()
                        .copy(entity)
                        .setDateSubmitted(LocalDateTime.now())
                        .build();
            }
        return prayerRequestRepository.save(entity);
    }

    @Override
    public PrayerRequest findById(Long aLong) {
        return prayerRequestRepository.findById(aLong)
                .orElseThrow(() -> new RuntimeException("Prayer Request not found with id: " + aLong));
    }

    @Override
    public PrayerRequest update(PrayerRequest entity) {
        Optional<PrayerRequest> existingRequest = prayerRequestRepository.findById(entity.getContentId());

        if(existingRequest.isPresent()) {
            PrayerRequest existingPrayerRequest = existingRequest.get();

            PrayerRequest updatedRequest = new PrayerRequest.PrayerRequestBuilder()
                    .copy(existingPrayerRequest)
                    .setAdmin(entity.getAdmin())
                    .setUser(entity.getUser())
                    .setTitle(entity.getTitle())
                    .setMessage(entity.getMessage())
                    .setDateSubmitted(entity.getDateSubmitted())
                    .setAnonymous(entity.isAnonymous())
                    .setApprovalStatuses(entity.getApprovalStatuses())
                    .setReviewedAt(entity.getReviewedAt())
                    .setReviewComment(entity.getReviewComment())
                    .build();
            return prayerRequestRepository.save(updatedRequest);
        }
        return null;
    }

    @Override
    public void deleteById(Long aLong) {
        if(prayerRequestRepository.existsById(aLong)) {
            prayerRequestRepository.deleteById(aLong);
        }
    }

    @Override
    public List<PrayerRequest> findAll() {
        return prayerRequestRepository.findAll();
    }

    @Override
    public Optional<PrayerRequest> findByTitle(String title) {
        return prayerRequestRepository.findByTitle(title);
    }

    @Override
    public Optional<PrayerRequest> findByContentId(Long contentId) {
        return prayerRequestRepository.findByContentId(contentId);
    }
}
