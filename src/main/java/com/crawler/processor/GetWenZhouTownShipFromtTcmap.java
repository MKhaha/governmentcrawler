package com.crawler.processor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by guotao on 2017/7/14.
 * ${PACKAGE_NAME}.
 * governmentcrawler
 */
public class GetWenZhouTownShipFromtTcmap {

    static StringBuffer stringBuffer = new StringBuffer();

    /**
     * 追加文件：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true
     *
     */
    private static void writeFile(String file, String content) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void parseHtmlContent(String url) {

        System.out.println(url);

        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements elements = document.select("#page_left > div:nth-child(6) > table > tbody > tr");
        int index = 0;
        for (Element element : elements) {

            if (index++ == 0) {
                continue;
            }

            Elements elementsChild = element.children();


            System.out.println(elementsChild.get(0).text() + " " + elementsChild.get(1).text());
        }
    }

    public static void main(String[] args){

        WebDriver driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


        for (int index = 2; index <= 12; index++) {
            String httpAddress = "http://www.tcmap.com.cn/zhejiangsheng/wenzhou.html";

            driver.get(httpAddress);

            String cssSelector = String.format("#page_left > div:nth-child(6) > table > tbody > tr:nth-child(%d) > td:nth-child(1) > strong > a", index);

            WebElement webElement = driver.findElement(By.cssSelector(cssSelector));

            String url = webElement.getAttribute("href");
            System.out.println(url);

            parseHtmlContent(url);
        }

        driver.close();

    }
}