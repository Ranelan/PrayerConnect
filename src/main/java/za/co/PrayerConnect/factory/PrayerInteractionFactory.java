package za.co.PrayerConnect.factory;

import za.co.PrayerConnect.domain.PrayerInteraction;
import za.co.PrayerConnect.domain.PrayerRequest;
import za.co.PrayerConnect.domain.RegularUser;
import za.co.PrayerConnect.util.Helper;

import java.time.LocalDateTime;

public class PrayerInteractionFactory {

    public static PrayerInteraction createPrayerInteraction(Long id, LocalDateTime datePrayed, RegularUser user, PrayerRequest prayerRequest){

        if(Helper.isValidDateTime(datePrayed)||
                user==null||
                prayerRequest==null){

            return null;
        }

        return new PrayerInteraction.PrayerInteractionBuilder()
                .setId(id)
                .setDatePrayed(datePrayed)
                .setUser(user)
                .setPrayerRequest(prayerRequest)
                .build();
    }
}
