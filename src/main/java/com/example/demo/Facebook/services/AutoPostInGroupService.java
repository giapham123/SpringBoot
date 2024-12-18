
package com.example.demo.Facebook.services;

import com.example.demo.Facebook.commonFunc.ConfigCommonFunc;
import com.example.demo.Facebook.commonFunc.ConfigCommonFuncFirefox;
import com.example.demo.Facebook.commonFunc.ConfigCommonFuncForRobotChooseFile;
import com.example.demo.Facebook.models.AutoPostGroup;
import com.example.demo.common.GenericResponse;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class AutoPostInGroupService {

    @Autowired
    ConfigCommonFunc configCommonFunc;

    @Autowired
    ConfigCommonFuncForRobotChooseFile configCommonFuncForRobotChooseFile;

    @Autowired
    ConfigCommonFuncFirefox configCommonFuncFirefox;

    public GenericResponse autoCommentPost(AutoPostGroup autoPostGroup) throws InterruptedException {
        boolean isMac = System.getProperty("os.name").toLowerCase().contains("mac");
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
        GenericResponse rs = new GenericResponse();
        WebDriver driver = configCommonFuncFirefox.loginByCookie(autoPostGroup.getPageId());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        // Navigate to the post page after adding cookies
        String[] splitGroupId = autoPostGroup.getGroupId().split(",");
        List<String> groupPostSuccess = new ArrayList<>();
        for(int i =0; i< splitGroupId.length; i++){
//            driver.navigate().refresh();
            driver.get("https://facebook.com/groups/" + splitGroupId[i]);
            Thread.sleep(1000); // Đợi hộp mở ra
            try {
                WebElement clickShare = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(.,'Write something...')]")));
                clickShare.click();
                Thread.sleep(1000); // Đợi hộp mở ra
                //Xác định dialog CREATE POST
                WebElement dialog = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='dialog' and contains(@class, 'x1n2onr6')]")));

                String[] pathsArray = autoPostGroup.getImage().split(",");

                //Xác định input file từ dialog CREATE POST
                WebElement imageInput = dialog.findElement(By.xpath(".//input[@type='file']"));
                //Input từng hình
                for (String path : pathsArray) {
                    imageInput.sendKeys(path.trim()); // Trim to remove extra spaces or newlines
                }
                Thread.sleep(500);

                //Input Content
                try{
                    WebElement postBox = dialog.findElement(By.xpath("//div[@aria-label='Create a public post…']"));
                    String[] lines = autoPostGroup.getContent().split("\n");
                    for (String line : lines) {
                        postBox.sendKeys(line);
                        postBox.sendKeys(Keys.RETURN);  // Simulate pressing "Enter" to create a new line
                        Thread.sleep(500); // Delay to mimic human-like typing behavior
                    }
                }catch (Exception e){
                    WebElement postBox = dialog.findElement(By.xpath("//div[@aria-label='Write something...']"));
                    String[] lines = autoPostGroup.getContent().split("\n");
                    for (String line : lines) {
                        postBox.sendKeys(line);
                        postBox.sendKeys(Keys.RETURN);  // Simulate pressing "Enter" to create a new line
                        Thread.sleep(500); // Delay to mimic human-like typing behavior
                    }
                }
                Thread.sleep(1000); // Wait for the next set of groups to load
//                Click Post Button
                WebElement clickPost = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-label='Post']")));
                clickPost.click();
                Thread.sleep(10000);
                groupPostSuccess.add("https://facebook.com/groups/" + splitGroupId[i]);
            } catch (Exception e) {
                System.out.println(e);
            }finally {
                continue;
            }
        }
        driver.quit();
        rs.setMessage("Post to group success.");
        rs.setData(groupPostSuccess);
        return rs;
    }

    public GenericResponse autoCommentPostBK(AutoPostGroup autoPostGroup) throws InterruptedException {
        boolean isMac = System.getProperty("os.name").toLowerCase().contains("mac");
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
        GenericResponse rs = new GenericResponse();
        WebDriver driver = configCommonFuncFirefox.loginByCookie(autoPostGroup.getPageId());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        // Navigate to the post page after adding cookies
        String[] splitGroupId = autoPostGroup.getGroupId().split(",");
        List<String> groupPostSuccess = new ArrayList<>();
        for(int i =0; i< splitGroupId.length; i++){
//            driver.navigate().refresh();
            driver.get("https://facebook.com/groups/" + splitGroupId[i]);
            Thread.sleep(1000); // Đợi hộp mở ra
            try {
                WebElement clickShare = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(.,'Write something...')]")));
                clickShare.click();
                Thread.sleep(1000); // Đợi hộp mở ra

                WebElement clickShare1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-label='Photo/video']")));
                clickShare1.click();

                if (isMac) {
                    selectFileInMac(autoPostGroup.getImage());
                } else if(isWindows){
                    try{
                        selectFileForWinDown(autoPostGroup.getImage());
                    }catch (Exception e){
                        WebElement clickShare2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(.,'Add Photos/Videos')]")));
                        clickShare2.click();
                        Thread.sleep(500);
                        selectFileForWinDown(autoPostGroup.getImage());
                    }
                }
                Thread.sleep(500);

                //Input Content
                try{
                    WebElement postBox = driver.findElement(By.xpath("//div[@aria-label='Create a public post…']"));
                    String[] lines = autoPostGroup.getContent().split("\n");
                    for (String line : lines) {
                        postBox.sendKeys(line);
                        postBox.sendKeys(Keys.RETURN);  // Simulate pressing "Enter" to create a new line
                        Thread.sleep(500); // Delay to mimic human-like typing behavior
                    }
                }catch (Exception e){
                    WebElement postBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-label='Write something...']")));
                    String[] lines = autoPostGroup.getContent().split("\n");
                    for (String line : lines) {
                        postBox.sendKeys(line);
                        postBox.sendKeys(Keys.RETURN);  // Simulate pressing "Enter" to create a new line
                        Thread.sleep(500); // Delay to mimic human-like typing behavior
                    }
                }
                Thread.sleep(1000); // Wait for the next set of groups to load
