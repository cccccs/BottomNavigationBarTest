package activitytest.com.example.bottomnavigationbartest.db;

/**
 * Created by pc on 2017/8/21.
 */

public class Inform {
    private String time;
    private Job job;
    private String phone;

    public String getTime(){return time;}

    public Job getJob(){return job;}

    public String getPhone(){return phone;}

    public void setTime(String time){
        this.time = time;
    }

    public void setJob(Job job){
        this.job = job;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

}
