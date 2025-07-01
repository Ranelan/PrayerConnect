package za.co.PrayerConnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.PrayerConnect.domain.Testimony;
import za.co.PrayerConnect.repository.TestimonyRepository;
import za.co.PrayerConnect.service.TestimonyServ.TestimonyService;

import java.util.List;

@RestController
@RequestMapping("api/testimonies")
public class TestimonyController {

    @Autowired
    private TestimonyService testimonyService;


    @PostMapping("/create")
    public ResponseEntity<Testimony> create(@RequestBody Testimony testimony) {
       try{
           Testimony savedTestimony = testimonyService.save(testimony);

           return ResponseEntity.ok(savedTestimony);
       } catch (Exception e) {
           return ResponseEntity.status(500).body(null); // Handle error appropriately
       }
    }

    @PostMapping("/update")
    public ResponseEntity<Testimony> update(@RequestBody Testimony testimony) {
        try{
            Testimony updatedTestimony = testimonyService.update(testimony);
            if (updatedTestimony != null) {
                return ResponseEntity.ok(updatedTestimony);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Handle error appropriately
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        try{
            testimonyService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.status(500).build(); // Handle error appropriately
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Testimony> findById(@PathVariable Long id) {
        try {
            Testimony testimony = testimonyService.findById(id);
            if (testimony != null) {
                return ResponseEntity.ok(testimony);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Handle error appropriately
        }
    }

    @GetMapping("/findByUserId/{userId}")
    public ResponseEntity<List<Testimony>> findByUserId(@PathVariable Long userId) {
        try {
            List<Testimony> testimonies = testimonyService.findByUserId(userId);
            return testimonies.isEmpty() ?
                    ResponseEntity.notFound().build() :
                    ResponseEntity.ok(testimonies);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/findByContentId/{contentId}")
    public ResponseEntity<List<Testimony>> findByContentId(@PathVariable Long contentId) {    try {
        List<Testimony> testimonies = testimonyService.findByPrayerRequest_ContentId(contentId);
        return testimonies.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(testimonies);
    } catch (Exception e) {
        return ResponseEntity.status(500).body(null);
    }
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.ok(testimonyService.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error retrieving testimonies");
        }
    }
}
