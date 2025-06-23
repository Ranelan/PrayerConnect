package za.co.PrayerConnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.PrayerConnect.domain.RegularUser;
import za.co.PrayerConnect.service.RegularUserServ.RegularUserService;
import za.co.PrayerConnect.util.JwtUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/regular-users")
public class RegularUserController {

    @Autowired
    private RegularUserService regularUserService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/create")
    public ResponseEntity<RegularUser> create(@RequestBody RegularUser regularUser) {
         RegularUser saved = regularUserService.save(regularUser);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody RegularUser regularUser) {
        RegularUser authenticatedUser = regularUserService.authenticate(regularUser.getEmail(), regularUser.getPassword());

        if (authenticatedUser != null) {
            String token = jwtUtil.generateToken(authenticatedUser.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", authenticatedUser);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap("error", "Invalid credentials"));
    }

    @GetMapping("/{email}")
    public ResponseEntity<RegularUser> getByEmail(@PathVariable String email) {
        RegularUser user = regularUserService.findByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/full-name/{fullName}")
    public ResponseEntity<RegularUser> getByFullName(@PathVariable String fullName) {
        RegularUser user = regularUserService.findByFullName(fullName);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/age/{age}")
    public ResponseEntity<RegularUser> getByAge(@PathVariable int age) {
        RegularUser user = regularUserService.findByAge(age);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<RegularUser> getById(@PathVariable String id) {
        RegularUser user = regularUserService.findById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update")
    public ResponseEntity<RegularUser> update(@RequestBody RegularUser regularUser) {
        RegularUser updated = regularUserService.update(regularUser);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.ok(regularUserService.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving users");
        }
    }




//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> delete(@PathVariable String id) {
//        RegularUser user = regularUserService.findById(id);
//        if (user != null) {
//            regularUserService.delete(user);
//            return ResponseEntity.ok("User deleted successfully");
//        }
//        return ResponseEntity.notFound().build();
//    }

}
