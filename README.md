# Selenium TestNG Demo Web Shop

UI test automation framework built using Java, Selenium WebDriver, TestNG, Maven and Page Object Model (POM).

The project automates the Demo Web Shop application:

https://demowebshop.tricentis.com

---

## Tech Stack

* Java
* Selenium WebDriver
* TestNG
* Maven
* WebDriverManager
* Git
* GitHub

---

## Framework Design

The framework follows the Page Object Model (POM) design pattern.

### Project Structure

```text
src
├── main
│   └── java
│       └── com.bogdan.automation
│           ├── driver
│           ├── pages
│           └── utils
│
└── test
    └── java
        └── com.bogdan.automation
            ├── base
            └── tests
                ├── smoke
                └── authentication
```

### Core Components

#### DriverFactory

Responsible for browser initialization and management.

#### ConfigReader

Loads configuration values from the properties file.

#### BaseTest

Provides common test setup and teardown methods.

#### BasePage

Contains reusable page actions shared by all page objects.

#### Page Objects

* ApplicationPage
* HomePage
* LoginPage

---

## Implemented Test Suites

### Smoke Suite

The smoke suite verifies that the application is available and the main functionality is accessible.

#### Covered Scenarios

* Application loads successfully
* Login page is accessible
* Register page is accessible
* Header navigation is visible
* Main categories are visible

---

### Authentication Suite

The authentication suite validates user login and logout functionality.

#### Covered Scenarios

* User can login with valid credentials
* User cannot login with invalid password
* User cannot login with invalid email
* User cannot login with empty email
* User cannot login with empty password
* User can logout successfully

---

## Current Progress

### Framework Foundation

* [x] Maven project setup
* [x] Selenium WebDriver integration
* [x] TestNG integration
* [x] WebDriverManager integration
* [x] Configuration management
* [x] Driver factory implementation
* [x] Base test setup and teardown
* [x] GitHub integration

### Page Object Model

* [x] BasePage
* [x] ApplicationPage
* [x] HomePage
* [x] LoginPage

### Smoke Testing

* [x] Application health validation
* [x] Navigation validation
* [x] Category visibility validation

### Authentication Testing

* [x] Login validation
* [x] Negative login scenarios
* [x] Logout validation

---

## Roadmap

### Registration

* [ ] User registration
* [ ] Duplicate email validation
* [ ] Registration field validation

### Product Catalog

* [ ] Category navigation
* [ ] Product details validation
* [ ] Product search

### Shopping Cart

* [ ] Add product to cart
* [ ] Update quantity
* [ ] Remove product from cart

### Checkout

* [ ] Checkout flow
* [ ] Order confirmation
* [ ] End-to-end purchase scenario

### Framework Enhancements

* [ ] Explicit waits utility
* [ ] Screenshots on failure
* [ ] Extent Reports integration
* [ ] CI/CD pipeline with GitHub Actions

---
