package stepDefinition
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By

import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty

import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil

import com.kms.katalon.core.webui.exception.WebElementNotFoundException

import cucumber.api.java.en.And
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When



class login {
	@Given("I open the SauceDemo login page")
	public void i_open_the_SauceDemo_login_page() {
		// open browser
		WebUI.openBrowser('')

		// go to URL
		WebUI.navigateToUrl(GlobalVariable.baseUrl)
	}


	@When("I enter the username {string} and password {string}")
	public void i_enter_the_username_and_password(String username, String password) {
		// type username
		WebUI.setText(findTestObject('Page Login/input_username'), username)

		// type password
		WebUI.setText(findTestObject('Page Login/input_password'), password)
	}


	@When("I click the login button")
	public void i_click_the_login_button() {
		// click login button
		WebUI.click(findTestObject('Page Login/button_login'))
	}



	// Scenario: Successful login with valid credentials

	@Then("I should be redirected to the product page")
	public void i_should_be_redirected_to_the_product_page() {
		// verify the URL after login
		WebUI.verifyMatch(WebUI.getUrl(), '.*\\/inventory.*', true)

		// verify app logo text
		WebUI.verifyElementText(findTestObject('Page Product/title_app'), 'Swag Labs')

		// verify burger menu is visible
		WebUI.verifyElementVisible(findTestObject('Page Product/button_menu'))

		// verify shopping cart is visible
		WebUI.verifyElementVisible(findTestObject('Page Product/link_shopping_cart'))

		// verify filter product is visible
		WebUI.verifyElementVisible(findTestObject('Page Product/select_filter_product'))
	}



	//   Scenario: Unsuccessful login with invalid credentials

	@Then("I should see an error message")
	public void i_should_see_an_error_message() {
		// error message is displayed
		WebUI.verifyElementVisible(findTestObject('Page Login/text_error_login'))

		// error message contains expected text
		WebUI.getText(findTestObject('Page Login/text_error_login')).contains("Username and password do not match any user in this service")

		// close browser
		WebUI.closeBrowser()
	}
}