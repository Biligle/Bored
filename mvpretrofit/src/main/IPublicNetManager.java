package com.wgl.mvpretrofit.delete;

import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 所有网络层接口定义（剪切到自己项目中）
 * @Author Biligle.
 */
public interface IPublicNetManager {
    /** 登录接口
     * @param NetUrl.LOGIN 接口url
     * @param LoginModle 实体（承载返回数据）
     * @param phone 接口参数
     * @param password 接口参数
     * @return LoginModle 实体
     */
    @POST(NetUrl.LOGIN)
    Call<LoginModle> login(@Query("phone") String phone, @Query("password") String password);
}
