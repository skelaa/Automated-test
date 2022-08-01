package ctcotest;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class CtcoTest {
	public String url = "https://ctco.lv/en";
	public String menuItemsSelector = "#menu-main a";
	public String menuVacanciesSelector = ".menu-main-container a";
	public String vacancySelector = ".vacancies-second-contents.active .wysiwyg";

	@Test
	public void test() throws Exception {
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		
		WebDriver driver =  new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(url);		
		Thread.sleep(1000);
		
		List<WebElement> links = driver.findElements(By.cssSelector(menuItemsSelector));
		
		WebElement careersLink = null;
		WebElement vacanciesLink = null;
		
		for (int i = 0; i < links.size(); i++) {
			WebElement link = links.get(i);
			String linkText = link.getAttribute("textContent");
			
			if (linkText.equals("Careers")) {
				careersLink = link;
			} 
			else if (linkText.equals("Vacancies")) {
				vacanciesLink = link;
			}	
		}
		
		if (careersLink != null) {
			Actions actions = new Actions(driver);
			actions.moveToElement(careersLink).perform();
			Thread.sleep(1000);
		} else {
			throw new Exception("Careers link not found");
		}
		
		if (vacanciesLink != null) {
			vacanciesLink.click();
			Thread.sleep(1000);
		} else {
			throw new Exception ("Vacancies link not found");
		}

		List<WebElement> vacanciesLinks = driver.findElements(By.cssSelector(menuVacanciesSelector));
		
		WebElement testAutomationLink = null;
		
		for (int i = 0; i < vacanciesLinks.size(); i++) {
			WebElement link = vacanciesLinks.get(i);
			String linkText = link.getText();
			
			if (linkText.equals("TEST AUTOMATION ENGINEER")) {
				testAutomationLink = link;
			} 
		}
		
		if (testAutomationLink != null ) {
			testAutomationLink.click();
			Thread.sleep(3000);
		} else {
			throw new Exception ("Test Autometion Engineer link not found");
		}
		
		WebElement vacancy = driver.findElement(By.cssSelector(vacancySelector));
		
		if (vacancy == null) {
			throw new Exception("Vacancy container not found");
		}
		
		List<WebElement> paragraphs = vacancy.findElements(By.tagName("p"));
		WebElement paragraph = null;
		
		for (int i = 0; i < paragraphs.size(); i++) {
			WebElement item = paragraphs.get(i);
			String itemText = item.getAttribute("textContent");
			
			if (itemText.equals("Professional skills and qualification:")) {
				paragraph = item;
				break;
			}
		}
		
		if (paragraph == null) {
			throw new Exception ("Paragraph 'Professional skills and qualification' not found");
		}
		
		List<WebElement> vacancyElements = vacancy.findElements(By.xpath("./child::*"));
		int position = vacancyElements.indexOf(paragraph);
		WebElement skillList = vacancyElements.get(position + 1);
		
		List<WebElement> skillListItems = skillList.findElements(By.tagName("li"));
		
		int expectedSkillAmount = 8;
		int actualSkillAmount = skillListItems.size();
		
		Assert.assertEquals("Invalid skill count", expectedSkillAmount, actualSkillAmount);
		
		driver.quit();
	}
}
