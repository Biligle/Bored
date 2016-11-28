package com.wgl.bored.util;

import com.wgl.bored.main.mainfragment.MainModle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Biligle.
 * 解析Html
 */

public class HtmlParseUtil {

    public static HtmlParseUtil htmlParseUtil;
    private HtmlParseUtil(){}

    public static HtmlParseUtil getHtmlParseUtil(){
        if(null == htmlParseUtil){
            htmlParseUtil = new HtmlParseUtil();
        }
        return htmlParseUtil;
    }

    /**
     *
     * 获取行尸走肉贴吧的内容
     * @param result
     * @return
     */
    public List<MainModle> parseTieBa(String result){
        List<MainModle> data = new ArrayList<>();

        Document doc = Jsoup.parse(result,"UTF-8");
        //置顶帖
        Elements items1 = doc.select("li[class=tl_top]");
        //解析置顶帖
        for(int i = 0; i< items1.size(); i++){
            MainModle mainModle = new MainModle();
            //得到发帖标题和内容,链接
            Elements tops = items1.get(i).select("div[class=ti_title]");
            Elements top = tops.select("span[class=ti_title_icon ti_icon_zhiding]");
            Elements contents = tops.select("span");
            String title = top.text().trim();
            String content = contents.text().trim();
            Elements href = items1.get(i).select("a[class = j_common ti_item no_border]");
            if(href.size() == 0){
                href = items1.get(i).select("a[class=j_common ti_item]");
            }
            String url = "http://tieba.baidu.com"+href.attr("href");
            mainModle.setTitle(title);
            mainModle.setContent(content);
            mainModle.setUrl(url);
            data.add(mainModle);
        }
        //其他帖
        Elements items2 = doc.select("li[class=tl_shadow]");
        //解析其他帖
        for(int i = 0; i < items2.size(); i++){
            MainModle mainModle = new MainModle();
            List<String> picList = new ArrayList<>();
            //得到发帖标题和内容,链接,图片
            Elements nomal = items2.get(i).select("a[class=j_common ti_item]");
            Elements titles = nomal.select("div[class=ti_title]");
            Elements contents = nomal.select("p[class=ti_abs]");
            Elements times = nomal.select("div[class=ti_infos clearfix]");
            Elements times2 = times.select("div[class=ti_author_time]");
            Elements spanTitle = titles.select("span");
            Elements spanTime = times2.select("span[class=ti_time]");
            String title = spanTitle.text().trim();
            String content = contents.text().trim();
            String url = "http://tieba.baidu.com"+nomal.attr("href");
            String time = spanTime.text().trim();
            mainModle.setTitle(title);
            mainModle.setContent(content);
            mainModle.setUrl(url);
            mainModle.setTime(time);
            Elements pictures = nomal.select("div[class=medias_wrap clearfix]");
            if(pictures.size() == 0){
            }else{
                Elements picture = pictures.select("div[class=medias_item]");
                for(int j = 0; j < picture.size(); j++){
                    Elements image = picture.get(j).select("img");
                    String imageUrl = image.attr("data-url");
                    if(!StringUtil.isEmpty(imageUrl)){
                        picList.add(imageUrl);
                    }
                }
                mainModle.setImageUrl(picList);
            }
            data.add(mainModle);
        }
        return data;
    }
}
