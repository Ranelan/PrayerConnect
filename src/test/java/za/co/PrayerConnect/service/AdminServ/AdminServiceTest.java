package za.co.PrayerConnect.service.AdminServ;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.PrayerConnect.domain.Admin;
import za.co.PrayerConnect.domain.Permissions;
import za.co.PrayerConnect.factory.AdminFactory;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.class)
class AdminServiceTest {

    @Autowired
    private AdminService adminService;
    private Admin admin = new AdminFactory().createAdmin("Engel Raney", "engelexample@gmail.com", "password123!", 30, "ADMIN123", LocalDateTime.now(), List.of(Permissions.BLOCK_USER, Permissions.UNBLOCK_USER, Permissions.VIEW_USER,Permissions.DELETE_USER, Permissions.APPROVE_CONTENT, Permissions.DELETE_CONTENT));


    @Test
    void a_save() {
    admin = adminService.save(admin);
    assertNotNull(admin);
        System.out.println("Admin saved: " + admin);
    }

    @Test
    void findById() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void blockUser() {
    }

    @Test
    void unblockUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void deleteContent() {
    }

    @Test
    void approveContent() {
    }

    @Test
    void rejectContent() {
    }

    @Test
    void getAllUsersByEmail() {
    }

    @Test
    void getAllUsersByAge() {
    }

    @Test
    void getAllUsersByFullName() {
    }

    @Test
    void authenticate() {
    }
}