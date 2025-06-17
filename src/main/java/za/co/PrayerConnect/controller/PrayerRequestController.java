package za.co.PrayerConnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.PrayerConnect.domain.PrayerRequest;
import za.co.PrayerConnect.service.PrayerRequestServ.PrayerRequestService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prayerRequest")
public class PrayerRequestController {

    @Autowired
    private PrayerRequestService prayerRequestService;


    @PostMapping("/create")
    public ResponseEntity<PrayerRequest> create(@RequestBody PrayerRequest entity) {
        try{
            PrayerRequest prayerRequest = prayerRequestService.save(entity);
            return ResponseEntity.ok(prayerRequest);
        }
        catch(Exception e){
            e.printStackTrace(); // Logs full error in server logs
            return ResponseEntity.internalServerError().body(null); // Return null or appropriate error response
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
    public ResponseEntity<PrayerRequest> findByTitle(@PathVariable String title) {
        try {
            return prayerRequestService.findByTitle(title)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
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
