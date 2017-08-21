package activitytest.com.example.bottomnavigationbartest.event;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by pc on 2017/6/7.
 */

public class StartBrotherResEvent {
    public SupportFragment targetFragment;

    public StartBrotherResEvent(SupportFragment targetFragment) {
        this.targetFragment = targetFragment;
    }
}
