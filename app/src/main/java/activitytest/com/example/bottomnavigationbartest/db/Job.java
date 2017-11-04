package activitytest.com.example.bottomnavigationbartest.db;

import android.os.Parcel;
import android.os.Parcelable;

import static activitytest.com.example.bottomnavigationbartest.db.Job.JobState.doing;

/**
 * Created by pc on 2017/2/15.
 */
//主页兼职选项
public class Job implements Parcelable {
    private String workName;
    private int workPay;
    private String workPlace;
    private String publishTime;
    private String workType;
    private String PeopleNum;
    private String jobSex;
    private String workData;
    private String workTime;
    private String workContent;
    private String havePeopleNum;
    private JobState jobState;
    private String startTime;
    private String endTime;
    private String phoneNum;
    private int employerId;
    private int workHour;

    private int imageId;
    private int jobId;
    public enum JobState{//兼职发布状态 审核中，真正报名 ，截止报名
        check,doing,done
    }

    public  Job(){
    }

    protected Job(Parcel in){
        startTime = in.readString();
        endTime =in.readString();
        phoneNum = in.readString();
        employerId = in.readInt();
        workHour =in.readInt();
        workName = in.readString();
        workPay = in.readInt();
        workPlace = in.readString();
        publishTime = in.readString();
        workType = in.readString();
        PeopleNum = in.readString();
        jobSex = in.readString();
        workData = in.readString();
        workTime = in.readString();
        workContent = in.readString();
        havePeopleNum = in.readString();
        imageId = in.readInt();
        jobId = in.readInt();
        jobState = doing;

    }
    public void setStartTime(String startTime){this.startTime =startTime;}
    public void setEndTime(String endTime){this.endTime =endTime;}
    public void setPhoneNum(String phoneNum){this.phoneNum =phoneNum;}
    public void setEmployerId(int employerId){this.employerId = employerId;}
    public void setWorkHour(int workHour){this.workHour =workHour;}
    public String getStartTime(){return startTime;}
    public String getEndTime(){return endTime;}
    public String getPhoneNum(){return phoneNum;}
    public int getEmployerId(){return employerId;}
    public int getWorkHour(){return workHour;}
    public void setWorkType(String workType){this.workType = workType;}
    public void setPeopleNum(String peopleNum){this.PeopleNum = peopleNum;}
    public void setJobSex(String jobSex){this.jobSex = jobSex;}
    public void setWorkData(String workData){this.workData = workData;}
    public void setWorkContent(String workContent){this.workContent = workContent;}
    public void setHavePeopleNum(String havePeopleNum){this.havePeopleNum = havePeopleNum;}
    public String getWorkType(){return workType;}
    public String getPeopleNum(){return PeopleNum;}
    public String getJobSex(){return jobSex;}
    public String getWorkData(){return workData;}
    public String getWorkContent(){return workContent;}
    public String getHavePeopleNum(){return havePeopleNum;}
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

    public void setWorkPay(int workPay){
        this.workPay = workPay;
    }

    public int  getWorkPay(){
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

    public void setJobState(JobState jobState){
        this.jobState =jobState;
    }

    public JobState getJobState(){
        return jobState;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(workName);
        dest.writeInt(workPay);
        dest.writeString(workPlace);
        dest.writeString(publishTime);
        dest.writeString(workType);
        dest.writeString(PeopleNum);
        dest.writeString(jobSex);
        dest.writeString(workData);
        dest.writeString(workTime);
        dest.writeString(workContent);
        dest.writeString(havePeopleNum);
        dest.writeInt(imageId);
        dest.writeInt(jobId);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(phoneNum);
        dest.writeInt(workHour);
        dest.writeInt(employerId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Job> CREATOR = new Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel in) {
            return new Job(in);
        }

        @Override
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };

}
