package com.crawler.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * @author code4crafter@gmail.com <br>
 */
public class SinaBlogProcessor implements PageProcessor {

    public static final String URL_LIST = "/col/col\\d+/index\\.html";

    public static final String URL_POST = "/art/.*\\.html";

    private Site site = Site
            .me()
            .setDomain("www.zjjxw.gov.cn");

    @Override
    public void process(Page page) {
        //列表页
        if (page.getUrl().regex(URL_LIST).match()) {
            List<String> links = page.getHtml().links().regex(URL_POST).all();
            System.out.println(links);
            page.addTargetRequests(links);

            List<String> listLinks = page.getHtml().links().regex(URL_LIST).all();
            System.out.println(listLinks);
            page.addTargetRequests(listLinks);
            //文章页
        } else {
            page.putField("url", page.getUrl().toString());
            page.putField("title", page.getHtml().xpath("//td[@class='title']/text()").toString());
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new SinaBlogProcessor()).addUrl("http://www.zjjxw.gov.cn/col/col1086962/index.html")
                .addPipeline(new ConsolePipeline())
                .run();
    }
}
