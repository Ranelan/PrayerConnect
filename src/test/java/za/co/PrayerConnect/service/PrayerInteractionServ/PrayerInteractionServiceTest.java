package za.co.PrayerConnect.service.PrayerInteractionServ;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.PrayerConnect.domain.*;
import za.co.PrayerConnect.service.PrayerRequestServ.PrayerRequestService;
import za.co.PrayerConnect.service.RegularUserServ.RegularUserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PrayerInteractionServiceTest {

    @Autowired
    private PrayerInteractionService prayerInteractionService;

    @Autowired
    private PrayerRequestService prayerRequestService;

    @Autowired
    private RegularUserService regularUserService;

    private RegularUser user;
    private PrayerRequest prayerRequest;
    private PrayerInteraction savedInteraction;

    @BeforeEach
    void setUp() {
        if (user == null) {
            user = new RegularUser.RegularUserBuilder()
                    .setFullName("Test User")
                    .setEmail("user_" + System.currentTimeMillis() + "@example.com")
                    .setPassword("password123")
                    .setAge(28)
                    .setIsBlocked(false)
                    .build();
            user = regularUserService.save(user);
        }

        prayerRequest = new PrayerRequest.PrayerRequestBuilder()
                .setTitle("Pray for Healing")
                .setMessage("Please pray for my family member who is sick.")
                .setDateSubmitted(LocalDateTime.now())
                .setUser(user)
                .setAnonymous(false)
                .build();
        prayerRequest = prayerRequestService.save(prayerRequest);

        PrayerInteraction interaction = new PrayerInteraction.PrayerInteractionBuilder()
                .setUser(user)
                .setPrayerRequest(prayerRequest)
                .setDatePrayed(LocalDateTime.now())
                .build();
        savedInteraction = prayerInteractionService.save(interaction);
    }

    @Test
    @Order(1)
    void save() {
        assertNotNull(savedInteraction);
        assertNotNull(savedInteraction.getId());
        assertEquals(user.getId(), savedInteraction.getUser().getId());
        assertEquals(prayerRequest.getContentId(), savedInteraction.getPrayerRequest().getContentId());
    }

    @Test
    @Order(2)
    void findById() {
        PrayerInteraction found = prayerInteractionService.findById(savedInteraction.getId());
        assertNotNull(found);
        assertEquals(savedInteraction.getId(), found.getId());
    }

    @Test
    @Order(3)
    void update() {
        PrayerInteraction updatedInteraction = new PrayerInteraction.PrayerInteractionBuilder()
                .copy(savedInteraction)
                .setDatePrayed(LocalDateTime.now().plusDays(1))
                .build();
        PrayerInteraction updated = prayerInteractionService.update(updatedInteraction);
        assertNotNull(updated);
        assertEquals(updatedInteraction.getDatePrayed(), updated.getDatePrayed());
        assertEquals(savedInteraction.getId(), updated.getId());
    }

    @Test
    @Order(4)
    void findByPrayerRequest_ContentId() {
        List<PrayerInteraction> list = prayerInteractionService.findByPrayerRequest_ContentId(prayerRequest.getContentId());
        assertFalse(list.isEmpty());
        assertTrue(list.stream().anyMatch(i -> i.getId().equals(savedInteraction.getId())));
    }

    @Test
    @Order(5)
    void findByUser_Id() {
        List<PrayerInteraction> list = prayerInteractionService.findByUser_Id(user.getId());
        assertFalse(list.isEmpty());
        assertTrue(list.stream().anyMatch(i -> i.getId().equals(savedInteraction.getId())));
    }

    @Test
    @Order(6)
    void findAll() {
        List<PrayerInteraction> all = prayerInteractionService.findAll();
        assertNotNull(all);
        assertTrue(all.size() >= 1);
    }

    @Test
    @Order(7)
    void hasUserPrayedForContent() {
        boolean hasPrayed = prayerInteractionService.hasUserPrayedForContent(user.getId(), prayerRequest.getContentId());
        assertTrue(hasPrayed);
    }

    @Test
    @Order(8)
    void deleteById() {
        Long idToDelete = savedInteraction.getId();
        prayerInteractionService.deleteById(idToDelete);
        assertThrows(RuntimeException.class, () -> prayerInteractionService.findById(idToDelete));
    }
}
