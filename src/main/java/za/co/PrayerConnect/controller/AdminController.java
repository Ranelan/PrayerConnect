package za.co.PrayerConnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.PrayerConnect.domain.Admin;
import za.co.PrayerConnect.domain.RegularUser;
import za.co.PrayerConnect.dto.AdminDto;
import za.co.PrayerConnect.service.AdminServ.AdminService;
import za.co.PrayerConnect.service.RegularUserServ.RegularUserService;
import za.co.PrayerConnect.util.JwtUtil;
import za.co.PrayerConnect.util.AdminMapper;
import za.co.PrayerConnect.dto.AdminDto;


@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AdminService adminService;
    @Autowired
    private RegularUserService regularUserService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody AdminDto adminDTO) {
        try {
            Admin admin = AdminMapper.toEntity(adminDTO, "defaultPassword123"); // handle password securely
            Admin savedAdmin = adminService.save(admin);
            return ResponseEntity.ok(AdminMapper.toDTO(savedAdmin));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to create Admin: " + e.getMessage());
        }
    }


    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody Admin admin) {
        try {
            Admin authenticatedAdmin = adminService.authenticate(admin.getEmail(), admin.getPassword());
            if (authenticatedAdmin != null) {
                String token = jwtUtil.generateToken(authenticatedAdmin.getEmail());
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(401).body("Invalid credentials");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
            return ResponseEntity.status(500).body("Authentication failed: " + e.getMessage());
        }
    }

    @GetMapping("/getAllByEmail/{email}")
    public ResponseEntity<RegularUser> getAllUsersByEmail(@PathVariable String email) {
        RegularUser user = regularUserService.findByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getAllByFullName/{fullName}")
    public ResponseEntity<RegularUser> getAllUsersByFullName(@PathVariable String fullName) {
        RegularUser user = regularUserService.findByFullName(fullName);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getAllByAge/{age}")
    public ResponseEntity<RegularUser> getAllUsersByAge(@PathVariable int age) {
        RegularUser user = regularUserService.findByAge(age);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable Long id, @RequestBody AdminDto adminDto) {
        try {
            Admin existingAdmin = adminService.findById(id);
            if (existingAdmin == null) {
                return ResponseEntity.notFound().build();
            }
            adminDto.setId(id);
            Admin updated = AdminMapper.toEntity(adminDto, existingAdmin.getPassword()); // keep existing password
            Admin saved = adminService.save(updated);
            return ResponseEntity.ok(AdminMapper.toDTO(saved));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to update Admin: " + e.getMessage());
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Long id) {
        try {
            adminService.deleteById(id);
            return ResponseEntity.ok("Admin deleted successfully");
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
            return ResponseEntity.status(500).body("Failed to delete Admin account: " + e.getMessage());
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<AdminDto> findById(@PathVariable Long id) {
        Admin admin = adminService.findById(id);
        if (admin != null) {
            return ResponseEntity.ok(AdminMapper.toDTO(admin));
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/blockUser/{id}")
    public ResponseEntity<?> blockUser(@PathVariable Long id) {
        try {
            RegularUser user = regularUserService.findById(id.toString());
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            user.setIsBlocked(true);
            regularUserService.save(user);
            return ResponseEntity.ok("User blocked successfully");
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
            return ResponseEntity.status(500).body("Failed to block user: " + e.getMessage());
        }
    }

    @PutMapping("/unblockUser/{id}")
    public ResponseEntity<?> unblockUser(@PathVariable Long id) {
        try {
            RegularUser user = regularUserService.findById(id.toString());
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            user.setIsBlocked(false);
            regularUserService.save(user);
            return ResponseEntity.ok("User unblocked successfully");
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
            return ResponseEntity.status(500).body("Failed to unblock user: " + e.getMessage());
        }
    }

    @PutMapping("/approve/{contentId}")
    public ResponseEntity<String> approveContent(@PathVariable Long contentId) {
        try {
            adminService.approveContent(contentId);
            return ResponseEntity.ok("Content approved successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error approving content: " + e.getMessage());
        }
    }

    @PutMapping("/reject/{contentId}")
    public ResponseEntity<String> rejectContent(@PathVariable Long contentId) {
        try {
            adminService.rejectContent(contentId);
            return ResponseEntity.ok("Content rejected successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error rejecting content: " + e.getMessage());
        }
    }

    @PutMapping("/deleteContent/{contentId}")
    public ResponseEntity<String> deleteContent(@PathVariable Long contentId) {
        try {
            adminService.deleteContent(contentId);
            return ResponseEntity.ok("Content deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error deleting content: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            RegularUser user = regularUserService.findById(id.toString());
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            regularUserService.deleteById(id.toString());
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
            return ResponseEntity.status(500).body("Failed to delete user: " + e.getMessage());
        }
    }

}
