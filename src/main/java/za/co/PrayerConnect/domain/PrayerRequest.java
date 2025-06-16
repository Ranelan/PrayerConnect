package za.co.PrayerConnect.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class PrayerRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentId;
    private String title;
    private String message;
    private LocalDateTime dateSubmitted;
    private boolean isAnonymous;
    @ElementCollection(targetClass = ApprovalStatus.class)
    @Enumerated(EnumType.STRING)
    private List<ApprovalStatus> approvalStatuses;
    private LocalDateTime reviewedAt;
    //Admin
    private String reviewComment;


    @ManyToOne
    @JoinColumn(name = "user_i", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name ="admin_id", referencedColumnName = "id")
    private Admin admin;

    public PrayerRequest() {
    }

    public PrayerRequest(Long contentId, String title, String message, LocalDateTime dateSubmitted, boolean isAnonymous, List<ApprovalStatus> approvalStatuses, LocalDateTime reviewedAt, String reviewComment, User user) {
        this.contentId = contentId;
        this.title = title;
        this.message = message;
        this.dateSubmitted = dateSubmitted;
        this.isAnonymous = isAnonymous;
        this.approvalStatuses = approvalStatuses;
        this.reviewedAt = reviewedAt;
        this.reviewComment = reviewComment;
        this.user = user;

    }

    public PrayerRequest(PrayerRequestBuilder prayerRequestBuilder) {
        this.contentId = prayerRequestBuilder.contentId;
        this.title = prayerRequestBuilder.title;
        this.message = prayerRequestBuilder.message;
        this.dateSubmitted = prayerRequestBuilder.dateSubmitted;
        this.isAnonymous = prayerRequestBuilder.isAnonymous;
        this.approvalStatuses = prayerRequestBuilder.approvalStatuses;
        this.reviewedAt = prayerRequestBuilder.reviewedAt;
        this.reviewComment = prayerRequestBuilder.reviewComment;
    }

    public Long getContentId() {
        return contentId;
    }


    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDateSubmitted() {
        return dateSubmitted;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public List<ApprovalStatus> getApprovalStatuses() {
        return approvalStatuses;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "PrayerRequest{" +
                "contentId=" + contentId +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", dateSubmitted=" + dateSubmitted +
                ", isAnonymous=" + isAnonymous +
                ", approvalStatuses=" + approvalStatuses +
                ", reviewedAt=" + reviewedAt +
                ", reviewComment='" + reviewComment + '\'' +
                ", user=" + user +
                '}';
    }

    public static class PrayerRequestBuilder {
        private Long contentId;
        private String title;
        private String message;
        private LocalDateTime dateSubmitted;
        private boolean isAnonymous;
        private List<ApprovalStatus> approvalStatuses;
        private LocalDateTime reviewedAt;
        //Admin
        private String reviewComment;



        public PrayerRequestBuilder setContentId(Long contentId) {
            this.contentId = contentId;
            return this;
        }


        public PrayerRequestBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public PrayerRequestBuilder setMessage(String message) {
            this.message = message;
            return this;
        }

        public PrayerRequestBuilder setDateSubmitted(LocalDateTime dateSubmitted) {
            this.dateSubmitted = dateSubmitted;
            return this;
        }

        public PrayerRequestBuilder setAnonymous(boolean isAnonymous) {
            this.isAnonymous = isAnonymous;
            return this;
        }

        public PrayerRequestBuilder setApprovalStatuses(List<ApprovalStatus> approvalStatuses) {
            this.approvalStatuses = approvalStatuses;
            return this;
        }

        public PrayerRequestBuilder setReviewedAt(LocalDateTime reviewedAt) {
            this.reviewedAt = reviewedAt;
            return this;
        }

        public PrayerRequestBuilder setReviewComment(String reviewComment) {
            this.reviewComment = reviewComment;
            return this;
        }

        public PrayerRequestBuilder copy(PrayerRequest prayerRequest) {
            this.contentId = prayerRequest.contentId;
            this.title = prayerRequest.title;
            this.message = prayerRequest.message;
            this.dateSubmitted = prayerRequest.dateSubmitted;
            this.isAnonymous = prayerRequest.isAnonymous;
            this.approvalStatuses = prayerRequest.approvalStatuses;
            this.reviewedAt = prayerRequest.reviewedAt;
            this.reviewComment = prayerRequest.reviewComment;
            return this;
        }

        public PrayerRequest build() {
            return new PrayerRequest(this);
        }
    }
}
