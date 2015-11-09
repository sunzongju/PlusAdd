package com.wrmoney.administrator.plusadd;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2015/11/2.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        ((BaseActivity) getActivity()).inject(this);
    }
}
