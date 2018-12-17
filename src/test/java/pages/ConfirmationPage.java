package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.Driver;

public class ConfirmationPage extends TopNavigationBar {
		public ConfirmationPage() {
			PageFactory.initElements(Driver.getDriver(), this);
		}
		@FindBy(xpath = "//*[@class='button is-dark]")
		public WebElement confirm;
	
}
