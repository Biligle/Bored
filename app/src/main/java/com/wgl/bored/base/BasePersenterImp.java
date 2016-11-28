package com.wgl.bored.base;

import com.wgl.bored.IPublicNetManager;
import com.wgl.bored.main.mainfragment.MainModle;
import com.wgl.bored.util.HtmlParseUtil;
import com.wgl.bored.util.StringUtil;
import com.wgl.mvpretrofit.mvp.m.IModel;

import java.io.InputStream;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 联网基类（剪切到自己项目中）
 * @Author Biligle.
 */
public abstract class BasePersenterImp<T extends IModel> {

    public IListener mListener;
    public Call mCall;

    public void toNet(IPublicNetManager manager, boolean flagDialog, String dialogStr) {
        if (mCall != null && mCall.isExecuted()) {
            mCall.cancel();
            mCall = null;
        }
        mCall = getCall(manager);

        if (mCall == null) {
            return;
        }
        if (flagDialog) {
            mListener.showLoadingDialog(dialogStr);
        }
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        InputStream inputStream = response.body().byteStream();
                        String result = StringUtil.inToString(inputStream);
                        List<MainModle> data = HtmlParseUtil.getHtmlParseUtil().parseTieBa(result);
                        if(data.size() > 0){
                            mListener.onSuccess(data);
                        }
                        //根据返回数据判断
//                        if (response.body().retCode) {
//                            mListener.onSuccess(response.body());
//                        } else {
//                            if (response.body().retMsg != null) {
//                                mListener.onFailure(response.body().retMsg,null);
//                            } else {
//                                mListener.onFailure("网络异常，请检查网络",null);
//                            }
//                        }
                    }
                } else {
//                    if (response.body() != null && response.body().retMsg != null) {
//                        mListener.onFailure(response.body().retMsg,null);
//                    } else {
//                        mListener.onFailure("网络异常，请检查网络",null);
//                    }

                }
                mListener.stopLoadingDialog();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mListener.onFailure("网络异常，请检查网络",t);
                mListener.stopLoadingDialog();
            }
        });

    }

    public abstract Call getCall(IPublicNetManager manager);

    public abstract void setListener(IListener listener);

    public interface IListener<T> {
        void onSuccess(T bean);

        void onFailure(String str, Throwable t);

        void showLoadingDialog(String str);

        void stopLoadingDialog();
    }

    /**
     * 获得实体类
     * @return
     */
    public abstract Class<T> getT();

}
