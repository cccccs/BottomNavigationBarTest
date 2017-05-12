package activitytest.com.example.bottomnavigationbartest.db;

/**
 * Created by pc on 2017/2/15.
 */
public class Job {

    private String workTime;
    private String publishTime;
    public String workPlace;
    public String workPay;
    public String workName;
    public int imageId;
    public int jobId;

    public  Job(String workTime,String publishTime,String workName,String workPlace,String workPay,int imageId,int jobId){
        this.jobId =jobId;
        this.imageId = imageId;
        this.workName = workName;
        this.workPay = workPay;
        this.workPlace = workPlace;
        this.workTime = workTime;
        this.publishTime = publishTime;
    }

    public void setWorkTime(String workTime){
        this.workTime = workTime;
    }

    public String getWorkTime(){
        return workTime;
    }

    public void setPublishTime(String publishTime){
        this.publishTime = publishTime;
    }

    public String getPublishTime(){
        return publishTime;
    }

    public void setWorkPlace(String workPlace){
        this.workPlace = workPlace;
    }

    public String getWorkPlace(){
        return workPlace;
    }

    public void setWorkPay(String workPay){
        this.workPay = workPay;
    }

    public String getWorkPay(){
        return workPay;
    }

    public void setWorkName(String workName){
        this.workName = workName;
    }

    public String getWorkName(){
        return workName;
    }

    public void setImageId(int imageId){
        this.imageId = imageId;
    }

    public int getImageId(){
        return imageId;
    }

    public void setJobId(int jobId){
        this.jobId = jobId;
    }

    public int getJobId(){
        return jobId;
    }

}
