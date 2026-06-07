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
* TestNG DataProviders
* TestNG groups
* TestNG suite execution
* Centralized configuration management
* Reusable page actions through BasePage
* Dynamic element attribute handling
* Authenticated end-to-end user flows
* Configurable product automation
* Dynamic price calculation
* Randomized test data selection
* SLF4J + Logback logging
* Java 21 Records
* Java 21 Switch Expressions
* Java 21 Text Blocks

---

## Project Structure

```text
src
├── main
│   └── java
│       └── com.bogdan.automation
│           ├── driver
│           ├── models
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
                ├── registration
                ├── catalog
                └── shoppingcart
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

### Phase 4 – Product Catalog

Status: ✅ Complete

#### Step 1 – Category Navigation

Implemented scenarios:

* Category navigation
* Category content validation
* Sub-category navigation
* Product details page

Status: ✅ Complete

---

#### Step 2 – Search

Implemented scenarios:

* Basic Search
* Search With No Results

Status: ✅ Complete

---

#### Step 3 – Advanced Search

Implemented scenarios:

* Advanced Search by Category
* Advanced Search with Subcategories
* Advanced Search in Product Descriptions
* Advanced Search by Price Range

Status: ✅ Complete

---

#### Step 4 – Product Sorting

Implemented scenarios:

* Sort By Name: A to Z
* Sort By Name: Z to A
* Sort By Price: Low to High
* Sort By Price: High to Low

Status: ✅ Complete

---

#### Step 5 – Product Display Options

Implemented scenarios:

* Display 4 Products Per Page
* Display 8 Products Per Page
* Display 12 Products Per Page

Status: ✅ Complete

---

#### Step 6 – Product View Modes

Implemented scenarios:

* Grid View
* List View

Status: ✅ Complete

---

### Product Catalog Notes

* Manufacturer filtering was investigated but is currently excluded from the automation suite because the Demo Web Shop application does not return valid search results when filtering by the only available manufacturer ("Tricentis").
* Product catalog scenarios use TestNG DataProviders where appropriate to reduce duplication and improve maintainability.

---

### Phase 5 – Shopping Cart

Status: ✅ Complete

#### Step 1 – Basic Shopping Cart Functionality

Implemented scenarios:

* Add simple product to cart
* Verify product appears in cart
* Remove product from cart

Status: ✅ Complete

---

#### Step 2 – Cart Updates and Totals

Implemented scenarios:

* Update product quantity
* Verify product subtotal updates correctly
* Verify cart total updates correctly

Status: ✅ Complete

---

#### Step 3 – Shopping Cart Header Functionality

Implemented scenarios:

* Verify Shopping Cart quantity updates in header after adding a product

Status: ✅ Complete

---

#### Step 4 – Configurable Products

Implemented scenarios:

* Configure "Build your own expensive computer"
* Select processor options
* Select RAM options
* Select HDD options
* Select optional software
* Verify calculated price matches application price
* Add configured product to cart
* Verify selected configuration in cart
* Verify shopping cart subtotal
* Verify shopping cart total

Advanced scenarios:

* Fixed configuration validation
* Random configuration generation
* Dynamic expected price calculation
* Randomized configuration verification against cart data

Status: ✅ Complete

Notes:

* Configurable products use a dedicated Page Object Model.
* Product options are stored using Maps for improved maintainability.
* Expected prices are calculated dynamically based on selected options.
* Randomized configurations are generated using the Randomizer utility.
* Shopping Cart validations verify both configuration data and pricing calculations.

---

#### Step 5 – Gift Cards

Implemented scenarios:

* Purchase virtual gift card
* Validate required gift card fields
* Validate invalid recipient email
* Verify gift card details in shopping cart

Status: ✅ Complete

Notes:

* Gift Card functionality uses a dedicated GiftCardPage.
* Validation messages are verified through application notifications.
* Shopping Cart validations verify sender and recipient information.

---

### Shopping Cart Framework Notes

* Shopping Cart tests are executed using an authenticated user.
* Shopping Cart cleanup removes all products before execution.
* Explicit waits ensure cart cleanup completes before test execution continues.
* Cart isolation improvements were implemented to support reliable regression execution.
* Tests follow a realistic user flow:

```text
Login
  ↓
Search Product
  ↓
Open Product Page
  ↓
Add Product To Cart
  ↓
