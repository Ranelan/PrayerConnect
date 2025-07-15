package za.co.PrayerConnect.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import za.co.PrayerConnect.domain.PrayerRequest;
import za.co.PrayerConnect.domain.User;
import za.co.PrayerConnect.dto.PrayerRequestDTO;
import za.co.PrayerConnect.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PrayerRequestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private String baseUrl() {
        return "http://localhost:" + port + "/api/prayerRequest";
    }

    private static PrayerRequest createdPrayerRequest;

    @Test
    @Order(1)
    void createPrayerRequest_anonymous() {
        PrayerRequestDTO dto = new PrayerRequestDTO();
        dto.setTitle("Test Prayer");
        dto.setDetails("Please pray for testing");
        dto.setAnonymous(true);
        dto.setUserId(null);

        ResponseEntity<PrayerRequest> response = restTemplate.postForEntity(baseUrl() + "/create", dto, PrayerRequest.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Prayer", response.getBody().getTitle());
        createdPrayerRequest = response.getBody();
    }

    @Test
    @Order(2)
    void createPrayerRequest_withUser() {
        // Create or get a user for the test
        User user = userRepository.findAll().stream().findFirst().orElse(null);
        if (user == null) {
            fail("No user available in repository for test");
        }

        PrayerRequestDTO dto = new PrayerRequestDTO();
        dto.setTitle("User Prayer");
        dto.setDetails("Please pray for user");
        dto.setAnonymous(false);
        dto.setUserId(user.getId());

        ResponseEntity<PrayerRequest> response = restTemplate.postForEntity(baseUrl() + "/create", dto, PrayerRequest.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getUser());
        assertEquals(user.getId(), response.getBody().getUser().getId());
    }

    @Test
    @Order(3)
    void findById_found() {
        ResponseEntity<PrayerRequest> response = restTemplate.getForEntity(baseUrl() + "/findById/" + createdPrayerRequest.getContentId(), PrayerRequest.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdPrayerRequest.getContentId(), response.getBody().getContentId());
    }

    @Test
    @Order(4)
    void updatePrayerRequest_success() {

        PrayerRequest updatedRequest = new PrayerRequest.PrayerRequestBuilder()
                .setContentId(createdPrayerRequest.getContentId())
                .setTitle(createdPrayerRequest.getTitle())
                .setMessage("Updated message")
                .setAnonymous(createdPrayerRequest.isAnonymous())
                .setUser(createdPrayerRequest.getUser())
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PrayerRequest> request = new HttpEntity<>(updatedRequest, headers);

        ResponseEntity<PrayerRequest> response = restTemplate.exchange(
                baseUrl() + "/update/" + createdPrayerRequest.getContentId(),
                HttpMethod.PUT, request, PrayerRequest.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated message", response.getBody().getMessage());

        // Update the static ref for further tests
        createdPrayerRequest = response.getBody();
    }

    @Test
    @Order(5)
    void updatePrayerRequest_badRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create a request entity with a mismatched ID using the builder
        PrayerRequest mismatched = new PrayerRequest.PrayerRequestBuilder()
                .setContentId(999L)  // Different from createdPrayerRequest.getContentId()
                .setTitle("Wrong Update")
                .setMessage("This should fail")
                .setAnonymous(true)
                .setUser(null)
                .build();

        HttpEntity<PrayerRequest> request = new HttpEntity<>(mismatched, headers);

        // Send update to endpoint with different ID in path
        ResponseEntity<PrayerRequest> response = restTemplate.exchange(
                baseUrl() + "/update/" + createdPrayerRequest.getContentId(), // This is not 999L
                HttpMethod.PUT, request, PrayerRequest.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    @Order(6)
    void findAllPrayerRequests() {
        ResponseEntity<List> response = restTemplate.getForEntity(baseUrl() + "/all", List.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    @Order(7)
    void findByTitle_found() {
        ResponseEntity<List> response = restTemplate.getForEntity(baseUrl() + "/findByTitle/Test Prayer", List.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    @Order(8)
    void findByTitle_notFound() {
        ResponseEntity<List> response = restTemplate.getForEntity(baseUrl() + "/findByTitle/NonExistentTitle", List.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(9)
    void findByContentId_found() {
        ResponseEntity<PrayerRequest> response = restTemplate.getForEntity(baseUrl() + "/findByContentId/" + createdPrayerRequest.getContentId(), PrayerRequest.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdPrayerRequest.getContentId(), response.getBody().getContentId());
    }

    @Test
    @Order(10)
    void findByContentId_notFound() {
        ResponseEntity<PrayerRequest> response = restTemplate.getForEntity(baseUrl() + "/findByContentId/9999999", PrayerRequest.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(11)
    void deletePrayerRequest_success() {
        ResponseEntity<Void> response = restTemplate.exchange(baseUrl() + "/delete/" + createdPrayerRequest.getContentId(), HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
