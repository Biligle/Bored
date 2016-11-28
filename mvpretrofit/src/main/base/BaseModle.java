package com.wgl.sunshan.base;

import com.wgl.mvpretrofit.mvp.m.IModel;

/**
 * 实体类父类（剪切到自己项目中）
 * @Author Biligle.
 */

public class BaseModle extends IModel {
    public String retMsg;//这是共同属性，名称和类型由后台返回json决定
    public boolean retCode;//这是共同属性，名称和类型由后台返回json决定
}
