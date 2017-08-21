package activitytest.com.example.bottomnavigationbartest;

import android.app.Application;

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

    public void userLogin(User user){
        loginUser.setUserId(user.getUserId());
        loginUser.setUserName(user.getName());
    }

    public void userLogout(){
        loginUser = new User();
    }
}
