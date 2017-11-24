package com.crawler.processor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import us.codecraft.webmagic.Site;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Set;


/**
 * Created by guotao on 2017/7/14.
 * ${PACKAGE_NAME}.
 * governmentcrawler
 */
public class GetDispatcher {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(0).setTimeOut(3000);

    private static String fileName = "送气工信息";

    //用来存储cookie信息
    private Set<Cookie> cookies;

    private WebDriver driver;

    public static void parseHtml(String content) {
        Document document = Jsoup.parse(content);
        Element elementTbody = document.select("#GridView1 > tbody").first();

        Elements elements = elementTbody.children();
        String origin = "";
        int i = 0;
        for (Element element : elements) {
            //  跳过第一行
            if (i++ == 0) {
                continue;
            }
            origin = origin + element.text() + "\n";
//            System.out.println("element = [" + element.text() + "]");
        }
        method1("c:/data/" + fileName + ".txt", origin);
    }

    /**
     * 追加文件：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true
     *
     */
    public static void method1(String file, String conent) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(conent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        int sleepTime = 1000;
        int optionNum;
        int optionMax;
        int pageNum;

        WebDriver driver = new ChromeDriver();
//        driver.manage().window().maximize();

        driver.get("http://hzrq.zhejqpgl.org");

        driver.findElement(By.id("txtcode")).clear();

        //在******中填你的用户名
        driver.findElement(By.id("txtcode")).sendKeys("13303000161");

        driver.findElement(By.id("txtpass")).clear();
        //在*******填你密码
        driver.findElement(By.id("txtpass")).sendKeys("123456");

        //模拟点击登录按钮
        driver.findElement(By.id("btnlogin")).click();

        driver.get("http://hzrq.zhejqpgl.org/djjg_city/datasearch/sqgsearch.aspx");
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String pageInfo = driver.findElement(By.xpath("//*[@id=\"Label4\"]")).getText();
        System.out.println("pageInfo = [" + pageInfo + "]");
        pageNum = Integer.parseInt(pageInfo);

        parseHtml(driver.getPageSource());

        for (int i = 0; i < pageNum - 1; i++) {
            driver.findElement(By.xpath("//*[@id=\"b3\"]")).click();
            try {
                Thread.sleep(sleepTime);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            parseHtml(driver.getPageSource());
        }

        driver.close();
    }
}