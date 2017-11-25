package com.crawler.processor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
public class GetGcFilling {

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
        String fileName = "D:/data/fillingInfo.txt";

        WebDriver driver = new ChromeDriver();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("http://hzrq.zhejqpgl.org");

        driver.findElement(By.cssSelector("#txtcode")).clear();
        driver.findElement(By.cssSelector("#txtcode")).sendKeys("13303000161");
        driver.findElement(By.cssSelector("#txtpass")).clear();
        driver.findElement(By.cssSelector("#txtpass")).sendKeys("123456");
        driver.findElement(By.cssSelector("#btnlogin")).click();


        driver.get("http://hzrq.zhejqpgl.org/Record/FillingList.aspx");
        driver.findElement(By.cssSelector("#txtFillDateStart")).clear();
        driver.findElement(By.cssSelector("#txtFillDateStart")).sendKeys("2017-7-1");
        driver.findElement(By.cssSelector("#btnSearch")).click();


        // 获取页数
        String pageInfo = driver.findElement(By.cssSelector("#Pager1_lblInfo")).getText();
        String[] temp1 = pageInfo.split("/");
        for (String item : temp1) {
            System.out.println("item = [" + item + "]");
        }
        String[] temp2 = temp1[1].trim().split(" ");
        System.out.println("temp2 = [" + temp2[0] + "]");
        System.out.println("pageInfo = [" + pageInfo + "]");
        pageNum = Integer.parseInt(temp2[0]);


        // 将第一页写入文件
        String cssSelectorFindBody = "#gridView > tbody";
        writeFile(fileName, driver.findElement(By.cssSelector(cssSelectorFindBody)).getText());

        // 抓取数据写入文件的缓存
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < pageNum - 1; i++) {
            System.out.println("pageNumber : " + (i + 2));
            driver.findElement(By.cssSelector("#Pager1_btnNext")).click();

            stringBuilder.append(driver.findElement(By.cssSelector(cssSelectorFindBody)).getText());

            if (i % 50 == 0) {
                writeFile(fileName, stringBuilder.toString());
                stringBuilder.setLength(0);
            }
        }
        if (stringBuilder.length() != 0) {
            writeFile(fileName, stringBuilder.toString());
            stringBuilder.setLength(0);
        }

        driver.close();
    }
}