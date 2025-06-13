package za.co.PrayerConnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.PrayerConnect.domain.RegularUser;
import za.co.PrayerConnect.repository.RegularUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
public class RegularUserService implements IRegularUserService {

        private final RegularUserRepository regularUserRepository;
        private final PasswordEncoder passwordEncoder;

        @Autowired
        public RegularUserService(RegularUserRepository regularUserRepository,
                                  PasswordEncoder passwordEncoder) {
            this.regularUserRepository = regularUserRepository;
            this.passwordEncoder = passwordEncoder;
        }



    @Override
    public RegularUser authenticate(String email, String password) {
        Optional<RegularUser> optionalUser = regularUserRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            RegularUser user = optionalUser.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }



    @Override
    public RegularUser findByEmail(String email) {
        return regularUserRepository.findByEmail(email)
                .orElse(null);
    }

    @Override
    public RegularUser findByFullName(String fullName) {
        return regularUserRepository.findByFullName(fullName)
                .orElse(null);
    }

    @Override
    public RegularUser findByAge(int age) {
        return regularUserRepository.findByAge(age)
                .orElse(null);
    }

    @Override
    public RegularUser save(RegularUser entity) {
        return regularUserRepository.save(entity);
    }

    @Override
    public RegularUser findById(String s) {
        try {
            Long id = Long.parseLong(s);
            return regularUserRepository.findById(id).orElse(null);
        } catch (NumberFormatException e) {
            return null;
        }
    }


    @Override
    public RegularUser update(RegularUser entity) {
            if (entity.getId() != null && regularUserRepository.existsById(entity.getId())) {
                // Optional: fetch existing user to retain unchanged fields
                RegularUser existing = regularUserRepository.findById(entity.getId()).orElse(null);
                if (existing != null) {
                    RegularUser updated = new RegularUser.RegularUserBuilder()
                            .copy(existing) // start from the existing one
                            .setFullName(entity.getFullName())
                            .setEmail(entity.getEmail())
                            .setPassword(entity.getPassword())
                            .setAge(entity.getAge())
                            .setIsBlocked(entity.isBlocked())
                            .build();

                    return regularUserRepository.save(updated);
                }
            }
            return null;
        }


    @Override
    public void deleteById(String s) {
        try {
            Long id = Long.parseLong(s);
            if (regularUserRepository.existsById(id)) {
                regularUserRepository.deleteById(id);
            }
        } catch (NumberFormatException e) {
            return;
        }
    }

}
