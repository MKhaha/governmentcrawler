package com.crawler.processor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
public class GetWenZhouTownShipFromXzqhOrg {

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

    private static void parseHtmlContent(String content) {
        Document document = Jsoup.parse(content);

        Element element = document.select("#main_content > table > tbody > tr > td > table > tbody").first();
        String regionString = document.select("#main_content > table > tbody > tr > td > table > tbody > tr:nth-child(2) > td > span").text();
        List<Element> elementChild = element.children();

        int index = 1;
        for (Element element1 : elementChild) {
            if (index++ < 4) {
                continue;
            }
            stringBuffer.append(element1.text() + " " + regionString + "\n");
            System.out.println(element1.text() + " " + regionString);
        }
    }

    public static void main(String[] args){

        WebDriver driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        for (int regionId = 5; regionId <= 15; regionId++) {

            driver.get("https://xingzhengquhua.51240.com/330300000000__xingzhengquhua/");

            driver.findElement(By.cssSelector(
                    String.format("#main_content > table > tbody > tr > td > table > tbody > tr:nth-child(%d) > td:nth-child(1) > a", regionId))).click();

            String content = driver.findElement(By.cssSelector("#main_content > table > tbody > tr > td > table > tbody")).getText();
            parseHtmlContent(driver.getPageSource());
        }

        writeFile("C:\\Users\\Administrator\\Desktop\\townShip.txt", stringBuffer.toString());

        driver.close();

    }
}