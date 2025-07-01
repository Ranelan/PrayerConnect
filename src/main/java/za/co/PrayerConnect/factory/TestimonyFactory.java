package za.co.PrayerConnect.factory;

import za.co.PrayerConnect.domain.PrayerRequest;
import za.co.PrayerConnect.domain.RegularUser;
import za.co.PrayerConnect.domain.Testimony;
import za.co.PrayerConnect.util.Helper;

import java.time.LocalDateTime;

public class TestimonyFactory {

    public static Testimony createTestimony(String message, LocalDateTime dateSubmitted, PrayerRequest prayerRequest, RegularUser user){
        if (Helper.isNullOrEmpty(message)||
            !Helper.isValidDateTime(dateSubmitted)||
            prayerRequest == null ||
            user == null) {
            return null;
        }

        return new Testimony.TestimonyBuilder()
                .setMessage(message)
                .setDateSubmitted(dateSubmitted)
                .setPrayerRequest(prayerRequest)
                .setUser(user)
                .build();
    }
}
