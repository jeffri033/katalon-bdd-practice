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

import CustomKeywords
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



class shopping {

	// list data products
	def products =
	[
		[
			name  : "Sauce Labs Onesie",
			price : 7.99,
			desc  : "Rib snap infant onesie for the junior automation engineer in development. Reinforced 3-snap bottom closure, two-needle hemmed sleeved and bottom won't unravel."
		],
		[
			name  : "Sauce Labs Backpack",
			price : 29.99,
			desc  : "carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection."
		],
		[
			name  : "Sauce Labs Fleece Jacket",
			price : 49.99,
			desc  : "It's not every day that you come across a midweight quarter-zip fleece jacket capable of handling everything from a relaxing day outdoors to a busy day at the office."
		]
	]


	def get_ObjectElement(objectName, objectText) {
		CustomKeywords.'functionKeyword.ProductHelper.GetObjectElement'(objectName, objectText)
	}


	def verify_ObjectElement(objectName, objectText, expectedValue) {
		CustomKeywords.'functionKeyword.ProductHelper.VerifyObjectElement'(objectName, objectText, expectedValue)
	}
	
	
	def continueCheckout(dataCheckout) {
		CustomKeywords.'functionKeyword.ProductHelper.ContinueCheckout'(dataCheckout)
	}


	def shoppingCart





	@Given("I am logged in with username {string} and password {string}")
	public void i_am_logged_in_with_username_and_password(String username, String password) {
		// call keyword login
		CustomKeywords.'functionKeyword.LoginHelper.login'(username, password)
	}



	@When("I add 3 products to the cart")
	public void i_add_3_products_to_the_cart() {

		// using 'each' to iterate over the list products
		products.eachWithIndex { item, index ->

			// get element "PRODUCT NAME"
			def productName = get_ObjectElement('Product Name', item.name)

			// scroll to product name
			WebUI.scrollToElement(productName, 3)

			// verify product name is displayed
			WebUI.verifyElementVisible(productName)



			// verify product description is correct
			verify_ObjectElement('Product Description', item.name, item.desc)



			// verify product price is correct
			String expectedPrice =  '$' + item.price
			verify_ObjectElement('Product Price', item.name, expectedPrice)



			// verify button Add To Cart is displayed
			def buttonAddToCart = get_ObjectElement('Button Add To Cart', item.name)
			WebUI.verifyElementVisible(buttonAddToCart)



			// verify product image is displayed
			def productImage = get_ObjectElement('Product Image', item.name)
			WebUI.verifyElementVisible(productImage)




			// ##########       See product detail       ##########

			// click product name
			WebUI.click(productName)


			// click button Add To Cart
			WebUI.click(buttonAddToCart)


			// verify quantity of item in shopping cart
			shoppingCart = get_ObjectElement('Shopping Cart', '-')

			String actualQuantity 	= WebUI.getText(shoppingCart)
			String expectedQuantity	= index + 1

			WebUI.verifyEqual(actualQuantity, expectedQuantity)



			// click button back to product page
			def buttonBack = get_ObjectElement('Button Back', '-')
			WebUI.click(buttonBack)


			// scroll to product name
			WebUI.scrollToElement(productName, 3)


			// verify button 'Remove' is displayed
			String actualButtonLabel = WebUI.getText(buttonAddToCart)
			String expectedButtonLabel = 'Remove'

			WebUI.verifyEqual(actualButtonLabel, expectedButtonLabel)
		}
	}



	@Then("the cart should contain 3 products")
	public void the_cart_should_contain_3_products() {
		// scroll to shopping cart
		WebUI.scrollToElement(shoppingCart, 3)


		// verify quantity of item in shopping cart
		String actualQuantity	= WebUI.getText(shoppingCart)
		String expectedQuantity	= products.size()

		WebUI.verifyEqual(actualQuantity, expectedQuantity)
	}



	@Given("I am on cart page")
	public void i_am_on_cart_page() {
		// get element shopping cart
		shoppingCart = get_ObjectElement('Shopping Cart', '-')

		// click shopping cart
		WebUI.click(shoppingCart)


		// using 'each' to iterate over the list products in shopping cart
		products.eachWithIndex { item, index ->
			// verify product name is correct
			verify_ObjectElement('Product Name', item.name, item.name)

			// verify product description is correct
			verify_ObjectElement('Product Description', item.name, item.desc)


			// verify product price is correct
			String expectedPrice =  '$' + item.price
			verify_ObjectElement('Product Price', item.name, expectedPrice)

			// verify quantity
			verify_ObjectElement('Product Quantity', item.name, '1')

			// verify button Remove
			verify_ObjectElement('Button Add To Cart', item.name, 'Remove')
		}
	}



