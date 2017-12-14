package activitytest.com.example.bottomnavigationbartest.db;

import java.util.List;

/**
 * Created by pc on 2017/5/25.
 */
//用户
public class User {
    public enum UserType{
        student,business
    }
    public enum Sex{
        man,woman
    }
    private List<MsgL> msgLList;
    private List<Job> jobList;
    private List<Job> myJobList;
    private int UserId;
    private String UserPassWord;
    private String UserName;
    private UserType userType= UserType.student;
    private Sex sex = Sex.man;
    private String Name;
    private String phone;
    private boolean login=false;
    private String sessionStr;
    public User(){
        UserId = 0;
        UserPassWord = "";
        UserName = "";
        sex = Sex.man;
        Name = "";
        phone="";
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public void setUserPassWord(String userPassWord) {
        UserPassWord = userPassWord;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setSession(String str){
        sessionStr = str;
    }
    public String getSession(){
        return sessionStr;
    }
    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public int getUserId() {
        return UserId;
    }

    public String getUserPassWord() {
        return UserPassWord;
    }

    public String getUserName() {
        return UserName;
    }

    public Sex getSex() {
        return sex;
    }

    public String getName() {
        return Name;
    }

    public UserType getUserType() {
        return userType;
    }
    public void setLogin(boolean login){
        this.login = login;
    }
    public boolean getlogin(){
        return login;
    }
    public void setPhone(String phone){this.phone = phone;}
    public String getPhone(){return phone;}
    public void setJobList(List<Job> jobList){this.jobList = jobList;}
    public List<Job> getJobList(){return jobList;}
    public void setMyJobList(List<Job> jobList){this.myJobList = jobList;}
    public List<Job> getMyJobList(){return myJobList;}

    public List<MsgL> getMsgLList(){return msgLList;}
    public void setMsgLList(List<MsgL> msgLList){this.msgLList = msgLList;}

}
