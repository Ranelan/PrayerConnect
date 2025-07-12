package za.co.PrayerConnect.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("REGULAR_USER")
public class RegularUser extends User {

    private boolean isBlocked = false;

    public RegularUser() {
        super();
    }

    public RegularUser(Long id, String fullName, String email, String password, int age, boolean isBlocked) {
        super(id, fullName, email, password, age);
        this.isBlocked = isBlocked;
    }

    public RegularUser(RegularUserBuilder builder) {
        this.id = builder.id;
        this.fullName = builder.fullName;
        this.email = builder.email;
        this.password = builder.password;
        this.age = builder.age;
        this.isBlocked = builder.isBlocked;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(boolean blocked) {
        this.isBlocked = blocked;
    }

    @Override
    public String toString() {
        return "RegularUser{" +
                "isBlocked=" + isBlocked +
                ", id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                '}';
    }

    public static class RegularUserBuilder {
        private Long id;
        private String fullName;
        private String email;
        private String password;
        private int age;
        private boolean isBlocked;

        public RegularUserBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public RegularUserBuilder setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public RegularUserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public RegularUserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public RegularUserBuilder setAge(int age) {
            this.age = age;
            return this;
        }

        public RegularUserBuilder setIsBlocked(boolean isBlocked) {
            this.isBlocked = isBlocked;
            return this;
        }

        public RegularUserBuilder copy(RegularUser user) {
            this.id = user.id;
            this.fullName = user.fullName;
            this.email = user.email;
            this.password = user.password;
            this.age = user.age;
            this.isBlocked = user.isBlocked;
            return this;
        }

        public RegularUser build() {
            return new RegularUser(this);
        }
    }
}
