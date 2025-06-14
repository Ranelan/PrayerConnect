package za.co.PrayerConnect.domain;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String fullName;
    protected String email;
    protected String password;
    protected int age;


    public User() {
    }

    public User(Long id, String fullName, String email, String password, int age) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.age = age;
    }

    public User(UserBuilder userBuilder) {
        this.id = userBuilder.id;
        this.fullName = userBuilder.fullName;
        this.email = userBuilder.email;
        this.password = userBuilder.password;
        this.age = userBuilder.age;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                '}';
    }

    private static class UserBuilder{
        private Long id;
        private String fullName;
        private String email;
        private String password;
        private int age;

        public UserBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setAge(int age) {
            this.age = age;
            return this;
        }

        public UserBuilder copy(User user) {
            this.id = user.getId();
            this.fullName = user.getFullName();
            this.email = user.getEmail();
            this.password = user.getPassword();
            this.age = user.getAge();
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
