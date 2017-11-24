package com.crawler.downloader;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.PlainText;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

/**
 * 使用Selenium调用浏览器进行渲染。目前仅支持chrome。<br>
 * 需要下载Selenium driver支持。<br>
 *
 * @author code4crafter@gmail.com <br>
 *         Date: 13-7-26 <br>
 *         Time: 下午1:37 <br>
 */
public class SeleniumDownloader implements Downloader, Closeable {

	private volatile WebDriverPool webDriverPool;

	private Logger logger = Logger.getLogger(getClass());

	private int sleepTime = 1;

	private int poolSize = 1;

	private static final String DRIVER_PHANTOMJS = "phantomjs";

	/**
	 * 新建
	 *
	 * @param chromeDriverPath chromeDriverPath
	 */
	public SeleniumDownloader(String chromeDriverPath) {
		System.getProperties().setProperty("webdriver.chrome.driver",
				chromeDriverPath);
	}

	/**
	 * Constructor without any filed. Construct PhantomJS browser
	 * 
	 * @author bob.li.0718@gmail.com
	 */
	public SeleniumDownloader() {
		// System.setProperty("phantomjs.binary.path",
		// "/Users/Bingo/Downloads/phantomjs-1.9.7-macosx/bin/phantomjs");
	}

	/**
	 * set sleep time to wait until load success
	 *
	 * @param sleepTime sleepTime
	 * @return this
	 */
	public SeleniumDownloader setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
		return this;
	}

	@Override
	public Page download(Request request, Task task) {
		checkInit();
		WebDriver webDriver;
		try {
			webDriver = webDriverPool.get();
		} catch (InterruptedException e) {
			logger.warn("interrupted", e);
			return null;
		}
		logger.info("downloading page " + request.getUrl());
		webDriver.get(request.getUrl());
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebDriver.Options manage = webDriver.manage();
		Site site = task.getSite();

		Cookie cookie = new Cookie("ASP.NET_SessionId", "ootiqjuowcmmtwmz2kfm5jim");
		manage.addCookie(cookie);
		Cookie cookie2 = new Cookie(".ASPXAUTH", "15E2AF9A323CA07E9098B5E8D2CB327F463CFE479CDD7AB2BE13DB42FB340308D94BD276A0A62531E918332E9251B8964EF0A306D25E23A9448A15321FEF28EBF6FD48B92EC873A4CF3911B494583AF076ABB45A1D71D4EBDE39AAAFB69CD8BC572102F6FFE052CAEDBB1554D48B89625289B3E8");
		manage.addCookie(cookie2);

//		site.addCookie("ASP.NET_SessionId", "ootiqjuowcmmtwmz2kfm5jim");
//		site.addCookie(".ASPXAUTH", "15E2AF9A323CA07E9098B5E8D2CB327F463CFE479CDD7AB2BE13DB42FB340308D94BD276A0A62531E918332E9251B8964EF0A306D25E23A9448A15321FEF28EBF6FD48B92EC873A4CF3911B494583AF076ABB45A1D71D4EBDE39AAAFB69CD8BC572102F6FFE052CAEDBB1554D48B89625289B3E8");

//		if (site.getCookies() != null) {
//			for (Map.Entry<String, String> cookieEntry : site.getCookies()
//					.entrySet()) {
//				Cookie cookie = new Cookie(cookieEntry.getKey(),
//						cookieEntry.getValue());
//				manage.addCookie(cookie);
//			}
//		}

		/*
		 * TODO You can add mouse event or other processes
		 * 
		 * @author: bob.li.0718@gmail.com
		 */

		WebElement webElement = webDriver.findElement(By.xpath("/html"));
		String content = webElement.getAttribute("outerHTML");
		Page page = new Page();
		page.setRawText(content);
		page.setHtml(new Html(content, request.getUrl()));
		page.setUrl(new PlainText(request.getUrl()));
		page.setRequest(request);
		webDriverPool.returnToPool(webDriver);
		return page;
	}

	private void checkInit() {
		if (webDriverPool == null) {
			synchronized (this) {
				webDriverPool = new WebDriverPool(poolSize);
			}
		}
	}

	@Override
	public void setThread(int thread) {
		this.poolSize = thread;
	}

	@Override
	public void close() throws IOException {
		webDriverPool.closeAll();
	}
}
