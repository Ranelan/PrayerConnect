package za.co.PrayerConnect.factory;

import za.co.PrayerConnect.domain.Admin;
import za.co.PrayerConnect.domain.ApprovalStatus;
import za.co.PrayerConnect.domain.PrayerRequest;
import za.co.PrayerConnect.domain.User;
import za.co.PrayerConnect.util.Helper;

import java.time.LocalDateTime;
import java.util.List;

public class PrayerRequestFactory {

    public static PrayerRequest createPrayerRequest(String title, String message, LocalDateTime dateSubmitted, boolean isAnonymous, List<ApprovalStatus> approvalStatuses, LocalDateTime reviewedAt, String reviewComment, User user, Admin admin)  {

        if(Helper.isNullOrEmpty(title)||
        !Helper.isValidMessage(message)||
                !Helper.isValidDateTime(dateSubmitted)||
                approvalStatuses==null||approvalStatuses.isEmpty()||
                !Helper.isValidDateTime(reviewedAt)||
                Helper.isNullOrEmpty(reviewComment)
        ){return null;
        }

        return new PrayerRequest.PrayerRequestBuilder().
                setAdmin(admin)
                .setUser(user)
                .setTitle(title)
                .setMessage(message)
                .setDateSubmitted(dateSubmitted)
                .setAnonymous(isAnonymous)
                .setApprovalStatuses(approvalStatuses)
                .setReviewedAt(reviewedAt)
                .setReviewComment(reviewComment)
                .build();
    }
}
