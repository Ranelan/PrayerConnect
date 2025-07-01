package za.co.PrayerConnect.domain;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Testimony {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testimonyId;
    private String message;
    private LocalDateTime dateSubmitted;

    @ManyToOne
    @JoinColumn(name = "contentId", referencedColumnName = "contentId")
    private PrayerRequest prayerRequest;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private RegularUser user;

    public Testimony() {

    }

    public Testimony(Long testimonyId, String message, LocalDateTime dateSubmitted, PrayerRequest prayerRequest, RegularUser user) {
        this.testimonyId = testimonyId;
        this.message = message;
        this.dateSubmitted = dateSubmitted;
        this.prayerRequest = prayerRequest;
        this.user = user;
    }

    public Testimony(TestimonyBuilder testimonyBuilder) {
        this.testimonyId = testimonyBuilder.testimonyId;
        this.message = testimonyBuilder.message;
        this.dateSubmitted = testimonyBuilder.dateSubmitted;
        this.prayerRequest = testimonyBuilder.prayerRequest;
        this.user = testimonyBuilder.user;
    }

    public Long getTestimonyId() {
        return testimonyId;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDateSubmitted() {
        return dateSubmitted;
    }

    public PrayerRequest getPrayerRequest() {
        return prayerRequest;
    }

    public RegularUser getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Testimony{" +
                "testimonyId=" + testimonyId +
                ", message='" + message + '\'' +
                ", dateSubmitted=" + dateSubmitted +
                ", prayerRequest=" + prayerRequest +
                ", user=" + user +
                '}';
    }

    public static class TestimonyBuilder {
        private Long testimonyId;
        private String message;
        private LocalDateTime dateSubmitted;
        private PrayerRequest prayerRequest;
        private RegularUser user;

        public TestimonyBuilder setTestimonyId(Long testimonyId) {
            this.testimonyId = testimonyId;
            return this;
        }

        public TestimonyBuilder setMessage(String message) {
            this.message = message;
            return this;
        }

        public TestimonyBuilder setDateSubmitted(LocalDateTime dateSubmitted) {
            this.dateSubmitted = dateSubmitted;
            return this;
        }

        public TestimonyBuilder setPrayerRequest(PrayerRequest prayerRequest) {
            this.prayerRequest = prayerRequest;
            return this;
        }

        public TestimonyBuilder setUser(RegularUser user) {
            this.user = user;
            return this;
        }

        public TestimonyBuilder copy(Testimony testimony) {
            this.testimonyId = testimony.testimonyId;
            this.message = testimony.message;
            this.dateSubmitted = testimony.dateSubmitted;
            this.prayerRequest = testimony.prayerRequest;
            this.user = testimony.user;
            return this;
        }

        public Testimony build() {
            if(this.dateSubmitted == null){
                this.dateSubmitted = LocalDateTime.now();
            }
            return new Testimony(this);
        }
    }
}