package za.co.PrayerConnect.service.TestimonyServ;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.PrayerConnect.domain.PrayerRequest;
import za.co.PrayerConnect.domain.RegularUser;
import za.co.PrayerConnect.domain.Testimony;
import za.co.PrayerConnect.service.PrayerRequestServ.PrayerRequestService;
import za.co.PrayerConnect.service.RegularUserServ.RegularUserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestimonyServiceTest {

    @Autowired
    private TestimonyService testimonyService;
    private Testimony testimony;

    @Autowired
    private RegularUserService regularUserService;
    private RegularUser user;

    @Autowired
    private PrayerRequestService prayerRequestService;

    private PrayerRequest prayerRequest;


    @BeforeEach
    void setUp() {
        user = new RegularUser.RegularUserBuilder()
                .setFullName("Test User")
                .setEmail("user_" + System.currentTimeMillis() + "@example.com")
                .setPassword("password123")
                .setAge(28)
                .setIsBlocked(false)
                .build();
        user = regularUserService.save(user);

        PrayerRequest temp = new PrayerRequest.PrayerRequestBuilder()
                .setTitle("Test Prayer")
                .setMessage("Please help with my test")
                .setAnonymous(false)
                .build();
        prayerRequest = prayerRequestService.save(temp);

        testimony = new Testimony.TestimonyBuilder()
                .setMessage("This is a test testimony message.")
                .setDateSubmitted(LocalDateTime.now())
                .setPrayerRequest(prayerRequest)
                .setUser(user)
                .build();
        testimony = testimonyService.save(testimony);
    }

    @Test
    @Order(1)
    void save() {
        assertNotNull(testimony);
        assertNotNull(testimony.getTestimonyId());
        assertEquals("This is a test testimony message.", testimony.getMessage());
        System.out.println("Testimony saved: " + testimony);
    }

    @Test
    @Order(2)
    void findById() {
        Testimony foundTestimony = testimonyService.findById(testimony.getTestimonyId());
        assertNotNull(foundTestimony);
        assertEquals(testimony.getTestimonyId(), foundTestimony.getTestimonyId());
        System.out.println("Found Testimony: " + foundTestimony);
    }

    @Test
    @Order(3)
    void update() {
        Testimony updatedTestimony = new Testimony.TestimonyBuilder()
                .copy(testimony)
                .setMessage("Updated testimony message")
                .build();

        Testimony updated = testimonyService.update(updatedTestimony);
        assertNotNull(updated);
        assertEquals("Updated testimony message", updated.getMessage());
    }

    @Test
    @Order(7)
    void deleteById() {
        Long idToDelete = testimony.getTestimonyId();
        testimonyService.deleteById(idToDelete);
        assertThrows(RuntimeException.class, () -> testimonyService.findById(idToDelete), "Testimony should be deleted");
        System.out.println("Testimony deleted with ID: " + idToDelete);
    }

    @Test
    @Order(4)
    void findAll() {
        List<Testimony> testimonies = testimonyService.findAll();
        assertNotNull(testimonies);
        assertTrue(testimonies.size() > 0);
    }

    @Test
    @Order(5)
    void findByUserId() {
        List<Testimony> userTestimonies = testimonyService.findByUserId(user.getId());
        assertNotNull(userTestimonies);
        assertTrue(userTestimonies.stream().anyMatch(t -> t.getTestimonyId().equals(testimony.getTestimonyId())));
    }

    @Test
    @Order(6)
    void findByPrayerRequest_ContentId() {
        List<Testimony> prayerRequestTestimonies = testimonyService.findByPrayerRequest_ContentId(prayerRequest.getContentId());
        assertNotNull(prayerRequestTestimonies);
        assertTrue(prayerRequestTestimonies.stream().anyMatch(t -> t.getTestimonyId().equals(testimony.getTestimonyId())));
    }
}