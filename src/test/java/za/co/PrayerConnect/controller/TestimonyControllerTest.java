package za.co.PrayerConnect.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import za.co.PrayerConnect.domain.Testimony;
import za.co.PrayerConnect.dto.TestimonyDto;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestimonyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static Long createdTestimonyId;

    private String baseUrl() {
        return "http://localhost:" + port + "/api/testimonies";
    }

    @Test
    @Order(1)
    void createTestimony() {
        TestimonyDto dto = new TestimonyDto();
        dto.setMessage("This is a powerful testimony");
        dto.setUserId(1L);

        ResponseEntity<TestimonyDto> response = restTemplate.postForEntity(baseUrl() + "/create", dto, TestimonyDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        createdTestimonyId = response.getBody().getTestimonyId();
    }

    @Test
    @Order(2)
    void updateTestimony() {
        TestimonyDto dto = new TestimonyDto();
        dto.setTestimonyId(createdTestimonyId);
        dto.setMessage("Updated testimony message");
        dto.setUserId(1L);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TestimonyDto> request = new HttpEntity<>(dto, headers);

        ResponseEntity<TestimonyDto> response = restTemplate.exchange(baseUrl() + "/update", HttpMethod.PUT, request, TestimonyDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated testimony message", response.getBody().getMessage());
    }

    @Test
    @Order(3)
    void getTestimonyById() {
        ResponseEntity<TestimonyDto> response = restTemplate.getForEntity(baseUrl() + "/findById/" + createdTestimonyId, TestimonyDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdTestimonyId, response.getBody().getTestimonyId());
    }

    @Test
    @Order(4)
    void getTestimoniesByUserId() {
        ResponseEntity<TestimonyDto[]> response = restTemplate.getForEntity(baseUrl() + "/findByUserId/1", TestimonyDto[].class);
        assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is4xxClientError());
    }

    @Test
    @Order(5)
    void getTestimoniesByContentId() {
        ResponseEntity<TestimonyDto[]> response = restTemplate.getForEntity(baseUrl() + "/findByContentId/1", TestimonyDto[].class);
        assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is4xxClientError());
    }

    @Test
    @Order(6)
    void getAllTestimonies() {
        ResponseEntity<TestimonyDto[]> response = restTemplate.getForEntity(baseUrl() + "/all", TestimonyDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(7)
    void deleteTestimony() {
        ResponseEntity<Void> response = restTemplate.exchange(baseUrl() + "/delete/" + createdTestimonyId, HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