	@When("I click checkout button")
	public void i_click_checkout_button() {
		// click checkout button
		
		def buttonCheckout = get_ObjectElement('Button Checkout', '-')
		WebUI.click(buttonCheckout)
	}



	@When("I enter the checkout information")
	public void i_enter_the_checkout_information() {
		
		def dataCheckout = [
			firstName   : "Jefri",
			lastName    : "Testing",
			postalCode  : "12345"
		  ]
		
		continueCheckout(dataCheckout)
	}



	@When("I finish the payment")
	public void i_finish_the_payment() {		
		// initiate subtotal
		def subtotal = 0	
				
		// using 'each' to iterate over the list products in checkout overview
		products.eachWithIndex { item, index ->
			// verify product name is correct
			verify_ObjectElement('Product Name', item.name, item.name)

			// verify product description is correct
			verify_ObjectElement('Product Description', item.name, item.desc)


			// verify product price is correct
			String expectedPrice =  '$' + item.price
			verify_ObjectElement('Product Price', item.name, expectedPrice)

			// verify quantity
			verify_ObjectElement('Product Quantity', item.name, '1')
			
			// calculate subtotal
			subtotal = subtotal + item.price			
		}
		
		// scroll down the page
		def buttonFinish = get_ObjectElement('Button Finish', '-')
		WebUI.scrollToElement(buttonFinish, 3)
	
		
		// verify payment information
		verify_ObjectElement('Payment Information', '-', 'SauceCard #31337')
		
		// verify shipping information
		verify_ObjectElement('Shipping Information', '-', 'Free Pony Express Delivery!')
		
		
		
		// verify subtotal
		String expectedSubtotal = 'Item total: $' + subtotal
		verify_ObjectElement('Subtotal', '-', expectedSubtotal)
		
		
		
		// calculate tax
		def tax 			= subtotal * 0.08
		tax 				= Math.ceil(tax * 100) / 100 // round up tax to 2 decimals
		String expectedTax 	= 'Tax: $' + tax
		
		// verify tax
		verify_ObjectElement('Tax', '-', expectedTax)
		
		
		
		// calculate grandtotal
		def grandtotal = subtotal + tax;
		
		// verify grandtotal
		String expected_GrandTotal = 'Total: $' + grandtotal
		verify_ObjectElement('Grand Total', '-', expected_GrandTotal)
		
		
		// click button Finish		
		WebUI.click(buttonFinish)
	}



	@Then("I should see information of the checkout process is complete")
	public void i_should_see_information_of_the_checkout_process_is_complete() {
		// verify text complete checkout
		String expected_TextCompleteCheckout = 'Checkout: Complete!'
		verify_ObjectElement('Text Complete Checkout', '-', expected_TextCompleteCheckout)
		
		
		// verify text Thank You
		String expected_TextThankYou = 'Thank you for your order!'
		verify_ObjectElement('Text Thank You', '-', expected_TextThankYou)
		
		
		// verify text Complete Order
		String expected_TextCompleteOrder = 'Your order has been dispatched, and will arrive just as fast as the pony can get there!'
		verify_ObjectElement('Text Complete Order', '-', expected_TextCompleteOrder)
		
		
		// verify image 'complete checkout' is displayed
		def imageCompleteCheckout = get_ObjectElement('Image Complete Checkout', '-')
		WebUI.verifyElementVisible(imageCompleteCheckout)
	}



	@When("I fill out the form incompletely")
	public void i_fill_out_the_form_incompletely() {
		
		def dataCheckout = [
			firstName   : "Jefri",
			lastName    : "Testing",
			postalCode  : "" // blank value
		  ]
		
		continueCheckout(dataCheckout)
	}



	@Then("I should see validation message")
	public void i_should_see_validation_message() {
		// verify text error checkout
		String expected_TextErrorCheckout = 'Error: Postal Code is required'
		verify_ObjectElement('Text Error Checkout', '-', expected_TextErrorCheckout)
		

		// close browser
		WebUI.closeBrowser()
	}
}