package com.bogdan.automation.tests.smoke;

import com.bogdan.automation.base.BaseTest;
import com.bogdan.automation.pages.ApplicationPage;
import com.bogdan.automation.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApplicationSmokeTest extends BaseTest {

    @Test(groups = {"smoke"})
    public void verifyApplicationLoadsSuccessfully() {

        ApplicationPage applicationPage = new ApplicationPage(driver);

        applicationPage.openHomePage();

        Assert.assertEquals(
                applicationPage.getPageTitle(),
                "Demo Web Shop");
    }

    @Test(groups = {"smoke"})
    public void verifyLoginPageIsAccessible() {

        ApplicationPage applicationPage = new ApplicationPage(driver);

        applicationPage.openLoginPage();

        Assert.assertEquals(
                applicationPage.getPageTitle(),
                "Demo Web Shop. Login");
    }

    @Test(groups = {"smoke"})
    public void verifyRegisterPageIsAccessible() {

        ApplicationPage applicationPage = new ApplicationPage(driver);

        applicationPage.openRegisterPage();

        Assert.assertEquals(
                applicationPage.getPageTitle(),
                "Demo Web Shop. Register");
    }

    @Test(groups = {"smoke"})
    public void verifyHeaderNavigationIsVisible() {

        ApplicationPage applicationPage = new ApplicationPage(driver);
        HomePage homePage = new HomePage(driver);

        applicationPage.openHomePage();

        Assert.assertTrue(homePage.isRegisterLinkDisplayed(), "Register link is not displayed");
        Assert.assertTrue(homePage.isLoginLinkDisplayed(), "Login link is not displayed");
        Assert.assertTrue(homePage.isShoppingCartLinkDisplayed(), "Shopping cart link is not displayed");
        Assert.assertTrue(homePage.isWishlistLinkDisplayed(), "Wishlist link is not displayed");
        Assert.assertTrue(homePage.isSearchBoxDisplayed(), "Search box is not displayed");
    }

    @Test(groups = {"smoke"})
    public void verifyMainCategoriesAreVisible() {

        ApplicationPage applicationPage = new ApplicationPage(driver);
        HomePage homePage = new HomePage(driver);

        applicationPage.openHomePage();

        Assert.assertTrue(homePage.isBooksCategoryDisplayed(), "Books category is not displayed");
        Assert.assertTrue(homePage.isComputersCategoryDisplayed(), "Computers category is not displayed");
        Assert.assertTrue(homePage.isElectronicsCategoryDisplayed(), "Electronics category is not displayed");
        Assert.assertTrue(homePage.isApparelShoesCategoryDisplayed(), "Apparel & Shoes category is not displayed");
        Assert.assertTrue(homePage.isDigitalDownloadsCategoryDisplayed(), "Digital Downloads category is not displayed");
        Assert.assertTrue(homePage.isJewelryCategoryDisplayed(), "Jewelry category is not displayed");
        Assert.assertTrue(homePage.isGiftCardsCategoryDisplayed(), "Gift Cards category is not displayed");
    }
}