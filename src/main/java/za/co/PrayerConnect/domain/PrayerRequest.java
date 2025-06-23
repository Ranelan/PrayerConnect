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
    @Lob // message has more than 4000 characters expected.
    private String message;
    private LocalDateTime dateSubmitted;
    private boolean isAnonymous = false;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<ApprovalStatus> approvalStatuses;
    private LocalDateTime reviewedAt;
    @Column(length = 1000)
    private String reviewComment;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name ="admin_id", referencedColumnName = "id")
    private Admin admin;

    public PrayerRequest() {
    }

    public PrayerRequest(Long contentId, String title, String message, LocalDateTime dateSubmitted, boolean isAnonymous, List<ApprovalStatus> approvalStatuses, LocalDateTime reviewedAt, String reviewComment, User user, Admin admin) {
        this.contentId = contentId;
        this.title = title;
        this.message = message;
        this.dateSubmitted = dateSubmitted;
        this.isAnonymous = isAnonymous;
        this.approvalStatuses = approvalStatuses;
        this.reviewedAt = reviewedAt;
        this.reviewComment = reviewComment;
        this.user = user;
        this.admin = admin;

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
        this.user = prayerRequestBuilder.user;
        this.admin = prayerRequestBuilder.admin;
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

    public Admin getAdmin() {
        return admin;
    }

    @Override
    public String toString() {
        return "PrayerRequestService{" +
                "contentId=" + contentId +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", dateSubmitted=" + dateSubmitted +
                ", isAnonymous=" + isAnonymous +
                ", approvalStatuses=" + approvalStatuses +
                ", reviewedAt=" + reviewedAt +
                ", reviewComment='" + reviewComment + '\'' +
                ", user=" + user +
                ", admin=" + admin +
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
        private User user;
        private Admin admin;



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
        public PrayerRequestBuilder setUser(User user) {
            this.user = user;
            return this;
        }
        public PrayerRequestBuilder setAdmin(Admin admin) {
            this.admin = admin;
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
            this.user = prayerRequest.user;
            this.admin = prayerRequest.admin;
            return this;
        }

        public PrayerRequest build() {
            if (this.dateSubmitted == null) {
                this.dateSubmitted = LocalDateTime.now(); // Automatically set if not provided
            }
            return new PrayerRequest(this);
        }

    }
}