//                Click Post Button
                WebElement clickPost = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-label='Post']")));
                clickPost.click();
                Thread.sleep(10000);
                groupPostSuccess.add("https://facebook.com/groups/" + splitGroupId[i]);
            } catch (Exception e) {
                System.out.println(e);
            }finally {
                continue;
            }
        }
        driver.quit();
        rs.setMessage("Post to group success.");
        rs.setData(groupPostSuccess);
        return rs;
    }


    //This run for windown
    public static void selectFileForWinDown(String filePath1) {
        try {
            // Create Robot instance
            Robot robot = new Robot();

            // Example file path (use a valid file path on your system)
            String filePath = filePath1;

            // Ensure the file selection popup is open and ready
            Thread.sleep(1000); // Adjust the delay if needed

            // Iterate through the file path characters
            for (char c : filePath.toCharArray()) {
                if (Character.isLetterOrDigit(c)) {
                    // Handle letters and digits
                    int keyCode = KeyEvent.getExtendedKeyCodeForChar(Character.toUpperCase(c));
                    if (Character.isLowerCase(c)) {
                        robot.keyPress(KeyEvent.VK_SHIFT);
                    }
                    robot.keyPress(keyCode);
                    robot.keyRelease(keyCode);
                    if (Character.isLowerCase(c)) {
                        robot.keyRelease(KeyEvent.VK_SHIFT);
                    }
                } else {
                    // Handle special characters explicitly
                    switch (c) {
                        case ':':
                            robot.keyPress(KeyEvent.VK_SHIFT);
                            robot.keyPress(KeyEvent.VK_SEMICOLON);
                            robot.keyRelease(KeyEvent.VK_SEMICOLON);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            break;
                        case '\\':
                            robot.keyPress(KeyEvent.VK_BACK_SLASH);
                            robot.keyRelease(KeyEvent.VK_BACK_SLASH);
                            break;
                        case '/':
                            robot.keyPress(KeyEvent.VK_SLASH);
                            robot.keyRelease(KeyEvent.VK_SLASH);
                            break;
                        case '.':
                            robot.keyPress(KeyEvent.VK_PERIOD);
                            robot.keyRelease(KeyEvent.VK_PERIOD);
                            break;
                        case '_':
                            robot.keyPress(KeyEvent.VK_SHIFT);
                            robot.keyPress(KeyEvent.VK_MINUS); // Underscore is Shift + Minus
                            robot.keyRelease(KeyEvent.VK_MINUS);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            break;
                        case '-':
                            robot.keyPress(KeyEvent.VK_MINUS);
                            robot.keyRelease(KeyEvent.VK_MINUS);
                            break;
                        default:
                            throw new IllegalArgumentException("Unsupported character: " + c);
                    }
                }

                // Small delay between key presses
                Thread.sleep(100);
            }

            // Press Enter to confirm the file selection
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void selectFileInMac(String filePath1) {
        try {
            // Create Robot instance
            Robot robot = new Robot();

            // Example file path (adjust this path for testing)
            String filePath = filePath1;  // Adjust path for your case

            // Check if the OS is macOS or Windows
            boolean isMac = System.getProperty("os.name").toLowerCase().contains("mac");

            // Type the file path character by character
            Thread.sleep(5000);  // Giving time for the application to focus

            // Type each character of the file path
            for (char c : filePath.toCharArray()) {
                int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);

                // Handle special characters for macOS (like '/', ':', etc.)
                if (keyCode == KeyEvent.VK_SLASH) {
                    robot.keyPress(KeyEvent.VK_SLASH);
                    robot.keyRelease(KeyEvent.VK_SLASH);
                } else if (keyCode == KeyEvent.VK_COLON) {
                    // On macOS, ':' is Shift + ';'
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_SEMICOLON);
                    robot.keyRelease(KeyEvent.VK_SEMICOLON);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                } else if (keyCode != 0) {
                    // Simulate the character normally
                    robot.keyPress(keyCode);
                    robot.keyRelease(keyCode);
                }

                // Small delay between key presses
                Thread.sleep(100);  // Adjust the sleep time if needed for better reliability
            }

            // Focus management: Bring focus back to the file selection dialog
            // Option 1: Simulate pressing TAB to switch focus (if applicable)
            robot.keyPress(KeyEvent.VK_TAB);  // This cycles the focus, may need to press TAB multiple times
            robot.keyRelease(KeyEvent.VK_TAB);
            Thread.sleep(500);  // Small delay to ensure focus switches
            // Now press "Enter" to select the file and close the dialog
            System.out.println("Pressing Enter to select the file...");
            if (isMac) {
                // On macOS, we might need to press Return (Enter) explicitly
                robot.keyPress(KeyEvent.VK_ENTER);  // Or KeyEvent.VK_RETURN for macOS
                robot.keyRelease(KeyEvent.VK_ENTER); // Or KeyEvent.VK_RETURN
            }

            // Wait for the file dialog to process the selection
            Thread.sleep(500);  // Adding a small delay after pressing Enter
            robot.keyPress(KeyEvent.VK_ENTER);  // Or KeyEvent.VK_RETURN for macOS
            robot.keyRelease(KeyEvent.VK_ENTER); // Or KeyEvent.VK_RETURN

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}