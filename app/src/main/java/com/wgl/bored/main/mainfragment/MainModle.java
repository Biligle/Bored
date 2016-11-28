package com.wgl.bored.main.mainfragment;

import com.wgl.mvpretrofit.mvp.m.IModel;

import java.util.List;

/**
 * @Author Biligle.
 * 首页实体
 */

public class MainModle extends IModel {

    private String title;//标题
    private String content;//内容
    private String time;//时间
    private String url;//跳转链接地址
    private List<String> imageUrl;//图片地址

    public String getTitle() {
            return title;
    }

    public void setTitle(String title) {
            this.title = title;
    }

    public String getContent() {
            return content;
    }

    public void setContent(String content) {
            this.content = content;
    }

    public String getTime() {
            return time;
    }

    public void setTime(String time) {
            this.time = time;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }
}
