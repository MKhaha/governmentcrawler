package com.crawler.processor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import us.codecraft.webmagic.Site;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.TimeUnit;


/**
 * Created by guotao on 2017/7/14.
 * ${PACKAGE_NAME}.
 * governmentcrawler
 */
public class GetGcDispatcher {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(0).setTimeOut(3000);

    private WebDriver driver;

    /**
     * 追加文件：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true
     *
     */
    public static void writeFile(String file, String content) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(content + "\n");
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

        System.setProperty("webdriver.chrome.driver", "D:/data/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("http://hzrq.zhejqpgl.org");

        driver.findElement(By.id("txtcode")).clear();

        //在******中填你的用户名
        driver.findElement(By.id("txtcode")).sendKeys("13303000161");

        driver.findElement(By.id("txtpass")).clear();
        //在*******填你密码
        driver.findElement(By.id("txtpass")).sendKeys("123456");

        //模拟点击登录按钮
        driver.findElement(By.id("btnlogin")).click();

        driver.get("http://hzrq.zhejqpgl.org/Record/DeliveryList.aspx");

        driver.findElement(By.cssSelector("#deliver_date1")).clear();
        driver.findElement(By.cssSelector("#deliver_date1")).sendKeys("2017-7-1");

        optionNum = 3;
        // 最大47
        optionMax = 47;
        while (optionNum <= optionMax) {
            String companyName = driver.findElement(By.cssSelector(String.format("#lstCompany > option:nth-child(%d)", optionNum))).getText();
            System.out.println("companyName = [" + companyName + "]");
            String fileName = "D:/data/配送信息/配送信息" + companyName + ".txt";
            driver.findElement(By.cssSelector(String.format("#lstCompany > option:nth-child(%d)", optionNum))).click();
            driver.findElement(By.cssSelector("#btnSearch")).click();

            String pageInfo = driver.findElement(By.cssSelector("#pager_lblInfo")).getText();
            String[] temp1 = pageInfo.split("/");
//            for (String item : temp1) {
//                System.out.println("item = [" + item + "]");
//            }
            String[] temp2 = temp1[1].trim().split(" ");
            System.out.println("temp2 = [" + temp2[0] + "]");
            System.out.println("pageInfo = [" + pageInfo + "]");
            pageNum = Integer.parseInt(temp2[0]);

//            System.out.println("driver.findElement(By.cssSelector(\"#grid > tbody\")).getText(); = [" + driver.findElement(By.cssSelector("#grid > tbody")).getText() + "]");
            Long startTime = System.nanoTime();
            writeFile(fileName, driver.findElement(By.cssSelector("#grid > tbody")).getText());
            Long endTime = System.nanoTime();
            System.out.println("deltaTime = [" + (endTime - startTime) + "]");

            String fileContent = "";
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < pageNum - 1; i++) {
                driver.findElement(By.xpath("//*[@id=\"pager_btnNext\"]")).click();

                pageInfo = driver.findElement(By.cssSelector("#pager_lblInfo")).getText();
                temp1 = pageInfo.split("/");
                temp2 = temp1[1].split(" ");
                System.out.println("temp2 = [" + temp2[0] + "]");
                System.out.println("pageInfo = [" + pageInfo + "]");

                stringBuilder.append(driver.findElement(By.cssSelector("#grid > tbody")).getText());


                if (i % 50 == 0) {
                    startTime = System.nanoTime();
                    writeFile(fileName, stringBuilder.toString());
                    stringBuilder.setLength(0);
                    endTime = System.nanoTime();
                    System.out.println("deltaTime = [" + (endTime - startTime) + "]");
                }
            }
            if (stringBuilder.length() != 0) {
                startTime = System.nanoTime();
                writeFile(fileName, stringBuilder.toString());
                stringBuilder.setLength(0);
                endTime = System.nanoTime();
                System.out.println("deltaTime = [" + (endTime - startTime) + "]");
            }
            optionNum++;
        }

        driver.close();
    }
}