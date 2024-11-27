package com.example.demo.Facebook.commonFunc;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConfigCommonFunc {

    public WebDriver loginByCookie(){
        boolean isMac = System.getProperty("os.name").toLowerCase().contains("mac");
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
        if (isMac) {
            System.setProperty("webdriver.chrome.driver", "/Users/giapham/Documents/chromedriver-mac-x64/chromedriver");
        } else if(isWindows){
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\Downloads\\chromedriver-win64\\chromedriver.exe");
        }
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // Chạy headless để ẩn trình duyệt
        options.addArguments("--disable-gpu");  // Tắt GPU (đề phòng lỗi trên một số máy Mac)
        options.addArguments("--no-sandbox");  // Tăng ổn định
        options.addArguments("--disable-notifications");
        options.setExperimentalOption("detach", false);
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");

        WebDriver driver = new ChromeDriver(options);
        // Add cookies for login
        driver.get("https://www.facebook.com");
        List<Cookie> cookies = new ArrayList<>();

        cookies.add(new Cookie("c_user", "61568239606429"));//1
        cookies.add(new Cookie("datr", "mwg0Z9THOO1-yWEpQbBW07Sx"));//2
        cookies.add(new Cookie("i_user", "100063707646753"));//pageId 100066835222220=page op lung dien thoai
        cookies.add(new Cookie("locale", "en_US"));
        cookies.add(new Cookie("ps_l", "1"));
        cookies.add(new Cookie("ps_n", "1"));
        cookies.add(new Cookie("sb", "mwg0Z16z_I75ZUIAXFwsTihu"));//4
        cookies.add(new Cookie("wd", "872x75"));
        cookies.add(new Cookie("fr", "1K0gQHZbuaUpoSS03.AWV5LR_JDEgmfee-hSvtEMAEXEw.BnPZyS..AAA.0.0.BnPe1j.AWXlZtq1FII"));//3
        cookies.add(new Cookie("xs", "5%3AVnGxBiq6gMxsbA%3A2%3A1732240063%3A-1%3A7580"));

        // Add necessary cookies here
        for (Cookie cookie : cookies) {
            driver.manage().addCookie(cookie);
        }
        return driver;
    }

    public void scrollTopToEndPage(int scrollNumbers, WebDriver driver) throws InterruptedException {
        for(int i =0; i<scrollNumbers; i++){
            // Tạo đối tượng JavascriptExecutor
            JavascriptExecutor js = (JavascriptExecutor) driver;
            // Cuộn xuống cuối trang
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            // Chờ một lúc để kiểm tra
            Thread.sleep(2000);
//            System.out.println("Đã cuộn xuống cuối trang.");
        }
    }
}
