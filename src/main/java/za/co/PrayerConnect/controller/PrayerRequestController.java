package za.co.PrayerConnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.PrayerConnect.domain.PrayerRequest;
import za.co.PrayerConnect.domain.User;
import za.co.PrayerConnect.dto.PrayerRequestDTO;
import za.co.PrayerConnect.repository.UserRepository;
import za.co.PrayerConnect.service.PrayerRequestServ.PrayerRequestService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prayerRequest")
public class PrayerRequestController {

    @Autowired
    private PrayerRequestService prayerRequestService;
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/create")
    public ResponseEntity<PrayerRequest> create(@RequestBody PrayerRequestDTO dto) {
        try {
            User user = null;
            if (!dto.isAnonymous() && dto.getUserId() != null) {
                user = userRepository.findById(dto.getUserId()).orElse(null);
            }

            PrayerRequest request = new PrayerRequest.PrayerRequestBuilder()
                    .setTitle(dto.getTitle())
                    .setMessage(dto.getDetails())
                    .setAnonymous(dto.isAnonymous())
                    .setUser(user)
                    .build();

            PrayerRequest saved = prayerRequestService.save(request);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<PrayerRequest> findById(@PathVariable Long id) {
        try {
            PrayerRequest prayerRequest = prayerRequestService.findById(id);
            if (prayerRequest != null) {
                return ResponseEntity.ok(prayerRequest);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<PrayerRequest> update(@PathVariable Long id, @RequestBody PrayerRequest entity) {
        if (!id.equals(entity.getContentId())) {
            return ResponseEntity.badRequest().build(); // IDs must match
        }
        try {
            PrayerRequest updatedPrayerRequest = prayerRequestService.update(entity);
            return ResponseEntity.ok(updatedPrayerRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null); // Return null or appropriate error response
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        try {
            prayerRequestService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace(); // Logs full error in server logs
            return ResponseEntity.internalServerError().build(); // Return appropriate error response
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<PrayerRequest>> findAll() {
        try {
            List<PrayerRequest> prayerRequests = prayerRequestService.findAll();
            if (prayerRequests.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(prayerRequests);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping("/findByTitle/{title}")
    public ResponseEntity<List<PrayerRequest>> findByTitle(@PathVariable String title) {
        try {
            List<PrayerRequest> found = prayerRequestService.findByTitle(title);
            if (found.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(found);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }



    @GetMapping("/findByContentId/{contentId}")
    public ResponseEntity<PrayerRequest> findByContentId(@PathVariable Long contentId) {
        try {
            return prayerRequestService.findByContentId(contentId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }


}
