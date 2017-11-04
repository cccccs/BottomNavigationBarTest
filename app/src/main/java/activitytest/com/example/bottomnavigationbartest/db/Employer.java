package activitytest.com.example.bottomnavigationbartest.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2017/8/28.
 */

public class Employer extends User {
    private List<Job> jobList = new ArrayList<>();

    private String company;
    private String companyDetail;

    public void publishJob(Job job){
        jobList.add(job);
    }

    public String getCompany(){
        return company;
    }
    public String getCompanyDetail(){
        return companyDetail;
    }
    public void setCompany(String company){
        this.company =company;
    }
    public void setCompanyDetail(String companyDetail){
        this.companyDetail = companyDetail;
    }

}
