package za.co.PrayerConnect.dto;

public class PrayerRequestDTO {
    private String title;
    private String details;
    private boolean isAnonymous;
    private Long userId;
    private String userName;

    public PrayerRequestDTO() {}

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
