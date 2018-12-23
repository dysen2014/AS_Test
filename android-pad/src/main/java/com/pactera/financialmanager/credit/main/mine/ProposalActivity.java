package com.pactera.financialmanager.credit.main.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.login.LogoActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Callable;

public class ProposalActivity extends ParentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal);
        initTitle(this, "", false,"");
        try {
            JSONObject extInfo = new JSONObject();
            extInfo.put("UserId", LogoActivity.user.getUserID());
            FeedbackAPI.setAppExtInfo(extInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        FeedbackAPI.activity = this; // 设置activity
        //support-v4包中的Fragment
        FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction transaction = fm.beginTransaction();
        final Fragment feedback = FeedbackAPI.getFeedbackFragment();
        // must be called
        FeedbackAPI.setFeedbackFragment(new Callable() {
            @Override
            public Object call() throws Exception {
                transaction.replace(R.id.content, feedback);
                transaction.commit();
                return null;
            }
        }, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FeedbackAPI.cleanFeedbackFragment();
        FeedbackAPI.cleanActivity();
    }
}
