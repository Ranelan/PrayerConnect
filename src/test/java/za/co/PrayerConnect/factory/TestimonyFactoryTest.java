package za.co.PrayerConnect.factory;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.PrayerConnect.domain.PrayerRequest;
import za.co.PrayerConnect.domain.RegularUser;
import za.co.PrayerConnect.domain.Testimony;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TestimonyFactoryTest {

    private String message;
    private LocalDateTime dateSubmitted;
    private PrayerRequest prayerRequest;
    private RegularUser user;

    private TestimonyFactory testimonyFactory;
    private Testimony testimony;

    @BeforeEach
    void setUp() {
        message = "I received healing after prayer.";
        dateSubmitted = LocalDateTime.now();
        prayerRequest = new PrayerRequest(); // Assuming a valid PrayerRequest object is created
        user = new RegularUser(); // Assuming a valid RegularUser object is created
    }

    @Test
    void createTestimony() {
        testimony = TestimonyFactory.createTestimony(message, dateSubmitted,prayerRequest, user);

        assertNotNull(testimony);
        assertEquals(message, testimony.getMessage());
        assertEquals(dateSubmitted, testimony.getDateSubmitted());
        assertEquals(prayerRequest, testimony.getPrayerRequest());
        assertEquals(user, testimony.getUser());

    }

    @Test
    void createTestimonyWithInvalidMessage() {
        testimony = TestimonyFactory.createTestimony("", dateSubmitted, prayerRequest, user);

        assertNull(testimony);
    }

    @Test
    void createTestimonyWithInvalidDate() {
        testimony = TestimonyFactory.createTestimony(message, null, prayerRequest, user);

        assertNull(testimony);
    }

    @Test
    void createTestimonyWithNullPrayerRequest() {
        testimony = TestimonyFactory.createTestimony(message, dateSubmitted, null, user);

        assertNull(testimony);
    }

    @Test
    void createTestimonyWithNullUser() {
        testimony = TestimonyFactory.createTestimony(message, dateSubmitted, prayerRequest, null);

        assertNull(testimony);
    }

}