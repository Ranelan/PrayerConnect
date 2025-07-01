package za.co.PrayerConnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.PrayerConnect.domain.Testimony;
import za.co.PrayerConnect.repository.TestimonyRepository;
import za.co.PrayerConnect.service.TestimonyServ.TestimonyService;

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
    public void update() {


    }
}
