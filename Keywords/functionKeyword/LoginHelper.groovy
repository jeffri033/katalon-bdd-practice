package functionKeyword
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class LoginHelper {
	@Keyword
	def login(String username, String password) {
		// open browser
		WebUI.openBrowser('')

		// go to URL
		WebUI.navigateToUrl(GlobalVariable.baseUrl)

		// type username
		WebUI.setText(findTestObject('Page Login/input_username'), username)

		// type password
		WebUI.setText(findTestObject('Page Login/input_password'), password)

		// click login button
		WebUI.click(findTestObject('Page Login/button_login'))




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
}
