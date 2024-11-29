package com.example.demo.Facebook.services;

import com.example.demo.Facebook.commonFunc.ConfigCommonFunc;
import com.example.demo.Facebook.commonFunc.ConfigCommonFuncFirefox;
import com.example.demo.Facebook.models.GetUidUserInGroupModel;
import com.example.demo.common.GenericResponse;
import org.openqa.selenium.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GetUidUserInGroupService {

    @Autowired
    ConfigCommonFuncFirefox configCommonFunc;

    public GenericResponse getUidUser(GetUidUserInGroupModel getUidUserInGroupModel) throws InterruptedException {
        GenericResponse rs = new GenericResponse();
        //Login by cookie
//        if (getUidUserInGroupModel.getTypeComp().toUpperCase().equals("MAC")) {
//            System.setProperty("webdriver.chrome.driver", "/Users/giapham/Documents/chromedriver-mac-x64/chromedriver");
//        } else {
//            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\Downloads\\chromedriver-win64\\chromedriver.exe");
//        }
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless=new"); // Chạy headless để ẩn trình duyệt
//        options.addArguments("--disable-gpu");  // Tắt GPU (đề phòng lỗi trên một số máy Mac)
//        options.addArguments("--no-sandbox");  // Tăng ổn định
//        options.addArguments("--disable-notifications");
//        options.setExperimentalOption("detach", false);
//        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");
//
//        WebDriver driver = new ChromeDriver(options);
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//
//        // Add cookies for login
//        driver.get("https://www.facebook.com");
//        List<Cookie> cookies = new ArrayList<>();
//
//        cookies.add(new Cookie("c_user", "61568239606429"));//1
//        cookies.add(new Cookie("datr", "mwg0Z9THOO1-yWEpQbBW07Sx"));//2
//        cookies.add(new Cookie("i_user", "100066835222220"));//page
//        cookies.add(new Cookie("locale", "en_US"));
//        cookies.add(new Cookie("ps_l", "1"));
//        cookies.add(new Cookie("ps_n", "1"));
//        cookies.add(new Cookie("sb", "mwg0Z16z_I75ZUIAXFwsTihu"));//4
//        cookies.add(new Cookie("wd", "872x75"));
//        cookies.add(new Cookie("fr", "1K0gQHZbuaUpoSS03.AWV5LR_JDEgmfee-hSvtEMAEXEw.BnPZyS..AAA.0.0.BnPe1j.AWXlZtq1FII"));//3
//        cookies.add(new Cookie("xs", "14%3APDjZjuM9Bp0zjw%3A2%3A1732111650%3A-1%3A-1"));
//
//        // Add necessary cookies here
//        for (Cookie cookie : cookies) {
//            driver.manage().addCookie(cookie);
//        }
        // End Login by cookie
        WebDriver driver = configCommonFunc.loginByCookie(getUidUserInGroupModel.getPageId());
        // Navigate to the post page after adding cookies
        driver.navigate().to("https://facebook.com/groups/" + getUidUserInGroupModel.getGroupId() + "/members");
        // Scroll top to end
        //        for (int i = 0; i < getUidUserInGroupModel.getScrollNumbers(); i++) {
//            // Tạo đối tượng JavascriptExecutor
//            JavascriptExecutor js = (JavascriptExecutor) driver;
//            // Cuộn xuống cuối trang
//            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
//            // Chờ một lúc để kiểm tra
//            Thread.sleep(2000);
//            System.out.println("Đã cuộn xuống cuối trang.");
//        }
        // End Scroll top to end
        configCommonFunc.scrollTopToEndPage(getUidUserInGroupModel.getScrollNumbers(),driver);
        try {
            List<WebElement> links = driver.findElements(By.tagName("a"));
            Set<String> uniqueNumbers = new HashSet<>();

            // In ra các URL hợp lệ
            for (WebElement link : links) {
                String url = link.getAttribute("href");
                if (url != null && !url.isEmpty() && url.contains("groups/" + getUidUserInGroupModel.getGroupId() + "/user")) {
                    Pattern pattern = Pattern.compile("/user/(\\d+)");
                    Matcher matcher = pattern.matcher(url);
                    if (matcher.find()) {
                        uniqueNumbers.add(matcher.group(1)); // Add number to the Set
                    }
                }
            }
            System.out.println("Total users in groups: " + uniqueNumbers.size());
            Map resultData = new HashMap();
            resultData.put("totalUser", uniqueNumbers.size());
            resultData.put("dataUid", uniqueNumbers);
            rs.setData(resultData);
            rs.setMessage("Get All Data Uid");
            driver.quit();
            return rs;
        }catch (Exception e){
            rs.setData(null);
            rs.setMessage("Get All Data Uid Failed");
            driver.quit();
            System.out.println(e);
            return rs;
        }
    }
}