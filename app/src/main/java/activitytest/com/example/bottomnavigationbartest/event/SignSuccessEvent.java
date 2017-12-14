package activitytest.com.example.bottomnavigationbartest.event;

/**
 * Created by pc on 2017/12/14.
 */

public class SignSuccessEvent {
    private int jobId;
    public SignSuccessEvent(int jobId){
        this.jobId = jobId;

    }
    public int getJobId(){return  jobId;}
}
