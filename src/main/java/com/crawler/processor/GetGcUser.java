package com.crawler.processor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import us.codecraft.webmagic.Site;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * Created by guotao on 2017/7/14.
 * ${PACKAGE_NAME}.
 * governmentcrawler
 */
public class GetGcUser {

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

    public static void mkDir(File file) {
        if (file.getParentFile().exists()) {
            if (!file.mkdir()) {
                System.out.println("创建文件返回失败");
            }
        } else {
            mkDir(file.getParentFile());
            if (!file.mkdir()) {
                System.out.println("创建文件返回失败");
            }

        }
    }

    // 判断文件夹是否存在
    public static void judeDirExists(String filePath) {

        File file = new File(filePath);
        if (file.exists()) {
            if (file.isDirectory()) {
                System.out.println("dir exists");
            } else {
                System.out.println("the same name file exists, can not create dir");
            }
        } else {
            System.out.println("文件夹不存在，创建文件夹 ：" + file);
            if (!file.mkdir()) {
                System.out.println("创建文件返回失败");
            }
        }

    }

    public static void main(String[] args) {

        int optionNum;
        int optionMax;
        int pageNum;
        String dirPath = "D:/data/燃气用户信息/";

        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String dirPathCurrentDate = dirPath + formatter.format(currentTime) + "/";
        System.out.println(dirPathCurrentDate);
        File file = new File(dirPathCurrentDate);
        mkDir(file);

        WebDriver driver = new ChromeDriver();
        try {
            Thread.sleep(3000);
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

        driver.get("http://hzrq.zhejqpgl.org/djjg_city/DataList/GasUserList.aspx");

        // 收集平阳县的燃气用户数据
        optionNum = 18;
        optionMax = 23;
        while (optionNum <= optionMax) {

            String companyName = driver.findElement(By.cssSelector(String.format("#lstCompany > option:nth-child(%d)", optionNum))).getText();
            String fileName = dirPathCurrentDate + companyName + ".txt";
            System.out.println("写入文件：" + fileName);

            driver.findElement(By.cssSelector(String.format("#lstCompany > option:nth-child(%d)", optionNum))).click();
            driver.findElement(By.cssSelector("#btnSearch")).click();

            String pageInfo = driver.findElement(By.cssSelector("#pager_pageinfo")).getText();
            String[] temp1 = pageInfo.split("/");
            String[] temp2 = temp1[1].trim().split(" ");
            pageNum = Integer.parseInt(temp2[0]);
            System.out.println("pageNum : " + pageNum );

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < pageNum - 1; i++) {
                stringBuilder.append(driver.findElement(By.cssSelector("#gridView > tbody")).getText());

                driver.findElement(By.cssSelector("#pager_btnNext")).click();

                if (i % 50 == 0) {
                    writeFile(fileName, stringBuilder.toString());
                    stringBuilder.setLength(0);
                }
            }
            if (stringBuilder.length() != 0) {
                writeFile(fileName, stringBuilder.toString());
                stringBuilder.setLength(0);
            }
            optionNum++;
        }

        driver.close();
    }
}