package za.co.PrayerConnect.service.AdminServ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.co.PrayerConnect.domain.Admin;
import za.co.PrayerConnect.domain.ApprovalStatus;
import za.co.PrayerConnect.domain.PrayerRequest;
import za.co.PrayerConnect.domain.RegularUser;
import za.co.PrayerConnect.repository.AdminRepository;
import za.co.PrayerConnect.service.PrayerRequestServ.PrayerRequestService;
import za.co.PrayerConnect.service.RegularUserServ.RegularUserService;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Service
public class AdminService implements IAdminService {

    @Autowired
    private RegularUserService regularUserService;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PrayerRequestService prayerRequestService;

    @Override
    public Admin save(Admin entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        if (entity.getLastLogin() == null) {
            entity = new Admin.AdminBuilder()
                    .copy(entity)
                    .setLastLogin(LocalDateTime.now())
                    .build();
        }

        return adminRepository.save(entity);
    }

    @Override
    public Admin findById(Long aLong) {
        return adminRepository.findById(aLong)
                .orElse(null);
    }

    @Override
    public Admin update(Admin updatedAdmin) {
        Optional<Admin> existingOptional = adminRepository.findById(updatedAdmin.getId());

        if (existingOptional.isPresent()) {
            Admin existing = existingOptional.get();

            Admin modified = new Admin.AdminBuilder()
                    .copy(existing) // copy current values
                    .setFullName(updatedAdmin.getFullName())
                    .setEmail(updatedAdmin.getEmail())
                    .setPassword(updatedAdmin.getPassword())
                    .setAge(updatedAdmin.getAge())
                    .setAdminCode(updatedAdmin.getAdminCode())
                    .setLastLogin(updatedAdmin.getLastLogin())
                    .setPermissions(updatedAdmin.getPermissions())
                    .build();

            return adminRepository.save(modified);
        }

        return null;
    }


    @Override
    public void deleteById(Long aLong) {
        if (adminRepository.existsById(aLong)) {
            adminRepository.deleteById(aLong);
        }

    }

    @Override
    public void blockUser(Long id) {
        RegularUser user = regularUserService.findById(id.toString());
        if (user != null) {
            user.setIsBlocked(true);
            regularUserService.save(user);
        }
    }

    @Override
    public void unblockUser(Long id) {
        RegularUser user = regularUserService.findById(id.toString());
        if (user != null) {
            user.setIsBlocked(false);
            regularUserService.save(user);
        }

    }

    @Override
    public void deleteUser(Long id) {
        RegularUser user = regularUserService.findById(id.toString());
        if (user != null) {
            regularUserService.deleteById(id.toString());
        }

    }

    @Override
    public void deleteContent(Long contentId) {
        Optional<PrayerRequest> optionalRequest = prayerRequestService.findByContentId(contentId);

        if (optionalRequest.isPresent()) {
            prayerRequestService.deleteById(contentId);
        } else {
            throw new RuntimeException("Cannot delete. Prayer request not found with contentId: " + contentId);
        }
    }


    @Override
    public void approveContent(Long contentId) {
        Optional<PrayerRequest> optionalRequest = prayerRequestService.findByContentId(contentId);

        if (optionalRequest.isPresent()) {
            PrayerRequest existing = optionalRequest.get();

            PrayerRequest updated = new PrayerRequest.PrayerRequestBuilder()
                    .copy(existing)
                    .setApprovalStatuses(Collections.singletonList(ApprovalStatus.APPROVED))
                    .setReviewedAt(java.time.LocalDateTime.now())
                    .setReviewComment("Approved by admin")
                    .build();

            prayerRequestService.save(updated);
        } else {
            throw new RuntimeException("Prayer request not found with contentId: " + contentId);
        }
    }

    @Override
    public void rejectContent(Long contentId) {
        Optional<PrayerRequest> optionalRequest = prayerRequestService.findByContentId(contentId);

        if (optionalRequest.isPresent()) {
            PrayerRequest existing = optionalRequest.get();

            PrayerRequest rejected = new PrayerRequest.PrayerRequestBuilder()
                    .copy(existing)
                    .setApprovalStatuses(Collections.singletonList(ApprovalStatus.REJECTED))
                    .setReviewedAt(java.time.LocalDateTime.now())
                    .setReviewComment("Rejected by admin for not having appropriate content, please re write") // Optional: Customize this
                    .build();

            prayerRequestService.save(rejected);
        } else {
            throw new RuntimeException("Prayer request not found with contentId: " + contentId);
        }
    }



    @Override
    public Optional<RegularUser> getAllUsersByEmail(String email) {
        return Optional.ofNullable(regularUserService.findByEmail(email));
    }

    @Override
    public Optional<RegularUser> getAllUsersByAge(int age) {
        return Optional.ofNullable(regularUserService.findByAge(age));
    }

    @Override
    public Optional<RegularUser> getAllUsersByFullName(String fullName) {
        return Optional.ofNullable(regularUserService.findByFullName(fullName));
    }

//    @Override
//    public Optional<RegularUser> getAll() {
//        return Optional.ofNullable(regularUserService.findAll());
//    }

    @Override
    public Admin authenticate(String email, String password) {
        Optional <Admin> optionalAdmin = adminRepository.findByEmail(email);
        if( optionalAdmin.isPresent() ) {
            Admin admin = optionalAdmin.get();
            if (passwordEncoder.matches(password, admin.getPassword())) {
                return admin;
            }
        }
        return null;

    }

}
