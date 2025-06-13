package za.co.PrayerConnect.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;
import java.util.List;

public class Admin extends User {

    private String adminCode;
    private LocalDateTime lastLogin;

    @ElementCollection(targetClass = Permissions.class)
    @Enumerated(EnumType.STRING)
    private List<Permissions> permissions;

    public Admin() {
        super();
    }

    public Admin(Long id, String fullName, String email, String password, int age,
                 String adminCode, LocalDateTime lastLogin, List<Permissions> permissions) {
        super(id, fullName, email, password, age);
        this.adminCode = adminCode;
        this.lastLogin = lastLogin;
        this.permissions = permissions;
    }

    public Admin(AdminBuilder builder) {
       this.id = builder.id;
        this.fullName = builder.fullName;
        this.email = builder.email;
        this.password = builder.password;
        this.age = builder.age;
        this.adminCode = builder.adminCode;
        this.lastLogin = builder.lastLogin;
        this.permissions = builder.permissions;
    }

    public String getAdminCode() {
        return adminCode;
    }

    public List<Permissions> getPermissions() {
        return permissions;
    }
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminCode='" + adminCode + '\'' +
                ", lastLogin=" + lastLogin +
                ", permissions=" + permissions +
                ", id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                '}';
    }

    public static class AdminBuilder {
        private Long id; // Optional: only set if updating
        private String fullName;
        private String email;
        private String password;
        private int age;
        private String adminCode;
        private LocalDateTime lastLogin;
        private List<Permissions> permissions;

        public AdminBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public AdminBuilder setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public AdminBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public AdminBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public AdminBuilder setAge(int age) {
            this.age = age;
            return this;
        }

        public AdminBuilder setAdminCode(String adminCode) {
            this.adminCode = adminCode;
            return this;
        }

        public AdminBuilder setLastLogin(LocalDateTime lastLogin) {
            this.lastLogin = lastLogin;
            return this;
        }

        public AdminBuilder setPermissions(List<Permissions> permissions) {
            this.permissions = permissions;
            return this;
        }

        public AdminBuilder copy(Admin admin) {
            this.id = admin.id;
            this.fullName = admin.fullName;
            this.email = admin.email;
            this.password = admin.password;
            this.age = admin.age;
            this.adminCode = admin.adminCode;
            this.lastLogin = admin.lastLogin;
            this.permissions = admin.permissions;
            return this;
        }

        public Admin build() {
            return new Admin(this);
        }
    }
}
