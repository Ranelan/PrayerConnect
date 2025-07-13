package za.co.PrayerConnect.service.RegularUserServ;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.PrayerConnect.domain.RegularUser;
import za.co.PrayerConnect.factory.RegularUserFactory;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RegularUserServiceTest {

    @Autowired
    private RegularUserService regularUserService;
    private RegularUser user;


//    private String generateRandomEmail(String prefix) {
//        return prefix + "_" + System.currentTimeMillis() + "@example.com";
//    }

    @BeforeEach
    void setUp() {
        user = new RegularUser.RegularUserBuilder()
                .setFullName("Elinah Ranelani")
                .setEmail("Elinah" + UUID.randomUUID() + "@example.com")
                .setPassword("password123!")
                .setAge(30)
                .setIsBlocked(false)
                .build();
        user = regularUserService.save(user);

    }


    @Test
    @Order(1)
    void save() {
       assertNotNull(user);
       assertNotNull(user.getId());
       assertEquals("Elinah Ranelani", user.getFullName());
         System.out.println("User saved: " + user);
    }

    @Test
    @Order(2)
    void authenticate() {
        RegularUser authenticated = regularUserService.authenticate(user.getEmail(), "password123!");
        assertNotNull(authenticated);
        assertEquals(user.getEmail(), authenticated.getEmail());
        System.out.println("User authenticated: " + authenticated);
    }

    @Test
    @Order(3)
    void findById() {
        RegularUser found = regularUserService.findById(String.valueOf(user.getId()));
        assertNotNull(found);
        assertEquals(user.getEmail(), found.getEmail());
    }

    @Test
    @Order(4)
    void update() {
        RegularUser updatedUser = new RegularUser.RegularUserBuilder()
                .copy(user)
                .setFullName("Elinah Ranelani")
                .setAge(28)
                .build();

        RegularUser result = regularUserService.update(updatedUser);
        assertNotNull(result);
        assertEquals("Elinah Ranelani", result.getFullName());
        assertEquals(28, result.getAge());

    }


    @Test
    @Order(5)
    void findByEmail() {
        RegularUser found = regularUserService.findByEmail(user.getEmail());
        assertNotNull(found);
        assertEquals(user.getEmail(), found.getEmail());
        System.out.println("User found by email: " + found);
    }

    @Test
    @Order(6)
    void findByFullName() {
        List<RegularUser> found = regularUserService.findByFullName(user.getFullName());
        assertFalse(found.isEmpty());
        assertTrue(found.stream().anyMatch(u -> u.getEmail().equals(user.getEmail())));
        System.out.println("Found by full name: " + found);
    }


    @Test
    @Order(7)
    void findByAge() {
        List<RegularUser> foundUsers = regularUserService.findByAge(user.getAge());

        assertNotNull(foundUsers);
        assertFalse(foundUsers.isEmpty());
        System.out.println("User found by age: " + foundUsers);
    }

    @Test
    @Order(8)
    void findAll() {
        var users = regularUserService.findAll();
        assertFalse(users.isEmpty());
        assertTrue(users.stream().anyMatch(u -> u.getId().equals(user.getId())));
        System.out.println("All users found: " + users);
    }

    @Test
    @Order(9)
    void deleteById() {
        regularUserService.deleteById(String.valueOf(user.getId()));
        RegularUser found = regularUserService.findById(String.valueOf(user.getId()));
        assertNull(found);
        System.out.println("User deleted: " + user.getId());
    }
}