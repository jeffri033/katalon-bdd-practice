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
import com.kms.katalon.core.testobject.ConditionType

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class ProductHelper {
	@Keyword
	def GetObjectElement(String objectName, String objectText){
		String object_xPath

		// check object name
		switch(objectName) {
			case "Product Name":
				object_xPath = "//div[(text() = '${objectText}' or . = '${objectText}')]"
				break


			case "Product Description":
				object_xPath = "(.//*[normalize-space(text()) and normalize-space(.)='${objectText}'])[1]/following::div[1]"
				break


			case "Product Price":
				object_xPath = "(.//*[normalize-space(text()) and normalize-space(.)='${objectText}'])[1]/following::div[3]"
				break


			case "Button Add To Cart":
				object_xPath = "(.//*[normalize-space(text()) and normalize-space(.)='${objectText}'])[1]/following::button[1]"
				break


			case "Product Quantity":
				object_xPath = "(.//*[normalize-space(text()) and normalize-space(.)='${objectText}'])[1]/preceding::div[1]"
				break


			case "Product Image":
				object_xPath = "//img[@alt='${objectText}']"
				break


			case "Shopping Cart":
				object_xPath = "//div[@id='shopping_cart_container']/a/span"
				break


			case "Button Back":
				object_xPath = "//button[@id='back-to-products']"
				break


			case "Button Checkout":
				object_xPath = "//button[@id='checkout']"
				break
				
				
			case "Input First Name":
				object_xPath = "//input[@id='first-name']"
				break
				
				
			case "Input Last Name":
				object_xPath = "//input[@id='last-name']"
				break
				
				
			case "Input Postal Code":
				object_xPath = "//input[@id='postal-code']"
				break
		
				
			case "Button Continue":
				object_xPath = "//input[@id='continue']"
				break
				
		
			case "Payment Information":
				object_xPath = "//*[@data-test = 'payment-info-value']"
				break			
				
						
			case "Shipping Information":
				object_xPath = "//*[@data-test = 'shipping-info-value']"
				break
			
			
			case "Subtotal":
				object_xPath = "//*[@data-test = 'subtotal-label']"
				break
			
			
			case "Tax":
				object_xPath = "//*[@data-test = 'tax-label']"
				break
			
			
			case "Grand Total":
				object_xPath = "//*[@data-test = 'total-label']"
				break
				
				
			case "Button Finish":
				object_xPath = "//button[@id='finish']"
				break
				
				
			case "Text Complete Checkout":
				object_xPath = "//*[@data-test = 'title']"
				break
				
				
			case "Text Thank You":
				object_xPath = "//*[@data-test = 'complete-header']"
				break
				
			
			case "Text Complete Order":
				object_xPath = "//*[@data-test = 'complete-text']"
				break
				
				
			case "Image Complete Checkout":
				object_xPath = "//*[@data-test = 'pony-express']"
				break
				
				
			case "Text Error Checkout":
				object_xPath = "//*[@data-test = 'error']"
				break
		}

		// build dynamic test object using xpath
		TestObject testObject = new TestObject(objectName)
		testObject.addProperty('xpath', ConditionType.EQUALS, object_xPath)

		return testObject
	}



	@Keyword
	def VerifyObjectElement(String objectName, String objectText, String expectedValue) {
		// get object element
		def testObject = GetObjectElement(objectName, objectText)

		// verify element is displayed
		WebUI.verifyElementVisible(testObject)

		// verify value is correct
		String actualValue = WebUI.getText(testObject)
		WebUI.verifyEqual(actualValue, expectedValue)
	}
	
	
	
	@Keyword
	def ContinueCheckout(Map dataCheckout) {
		// enter the checkout information
		def inputFirstName = GetObjectElement('Input First Name', '-')
		WebUI.setText(inputFirstName, dataCheckout.firstName)
		
		def inputLastName = GetObjectElement('Input Last Name', '-')
		WebUI.setText(inputLastName, dataCheckout.lastName)
	
		
		if (dataCheckout.postalCode !== '') {
			def inputPostalCode = GetObjectElement('Input Postal Code', '-')
			WebUI.setText(inputPostalCode, dataCheckout.postalCode )
		}
		
		
		
		// click button Continue
		def buttonContinue = GetObjectElement('Button Continue', '-')
		WebUI.click(buttonContinue)
	
	}
}
