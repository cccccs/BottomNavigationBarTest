package activitytest.com.example.bottomnavigationbartest.db;

/**
 * Created by pc on 2017/8/30.
 */

public class Company {
    private String name;
    private String location;
    private String introduction;

    public void setName(String name){
        this.name =name;
    }
    public String getName(){
        return name;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public String getLocation(){
        return location;
    }

    public void setIntroduction(String introduction){
        this.introduction = introduction;
    }
    public String getIntroduction(){
        return introduction;
    }
}
