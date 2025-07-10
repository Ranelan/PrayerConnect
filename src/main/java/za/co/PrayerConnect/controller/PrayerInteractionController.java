package za.co.PrayerConnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.PrayerConnect.domain.PrayerInteraction;
import za.co.PrayerConnect.dto.PrayerInteractionDto;
import za.co.PrayerConnect.service.PrayerInteractionServ.PrayerInteractionService;
import za.co.PrayerConnect.util.PrayerInteractionMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/prayerInteraction")
public class PrayerInteractionController {

    @Autowired
    private PrayerInteractionService prayerInteractionService;

    @Autowired
    private PrayerInteractionMapper prayerInteractionMapper;

    @PostMapping("/create")
    public ResponseEntity<PrayerInteractionDto> create(@RequestBody PrayerInteractionDto dto) {
        try {
            PrayerInteraction entity = prayerInteractionMapper.toEntity(dto);
            PrayerInteraction saved = prayerInteractionService.save(entity);
            return ResponseEntity.ok(prayerInteractionMapper.toDto(saved));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<PrayerInteractionDto> findById(@PathVariable Long id) {
        try {
            PrayerInteraction entity = prayerInteractionService.findById(id);
            if (entity != null) {
                return ResponseEntity.ok(prayerInteractionMapper.toDto(entity));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/findByUser/{userId}")
    public ResponseEntity<List<PrayerInteractionDto>> getByUserId(@PathVariable Long userId) {
        try {
            List<PrayerInteractionDto> dtos = prayerInteractionService.findByUser_Id(userId)
                    .stream()
                    .map(prayerInteractionMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/findByPrayerRequest/{prayerRequestId}")
    public ResponseEntity<List<PrayerInteractionDto>> getByContentId(@PathVariable Long prayerRequestId) {
        try {
            List<PrayerInteractionDto> dtos = prayerInteractionService.findByPrayerRequest_ContentId(prayerRequestId)
                    .stream()
                    .map(prayerInteractionMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/hasPrayed")
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
    public ResponseEntity<List<PrayerInteractionDto>> getAll() {
        try {
            List<PrayerInteractionDto> dtos = prayerInteractionService.findAll()
                    .stream()
                    .map(prayerInteractionMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
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
