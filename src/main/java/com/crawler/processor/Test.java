package com.crawler.processor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Set;


/**
 * Created by guotao on 2017/7/14.
 * ${PACKAGE_NAME}.
 * governmentcrawler
 */
public class Test  {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(0).setTimeOut(3000);

    private static String companyName = "";

    //用来存储cookie信息
    private Set<Cookie> cookies;

    private WebDriver driver;

    public static void parseHtml(String content) {
        Document document = Jsoup.parse(content);
        Element elementTbody = document.select("#gridView > tbody").first();

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
        method1("c:/data/" + companyName + ".txt", origin);
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

        int sleepTime = 2000;
        int optionNum;
        int optionMax;
        int pageNum;

        WebDriver driver = new ChromeDriver();
        driver.get("http://hzrq.zhejqpgl.org");

        driver.findElement(By.id("txtcode")).clear();

        //在******中填你的用户名
        driver.findElement(By.id("txtcode")).sendKeys("13303000161");

        driver.findElement(By.id("txtpass")).clear();
        //在*******填你密码
        driver.findElement(By.id("txtpass")).sendKeys("123456");

        //模拟点击登录按钮
        driver.findElement(By.id("btnlogin")).click();

        driver.get("http://hzrq.zhejqpgl.org/djjg_city/DataList/GasUserList.aspx");
        try {
            Thread.sleep(sleepTime);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        optionNum = 5;
        optionMax = 45;
        while (optionNum <= optionMax) {
            companyName = driver.findElement(By.xpath(String.format("//*[@id=\"lstCompany\"]/option[%d]", optionNum))).getText();
            System.out.println("companyName = [" + companyName + "]");
            driver.findElement(By.xpath(String.format("//*[@id=\"lstCompany\"]/option[%d]", optionNum))).click();
            driver.findElement(By.xpath("//*[@id=\"btnSearch\"]")).click();
            try {
                Thread.sleep(sleepTime);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String pageInfo = driver.findElement(By.xpath("//*[@id=\"pager_pageinfo\"]")).getText();
            String[] temp1 = pageInfo.split("/");
            String[] temp2 = temp1[1].split(" ");
            System.out.println("temp2 = [" + temp2[0] + "]");
            System.out.println("pageInfo = [" + pageInfo + "]");
            pageNum = Integer.parseInt(temp2[0]);

            parseHtml(driver.getPageSource());
            //        System.out.println("driver.getPageSource() = [" + driver.getPageSource() + "]");


            for (int i = 0; i < pageNum - 1; i++) {
                driver.findElement(By.xpath("//*[@id=\"pager_btnNext\"]")).click();
                try {
                    Thread.sleep(sleepTime);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                parseHtml(driver.getPageSource());
            }
            optionNum++;
        }

        driver.close();
    }
}