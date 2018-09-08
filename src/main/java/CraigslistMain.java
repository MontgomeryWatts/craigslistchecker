import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.LocalDateTime;

public class CraigslistMain {
    private static final int ONE_MINUTE = 60000;

    public static void main(String[] args) {
        WebDriver driver = null;

        String url = null;
        if(args[0] != null) {
            url = args[0];
        } else {
            System.err.println("Please provide a URL");
            System.exit(1);
        }

        try{
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");
            driver = new FirefoxDriver(options);
            driver.get(url);
        } catch (InvalidArgumentException iae){
            System.err.println("Invalid URL provided.");
        }

        while (true){
            try {
                // This will throw an exception if the listing is not removed,
                // which is handled in the second catch
                driver.findElement(By.className("removed"));
                System.out.println("The given listing has been removed.");
                System.out.println("The listing was discovered to be removed at " + LocalDateTime.now());
                break;
            } catch (NoSuchElementException nsee) {
                try {
                    Thread.sleep(ONE_MINUTE);
                    driver.get(url);
                } catch (InterruptedException ie){
                    // Should never happen
                }
            }
        }

        if(driver != null)
            driver.close();
    }
}
