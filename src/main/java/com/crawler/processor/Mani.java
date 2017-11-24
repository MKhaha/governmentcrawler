package com.crawler.processor;

import com.crawler.downloader.SeleniumDownloader;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.Set;


/**
 * Created by guotao on 2017/7/14.
 * ${PACKAGE_NAME}.
 * governmentcrawler
 */
public class Mani implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(0).setTimeOut(3000);

    //用来存储cookie信息
    private Set<Cookie> cookies;

    private WebDriver driver;

    @Override
    public void process(Page page) {


        List<String> links = page.getHtml().links().all();

        System.out.println("page.getHtml().toString() = [" + page.getHtml().toString() + "]");

        driver.findElement(By.xpath("//*[@id=\"pager_btnNext\"]")).click();



//        //获取用户的id
//        page.putField("", page.getHtml().xpath("//div[@class='member_info_r yh']/h4/span/text()"));
//
//        //获取用户的详细信息
//        List<String> information = page.getHtml().xpath("//ul[@class='member_info_list fn-clear']//li/div[@class='fl pr']/em/text()").all();
//        page.putField("information = ", information);

    }

    //使用 selenium 来模拟用户的登录获取cookie信息
    public void login()
    {
        driver = new ChromeDriver();
        driver.get("http://60.190.114.126:12080/");

        driver.findElement(By.id("txtcode")).clear();

        //在******中填你的用户名
        driver.findElement(By.id("txtcode")).sendKeys("13303000011");

        driver.findElement(By.id("txtpass")).clear();
        //在*******填你密码
        driver.findElement(By.id("txtpass")).sendKeys("123456");

        //模拟点击登录按钮
        driver.findElement(By.id("btnlogin")).click();

        //获取cookie信息
        cookies = driver.manage().getCookies();
        driver.close();
    }
    @Override
    public Site getSite() {

        //将获取到的cookie信息添加到webmagic中
        for (Cookie cookie : cookies) {
            site.addCookie(cookie.getName(),cookie.getValue());
        }

        return site.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
    }

    public static void main(String[] args){
        Mani miai = new Mani();

        //调用selenium，进行模拟登录
        miai.login();
        Spider.create(miai)
                .addUrl("http://60.190.114.126:12080/djjg_city/DataList/GasUserList.aspx")
                .run();
    }
}