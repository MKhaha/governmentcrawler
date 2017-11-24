package com.crawler.processor;

import com.crawler.downloader.SeleniumDownloader;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by guotao on 2017/7/14.
 * ${PACKAGE_NAME}.
 * governmentcrawler
 */
public class WenZhouRegionPageProcessor implements PageProcessor {

    private Site site = Site.me().setDomain("http://www.xzqh.org");

    private static final String URL_DOMAIN = "http://www.zjjxw.gov.cn";
    private static final String URL_LIST = ".*/col/col\\d+/index\\.html.*";
    private static final String URL_LIST_WITHOUT_DOMAIN = "^/col/col\\d+/index\\.html.*";
    private static final String URL_LIST_WITH_PAGENUM = "http://www.zjjxw.gov.cn/col/col\\d+/index\\.html\\?uid=\\d+&pageNum=\\d+";
    private static final String URL_POST = ".*/art/.*/art_\\d+_\\d+\\.html";
    private static final String URL_POST_WITHOUT_DOMAIN = "^/art/.*/art_\\d+_\\d+\\.html";
    private static final String URL_ATTACHMENT = ".*/module/download/downfile\\.jsp.*";


    @Override
    public void process(Page page) {

        if (page.getUrl().regex(URL_LIST_WITHOUT_DOMAIN).match() || page.getUrl().regex(URL_POST_WITHOUT_DOMAIN).match()) {
            System.out.println("match1");
            page.addTargetRequest(URL_DOMAIN + page.getUrl().toString());
            page.setSkip(true);
            return;
        }

        if (page.getUrl().regex(URL_LIST).match()) {
            System.out.println("match2");
            if (page.getUrl().regex(URL_LIST_WITH_PAGENUM).match()) {
                System.out.println("match3");

                // 首先判断当前页面是否小于等于总页面，如果不是，抛弃这样的页面
                String totalPageNumString = page.getHtml().xpath("span[@class='default_pgTotalPage']/text()").toString();
                int totalPageNum = Integer.parseInt(totalPageNumString);
                System.out.println(totalPageNum);

                String currentPageNumString = page.getUrl().regex(".*pageNum=(\\d+)").toString();
                System.out.println(currentPageNumString);
                int currentPageNum = Integer.parseInt(currentPageNumString);
                if (currentPageNum > totalPageNum) {
                    page.setSkip(true);
                    return;
                }

            }

            page.setSkip(true);

            // 当前页面小于等于总页面，获取当前页面所有链接，加入链接集合中
            List<String> listLinks = page.getHtml().links().regex(URL_LIST).all();
            System.out.println(listLinks);
            List<String> postLinks = page.getHtml().links().regex(URL_POST).all();
            System.out.println(postLinks);
            page.addTargetRequests(listLinks, 1);
            page.addTargetRequests(postLinks, 1000);


        } else if (page.getUrl().regex(URL_POST).match()) {
            // 具体文章页面，获取文章标题和页面url

            System.out.println("match234");

            // 第一梯队中是否有合适的文章标题
            String majorTitle = page.getHtml().xpath("//td[@class='title']/text()").toString();
            if (StringUtils.isBlank(majorTitle)) {
                majorTitle = page.getHtml().xpath("//*[@id='article']/tbody/tr[1]/td/text()").toString();
            }

            // 查找第二、三梯队是否有合适的文章标题
            if (StringUtils.isBlank(majorTitle)) {
                majorTitle = page.getHtml().xpath("//*[@id=\"barrierfree_container\"]/table[2]/tbody/tr/td/table[1]/tbody/tr[1]/td/text()").toString();
                if (StringUtils.isBlank(majorTitle)) {
                    String nameStrong1 = page.getHtml().xpath("/html/body/table[2]/tbody/tr/td[1]/table/tbody/tr/td/table[1]/tbody/tr[1]/td/span/font/strong/text()").toString();
                    String nameStrong2 = page.getHtml().xpath("/html/body/table[2]/tbody/tr/td[1]/table/tbody/tr/td/table[1]/tbody/tr[1]/td/text()").toString();

                    majorTitle = StringUtils.isNotBlank(nameStrong1) ? nameStrong1 : nameStrong2;

                    if (StringUtils.isBlank(majorTitle)) {
                        // 没找到文章标题
                        page.setSkip(true);
                        return;
                    }
                }
            }

            System.out.println(majorTitle);
            /* 将下列数据项导入结果中：
            title,url,attachmentUrl,date,content
             */
            page.putField("title", majorTitle);
            page.putField("url", page.getUrl().toString());

            List<String> attachmentUrlList = page.getHtml().links().regex(URL_ATTACHMENT).all();
            page.putField("attachmentUrl", String.join(",", attachmentUrlList));
            String dateString = page.getUrl().regex(".*/art/(.*)/art_.*").toString();
            try {
                Date date = new SimpleDateFormat("yyyy/MM/dd").parse(dateString);
                System.out.println(date);
                page.putField("date", date);
            } catch (ParseException e) {
                page.putField("date", dateString);
            }
            String content = page.getHtml().xpath("//*[@id=\"barrierfree_container\"]/table[2]/tbody/tr[2]/td/table/tbody").smartContent().toString();
            //*[@id="barrierfree_container"]/table[2]/tbody/tr[2]/td/table/tbody
            page.putField("content", content);
            System.out.println(content);

        } else {
            System.out.println("page match error:");
            System.out.println(page.getUrl());
        }

    }

    @Override
    public Site getSite() {
        return site;

    }

    public static void main(String[] args) {
        Spider.create(new WenZhouRegionPageProcessor()).addUrl("http://www.xzqh.org/html/list/122.html")
                .setDownloader(new SeleniumDownloader())
                .addPipeline(new FilePipeline("D:\\webmagic\\"))
                .run();
    }
}
