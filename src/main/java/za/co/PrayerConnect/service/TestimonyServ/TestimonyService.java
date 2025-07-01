package za.co.PrayerConnect.service.TestimonyServ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.PrayerConnect.domain.Testimony;
import za.co.PrayerConnect.repository.TestimonyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TestimonyService implements ITestimonyService{

    @Autowired
    private TestimonyRepository testimonyRepository;

    @Override
    public Optional<Testimony> findByUserId(Long userId) {
        return testimonyRepository.findByUserId(userId);
    }

    @Override
    public Optional<Testimony> findByContentId(Long contentId) {
        return Optional.empty();
    }

    @Override
    public Testimony save(Testimony entity) {
        return testimonyRepository.save(entity);
    }

    @Override
    public Testimony findById(Long aLong) {
        return testimonyRepository.findById(aLong)
                .orElseThrow(() -> new RuntimeException("Testimony not found with id: " + aLong));
    }

    @Override
    public Testimony update(Testimony entity) {
        return testimonyRepository.findById(entity.getTestimonyId())
                .map(existingTestimony -> {
                    Testimony updatedTestimony = new Testimony.TestimonyBuilder()
                            .copy(existingTestimony)
                            .setUser(entity.getUser())
                            .setMessage(entity.getMessage())
                            .setDateSubmitted(entity.getDateSubmitted())
                            .build();
                    return testimonyRepository.save(updatedTestimony);
                })
                .orElse(null);
    }

    @Override
    public void deleteById(Long aLong) {
        testimonyRepository.deleteById(aLong);
    }

    @Override
    public List<Testimony> findAll() {
        return testimonyRepository.findAll();
    }
}
