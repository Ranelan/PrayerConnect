package za.co.PrayerConnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.co.PrayerConnect.domain.Admin;
import za.co.PrayerConnect.domain.RegularUser;
import za.co.PrayerConnect.repository.AdminRepository;

import java.util.Optional;

@Service
public class AdminService implements IAdminService {

    @Autowired
    private RegularUserService regularUserService;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Admin save(Admin entity) {
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
        // Implementation for deleting content by contentId
        // This method should interact with the content repository to delete the content
        // For now, we can leave it empty or throw an UnsupportedOperationException
        throw new UnsupportedOperationException("Method not implemented yet");

    }

    @Override
    public void approveContent(Long contentId) {
        // Implementation for approving content by contentId
        // This method should interact with the content repository to approve the content
        // For now, we can leave it empty or throw an UnsupportedOperationException
        throw new UnsupportedOperationException("Method not implemented yet");

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
