# Demo Web Shop Automation Framework

## Overview

UI Test Automation Framework for Tricentis Demo Web Shop built using:

* Java 21
* Selenium WebDriver 4
* TestNG
* Maven
* WebDriverManager

The framework follows the Page Object Model (POM) design pattern and is being developed incrementally to simulate a real-world automation project.

---

## Framework Features

* Page Object Model (POM)
* Cross-browser execution
* Configurable headless execution
* Explicit waits
* Dynamic test data generation
* TestNG groups
* TestNG suite execution
* Centralized configuration management
* Reusable page actions through BasePage

---

## Project Structure

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
                ├── authentication
                └── registration
```

---

## Completed Phases

### Phase 1 – Smoke / Application Health

Implemented scenarios:

* Application loads successfully
* Register link is visible
* Login link is visible
* Search box is visible
* Main categories are displayed

Status: ✅ Complete

---

### Phase 2 – Authentication

Implemented scenarios:

* Successful login
* Login with invalid password
* Login with invalid email
* Login with empty email
* Login with empty password
* Successful logout

Status: ✅ Complete

---

### Phase 3 – Registration

Implemented scenarios:

* Successful registration
* Registration with existing email
* Registration with empty required fields
* Registration with password mismatch
* Registration with password below minimum length

Status: ✅ Complete

---

## TestNG Suites

### Smoke Suite

Runs only critical application health checks.

File:

```text
testng-smoke.xml
```

Included tests:

* Application Smoke Tests

---

### Regression Suite

Runs all implemented automated tests.

File:

```text
testng-regression.xml
```

Included tests:

* Smoke Tests
* Authentication Tests
* Registration Tests

As new phases are implemented, they will automatically become part of the regression suite.

---

## Configuration

Example configuration:

```properties
browser=chrome
headless=false
baseUrl=https://demowebshop.tricentis.com
```

### Supported Browsers

* Chrome
* Firefox
* Edge

### Headless Execution

To run tests in headless mode:

```properties
headless=true
```

---

## Utility Components

### TestDataGenerator

Generates unique email addresses for registration tests:

```java
TestDataGenerator.generateUniqueEmail();
```

This prevents failures caused by duplicate registration attempts.

---

## Upcoming Phases

### Phase 4 – Product Catalog

Planned scenarios:

* Category navigation
* Product details page validation
* Product search
* Product information verification

Status: ⏳ Planned

---

### Phase 5 – Shopping Cart

Planned scenarios:

* Add product to cart
* Update quantity
* Remove product
* Verify cart totals

Status: ⏳ Planned

---

### Phase 6 – Checkout

Planned scenarios:

* Guest checkout
* Registered user checkout
* Order confirmation
* End-to-end purchase flow

Status: ⏳ Planned

---

## Future Framework Enhancements

* Parallel execution support
* Retry Analyzer for flaky tests
* Screenshot capture on failure
* Extent Reports integration
* GitHub Actions CI/CD pipeline
* Jenkins integration

---

## How to Run

### Run Smoke Suite

```text
Run As → TestNG Suite
testng-smoke.xml
```

### Run Regression Suite

```text
Run As → TestNG Suite
testng-regression.xml
```

---
