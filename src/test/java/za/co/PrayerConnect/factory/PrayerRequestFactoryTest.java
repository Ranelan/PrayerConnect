package za.co.PrayerConnect.factory;

import jakarta.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.PrayerConnect.domain.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrayerRequestFactoryTest {

    private String title;
    private String message;
    private LocalDateTime dateSubmitted;
    private boolean isAnonymous = false;
    private List<ApprovalStatus> approvalStatuses;
    private LocalDateTime reviewedAt;
    private String reviewComment;
    private User user;
    private Admin admin;

    private PrayerRequestFactory prayerRequestFactory;
    private PrayerRequest prayerRequest;


    @BeforeEach
    void setUp() {
        title = "Prayer for Healing";
        message = "Please pray for my healing from a recent illness.";
        dateSubmitted = LocalDateTime.now();
        isAnonymous = false;
        approvalStatuses = List.of(ApprovalStatus.PENDING);
        reviewedAt = LocalDateTime.now();
        reviewComment = "Pending review";
        user = new RegularUser();
        admin = new Admin();


    }

    @Test
    void createPrayerRequest() {
        prayerRequest = PrayerRequestFactory.createPrayerRequest(title, message, dateSubmitted, isAnonymous, approvalStatuses, reviewedAt, reviewComment, user, admin);

        assertNotNull(prayerRequest);
        assertEquals(title, prayerRequest.getTitle());
        assertEquals(message, prayerRequest.getMessage());
        assertEquals(dateSubmitted, prayerRequest.getDateSubmitted());
        assertFalse(prayerRequest.isAnonymous());
        assertEquals(approvalStatuses, prayerRequest.getApprovalStatuses());
        assertEquals(reviewedAt, prayerRequest.getReviewedAt());
        assertEquals(reviewComment, prayerRequest.getReviewComment());
        assertEquals(user, prayerRequest.getUser());
        assertEquals(admin, prayerRequest.getAdmin());

    }

    @Test
    void createPrayerRequestWithInvalidTitle() {
        prayerRequest = PrayerRequestFactory.createPrayerRequest("", message, dateSubmitted, isAnonymous, approvalStatuses, reviewedAt, reviewComment, user, admin);

        assertNull(prayerRequest);
    }

    @Test
    void createPrayerRequestWithInvalidMessage() {
        prayerRequest = PrayerRequestFactory.createPrayerRequest(title, "", dateSubmitted, isAnonymous, approvalStatuses, reviewedAt, reviewComment, user, admin);

        assertNull(prayerRequest);
    }

    @Test
    void createPrayerRequestWithInvalidDateSubmitted() {
        prayerRequest = PrayerRequestFactory.createPrayerRequest(title, message, null, isAnonymous, approvalStatuses, reviewedAt, reviewComment, user, admin);

        assertNull(prayerRequest);
    }

    @Test
    void createPrayerRequestWithInvalidApprovalStatuses() {
        prayerRequest = PrayerRequestFactory.createPrayerRequest(title, message, dateSubmitted, isAnonymous, null, reviewedAt, reviewComment, user, admin);

        assertNull(prayerRequest);
    }

    @Test
    void createPrayerRequestWithInvalidReviewedAt() {
        prayerRequest = PrayerRequestFactory.createPrayerRequest(title, message, dateSubmitted, isAnonymous, approvalStatuses, null, reviewComment, user, admin);

        assertNull(prayerRequest);
    }

    @Test
    void createPrayerRequestWithInvalidReviewComment() {
        prayerRequest = PrayerRequestFactory.createPrayerRequest(title, message, dateSubmitted, isAnonymous, approvalStatuses, reviewedAt, "", user, admin);

        assertNull(prayerRequest);
    }

    @Test
    void createPrayerRequestWithEmptyApprovalStatuses() {
        prayerRequest = PrayerRequestFactory.createPrayerRequest(title, message, dateSubmitted, isAnonymous, List.of(), reviewedAt, reviewComment, user, admin);

        assertNull(prayerRequest);
    }

    @Test
    void createPrayerRequestWithNullUserAndAdmin() {
        prayerRequest = PrayerRequestFactory.createPrayerRequest(title, message, dateSubmitted, isAnonymous, approvalStatuses, reviewedAt, reviewComment, null, null);

        assertNotNull(prayerRequest);
        assertNull(prayerRequest.getUser());
        assertNull(prayerRequest.getAdmin());
    }

}