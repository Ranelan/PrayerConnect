package za.co.PrayerConnect.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.PrayerConnect.domain.Admin;
import za.co.PrayerConnect.domain.Permissions;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class AdminFactoryTest {
    private String fullName;
    private String email;
    private String password;
    private int age;
    private String adminCode;
    private LocalDateTime lastLogin;
    private  List<Permissions> permissions;


    private AdminFactory adminFactory;
    private Admin admin;

    @BeforeEach
    void setUp() {
        fullName = "Engel Raney";
        email = "engelexample@gmail.com";
        password = "password123!";
        age = 30;
        adminCode = "ADMIN123";
        lastLogin = LocalDateTime.now();
        permissions = List.of(Permissions.BLOCK_USER, Permissions.UNBLOCK_USER, Permissions.VIEW_USER,Permissions.DELETE_USER, Permissions.APPROVE_CONTENT, Permissions.DELETE_CONTENT);
    }

    @Test
    void createAdmin() {
        admin = AdminFactory.createAdmin(fullName, email, password, age, adminCode, lastLogin, permissions);

        assertNotNull(admin);
        assertEquals(fullName, admin.getFullName());
        assertEquals(email, admin.getEmail());
        assertEquals(password, admin.getPassword());
        assertEquals(age, admin.getAge());
        assertEquals(adminCode, admin.getAdminCode());
        assertEquals(lastLogin, admin.getLastLogin());
        assertEquals(permissions, admin.getPermissions());
    }

    @Test
    void createAdminWithInvalidEmail() {
        admin = AdminFactory.createAdmin(fullName, "", password, age, adminCode, lastLogin, permissions);

        assertNull(admin);
    }

    @Test
    void createAdminWithInvalidPassword() {
        admin = AdminFactory.createAdmin(fullName, email, "", age, adminCode, lastLogin, permissions);

        assertNull(admin);
    }

    @Test
    void createAdminWithInvalidAge() {
        admin = AdminFactory.createAdmin(fullName, email, password, -1, adminCode, lastLogin, permissions);

        assertNull(admin);
    }

    @Test
    void createAdminWithInvalidAdminCode() {
        admin = AdminFactory.createAdmin(fullName, email, password, age, "", lastLogin, permissions);

        assertNull(admin);
    }

    @Test
    void createAdminWithInvalidLastLogin() {
        admin = AdminFactory.createAdmin(fullName, email, password, age, adminCode, null, permissions);

        assertNull(admin);
    }

    @Test
    void createAdminWithEmptyPermissions() {
        admin = AdminFactory.createAdmin(fullName, email, password, age, adminCode, lastLogin, List.of());

        assertNull(admin);
    }


}