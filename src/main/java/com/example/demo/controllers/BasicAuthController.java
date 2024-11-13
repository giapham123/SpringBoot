package com.example.demo.controllers;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.interactions.Actions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.chrome.ChromeOptions;

@RestController
@RequestMapping("/api/basic")
public class BasicAuthController {

    @GetMapping("/test")
    public ResponseEntity<String> secureEndpointBasic(String urlPost) {
        //For Win
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\Downloads\\chromedriver-win64\\chromedriver.exe");
        //For Mac
//        System.setProperty("webdriver.chrome.driver", "/Users/giapham/Documents/chromedriver-mac-x64/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.setExperimentalOption("detach", false);

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Add cookies for login
        driver.get("https://www.facebook.com");
        List<Cookie> cookies = new ArrayList<>();
        cookies.add(new Cookie("b_user", "61560391100126"));
        cookies.add(new Cookie("c_user", "100007769235838"));
        cookies.add(new Cookie("datr", "U75EZr-96XGfZQxL1SfKqCYg"));
        cookies.add(new Cookie("fr", "19iXxFLGIGy6rFS4n.AWVnnE0jjG7xGNiQZLcoqULdjWs.BnM0mu..AAA.0.0.BnM1LE.AWXswWiNdXQ"));
        cookies.add(new Cookie("i_user", "61551932516721"));
        cookies.add(new Cookie("locale", "en_US"));
        cookies.add(new Cookie("ps_l", "1"));
        cookies.add(new Cookie("ps_n", "1"));
        cookies.add(new Cookie("sb", "JmIpZG_pK3duwRSUYwZA-Mwa"));
        cookies.add(new Cookie("wd", "906x983"));
        cookies.add(new Cookie("xs", "33%3A7BAMKsuAgazkTA%3A2%3A1717076724%3A-1%3A6191%3ACrRmDO5Ou4vwHA%3AAcVbaDQhIKglET53RN6j-0t7Eg6xpJnYtqODor0EAkKG"));

        // Add necessary cookies here
        for (Cookie cookie : cookies) {
            driver.manage().addCookie(cookie);
        }

        // Navigate to the post page after adding cookies
        driver.navigate().to("https://www.facebook.com/tuvanphapluat.hp/posts/122206677680064417");
        List<String> groupName = new ArrayList<>();
//        groupName.add("LUẬT SƯ VIỆT NAM");
//        groupName.add("Luật sư tư vấn miễn phí");
//        groupName.add("Luật sư Tư vấn Pháp luật 247");
//        groupName.add("Học Luật Đừng Học Đại");
//        groupName.add("Cùng Hiểu Luật");
//        groupName.add("Việc làm Nghề Luật - Tư vấn Pháp Luật - HLU");
//        groupName.add("Hội những người thích Học Luật ☑\uFE0F");
//        groupName.add("NGHỀ LUẬT VÀ VIỆC LÀM");
//        groupName.add("CỘNG ĐỒNG SINH VIÊN LUẬT");
//        groupName.add("SINH VIÊN NGÀNH LUẬT");
//        groupName.add("Học Luật");
//        groupName.add("Diễn đàn luật sư LOF");
//        groupName.add("CHUYỆN NGHỀ LUẬT SƯ");
//        groupName.add("Luật sư tư vấn Pháp luật miễn phí");
//        groupName.add("TÔI HỌC LUẬT - ULAW");
//        groupName.add("LUẬT SƯ TƯ VẤN PHÁP LUẬT MIỄN PHÍ TẠI THÀNH PHỐ HỒ CHÍ MINH");
//        groupName.add("Luật Sư tư vấn Doanh Nghiệp, Hộ Kinh doanh, Thuế, Giấy phép con miễn phí");
//        groupName.add("Tư vấn ly hôn miễn phí toàn quốc");
//        groupName.add("LUẬT SƯ TƯ VẤN LY HÔN MIỄN PHÍ (Thuận tình, Đơn phương, Yếu tố nước ngoài)");
//        groupName.add("Tư vấn luật miễn phí từ luật sư giỏi");
//        groupName.add("LUẬT SƯ HÌNH SỰ - TƯ VẤN MIỄN PHÍ");
//        groupName.add("DIỄN ĐÀN NHỮNG NGƯỜI HÀNH NGHỀ LUẬT");
//        groupName.add("Ở đây có Luật sư - Tư vấn pháp luật");
//        groupName.add("NGHIỆN LUẬT");
//        groupName.add("Luật Sư Tư Vấn Luật Miễn Phí-Luật Sư Tư Vấn Luật Miễn Phí");
//        groupName.add("Cộng đồng hỗ trợ tư vấn pháp luật miễn phí (Thuế, Đất đai, Doanh nghiệp...)");
//        groupName.add("CỘng ĐỒng Tư Vấn Pháp Luật");
//        groupName.add("Luật Sư Tư Vấn Pháp Luật Miễn Phí Online");
//        groupName.add("Báo Pháp Luật");
        try {
            for (String group : groupName) {
                System.out.println("Group Name: " + group);
                //Click share at post
                WebElement clickShare = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[3]/div/div/div[2]/span/span")));
                clickShare.click();
                //Click group at popup share
                WebElement clickGroup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[4]/div/div/div/div/div/i")));
                clickGroup.click();
                //Input search for find group
                WebElement inputSearch = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[2]/div/div/div/div/div/div/div/label/input")));
                inputSearch.click();
                WebElement inputSearchValue = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[2]/div/div/div/div/div/div/div/label/input")));
                String targetGroup = group;  // Replace with your target group name
                inputSearchValue.sendKeys(targetGroup);
                Thread.sleep(2000); // Wait for the next set of groups to load
                //Select group for share
                try {
                    WebElement clickSelectGroup = wait.until(
                            ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(.,'" + group + "')]"))
                    );
                    clickSelectGroup.click();
                }
                catch(Exception e){
                    System.out.println("Dont have this group: "+ groupName);
                    continue;
                }
                //Click post
                WebElement clickPost = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[3]/div[3]/div/div/div")));
                clickPost.click();

                // Pause for a few seconds to ensure the post completes
                Thread.sleep(10000);
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }

        return ResponseEntity.ok("Basic Auth API accessed!");
    }

