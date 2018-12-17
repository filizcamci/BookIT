package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import utilities.Driver;

public class Hunt2Page extends TopNavigationBar {
	public Hunt2Page() {
		PageFactory.initElements(Driver.getDriver(), this);
	}
	

	public void book() {
		List<WebElement> bookButtons=Driver.getDriver().findElements(By.xpath("//*[@class='button is-transparent is-white']"));
		System.out.println(bookButtons);
		bookButtons.get(1).click();
		
	}
}
