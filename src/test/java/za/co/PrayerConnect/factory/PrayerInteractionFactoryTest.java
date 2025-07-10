package za.co.PrayerConnect.factory;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.PrayerConnect.domain.PrayerInteraction;
import za.co.PrayerConnect.domain.PrayerRequest;
import za.co.PrayerConnect.domain.RegularUser;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PrayerInteractionFactoryTest {

    private LocalDateTime datePrayed;
    private RegularUser user;
    private PrayerRequest prayerRequest;

    private PrayerInteractionFactory prayerInteractionFactory;
    private PrayerInteraction prayerInteraction;

    @BeforeEach
    void setUp() {
        datePrayed = LocalDateTime.now();
        user = new RegularUser();
        prayerRequest = new PrayerRequest();
    }

    @Test
    void createPrayerInteraction() {
        prayerInteraction = PrayerInteractionFactory.createPrayerInteraction( datePrayed, user, prayerRequest);

        assertNotNull(prayerInteraction);
        assertEquals(datePrayed, prayerInteraction.getDatePrayed());
        assertEquals(user, prayerInteraction.getUser());
        assertEquals(prayerRequest, prayerInteraction.getPrayerRequest());
    }

    @Test
    void createPrayerInteractionWithInvalidDate() {
        prayerInteraction = PrayerInteractionFactory.createPrayerInteraction(null, user, prayerRequest);

        assertNull(prayerInteraction);
    }

    @Test
    void createPrayerInteractionWithNullUser() {
        prayerInteraction = PrayerInteractionFactory.createPrayerInteraction(datePrayed, null, prayerRequest);

        assertNull(prayerInteraction);
    }

    @Test
    void createPrayerInteractionWithNullPrayerRequest() {
        prayerInteraction = PrayerInteractionFactory.createPrayerInteraction(datePrayed, user, null);

        assertNull(prayerInteraction);
    }
}