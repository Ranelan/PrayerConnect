package za.co.PrayerConnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.PrayerConnect.domain.RegularUser;
import za.co.PrayerConnect.service.RegularUserService;

@RestController
@RequestMapping("/api/regular-users")
public class RegularUserController {

    @Autowired
    private RegularUserService regularUserService;

    @PostMapping("/create")
    public ResponseEntity<RegularUser> create(@RequestBody RegularUser regularUser) {
         RegularUser saved = regularUserService.save(regularUser);
        return ResponseEntity.ok(saved);
    }

}
