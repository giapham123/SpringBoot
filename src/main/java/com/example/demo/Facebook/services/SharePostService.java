package com.example.demo.Facebook.services;

import com.example.demo.Facebook.commonFunc.ConfigCommonFunc;
import com.example.demo.Facebook.models.GetUidUserInGroupModel;
import com.example.demo.Facebook.models.SharePostPageModel;
import com.example.demo.common.GenericResponse;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SharePostService {

    @Autowired
    ConfigCommonFunc configCommonFunc;

    public GenericResponse getAllPostId(SharePostPageModel sharePostPageModel) throws InterruptedException {
        GenericResponse rs = new GenericResponse();
        WebDriver driver = configCommonFunc.loginByCookie(sharePostPageModel.getTypeComp());
        driver.navigate().to("https://web.facebook.com/gpcarnews/");
        configCommonFunc.scrollTopToEndPage(sharePostPageModel.getScrollNumbers(),driver);
        try {
            List<WebElement> links = driver.findElements(By.xpath("//a[@aria-label='Boost post']"));
            Set<String> uniqueNumbers = new HashSet<>();

            // In ra các URL hợp lệ
            for (WebElement link : links) {
                String url = link.getAttribute("href");
                // Define the regex pattern
                String regex = "target_id=(\\d+)&__cft__";
                // Compile the pattern
                Pattern pattern = Pattern.compile(regex);
                // Match the pattern in the input string
                Matcher matcher = pattern.matcher(url);
                if (matcher.find()) {
                    // Extract the number (group 1)
                    String targetId = matcher.group(1);
                    uniqueNumbers.add(targetId);
                    System.out.println("Extracted target_id: " + targetId);
                    break;
                } else {
                    System.out.println("No match found.");
                }
            }
            System.out.println("Total groups in page: " + uniqueNumbers.size());

            //Set data for return
            Map resultData = new HashMap();
            resultData.put("quantityPostId", uniqueNumbers.size());
            resultData.put("dataPostId", uniqueNumbers);
            rs.setData(resultData);
            rs.setMessage("Get All Group In Page Success");
            driver.quit();
            return rs;
        }catch (Exception e){
            rs.setData(null);
            rs.setMessage("Get All Group In Page Failed");
            driver.quit();
            System.out.println(e);
            return rs;
        }
    }

    public ResponseEntity<String> sharePostPage(SharePostPageModel sharePostPageModel){
        if(sharePostPageModel.getTypeComp().toUpperCase().equals("MAC")){
            System.setProperty("webdriver.chrome.driver", "/Users/giapham/Documents/chromedriver-mac-x64/chromedriver");
        }else{
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\Downloads\\chromedriver-win64\\chromedriver.exe");
        }
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.setExperimentalOption("detach", false);

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Add cookies for login
        driver.get("https://www.facebook.com");
        List<Cookie> cookies = new ArrayList<>();
//        cookies.add(new Cookie("b_user", "61560391100126"));
//        cookies.add(new Cookie("c_user", "100007769235838"));
//        cookies.add(new Cookie("datr", "U75EZr-96XGfZQxL1SfKqCYg"));
//        cookies.add(new Cookie("fr", "19iXxFLGIGy6rFS4n.AWVnnE0jjG7xGNiQZLcoqULdjWs.BnM0mu..AAA.0.0.BnM1LE.AWXswWiNdXQ"));
//        cookies.add(new Cookie("i_user", "61551932516721"));
//        cookies.add(new Cookie("locale", "en_US"));
//        cookies.add(new Cookie("ps_l", "1"));
//        cookies.add(new Cookie("ps_n", "1"));
//        cookies.add(new Cookie("sb", "JmIpZG_pK3duwRSUYwZA-Mwa"));
//        cookies.add(new Cookie("wd", "906x983"));
//        cookies.add(new Cookie("xs", "33%3A7BAMKsuAgazkTA%3A2%3A1717076724%3A-1%3A6191%3ACrRmDO5Ou4vwHA%3AAcVbaDQhIKglET53RN6j-0t7Eg6xpJnYtqODor0EAkKG"));
        cookies.add(new Cookie("c_user", "61568239606429"));//1
        cookies.add(new Cookie("datr", "mwg0Z9THOO1-yWEpQbBW07Sx"));//2
        cookies.add(new Cookie("fr", "1ktrjz4zWqnLmuIwd.AWUctX-3saXXxC7hiXbdwGuLA6Y.BnNZa3..AAA.0.0.BnNZa3.AWWkQALbtFw"));//3
        cookies.add(new Cookie("i_user", "100063707646753"));
        cookies.add(new Cookie("locale", "en_US"));
        cookies.add(new Cookie("ps_l", "1"));
        cookies.add(new Cookie("ps_n", "1"));
        cookies.add(new Cookie("sb", "mwg0Z16z_I75ZUIAXFwsTihu"));//4
        cookies.add(new Cookie("wd", "906x983"));
        cookies.add(new Cookie("xs", "11%3AWe5fJeHEAhBxyg%3A2%3A1731463337%3A-1%3A-1%3A%3AAcU1hF7Rm55ZFTJJNCUbVv9eHnK1MfKUNutjfw5XNA"));

        // Add necessary cookies here
        for (Cookie cookie : cookies) {
            driver.manage().addCookie(cookie);
        }

        // Navigate to the post page after adding cookies
//        driver.navigate().to("https://www.facebook.com/tuvanphapluat.hp/posts/122206677680064417");
        driver.navigate().to("https://www.facebook.com/"+sharePostPageModel.getPageName()+"/posts/"+sharePostPageModel.getIdPost());
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
            for (String group : sharePostPageModel.getGroupName()) {
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
                    System.out.println("Dont have this group: "+ sharePostPageModel.getGroupName());
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
}
