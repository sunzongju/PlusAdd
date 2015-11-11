package com.wrmoney.administrator.plusadd.accountview.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wrmoney.administrator.plusadd.BaseFragment;
import com.wrmoney.administrator.plusadd.R;

/**
 * Created by Administrator on 2015/11/11.
 */
public class WaterCutFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account_water_cut,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
