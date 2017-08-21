package activitytest.com.example.bottomnavigationbartest.db;

/**
 * Created by pc on 2017/3/30.
 */
//主页工作分类
public class TypeOfJob {

    private String typeName;

    private int typeIamgeId;

    public String getTypeName(){
        return typeName;
    }

    public void setJobType(String jobType) {

        this.typeName = jobType;
    }

    public int getImageType(){
        return typeIamgeId;
    }

    public void setImageType(int imageType){
        this.typeIamgeId = imageType;
    }
}
