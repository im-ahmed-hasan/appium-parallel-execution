import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by henrrich on 26/06/17.
 */
public class IOSNativeParallelTests {

    private IOSDriver driver;
    private WebDriverWait wait;

    private By siriCalcQ = MobileBy.AccessibilityId("2 + 2 =");
    private By siriCalcA = MobileBy.AccessibilityId("4");

    private final static String APPIUM_SERVER_URL = "http://127.0.0.1:4723/wd/hub";

    @BeforeTest(alwaysRun = true)
    @Parameters({"platform", "udid", "deviceName"})
    public void setup(String platform, String udid, String deviceName) throws Exception {

        URL url = new URL(APPIUM_SERVER_URL);

        String[] platformInfo = platform.split(" ");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformInfo[0]);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformInfo[1]);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        capabilities.setCapability(MobileCapabilityType.UDID, udid);
        int wdaLocalPort = 8101;
        capabilities.setCapability(IOSMobileCapabilityType.WDA_LOCAL_PORT, 8100);
        wdaLocalPort = new Random().nextInt((8200 - 8100) + 1) + 8100;
        capabilities.setCapability(IOSMobileCapabilityType.WDA_LOCAL_PORT, wdaLocalPort);
        capabilities.setCapability("waitForQuiescence",false);
        //capabilities.setCapability("wdaLocalPort", wdaLocalPort);
        capabilities.setCapability(MobileCapabilityType.ORIENTATION, "PORTRAIT");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
        capabilities.setCapability("bundleId","com.apple.SiriViewService");

        driver = new IOSDriver<MobileElement>(url, capabilities);
        wait  = new WebDriverWait(driver, 20);
    }

    @Test
    public void testSiriCalculation() {
        HashMap<String, String> args = new HashMap<>();
        args.put("text", "What's two plus two?");
        driver.executeScript("mobile: siriCommand", args);
//        wait.until(ExpectedConditions.presenceOfElementLocated(siriCalcQ));
//        wait.until(ExpectedConditions.presenceOfElementLocated(siriCalcA));
    }

    @AfterTest(alwaysRun = true)
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
