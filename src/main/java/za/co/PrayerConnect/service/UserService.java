package za.co.PrayerConnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.PrayerConnect.domain.Admin;
import za.co.PrayerConnect.domain.User;
import za.co.PrayerConnect.repository.AdminRepository;
import za.co.PrayerConnect.repository.RegularUserRepository;

import java.util.Optional;

@Service
public class UserService implements IUserService{


    private final AdminRepository adminRepository;
    private final RegularUserRepository regularUserRepository;

    @Autowired
    public UserService(AdminRepository adminRepository, RegularUserRepository regularUserRepository) {
        this.adminRepository = adminRepository;
        this.regularUserRepository = regularUserRepository;
    }

    @Override
    public Optional<? extends User> findByEmail(String email) {
        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isPresent()) return admin;

        return regularUserRepository.findByEmail(email);
    }
}