Open Shopping Cart
```

---


#### Step 6 – Downloadable Products

Planned scenarios:

* Add downloadable product to cart
* Verify downloadable product in cart
* Complete checkout preparation for downloadable product

Status: ✅ Complete

---

#### Step 7 – Wishlist Functionality

Implemented scenarios:

* Add product to wishlist
* Remove product from wishlist
* Validate wishlist-only products

Supporting components:

* WishlistPage
* Wishlist cleanup utilities
* Product action classification

Status: ✅ Complete

---

#### Step 8 – Product Catalog Scanner

Implemented functionality:

* Automated product catalog scanning
* Product availability detection
* Product action detection
* Product metadata generation
* JSON catalog maintenance support

Captured metadata:

* Product name
* Category
* Subcategory
* Price
* Availability
* Supported action
* Notes

Supported actions:

* ADD_TO_CART
* ADD_TO_WISHLIST
* VIEW_ONLY

Status: ✅ Complete

Notes:

* ProductCatalogScannerTest is used to scan products directly from the application.
* Scanner output is used to maintain products.json.
* Product tests dynamically select compatible products based on supportedAction.
* This reduces hardcoded test data and improves long-term maintainability.

---

## Upcoming Phases

### Phase 6 – Checkout

Planned scenarios:

* Guest checkout
* Registered user checkout
* Billing address validation
* Shipping address validation
* Shipping method selection
* Payment method selection
* Order review
* Order confirmation
* End-to-end purchase flow

Status: ⬜ Not started

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
* Product Catalog Tests
* Search Tests
* Shopping Cart Tests

As new phases are implemented, they will automatically become part of the regression suite.

---

## Configuration

Example configuration:

```properties
browser=chrome
headless=false
baseUrl=https://demowebshop.tricentis.com

validEmail=automation_tester@test.com
validPassword=Password123!

invalidEmail=invalid@test.com
invalidPassword=WrongPassword123
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

### Randomizer

Provides reusable random data generation and random selection utilities.

Current functionality:

* Random selection from Lists
* Random selection from Maps
* Random selection of multiple items from a List
* Random integer generation within a range
* Random first name generation
* Random last name generation

Current usage:

* Random simple product selection
* Random cart-ready product selection
* Random wishlist-ready product selection
* Random gift card selection
* Random configurable product selection
* Random configurable product option selection
* Random product quantity updates
* Random recipient name generation for gift card tests

Benefits:

* Improves test coverage
* Enables dynamic test execution
* Reduces hardcoded test data
* Supports reusable test data generation

---

### Logging

The framework uses SLF4J with Logback for structured logging.

Current usage:

* Random configurable product generation
* Shopping Cart validation
* Dynamic price verification
* Product catalog scanning
* Product classification validation

Benefits:

* Cleaner console output
* Timestamped execution logs
* Easier debugging and test analysis
* Centralized logging configuration

### BasePage Utilities

Common reusable functionality includes:

* Element interactions
* Explicit waits
* Attribute retrieval
* Dynamic typing support
* Shared page operations

These utilities reduce duplication and improve framework maintainability.

---

## Models

### Product Models

Current models:

* ProductData
* GiftCardData
* ConfigurableProductData
* ProductAttributes
* ProductOption
* ComputerConfiguration

Benefits:

* Strong typing
* JSON-driven execution
* Reusable test data structures

---

## Test Data Management

### TestNG DataProviders

The framework uses TestNG DataProviders for data-driven testing.

Current usage:

* Product category navigation
* Product subcategory navigation
* Product display options
* Product view modes
* Product sorting

Benefits:

* Eliminates duplicate test methods
* Improves maintainability
* Simplifies test coverage expansion

---

### JSON Driven Product Data

Product test data is maintained in:

src/test/resources/testdata/products.json

Current support:

* Simple products
* Downloadable products
* Gift cards
* Configurable products

Benefits:

* Centralized product maintenance
* Dynamic product selection
* Reduced test fragility
* Easier expansion of product coverage

---

## Current Automation Coverage

```text
Phase 1 - Smoke                    ✅ Complete
Phase 2 - Authentication           ✅ Complete
Phase 3 - Registration             ✅ Complete
Phase 4 - Product Catalog          ✅ Complete

Phase 5 - Shopping Cart
  Step 1 - Basic Cart              ✅ Complete
  Step 2 - Cart Updates            ✅ Complete
  Step 3 - Cart Header             ✅ Complete
  Step 4 - Configurable Products   ✅ Complete
  Step 5 - Gift Cards ✅ Complete
  Step 6 - Downloadable Products ✅ Complete
  Step 7 - Wishlist Functionality    ✅ Complete
  Step 8 - Product Catalog Scanner   ✅ Complete

Phase 6 - Checkout                 ⬜ Not Started
```

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
