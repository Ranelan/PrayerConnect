package za.co.PrayerConnect.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import za.co.PrayerConnect.domain.Admin;
import za.co.PrayerConnect.domain.Permissions;
import za.co.PrayerConnect.domain.RegularUser;
import za.co.PrayerConnect.dto.AdminDto;
import za.co.PrayerConnect.dto.PrayerRequestDTO;
import za.co.PrayerConnect.factory.AdminFactory;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static AdminDto createdAdminDto;
    private static String jwtToken;
    private static Long contentId;
    private static Long userId;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/admins";
    }

    @Test
    @Order(1)
    void createAdmin() {
        AdminDto adminDto = new AdminDto();
        adminDto.setFullName("Engel Raney");
        adminDto.setEmail("admin_" + System.currentTimeMillis() + "@example.com");
        adminDto.setAge(30);
        adminDto.setPermissions(List.of());


        ResponseEntity<AdminDto> response = restTemplate.postForEntity(getBaseUrl() + "/create", adminDto, AdminDto.class);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        createdAdminDto = response.getBody();
    }

    @Test
    @Order(2)
    void authenticate() {
        Admin login = new Admin();
        login.setEmail(createdAdminDto.getEmail());
        login.setPassword("defaultPassword123");

        ResponseEntity<String> response = restTemplate.postForEntity(getBaseUrl() + "/authenticate", login, String.class);
        assertEquals(200, response.getStatusCodeValue());
        jwtToken = response.getBody();
        assertNotNull(jwtToken);
    }

    @Test
    @Order(3)
    void updateAdmin() {
        createdAdminDto.setFullName("Updated Admin");
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AdminDto> request = new HttpEntity<>(createdAdminDto, headers);

        ResponseEntity<AdminDto> response = restTemplate.exchange(getBaseUrl() + "/update/" + createdAdminDto.getId(), HttpMethod.PUT, request, AdminDto.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated Admin", response.getBody().getFullName());
    }

    @Test
    @Order(4)
    void getAllUsersByFullName() {
        ResponseEntity<String> response = restTemplate.getForEntity(getBaseUrl() + "/getAllByFullName/Updated Admin", String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is4xxClientError());
    }

    @Test
    @Order(5)
    void getAllUsersByAge() {
        ResponseEntity<String> response = restTemplate.getForEntity(getBaseUrl() + "/getAllByAge/30", String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is4xxClientError());
    }

    @Test
    @Order(6)
    void blockUnblockAndDeleteUser() {
            // Create a RegularUser to test block/unblock/delete
            RegularUser user = new RegularUser.RegularUserBuilder()
                    .setEmail("testuser_" + System.currentTimeMillis() + "@example.com")
                    .setFullName("Test User")
                    .setAge(25)
                    .setPassword("test123")
                    .build();

            // Set headers with JWT token for authentication
            HttpHeaders userHeaders = new HttpHeaders();
            userHeaders.setBearerAuth(jwtToken);  // Add Bearer token
            userHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<RegularUser> userRequest = new HttpEntity<>(user, userHeaders);

            // Create user with authenticated request
            ResponseEntity<RegularUser> userResponse = restTemplate.postForEntity(
                    "http://localhost:" + port + "/api/regular-users/create",
                    userRequest,
                    RegularUser.class
            );
            assertEquals(200, userResponse.getStatusCodeValue());
            RegularUser createdUser = userResponse.getBody();
            userId = createdUser.getId();

            // Prepare headers with JWT token for subsequent requests
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(jwtToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            // Block user
            ResponseEntity<String> block = restTemplate.exchange(
                    getBaseUrl() + "/blockUser/" + userId,
                    HttpMethod.PUT,
                    entity,
                    String.class
            );
            assertEquals(200, block.getStatusCodeValue());

            // Unblock user
            ResponseEntity<String> unblock = restTemplate.exchange(
                    getBaseUrl() + "/unblockUser/" + userId,
                    HttpMethod.PUT,
                    entity,
                    String.class
            );
            assertEquals(200, unblock.getStatusCodeValue());

            // Delete user
            ResponseEntity<String> delete = restTemplate.exchange(
                    getBaseUrl() + "/deleteUser/" + userId,
                    HttpMethod.DELETE,
                    entity,
                    String.class
            );
            assertEquals(200, delete.getStatusCodeValue());

    }

    @Test
    @Order(7)
    void contentModeration() {
        // Create a content to moderate
        PrayerRequestDTO dto = new PrayerRequestDTO();
        dto.setTitle("Pray for Healing");
        dto.setDetails("Please pray for me");
        dto.setAnonymous(true);

        ResponseEntity<Map> postResponse = restTemplate.postForEntity("http://localhost:" + port + "/api/prayerRequest/create", dto, Map.class);
        assertEquals(200, postResponse.getStatusCodeValue());
        contentId = Long.valueOf(postResponse.getBody().get("contentId").toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> approve = restTemplate.exchange(getBaseUrl() + "/approve/" + contentId, HttpMethod.PUT, entity, String.class);
        assertEquals(200, approve.getStatusCodeValue());

        ResponseEntity<String> reject = restTemplate.exchange(getBaseUrl() + "/reject/" + contentId, HttpMethod.PUT, entity, String.class);
        assertEquals(200, reject.getStatusCodeValue());

        ResponseEntity<String> delete = restTemplate.exchange(getBaseUrl() + "/deleteContent/" + contentId, HttpMethod.PUT, entity, String.class);
        assertEquals(200, delete.getStatusCodeValue());
    }

    @Test
    @Order(8)
    void deleteAdmin() {
        ResponseEntity<String> response = restTemplate.exchange(getBaseUrl() + "/delete/" + createdAdminDto.getId(), HttpMethod.DELETE, null, String.class);
        assertEquals(200, response.getStatusCodeValue());
    }
}
