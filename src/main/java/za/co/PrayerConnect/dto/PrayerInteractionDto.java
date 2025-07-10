package za.co.PrayerConnect.dto;

import java.time.LocalDateTime;

public class PrayerInteractionDto {
    private Long id;
    private LocalDateTime datePrayed;
    private Long userId;
    private Long prayerRequestId;

    public PrayerInteractionDto() {}

    public PrayerInteractionDto(Long id, LocalDateTime datePrayed, Long userId, Long prayerRequestId) {
        this.id = id;
        this.datePrayed = datePrayed;
        this.userId = userId;
        this.prayerRequestId = prayerRequestId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDatePrayed() {
        return datePrayed;
    }

    public void setDatePrayed(LocalDateTime datePrayed) {
        this.datePrayed = datePrayed;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPrayerRequestId() {
        return prayerRequestId;
    }

    public void setPrayerRequestId(Long prayerRequestId) {
        this.prayerRequestId = prayerRequestId;
    }
}
