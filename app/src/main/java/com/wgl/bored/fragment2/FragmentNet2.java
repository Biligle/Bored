package com.wgl.bored.fragment2;

import com.wgl.bored.IPublicNetManager;
import com.wgl.bored.base.BasePersenterImp;
import com.wgl.bored.main.mainfragment.MainModle;

import retrofit2.Call;

/**
 * @Author Biligle.
 */

public class FragmentNet2 extends BasePersenterImp<MainModle> {

    @Override
    public Call getCall(IPublicNetManager manager) {
        return manager.art();
    }

    @Override
    public void setListener(IListener listener) {
        mListener = listener;
    }

    @Override
    public Class<MainModle> getT() {
        return MainModle.class;
    }
}
