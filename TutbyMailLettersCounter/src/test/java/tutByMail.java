import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class tutByMail {

    private WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void browserSetup() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test (description = "Tut.by mail letters count check")
    public void lettersCounter() {

        driver.get("https://www.tut.by");
        driver.manage().window().maximize();

        // MAIL PAGE
        WebElement mailLinkElement = driver.findElement(By.xpath("//*[@id=\"mainmenu\"]/ul/li[3]/a"));
        mailLinkElement.click();

        // SIGN IN
        WebElement userNameElement = driver.findElement(By.xpath("//*[@id=\"Username\"]"));
        userNameElement.sendKeys("vorvule");

        WebElement passWordElement = driver.findElement(By.xpath("//input[@id=\"Password\"]"));
        passWordElement.sendKeys("temporary");

        WebElement submitDataElement = driver.findElement(By.xpath("//*[@id=\"form\"]/fieldset/div[3]/input"));
        submitDataElement.click();

        // MAIL COUNT
        int countOfLetters = -1;
        try {
            WebElement mailCountElement = driver.findElement(By.xpath("//*[@id=\"nb-1\"]/body/div[2]/div[5]/div/div[3]/div[2]/div[3]/div/div[2]/div[1]/a[1]/div/span"));
            String text = mailCountElement.getText();

            text = text.replaceAll(" ⁄ ", "");
            countOfLetters = Integer.parseInt(text);
        } catch (NoSuchElementException e) {
            System.out.println("Exception occured: " + e.getMessage());
        }

        // CONSOLE
        System.out.println("The count of letters is " + countOfLetters);

        // ASSERTION
        Assert.assertTrue(countOfLetters > -1, "Wrong count of letters");
    }

    @AfterMethod (alwaysRun = true)
    public void closeAndQuitDriver () {
        driver.close();
        driver.quit();
        driver = null;
    }

}