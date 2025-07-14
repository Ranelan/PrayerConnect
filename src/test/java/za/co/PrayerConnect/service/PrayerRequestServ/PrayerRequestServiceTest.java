package za.co.PrayerConnect.service.PrayerRequestServ;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.PrayerConnect.domain.ApprovalStatus;
import za.co.PrayerConnect.domain.PrayerRequest;
import za.co.PrayerConnect.domain.RegularUser;
import za.co.PrayerConnect.service.RegularUserServ.RegularUserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PrayerRequestServiceTest {

    @Autowired
    private PrayerRequestService prayerRequestService;
    private PrayerRequest prayerRequest;

    @Autowired
    private RegularUserService regularUserService;
    private RegularUser user;

    @BeforeEach
    void setUp() {

        user = new RegularUser.RegularUserBuilder()
                .setFullName("Test User")
                .setEmail("user_" + UUID.randomUUID() + "@example.com")
                .setPassword("password123")
                .setAge(28)
                .setIsBlocked(false)
                .build();
        user = regularUserService.save(user);

        prayerRequest = new PrayerRequest.PrayerRequestBuilder()
                .setTitle("Pray for Healing")
                .setMessage("Please pray for my family member who is sick.")
                .setDateSubmitted(LocalDateTime.now())
                .setUser(user)
                .setAnonymous(false)
                .setApprovalStatuses(List.of(ApprovalStatus.PENDING))
                .build();
        prayerRequest = prayerRequestService.save(prayerRequest);

    }

    @Test
    @Order(1)
    void save() {
        assertNotNull(prayerRequest);
        assertNotNull(prayerRequest.getContentId());
        System.out.println("Saved PrayerRequest: " + prayerRequest);
    }

    @Test
    @Order(2)
    void findById() {
        PrayerRequest found = prayerRequestService.findById(prayerRequest.getContentId());
        assertNotNull(found);
        assertEquals(prayerRequest.getContentId(), found.getContentId());
    }

    @Test
    @Order(3)
    void update() {
        PrayerRequest updated = new PrayerRequest.PrayerRequestBuilder()
                .copy(prayerRequest)
                .setMessage("Updated prayer message.")
                .build();

        PrayerRequest savedUpdate = prayerRequestService.update(updated);
        assertEquals("Updated prayer message.", savedUpdate.getMessage());
        System.out.println("Updated PrayerRequest: " + savedUpdate);
    }

    @Test
    @Order(7)
    void deleteById() {
        prayerRequestService.deleteById(prayerRequest.getContentId());
        Optional<PrayerRequest> deleted = prayerRequestService.findByContentId(prayerRequest.getContentId());
        assertTrue(deleted.isEmpty());
        System.out.println("PrayerRequest deleted successfully");
    }

    @Test
    @Order(4)
    void findAll() {
        List<PrayerRequest> requests = prayerRequestService.findAll();
        assertFalse(requests.isEmpty());
        System.out.println("Total PrayerRequests: " + requests.size());
    }

    @Test
    @Order(5)
    void findByTitle() {
        List<PrayerRequest> found = prayerRequestService.findByTitle("Pray for Healing");
        assertFalse(found.isEmpty());
        assertEquals("Pray for Healing", found.get(0).getTitle());
    }


    @Test
    @Order(6)
    void findByContentId() {
        Optional<PrayerRequest> found = prayerRequestService.findByContentId(prayerRequest.getContentId());
        assertTrue(found.isPresent());
        assertEquals(prayerRequest.getContentId(), found.get().getContentId());
    }
}