package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.Driver;




public class MapPage extends TopNavigationBar {
	public MapPage() {
		PageFactory.initElements(Driver.getDriver(), this);
	}

	@FindBy(tagName = "h1")
	public WebElement campus;

}
