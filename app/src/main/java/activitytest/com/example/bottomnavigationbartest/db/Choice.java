package activitytest.com.example.bottomnavigationbartest.db;

/**
 * Created by pc on 2017/8/19.
 */
//个人中心选项
public class Choice {
    private int imageId;
    private String choice;

    public int getImageId(){
        return imageId;
    }

    public void setImageId(int imageId){
        this.imageId = imageId;
    }

    public String getChoice(){
        return choice;
    }

    public void setChoice(String choice){
        this.choice = choice;
    }
}
