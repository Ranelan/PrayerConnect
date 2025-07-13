package za.co.PrayerConnect.service.AdminServ;

import za.co.PrayerConnect.domain.Admin;
import za.co.PrayerConnect.domain.RegularUser;
import za.co.PrayerConnect.service.IService;

import java.util.List;
import java.util.Optional;

public interface IAdminService extends IService<Admin, Long> {

    void blockUser(Long userId);
    void unblockUser(Long userId);
    void deleteUser(Long userId);
    void deleteContent(Long contentId);
    void approveContent(Long contentId);
    void rejectContent(Long contentId);

    List<RegularUser> getAllUsersByEmail(String email);
    List<RegularUser> getAllUsersByAge(int age);
    List<RegularUser> getAllUsersByFullName(String fullName);
//    Optional<RegularUser> getAll();
    Admin authenticate(String email, String password);

}
