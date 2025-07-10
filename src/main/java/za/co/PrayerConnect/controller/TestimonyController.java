package za.co.PrayerConnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.PrayerConnect.domain.Testimony;
import za.co.PrayerConnect.dto.TestimonyDto;
import za.co.PrayerConnect.service.TestimonyServ.TestimonyService;
import za.co.PrayerConnect.util.TestimonyMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/testimonies")
public class TestimonyController {

    @Autowired
    private TestimonyService testimonyService;

    @Autowired
    private TestimonyMapper testimonyMapper;

    // Create
    @PostMapping("/create")
    public ResponseEntity<TestimonyDto> create(@RequestBody TestimonyDto dto) {
        try {
            Testimony testimony = testimonyMapper.toEntity(dto);
            Testimony saved = testimonyService.save(testimony);
            return ResponseEntity.ok(testimonyMapper.toDto(saved));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    // Update
    @PutMapping("/update")
    public ResponseEntity<TestimonyDto> update(@RequestBody TestimonyDto dto) {
        try {
            Testimony updated = testimonyService.update(testimonyMapper.toEntity(dto));
            if (updated != null) {
                return ResponseEntity.ok(testimonyMapper.toDto(updated));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    // Delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        try {
            testimonyService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    // Find by ID
    @GetMapping("/findById/{id}")
    public ResponseEntity<TestimonyDto> findById(@PathVariable Long id) {
        try {
            Testimony testimony = testimonyService.findById(id);
            return (testimony != null)
                    ? ResponseEntity.ok(testimonyMapper.toDto(testimony))
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    // Find by userId
    @GetMapping("/findByUserId/{userId}")
    public ResponseEntity<List<TestimonyDto>> findByUserId(@PathVariable Long userId) {
        try {
            List<Testimony> list = testimonyService.findByUserId(userId);
            if (list.isEmpty()) return ResponseEntity.notFound().build();

            List<TestimonyDto> dtos = list.stream()
                    .map(testimonyMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    // Find by contentId
    @GetMapping("/findByContentId/{contentId}")
    public ResponseEntity<List<TestimonyDto>> findByContentId(@PathVariable Long contentId) {
        try {
            List<Testimony> list = testimonyService.findByPrayerRequest_ContentId(contentId);
            if (list.isEmpty()) return ResponseEntity.notFound().build();

            List<TestimonyDto> dtos = list.stream()
                    .map(testimonyMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    // Get all
    @GetMapping("/all")
    public ResponseEntity<List<TestimonyDto>> findAll() {
        try {
            List<TestimonyDto> dtos = testimonyService.findAll().stream()
                    .map(testimonyMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
