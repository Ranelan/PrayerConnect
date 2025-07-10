package za.co.PrayerConnect.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.PrayerConnect.domain.RegularUser;

import static org.junit.jupiter.api.Assertions.*;

class RegularUserFactoryTest {

    private String fullName;
    private String email;
    private String password;
    private int age;
    private boolean isBlocked;

    private RegularUserFactory regularUserFactory;
    private RegularUser regularUser;

    @BeforeEach
    void setUp() {
        fullName = "Elinah Ranelani";
        email = "elinahexample@gmail.com";
        password = "password123!";
        age = 25;
        isBlocked = false;


    }

    @Test
    void createRegularUser() {
        regularUser = new RegularUserFactory().createRegularUser(fullName, email, password, age, isBlocked);

        assertNotNull(regularUser);
        assertEquals(fullName, regularUser.getFullName());
        assertEquals(email, regularUser.getEmail());
        assertEquals(password, regularUser.getPassword());
        assertEquals(age, regularUser.getAge());
        assertFalse(regularUser.isBlocked());

    }

    @Test
    void TestCreateRegularUserWithInvalidEmail() {
        regularUser = new RegularUserFactory().createRegularUser(fullName, "", password, age, isBlocked);

        assertNull(regularUser);
    }

    @Test
    void TestCreateRegularUserWithInvalidPassword() {
        regularUser = new RegularUserFactory().createRegularUser(fullName, email, "", age, isBlocked);

        assertNull(regularUser);
    }

    @Test
    void TestCreateRegularUserWithInvalidAge() {
        regularUser = new RegularUserFactory().createRegularUser(fullName, email, password, -5, isBlocked);

        assertNull(regularUser);
    }

    @Test
    void TestCreateRegularUserWithNullFullName() {
        regularUser = new RegularUserFactory().createRegularUser(null, email, password, age, isBlocked);

        assertNull(regularUser);
    }

}