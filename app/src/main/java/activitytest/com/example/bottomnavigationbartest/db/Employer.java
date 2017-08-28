package activitytest.com.example.bottomnavigationbartest.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2017/8/28.
 */

public class Employer extends User {
    private List<Job> jobList = new ArrayList<>();

    public void publishJob(Job job){
        jobList.add(job);
    }

}
