package activitytest.com.example.bottomnavigationbartest.util;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import activitytest.com.example.bottomnavigationbartest.db.Job;
import activitytest.com.example.bottomnavigationbartest.db.MsgL;
import activitytest.com.example.bottomnavigationbartest.db.User;

/**
 * Created by pc on 2017/9/12.
 */

public class Utility {
    public static boolean handleStatusResponse(String response){
        Log.d("LoginFragment","MMMMMMMMM:status"+response);
        if(!TextUtils.isEmpty(response)) {
            Log.d("LoginFragment","response11"+response);
            try {
                JSONObject object = new JSONObject(response);
                String status = object.getString("status");

                return status.equals("1");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static User handleUserResponse(String response){
        Log.d("FirstFragment","用户UserList:"+response);
        if(!TextUtils.isEmpty(response)) {
            try{
                JSONObject jsonObject = new JSONObject(response);
                User user = new User();
                user.setUserName(jsonObject.getString("username"));
                if(jsonObject.getString("phoneNumber")!="") {
                    user.setPhone(jsonObject.getString("phoneNumber"));
                }
                return user;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;

    }
    public static String handleStringResponse(String response,String keyword){
        Log.d("FirstFragment","用户UserList:"+response);
        if(!TextUtils.isEmpty(response)) {
            try{
                JSONObject jsonObject = new JSONObject(response);
                String key = jsonObject.getString(keyword);


                return key;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;

    }
    public static int handleIntResponse(String response,String keyword){
        Log.d("FirstFragment","用户UserList:"+response);
        if(!TextUtils.isEmpty(response)) {
            try{
                JSONObject jsonObject = new JSONObject(response);
                int key = jsonObject.getInt(keyword);


                return key;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return 0;

    }


    //处理Job数据
    public static List<Job> handleJobResponse(String response){
        Log.d("FirstFragment","工作JobList:"+response);

        Log.d("FirstFragment","工作isEmpty"+TextUtils.isEmpty(response));
        if(!TextUtils.isEmpty(response)){

            try{
                JSONObject jsonObject = new JSONObject(response);
                List<Job> jobList = new ArrayList<>();
                JSONArray allJobs = jsonObject.getJSONArray("joblist");
                Log.d("FirstFragment","jobLength"+allJobs.length());
                for(int i = 0;i<allJobs.length();i++) {
                    JSONObject jobObject = allJobs.getJSONObject(i);

                    Job job = new Job();
                    Log.d("PublishFragment","日期GETDATE"+jobObject.getString("publishTime"));
                    job.setJobId(jobObject.getInt("jobId"));
                    job.setWorkPlace(jobObject.getString("address"));
                    job.setWorkName(jobObject.getString("jobName"));
                    job.setWorkPay(jobObject.getInt("salary"));
                    job.setWorkContent(jobObject.getString("detail"));
                    job.setWorkTime(jobObject.getString("workTime"));
                    job.setPublishTime(jobObject.getString("publishTime").substring(0,10));
                    job.setEmployerId(jobObject.getInt("employer_Id"));
                    job.setPhoneNum(jobObject.getString("phoneNumber"));
                    job.setWorkHour(jobObject.getInt("workHour"));
                    job.setStartTime(jobObject.getString("startTime"));
                    job.setEndTime(jobObject.getString("endTime"));
                    job.setStatus(jobObject.getInt("status"));

                    jobList.add(job);
                }

                Log.d("publishWanted","工作列表2");
                return jobList;

            }catch (JSONException e){
                e.printStackTrace();
            }

        }
        return null;
    }
    //处理Job数据
    public static List<Job> handlejobResponse(String response){
        Log.d("FirstFragment","工作JobList:"+response);

        Log.d("FirstFragment","工作isEmpty"+TextUtils.isEmpty(response));
        if(!TextUtils.isEmpty(response)){

            try{
                JSONObject jsonObject = new JSONObject(response);
                List<Job> jobList = new ArrayList<>();
                JSONArray allJobs = jsonObject.getJSONArray("jobList");
                Log.d("FirstFragment","jobLength"+allJobs.length());
                for(int i = 0;i<allJobs.length();i++) {
                    JSONObject jobObject = allJobs.getJSONObject(i);

                    Job job = new Job();
                    job.setJobId(jobObject.getInt("jobId"));
                    job.setWorkPlace(jobObject.getString("address"));
                    job.setWorkName(jobObject.getString("jobName"));
                    job.setWorkPay(jobObject.getInt("salary"));
                    job.setWorkContent(jobObject.getString("detail"));
                    job.setWorkTime(jobObject.getString("workTime"));


                    job.setPublishTime(jobObject.getString("publishTime").substring(0,10));
                    job.setEmployerId(jobObject.getInt("employer_Id"));
                    job.setPhoneNum(jobObject.getString("phoneNumber"));
                    job.setWorkHour(jobObject.getInt("workHour"));
                    job.setStartTime(jobObject.getString("startTime"));
                    job.setEndTime(jobObject.getString("endTime"));
                    job.setStatus(jobObject.getInt("status"));
                    jobList.add(job);
                }

                return jobList;

            }catch (JSONException e){
                e.printStackTrace();
            }

        }
        return null;
    }
    public static List<MsgL> handleMsgResponse(String response){
        if(!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                List<MsgL> msgList = new ArrayList<>();
                JSONArray allMsgs = jsonObject.getJSONArray("msglist");
                Log.d("FirstFragment", "jobLength" + allMsgs.length());
                for (int i = 0; i < allMsgs.length(); i++) {
                    JSONObject jobObject = allMsgs.getJSONObject(i);
                    MsgL msgL = new MsgL();
                    msgL.setFromId(jobObject.getInt("fromId"));
                    msgL.setToId(jobObject.getInt("toId"));
                    msgL.setInfo(jobObject.getString("info"));
                    msgL.setMsgId(jobObject.getInt("msgId"));
                    msgList.add(msgL);

                }
                return msgList;
            }catch(Exception e){
                e.printStackTrace();
                }

        }
        return null;
    }
    public static List<Job> handlejobStatusResponse(String response){
        Log.d("FirstFragment","工作JobList:"+response);

        Log.d("FirstFragment","工作isEmpty"+TextUtils.isEmpty(response));
        if(!TextUtils.isEmpty(response)){

            try{
                JSONObject jsonObject = new JSONObject(response);
                List<Job> jobList = new ArrayList<>();
                JSONArray allJobs = jsonObject.getJSONArray("jobList");
                Log.d("FirstFragment","jobLength"+allJobs.length());
                for(int i = 0;i<allJobs.length();i++) {
                    JSONObject jobObject = allJobs.getJSONObject(i);

                    if(jobObject.getInt("status")==1) {
                        Job job = new Job();
                        job.setJobId(jobObject.getInt("jobId"));
                        job.setWorkPlace(jobObject.getString("address"));
                        job.setWorkName(jobObject.getString("jobName"));
                        job.setWorkPay(jobObject.getInt("salary"));
                        job.setWorkContent(jobObject.getString("detail"));
                        job.setWorkTime(jobObject.getString("workTime"));


                        job.setPublishTime(jobObject.getString("publishTime").substring(0, 10));
                        job.setEmployerId(jobObject.getInt("employer_Id"));
                        job.setPhoneNum(jobObject.getString("phoneNumber"));
                        job.setWorkHour(jobObject.getInt("workHour"));
                        job.setStartTime(jobObject.getString("startTime"));
                        job.setEndTime(jobObject.getString("endTime"));
                        job.setStatus(jobObject.getInt("status"));
                        jobList.add(job);
                    }
                }

                return jobList;

            }catch (JSONException e){
                e.printStackTrace();
            }

        }
        return null;
    }

}
