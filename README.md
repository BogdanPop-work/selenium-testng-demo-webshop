# Demo Web Shop Automation Framework

## Overview

UI Test Automation Framework for Tricentis Demo Web Shop built with:

* Java 21
* Selenium WebDriver 4
* TestNG
* Maven
* WebDriverManager

The framework follows the Page Object Model (POM) design pattern and focuses on scalability, maintainability, and real-world automation practices.

---

## Framework Features

* Page Object Model (POM)
* Cross-browser execution
* Configurable headless execution
* Explicit waits
* Dynamic test data generation
* TestNG grouping
* Centralized configuration management

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
* Invalid password
* Invalid email
* Empty email
* Empty password
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

## Current Project Structure

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

## Configuration

Example:

```properties
browser=chrome
headless=false
baseUrl=https://demowebshop.tricentis.com
```

### Headless Execution

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

* TestNG XML Suite Execution
* Parallel Execution
* Retry Analyzer
* Screenshots on Failure
* Extent Reports
* GitHub Actions CI/CD

---
