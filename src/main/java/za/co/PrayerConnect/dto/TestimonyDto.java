package za.co.PrayerConnect.dto;

import java.time.LocalDateTime;

public class TestimonyDto {
    private Long testimonyId;
    private String message;
    private LocalDateTime dateSubmitted;

    private Long prayerRequestId;  // Instead of embedding full object
    private Long userId;           // Instead of embedding RegularUser

    // Constructors
    public TestimonyDto() {}

    public TestimonyDto(Long testimonyId, String message, LocalDateTime dateSubmitted, Long prayerRequestId, Long userId) {
        this.testimonyId = testimonyId;
        this.message = message;
        this.dateSubmitted = dateSubmitted;
        this.prayerRequestId = prayerRequestId;
        this.userId = userId;
    }

    // Getters and Setters
    public Long getTestimonyId() {
        return testimonyId;
    }

    public void setTestimonyId(Long testimonyId) {
        this.testimonyId = testimonyId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(LocalDateTime dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public Long getPrayerRequestId() {
        return prayerRequestId;
    }

    public void setPrayerRequestId(Long prayerRequestId) {
        this.prayerRequestId = prayerRequestId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


}
