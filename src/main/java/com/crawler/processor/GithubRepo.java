package com.crawler.processor;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

/**
 * Created by Administrator on 2017/7/14.
 */
//@TargetUrl({"/art/*.html", "http://www.zjjxw.gov.cn/art/*.html"})
//@HelpUrl({"http://www.zjjxw.gov.cn","/col/col\\d+/index.html", "http://www.zjjxw.gov.cn/col/col\\d+/index.html",
//        "http://www.zjjxw.gov.cn/col/col\\d+/index.html\\?uid=\\d+&amp;pageNum=\\d+", "/col/col\\d+/index.html\\?uid=\\d+&amp;pageNum=\\d+"})
@TargetUrl("http://www\\.zjjxw\\.gov\\.cn/col/col1086962/index\\.html\\?uid=2996130&amp;pageNum=2")
//@HelpUrl({"http://www.zjjxw.gov.cn","/col/col\\d+/index.html", "http://www.zjjxw.gov.cn/col/col\\d+/index.html"})
public class GithubRepo {

    @ExtractBy(value = "//td[@class='title']/text() | //*[@id='article']/tbody/tr[1]/td/text()")
//    @ExtractBy(value = "<!--<\\$\\[标题名称\\(html\\)\\]>begin-->(.*)<!--<\\$\\[标题名称\\(html\\)\\]>end--> | ", type = ExtractBy.Type.Regex)
    private String name;

    @ExtractBy(value = "/html/body/table[2]/tbody/tr/td[1]/table/tbody/tr/td/table[1]/tbody/tr[1]/td/span/font/strong/text() | " +
            "/html/body/table[2]/tbody/tr/td[1]/table/tbody/tr/td/table[1]/tbody/tr[1]/td/text()")
    private String nameStrong;

    @ExtractBy(value = "//*[@id=\"barrierfree_container\"]/table[2]/tbody/tr/td/table[1]/tbody/tr[1]/td/text()")
    private String nameSpecialFind;


    @ExtractByUrl()
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameStrong() {
        return nameStrong;
    }

    public void setNameStrong(String nameStrong) {
        this.nameStrong = nameStrong;
    }

    public String getNameSpecialFind() {
        return nameSpecialFind;
    }

    public void setNameSpecialFind(String nameSpecialFind) {
        this.nameSpecialFind = nameSpecialFind;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static void main(String[] args) {
        OOSpider.create(Site.me()
                        .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36")
                        .setDomain("www.zjjxw.gov.cn")
                        .setSleepTime(1000),
                new ConsolePageModelPipeline(),
                GithubRepo.class)
                .addUrl("http://www.zjjxw.gov.cn/col/col1086962/index.html").thread(5).run();
    }
}