    @PostMapping("/test11")
    public ResponseEntity<String> secureEndpointBasic1() {
        System.setProperty("webdriver.chrome.driver", "D:\\PROJECT\\chromedriver-win64\\chromedriver.exe");

        try {
            String userProfilePath = "C:\\Users\\mafc4568\\AppData\\Local\\Google\\Chrome\\User Data";  // Windows path


            // Configure ChromeOptions to use existing Chrome profile
            ChromeOptions options = new ChromeOptions();
            options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");

            // Path to Chrome's user data directory
            options.addArguments("user-data-dir=" + userProfilePath);

            // Optional: specify the profile within the user data directory
            options.addArguments("profile-directory=Default");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-debugging-port=9222");
            // Start Chrome with the specified profile
            ChromeDriver driver = new ChromeDriver(options);
            driver.get("https://chatgpt.com/");
            // Open a new tab using JavaScript
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.open('https://www.example.com', '_blank');");

            String originalTab = driver.getWindowHandle();
            for (String handle : driver.getWindowHandles()) {
                if (!handle.equals(originalTab)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }

            driver.get("https://github.com/");
            if( checkLinkStatus("https://aaaaaaaaaaaaaaaaaaaaaaaaaaaa.com/")){
                driver.navigate().to("https://aaaaaaaaaaaaaaaaaaaaaaaaaaaa.com/");
                String a = driver.getPageSource();
                return new ResponseEntity<>(a, HttpStatus.OK);
            }else{
                driver.navigate().to("https://github.com/");
                String a = driver.getPageSource();
                return new ResponseEntity<>(a,HttpStatus.OK);
            }

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
        }

        return new ResponseEntity<>("numbers",HttpStatus.OK);
    }
    private static boolean checkLinkStatus(String urlString) {
        try {
            // Create a URL object
            URL url = new URL(urlString);

            // Open an HTTP connection
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("HEAD"); // Use HEAD request to avoid downloading content

            // Get the response code
            int responseCode = httpURLConnection.getResponseCode();

            // Print out the response code
            if (responseCode != 200) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }
    }
}