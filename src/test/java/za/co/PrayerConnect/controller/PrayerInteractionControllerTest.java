package za.co.PrayerConnect.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import za.co.PrayerConnect.domain.PrayerInteraction;
import za.co.PrayerConnect.domain.PrayerRequest;
import za.co.PrayerConnect.domain.RegularUser;
import za.co.PrayerConnect.dto.PrayerInteractionDto;
import za.co.PrayerConnect.repository.PrayerInteractionRepository;
import za.co.PrayerConnect.repository.PrayerRequestRepository;
import za.co.PrayerConnect.repository.RegularUserRepository;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PrayerInteractionControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RegularUserRepository regularUserRepository;

    @Autowired
    private PrayerRequestRepository prayerRequestRepository;

    @Autowired
    private PrayerInteractionRepository prayerInteractionRepository;

    private static Long interactionId;
    private static Long userId;
    private static Long contentId;

    private String baseUrl() {
        return "http://localhost:" + port + "/api/prayerInteraction";
    }

    @BeforeAll
    static void setup() {
        // You can do JWT setup here if required in future
    }

    @Test
    @Order(1)
    void createDependencies() {
        RegularUser user = new RegularUser.RegularUserBuilder()
                .setEmail("regularUser_" + System.currentTimeMillis() + "@example.com")
                .setFullName("Interaction User")
                .setPassword("pass123")
                .setAge(30)
                .build();
        user = regularUserRepository.save(user);
        userId = user.getId();

        PrayerRequest request = new PrayerRequest.PrayerRequestBuilder()
                .setTitle("Healing")
                .setMessage("Pray for healing")
                .setAnonymous(false)
                .setUser(user)
                .build();
        request = prayerRequestRepository.save(request);
        contentId = request.getContentId();
    }

    @Test
    @Order(2)
    void createPrayerInteraction() {
        PrayerInteractionDto dto = new PrayerInteractionDto();
        dto.setUserId(userId);
        dto.setPrayerRequestId(contentId);

        ResponseEntity<PrayerInteractionDto> response = restTemplate.postForEntity(
                "/api/prayerInteraction/create", dto, PrayerInteractionDto.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        interactionId = response.getBody().getId();
        assertNotNull(interactionId);
    }

    @Test
    @Order(3)
    void findById() {
        ResponseEntity<PrayerInteractionDto> response = restTemplate.getForEntity(baseUrl() + "/findById/" + interactionId, PrayerInteractionDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(interactionId, response.getBody().getId());
    }

    @Test
    @Order(4)
    void getByUserId() {
        ResponseEntity<PrayerInteractionDto[]> response = restTemplate.getForEntity(baseUrl() + "/findByUser/" + userId, PrayerInteractionDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    @Order(5)
    void getByPrayerRequestId() {
        ResponseEntity<PrayerInteractionDto[]> response = restTemplate.getForEntity(baseUrl() + "/findByPrayerRequest/" + contentId, PrayerInteractionDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    @Order(6)
    void hasUserPrayed() {
        ResponseEntity<Boolean> response = restTemplate.getForEntity(baseUrl() + "/hasPrayed?userId=" + userId + "&contentId=" + contentId, Boolean.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
    }

    @Test
    @Order(7)
    void getAll() {
        ResponseEntity<PrayerInteractionDto[]> response = restTemplate.getForEntity(baseUrl() + "/all", PrayerInteractionDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    @Order(8)
    void deleteById() {
        ResponseEntity<Void> response = restTemplate.exchange(baseUrl() + "/delete/" + interactionId, HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
