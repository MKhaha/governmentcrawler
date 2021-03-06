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
public class GetWenZhouTownShipFromStats {

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

        for (int regionId = 3; regionId <= 13; regionId++) {

            driver.get("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/33/3303.html");

            String parentRegionString = driver.findElement(
                    By.cssSelector(String.format("body > table:nth-child(3) > tbody > tr:nth-child(1) > " +
                            "td > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > table > tbody > tr:nth-child(%d)", regionId))).getText();
            System.out.println("parentRegionString = [" + parentRegionString + "]");
//            stringBuffer.append(parentRegionString);

            driver.findElement(
                    By.cssSelector(String.format("body > table:nth-child(3) > tbody > tr:nth-child(1) > " +
                            "td > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > table > tbody > tr:nth-child(%d) > td:nth-child(1) > a", regionId))).click();

            String childRegionTable = driver.findElement(
                    By.cssSelector("body > table:nth-child(3) > tbody > tr:nth-child(1) > td > table > tbody > tr:nth-child(2) > td > table > tbody > tr > td > table")).getText();
            System.out.println("childRegionTable = [" + childRegionTable + "]");
            stringBuffer.append(childRegionTable + "\n");

        }

        writeFile("C:\\Users\\Administrator\\Desktop\\townShip2.txt", stringBuffer.toString());

        driver.close();

    }
}