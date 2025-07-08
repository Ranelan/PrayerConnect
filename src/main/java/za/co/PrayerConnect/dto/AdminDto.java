package za.co.PrayerConnect.dto;

import java.time.LocalDateTime;
import java.util.List;

public class AdminDto {

    private Long id;
    private String fullName;
    private String email;
    private int age;
    private String adminCode;
    private LocalDateTime lastLogin;
    private List<String> permissions;

    public AdminDto() {
    }

    public AdminDto(Long id, String fullName, String email, int age, String adminCode,
                    LocalDateTime lastLogin, List<String> permissions) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.age = age;
        this.adminCode = adminCode;
        this.lastLogin = lastLogin;
        this.permissions = permissions;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAdminCode() {
        return adminCode;
    }

    public void setAdminCode(String adminCode) {
        this.adminCode = adminCode;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
