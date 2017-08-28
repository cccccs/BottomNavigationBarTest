package activitytest.com.example.bottomnavigationbartest.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2017/8/28.
 */

public class Student extends User {
    List<Job> starJobs = new ArrayList<>();
    List<Job> applyJobs=new ArrayList<>();


    public void starJob(Job job){
        starJobs.add(job);
    }

    public void clearStarJob(){
        starJobs.clear();
    }

    public void applyJob(Job job){
        applyJobs.add(job);
    }

}
