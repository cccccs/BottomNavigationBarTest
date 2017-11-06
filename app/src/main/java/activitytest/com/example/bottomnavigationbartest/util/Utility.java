package activitytest.com.example.bottomnavigationbartest.util;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import activitytest.com.example.bottomnavigationbartest.db.Job;

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

    //处理Job数据
    public static List<Job> handleJobResponse(String response){
        Log.d("FirstFragment","JobList:"+response);

        Log.d("FirstFragment","isEmpty"+TextUtils.isEmpty(response));
        if(!TextUtils.isEmpty(response)){

            try{
                JSONObject jsonObject = new JSONObject(response);
                List<Job> jobList = new ArrayList<>();
                JSONArray allJobs = jsonObject.getJSONArray("joblist");
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
                    job.setPublishTime(jobObject.getString("publishTime"));
                    job.setEmployerId(jobObject.getInt("employer_Id"));
                    job.setPhoneNum(jobObject.getString("phoneNumber"));
                    job.setWorkHour(jobObject.getInt("workHour"));
                    job.setStartTime(jobObject.getString("startTime"));
                    job.setEndTime(jobObject.getString("endTime"));
                    Log.d("FirstFragment","job"+response);
                    jobList.add(job);
                }
                return jobList;
            }catch (JSONException e){
                e.printStackTrace();
            }

        }
        return null;
    }

}
