package za.co.PrayerConnect.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.PrayerConnect.domain.PrayerInteraction;
import za.co.PrayerConnect.dto.PrayerIneractionDto;
import za.co.PrayerConnect.service.PrayerInteractionServ.PrayerInteractionService;

import java.util.List;

@RestController
@RequestMapping("/api/prayerInteraction")
public class PrayerInteractionController {

    @Autowired
    private PrayerInteractionService prayerInteractionService;


    @PostMapping("/create")
    public ResponseEntity<PrayerInteraction> create(@RequestBody PrayerInteraction prayerInteraction) {
     try{
         PrayerInteraction saved = prayerInteractionService.save(prayerInteraction);
         return ResponseEntity.ok(saved);
     } catch (Exception e) {
         e.printStackTrace(); // Log the error for debugging
         return ResponseEntity.status(500).body(null);
     }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<PrayerInteraction> findById(@PathVariable Long id) {
        try {
            PrayerInteraction prayerInteraction = prayerInteractionService.findById(id);
            if (prayerInteraction != null) {
                return ResponseEntity.ok(prayerInteraction);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/findByUser/{userId}")
    public ResponseEntity<List<PrayerInteraction>> getByUserId(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(prayerInteractionService.findByUser_Id(userId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/findByPrayerRequest/{prayerRequestId}")
    public ResponseEntity<List<PrayerInteraction>> getByContentId(@PathVariable Long contentId) {
        try {
            return ResponseEntity.ok(prayerInteractionService.findByPrayerRequest_ContentId(contentId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }


    @GetMapping("/HasPrayed")
    public ResponseEntity<Boolean> hasUserPrayed(
            @RequestParam Long userId,
            @RequestParam Long contentId) {
        try {
            boolean exists = prayerInteractionService.hasUserPrayedForContent(userId, contentId);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<PrayerInteraction>> getAll() {
        return ResponseEntity.ok(prayerInteractionService.findAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            prayerInteractionService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }




}
