package activitytest.com.example.bottomnavigationbartest.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2017/8/28.
 */

public class Student extends User {
    List<Job> starJobs = new ArrayList<>();
    List<Job> applyJobs=new ArrayList<>();
    public  enum Grade{
        大一,大二,大三,大四,研究生及以上
    }
    private Grade grade;

    public void starJob(Job job){
        starJobs.add(job);
    }

    public void clearStarJob(){
        starJobs.clear();
    }

    public void applyJob(Job job){
        applyJobs.add(job);
    }
    public void setGrade(Grade grade){
        this.grade = grade;
    }
    public Grade getGrade(){
        return grade;
    }
}
