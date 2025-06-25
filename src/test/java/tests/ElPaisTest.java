package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.OpinionPage;
import utils.TranslationUtil;
import utils.ImageDownloader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ElPaisTest {
    private WebDriver driver;
    private static final String EL_PAIS_OPINION_URL = "https://elpais.com/opinion/";
    private static final String BROWSERSTACK_USERNAME = ""; 
    private static final String BROWSERSTACK_ACCESS_KEY = "";
    private static final String BROWSERSTACK_URL = "https://" + BROWSERSTACK_USERNAME + ":" + BROWSERSTACK_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";

    @DataProvider(name = "browserConfigs", parallel = true)
    public Object[][] browserConfigs() {
        return new Object[][] {
            { "Chrome", "latest", "Windows", "11", null, null, "ElPais_Chrome_Win11" },
            { "Firefox", "latest", "Windows", "11", null, null, "ElPais_Firefox_Win11" },
            { "Edge", "latest", "Windows", "11", null, null, "ElPais_Edge_Win11" },
            { "Safari", null, null, null, "iPhone 14", "16", "ElPais_Safari_iPhone14" },
            { "Chrome", null, null, null, "Samsung Galaxy S22", "12.0", "ElPais_Chrome_GalaxyS22" }
        };
    }

    @BeforeMethod
    public void setUp(Object[] testArgs) throws Exception {
        String browserName = (String) testArgs[0];
        String browserVersion = (String) testArgs[1];
        String os = (String) testArgs[2];
        String osVersion = (String) testArgs[3];
        String deviceName = (String) testArgs[4];
        String deviceOsVersion = (String) testArgs[5];
        String buildName = (String) testArgs[6];

        System.out.println("[DEBUG] browserName: " + browserName + ", browserVersion: " + browserVersion + ", os: " + os + ", osVersion: " + osVersion + ", deviceName: " + deviceName + ", deviceOsVersion: " + deviceOsVersion);

        if (deviceName == null) {
            if (browserName.equals("Chrome") && os == null) {
                System.out.println("[DEBUG] Using local ChromeDriver");
                System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless=new");
                driver = new ChromeDriver(options);
            } else {
                System.out.println("[DEBUG] Using BrowserStack for desktop");
                DesiredCapabilities caps = new DesiredCapabilities();
                caps.setCapability("browserName", browserName);
                if (browserVersion != null) {
                    caps.setCapability("browserVersion", browserVersion);
                }
                caps.setCapability("os", os);
                caps.setCapability("os_version", osVersion);
                caps.setCapability("build", buildName);
                caps.setCapability("name", buildName);
                driver = new RemoteWebDriver(new URL(BROWSERSTACK_URL), caps);
            }
        } else {
            System.out.println("[DEBUG] Using BrowserStack for mobile");
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("browserName", browserName);
            caps.setCapability("device", deviceName);
            caps.setCapability("os_version", deviceOsVersion);
            caps.setCapability("real_mobile", "true");
            caps.setCapability("build", buildName);
            caps.setCapability("name", buildName);
            driver = new RemoteWebDriver(new URL(BROWSERSTACK_URL), caps);
        }
    }

    @Test(dataProvider = "browserConfigs")
    public void testElPaisScraper(String browserName, String browserVersion, String os, String osVersion, String deviceName, String deviceOsVersion, String buildName) throws Exception {
        driver.get(EL_PAIS_OPINION_URL);
        System.out.println("[DEBUG] Current URL: " + driver.getCurrentUrl());
        System.out.println("[DEBUG] Page Title: " + driver.getTitle());
        try {
            OpinionPage opinionPage = new OpinionPage(driver);
            if (!opinionPage.isSpanishLanguageDisplayed()) {
                throw new Exception("Spanish language is not displayed on the page.");
            }

            List<OpinionPage.Article> articles = opinionPage.getFirstFiveArticles();
            if (articles.isEmpty()) {
                System.out.println("[DEBUG] No articles found. Dumping page source:");
                System.out.println(driver.getPageSource());
            }
            List<String> titles = new ArrayList<>();
            List<String> imageUrls = new ArrayList<>();

            System.out.println("=== Articles ===");
            for (OpinionPage.Article article : articles) {
                System.out.println("Title: " + article.getTitle());
                System.out.println("Content: " + (article.getContent().isEmpty() ? "Content not available" : article.getContent()));
                System.out.println("Image URL: " + article.getImageUrl());
                System.out.println("---");
                titles.add(article.getTitle());
                imageUrls.add(article.getImageUrl());
            }

            ImageDownloader downloader = new ImageDownloader();
            downloader.downloadImages(imageUrls, titles);

            TranslationUtil translationUtil = new TranslationUtil();
            List<String> translatedTitles = translationUtil.translateToEnglish(titles);
            System.out.println("=== Translated Titles ===");
            for (String translatedTitle : translatedTitles) {
                System.out.println(translatedTitle);
            }

            System.out.println("=== Repeated Words (More than twice) ===");
            Map<String, Integer> wordCounts = new java.util.HashMap<>();
            for (String header : translatedTitles) {
                for (String word : header.split("\\s+")) {
                    word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
                    if (word.isEmpty()) continue;
                    wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                }
            }
            boolean found = false;
            for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
                if (entry.getValue() > 2) {
                    System.out.println("Word: " + entry.getKey() + ", Count: " + entry.getValue());
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No words repeated more than twice.");
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Exception during test execution: " + e.getMessage());
            System.out.println("[ERROR] Dumping page source:");
            System.out.println(driver.getPageSource());
            throw e;
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.out.println("Error during driver.quit(): " + e.getMessage());
            }
        }
    }
}