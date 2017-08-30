package activitytest.com.example.bottomnavigationbartest;

import android.app.Application;

import activitytest.com.example.bottomnavigationbartest.db.Employer;
import activitytest.com.example.bottomnavigationbartest.db.Student;
import activitytest.com.example.bottomnavigationbartest.db.User;

/**
 * Created by pc on 2017/5/25.
 */

public class MyApplication extends Application {
    public String appVersion = "v1.0";

    //当前登录用户
    public User loginUser = new User();

    public User getLoginUser(){
        return loginUser;
    }

    public void StuLogin(User stu){
        loginUser = new Student();
        loginUser.setName(stu.getName());
        loginUser.setUserPassWord(stu.getUserPassWord());
        loginUser.setLogin(true);
    }
    public void EmpLogin(User employer){
        loginUser = new Employer();
        loginUser.setName(employer.getName());
        loginUser.setUserPassWord(employer.getUserPassWord());
        loginUser.setLogin(true);
    }


    public void userLogout(){
        loginUser = new User();
    }
}
