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
public class WenZhouGasProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(0).setTimeOut(3000);

    @Override
    public void process(Page page) {

        String content = page.getHtml().xpath("//*[@id=\"gridView\"]").toString();

        System.out.println("content = [" + content + "]");
        page.putField("content", content);


    }

    @Override
    public Site getSite() {

        site.addCookie("ASP.NET_SessionId", "ootiqjuowcmmtwmz2kfm5jim");
        site.addCookie(".ASPXAUTH", "15E2AF9A323CA07E9098B5E8D2CB327F463CFE479CDD7AB2BE13DB42FB340308D94BD276A0A62531E918332E9251B8964EF0A306D25E23A9448A15321FEF28EBF6FD48B92EC873A4CF3911B494583AF076ABB45A1D71D4EBDE39AAAFB69CD8BC572102F6FFE052CAEDBB1554D48B89625289B3E8");
        site.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");

        return site;

    }

    public static void main(String[] args) {
        Spider.create(new WenZhouGasProcessor()).addUrl("http://60.190.114.126:12080/mainframe_djjg_city.aspx")
                .setDownloader(new SeleniumDownloader())
                .addPipeline(new FilePipeline("D:\\webmagic\\"))
                .run();
    }
}
