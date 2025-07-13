package za.co.PrayerConnect.service.AdminServ;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.PrayerConnect.domain.*;
import za.co.PrayerConnect.factory.AdminFactory;
import za.co.PrayerConnect.service.PrayerRequestServ.PrayerRequestService;
import za.co.PrayerConnect.service.RegularUserServ.RegularUserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminServiceTest {

    @Autowired
    private RegularUserService regularUserService;

    @Autowired
    private PrayerRequestService prayerRequestService;

    @Autowired
    private AdminService adminService;
    private Admin admin;

    @BeforeAll
    void setup(@Autowired AdminService adminService) {
        String randomEmail = generateRandomEmail("admin");
        admin = AdminFactory.createAdmin(
                "Engel Raney",
                randomEmail,
                "password123!",
                30,
                "ADMIN123",
                LocalDateTime.now(),
                List.of(Permissions.BLOCK_USER, Permissions.UNBLOCK_USER, Permissions.VIEW_USER)
        );
        admin = adminService.save(admin);
    }

    private String generateRandomEmail(String prefix) {
        return prefix + "_" + System.currentTimeMillis() + "@example.com";
    }

    @Test
    @Order(1)
    void save() {
    admin = adminService.save(admin);
    assertNotNull(admin);
        System.out.println("Admin saved: " + admin);
    }

    @Test
    @Order(4)
    void findById() {
        Admin foundAdmin = adminService.findById(admin.getId());
        assertNotNull(foundAdmin);
        assertEquals(admin.getId(), foundAdmin.getId());
        System.out.println("Admin found: " + foundAdmin);
    }

    @Test
    @Order(3)
    void update() {
        Admin saved = adminService.save(admin);
        Admin updated = new Admin.AdminBuilder().copy(saved)
                .setFullName("Updated Name")
                .build();
        adminService.save(updated);
        Admin foundAdmin = adminService.findById(saved.getId());
        assertNotNull(foundAdmin);
        assertEquals("Updated Name", foundAdmin.getFullName());
        System.out.println("Admin updated: " + foundAdmin);
    }

//    @Test
//    @Order(14)
//    void deleteById() {
//        Admin saved = adminService.save(admin);
//
//        // Delete all requests related to this admin
//        List<PrayerRequest> relatedRequests = prayerRequestService.findAll().stream()
//                .filter(p -> p.getAdmin() != null && p.getAdmin().getId().equals(saved.getId()))
//                .toList();
//        relatedRequests.forEach(req -> prayerRequestService.deleteById(req.getContentId()));
//
//        adminService.deleteById(saved.getId());
//        Admin foundAdmin = adminService.findById(saved.getId());
//        assertNull(foundAdmin);
//        System.out.println("Admin deleted: " + saved.getId());
//    }

    @Test
    @Order(5)
    void blockUser() {
        RegularUser user = new RegularUser();
        user.setFullName("Test User");
        user.setEmail(generateRandomEmail("testuser"));
        user.setPassword("password123!");
        user.setAge(25);
        RegularUser savedUser = regularUserService.save(user);

        adminService.blockUser(savedUser.getId());

        RegularUser blockedUser = regularUserService.findById(String.valueOf(savedUser.getId()));
        assertTrue(blockedUser.getIsBlocked());
        System.out.println("User blocked: " + blockedUser.getEmail());
    }


    @Test
    @Order(6)
    void unblockUser() {
        RegularUser user = new RegularUser();
        user.setFullName("Test User");
        user.setEmail(generateRandomEmail("testuser"));
        user.setPassword("password123!");
        user.setAge(25);
        RegularUser savedUser = regularUserService.save(user);

        adminService.unblockUser(savedUser.getId());

        RegularUser unblockUser = regularUserService.findById(String.valueOf(savedUser.getId()));
        assertFalse(unblockUser.getIsBlocked());
        System.out.println("User unblocked: " + unblockUser.getEmail());
    }

    @Test
    @Order(7)
    void deleteUser() {
        RegularUser user = new RegularUser();
        user.setFullName("Test User");
        user.setEmail(generateRandomEmail("testuser"));
        user.setPassword("password123!");
        user.setAge(25);
        RegularUser savedUser = regularUserService.save(user);

        adminService.deleteUser(savedUser.getId());
        RegularUser deletedUser = regularUserService.findById(String.valueOf(savedUser.getId()));
        assertNull(deletedUser);
        System.out.println("User deleted: " + savedUser.getEmail());
    }

    @Test
    @Order(8)
    void deleteContent() {
        RegularUser user = new RegularUser();
        user.setFullName("Delete User");
        user.setEmail(generateRandomEmail("testuser"));
        user.setPassword("password123!");
        user.setAge(28);
        user = regularUserService.save(user);

        PrayerRequest request = new PrayerRequest.PrayerRequestBuilder()
                .setTitle("Delete me")
                .setMessage("Temporary prayer request")
                .setDateSubmitted(LocalDateTime.now())
                .setApprovalStatuses(List.of(ApprovalStatus.PENDING))
                .setUser(user)
                .build();

        PrayerRequest saved = prayerRequestService.save(request);
        adminService.deleteContent(saved.getContentId());

        Optional<PrayerRequest> deleted = prayerRequestService.findByContentId(saved.getContentId());
        assertTrue(deleted.isEmpty());
    }

    @Test
    @Order(9)
    void approveContent() {
        RegularUser user = new RegularUser();
        user.setFullName("Peace User");
        user.setEmail(generateRandomEmail("peaceuser"));
        user.setPassword("password123!");
        user.setAge(22);
        user = regularUserService.save(user);

        PrayerRequest request = new PrayerRequest.PrayerRequestBuilder()
                .setTitle("Peace")
                .setMessage("Pray for peace in our country")
                .setDateSubmitted(LocalDateTime.now())
                .setApprovalStatuses(List.of(ApprovalStatus.PENDING))
                .setUser(user)
                .build();

        PrayerRequest saved = prayerRequestService.save(request);
        adminService.approveContent(saved.getContentId());

        Optional<PrayerRequest> approved = prayerRequestService.findByContentId(saved.getContentId());
        assertTrue(approved.isPresent());
        assertEquals(List.of(ApprovalStatus.APPROVED), approved.get().getApprovalStatuses());
        System.out.println("Content approved: " + saved.getContentId());
    }

    @Test
    @Order(10)
    void rejectContent() {
        PrayerRequest request = new PrayerRequest.PrayerRequestBuilder()
                .setTitle("Spam")
                .setMessage("Buy bitcoin now!")
                .setDateSubmitted(LocalDateTime.now())
                .setApprovalStatuses(List.of())
                .setUser(admin)
                .build();

        PrayerRequest saved = prayerRequestService.save(request);
        adminService.rejectContent(saved.getContentId());

        Optional<PrayerRequest> rejected = prayerRequestService.findByContentId(saved.getContentId());
        assertEquals(List.of(ApprovalStatus.REJECTED), rejected.get().getApprovalStatuses());
        assertNotNull(rejected.get().getReviewComment());
        System.out.println("Content rejected: " + saved.getContentId());

    }

    @Test
    @Order(11)
    void getAllUsersByEmail() {
        List<RegularUser> users = regularUserService.findAll();
        assertFalse(users.isEmpty());
        System.out.println("All users: " + users);
    }

    @Test
    @Order(12)
    void getAllUsersByAge() {
        List<RegularUser> users = regularUserService.findAll();
        assertFalse(users.isEmpty());
        System.out.println("All users: " + users);
    }

    @Test
    @Order(13)
    void getAllUsersByFullName() {
        List<RegularUser> users = regularUserService.findAll();
        assertFalse(users.isEmpty());
        System.out.println("All users: " + users);
    }

    @Test
    @Order(2)
    void authenticate() {
        String uniqueEmail = "authadmin_" + System.currentTimeMillis() + "@example.com";
        Admin newAdmin = new AdminFactory().createAdmin(
                "Auth Admin",
                uniqueEmail,
                "Pass1234!",
                35,
                "CODE99",
                LocalDateTime.now(),
                List.of(Permissions.VIEW_USER)
        );
        Admin saved = adminService.save(newAdmin);

        Admin result = adminService.authenticate(uniqueEmail, "Pass1234!");
        assertNotNull(result);
        assertEquals(uniqueEmail, result.getEmail());
    }
}