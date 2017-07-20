package com.crawler.crawlerFolder;

import com.crawler.downloader.SeleniumDownloader;
import com.crawler.pipeline.MysqlPipeline;
import com.crawler.processor.ZjjxwDocumentPageProcessor;
import us.codecraft.webmagic.Spider;

/**
 * @author code4crafer@gmail.com
 *         Date: 13-6-23
 *         Time: 下午4:19
 */
public class ZjjxwDocumentCrawler {

    public static void crawl() {
        Spider.create(new ZjjxwDocumentPageProcessor()).addUrl("http://www.zjjxw.gov.cn/col/col1087010/index.html")
                .setDownloader(new SeleniumDownloader())
                .addPipeline(new MysqlPipeline())
                .run();
    }

    public static void main(String[] args) {
        ZjjxwDocumentCrawler zjjxwDocumentCrawler = new ZjjxwDocumentCrawler();
        ZjjxwDocumentCrawler.crawl();
    }
}
