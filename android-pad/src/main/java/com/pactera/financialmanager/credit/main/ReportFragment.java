package com.pactera.financialmanager.credit.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentFragment;

/**
 * Created by dysen on 2017/8/16.
 */

public class ReportFragment extends ParentFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_warn, null);
        return view;
    }
}
