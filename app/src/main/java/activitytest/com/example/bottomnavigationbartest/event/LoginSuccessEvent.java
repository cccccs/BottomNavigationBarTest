package activitytest.com.example.bottomnavigationbartest.event;

/**
 * Created by pc on 2017/8/1.
 */

public class LoginSuccessEvent {
    private String account;

    public LoginSuccessEvent(String account){
        this.account = account;
    }

    public String getAccount(){
        return account;
    }
}
