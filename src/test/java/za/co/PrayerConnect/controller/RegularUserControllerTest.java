package za.co.PrayerConnect.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import za.co.PrayerConnect.domain.RegularUser;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RegularUserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/regular-users";
    }

    private static RegularUser testUser;

    @Test
    @Order(1)
    void createUser() {
        RegularUser user = new RegularUser.RegularUserBuilder()
                .setEmail("testuser_" + System.currentTimeMillis() + "@example.com")
                .setFullName("Test User")
                .setAge(30)
                .setPassword("testpass123")
                .build();

        ResponseEntity<RegularUser> response = restTemplate.postForEntity(getBaseUrl() + "/create", user, RegularUser.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        testUser = response.getBody(); // Save for later tests
    }

    @Test
    @Order(2)
    void authenticateUser() {
        RegularUser login = new RegularUser();
        login.setEmail(testUser.getEmail());
        login.setPassword("testpass123");

        ResponseEntity<Map> response = restTemplate.postForEntity(getBaseUrl() + "/authenticate", login, Map.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("token"));
        assertTrue(response.getBody().containsKey("user"));
    }

    @Test
    @Order(3)
    void getByEmail() {
        ResponseEntity<RegularUser> response = restTemplate.getForEntity(getBaseUrl() + "/" + testUser.getEmail(), RegularUser.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUser.getEmail(), response.getBody().getEmail());
    }

    @Test
    @Order(4)
    void getByFullName() {
        ResponseEntity<List> response = restTemplate.getForEntity(getBaseUrl() + "/full-name/" + testUser.getFullName(), List.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    @Order(5)
    void getByAge() {
        ResponseEntity<List> response = restTemplate.getForEntity(getBaseUrl() + "/age/" + testUser.getAge(), List.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    @Order(6)
    void getById() {
        ResponseEntity<RegularUser> response = restTemplate.getForEntity(getBaseUrl() + "/findById/" + testUser.getId(), RegularUser.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUser.getId(), response.getBody().getId());
    }

    @Test
    @Order(7)
    void updateUser() {
        testUser.setFullName("Updated User Name");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RegularUser> request = new HttpEntity<>(testUser, headers);

        ResponseEntity<RegularUser> response = restTemplate.exchange(getBaseUrl() + "/update", HttpMethod.PUT, request, RegularUser.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated User Name", response.getBody().getFullName());
    }

    @Test
    @Order(8)
    void getAllUsers() {
        ResponseEntity<List> response = restTemplate.getForEntity(getBaseUrl() + "/all", List.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertFalse(response.getBody().isEmpty());
    }

}
