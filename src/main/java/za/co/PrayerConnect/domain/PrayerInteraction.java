package za.co.PrayerConnect.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PrayerInteraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime datePrayed;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private RegularUser user;

    @ManyToOne
    @JoinColumn(name = "prayer_request_id", referencedColumnName = "contentId")
    private PrayerRequest prayerRequest;

    public PrayerInteraction() {
    }

    public PrayerInteraction(Long id, LocalDateTime datePrayed, RegularUser user, PrayerRequest prayerRequest) {
        this.id = id;
        this.datePrayed = datePrayed;
        this.user = user;
        this.prayerRequest = prayerRequest;
    }

    public PrayerInteraction(PrayerInteractionBuilder prayerInteractionBuilder) {
        this.id = prayerInteractionBuilder.id;
        this.datePrayed = prayerInteractionBuilder.datePrayed;
        this.user = prayerInteractionBuilder.user;
        this.prayerRequest = prayerInteractionBuilder.prayerRequest;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDatePrayed() {
        return datePrayed;
    }

    public RegularUser getUser() {
        return user;
    }

    public PrayerRequest getPrayerRequest() {
        return prayerRequest;
    }

    public static class PrayerInteractionBuilder {
        private Long id;
        private LocalDateTime datePrayed;
        private RegularUser user;
        private PrayerRequest prayerRequest;

        public PrayerInteractionBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public PrayerInteractionBuilder setDatePrayed(LocalDateTime datePrayed) {
            this.datePrayed = datePrayed;
            return this;
        }

        public PrayerInteractionBuilder setUser(RegularUser user) {
            this.user = user;
            return this;
        }

        public PrayerInteractionBuilder setPrayerRequest(PrayerRequest prayerRequest) {
            this.prayerRequest = prayerRequest;
            return this;
        }

        public PrayerInteractionBuilder copy(PrayerInteraction prayerInteraction) {
            this.id = prayerInteraction.id;
            this.datePrayed = prayerInteraction.datePrayed;
            this.user = prayerInteraction.user;
            this.prayerRequest = prayerInteraction.prayerRequest;
            return this;
        }

        public PrayerInteraction build() {
            return new PrayerInteraction(this);
        }
    }
}
