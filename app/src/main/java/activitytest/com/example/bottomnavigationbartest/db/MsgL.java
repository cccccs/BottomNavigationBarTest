package activitytest.com.example.bottomnavigationbartest.db;

/**
 * Created by pc on 2017/12/14.
 */

public class MsgL {
    private int fromId;
    private int toId;
    private int msgId;
    private String info;

    public int getFromId(){
        return fromId;
    }
    public int getToId(){
        return toId;
    }
    public int getMsgId(){
        return msgId;
    }
    public String getInfo(){
        return info;
    }
    public void setFromId(int fromId){
        this.fromId = fromId;
    }
    public void setToId(int toId){
        this.toId = toId;
    }
    public void setMsgId(int msgId){
        this.msgId = msgId;
    }
    public void setInfo(String info){
        this.info = info;
    }
}
