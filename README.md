# Demo Web Shop End-to-End Test Automation Framework

## Overview

UI Test Automation Framework for Tricentis Demo Web Shop built using:

* Java 21
* Selenium WebDriver 4
* TestNG
* Maven
* WebDriverManager
* H2 Database
* JDBC
* SQL

The framework follows the Page Object Model (POM) design pattern and is being developed incrementally to simulate a real-world automation project.

---

## Framework Features

* Page Object Model (POM)
* Cross-browser execution
* Configurable headless execution
* Explicit waits
* Dynamic test data generation
* TestNG DataProviders, groups and suite execution
* Centralized configuration management
* Reusable page actions through BasePage
* Dynamic element attribute handling
* Authenticated end-to-end user flows
* Configurable product automation
* Dynamic price calculation
* Randomized test data selection
* SLF4J + Logback logging
* Java 21 Records, Switch Expressions and Text Blocks 
* DriverManager with ThreadLocal WebDriver support
* TestNG Listeners
* Automatic screenshot capture on test failure
* Luhn-valid credit card generation
* Dynamic Purchase Order number generation
* SQL database validation
* JDBC repository layer
* Order lifecycle orchestration
* Warehouse API simulation
* Multi-layer end-to-end validation

---

## Continuous Integration

This project uses GitHub Actions to automatically execute the full regression suite on every push and pull request to the `main` branch.

### CI Pipeline

- Checks out the latest source code
- Sets up Java 21 environment
- Restores Maven dependencies
- Executes the TestNG regression suite
- Reports build and test execution status directly on GitHub

### Benefits

- Automatic regression validation
- Early detection of broken functionality
- Consistent execution environment
- Improved code quality and reliability

Current regression status:

```text
70 / 70 Tests Passing
```

---

## TestNG Listeners

The framework uses custom TestNG listeners to monitor and react to test execution events.

Current functionality:

* Test execution lifecycle logging
* Screenshot capture on failure
* Failure diagnostics

Benefits:

* Improved execution visibility
* Faster failure analysis
* Foundation for future reporting integrations

---

## Project Structure

```text
src
├── main
│   └── java
│       └── com.bogdan.automation
│           ├── driver
│           │   ├── DriverFactory
│           │   └── DriverManager
│           ├── clients
│           │   ├── WarehouseApiClient
│           │   └── WarehouseApiResponse
│           ├── database
│           │   ├── DatabaseInitializer
│           │   └── DatabaseManager
│           ├── models
│           ├── repositories
│           │   └── OrderRepository
│           ├── services
│           │   └── OrderLifecycleService
│           ├── pages
│           └── utils
│               ├── ConfigReader
│               ├── FakeCreditCardGenerator
│               ├── ProductDataReader
│               ├── PurchaseOrderGenerator
│               ├── Randomizer
│               ├── ScreenshotUtils
│               └── TestDataGenerator
│
└── test
    └── java
        └── com.bogdan.automation
            ├── base
            ├── listeners
            │   └── TestListener
            └── tests
                ├── authentication
                ├── database
                ├── checkout
                ├── e2e
                ├── product
                ├── registration
                ├── search
                ├── shoppingcart
                └── wishlist
```

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
* Verify cart total for multiple products
* Verify multiple products are displayed correctly in cart

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
* Multiple-product cart validation verifies dynamic cart total calculations.
* Randomized cart composition is used to improve regression coverage.
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

### Phase 6 – Checkout

Status: ✅ In Progress

#### Step 1 – Registered User Checkout Entry

Implemented scenarios:

* Open checkout as a registered user
* Validate checkout page structure
* Validate Terms of Service blocking behavior
* Validate checkout opens only after Terms of Service acceptance

Status: ✅ Complete

---

#### Step 2 – Address and Shipping Flow

Implemented scenarios:

* Complete billing address step
* Reuse saved billing address when available
* Generate customer name dynamically using Randomizer
* Continue through shipping address step
* Select Ground shipping method
* Validate payment method step is reached after shipping method selection

Status: ✅ Complete

---

#### Step 3 – Payment Methods

Implemented scenarios:

* Cash On Delivery payment
* Check / Money Order payment
* Credit Card payment
* Purchase Order payment

Validated behavior:

* Cash On Delivery displays the expected COD payment message
* Check / Money Order displays the Tricentis payment instructions
* Credit Card payment accepts generated valid card data
* Purchase Order payment accepts generated PO numbers
* All payment methods correctly reach the Confirm Order step

Status: ✅ Complete

---

#### Step 4 – Credit Card Test Data Generation

Implemented functionality:

* FakeCreditCardGenerator utility
* Luhn-valid credit card number generation
* Visa card number generation
* MasterCard card number generation
* Discover card number generation
* Amex card number generation
* Dynamic expiration month generation
* Dynamic future expiration year generation
* Dynamic card code generation

Notes:

* Credit card numbers are generated dynamically instead of hardcoded.
* Generated card numbers pass Luhn validation.
* The cardholder name is aligned with the generated checkout customer name.
* This makes the credit card checkout flow more realistic and portfolio-relevant.

Status: ✅ Complete

---

#### Step 5 – Order Confirmation

Implemented scenarios:

* Confirm completed checkout order
* Validate successful order completion message
* Validate order number is generated after confirmation

Status: ✅ Complete

---

Notes:

* Credit card test data is generated dynamically using the FakeCreditCardGenerator utility.
* Generated card numbers include a valid Luhn checksum and are accepted by the application.
* Purchase Order scenarios use dynamically generated PO numbers.
* Billing address and payment information use consistent customer data during checkout execution.

### Phase 7 – End-to-End User Journeys

Status: ✅ Complete

#### Step 1 – Guest Checkout

Implemented scenario:

* Complete checkout as a guest user
* Validate guest checkout entry point
* Complete billing address step
* Complete shipping and payment flow
* Confirm order successfully

Status: ✅ Complete

---

#### Step 2 – Registered User End-to-End Purchase Flow

Implemented scenario:

* Register a new user
* Log in with the newly created account
* Add multiple products to cart
* Validate cart contents before checkout
* Complete billing address flow
* Complete shipping and payment flow
* Confirm order successfully
* Open Order Details page
* Validate order information
* Validate billing and shipping information
* Validate purchased products section

Status: ✅ Complete

---

#### Step 3 – Order Details Validation

Implemented validations:

* Order Details page is accessible after purchase
* Order number is displayed
* Order status is displayed
* Order total is displayed
* Billing address section is displayed
* Shipping address section is displayed
* Purchased products section is displayed

Status: ✅ Complete
```

### Phase 8 – Order Lifecycle Validation

Status: ✅ Complete

#### Step 1 – SQL Database Integration

Implemented functionality:

* H2 embedded database integration
* JDBC database connectivity
* Automated schema creation through `schema.sql`
* Order persistence after successful checkout

Implemented tables:

* customers
* orders
* payments
* shipments
* warehouse_events

Status: ✅ Complete

---

#### Step 2 – Repository Layer

Implemented functionality:

* OrderRepository
* Order creation and persistence
* Order existence validation
* Warehouse status updates

Status: ✅ Complete

---

#### Step 3 – Warehouse Integration Simulation

Implemented functionality:

* WarehouseApiClient
* WarehouseApiResponse
* Warehouse order acceptance simulation
* Warehouse reference generation

Validated behavior:

* Order status updated from `COMPLETED` to `SENT_TO_WAREHOUSE`
* Shipment status updated to `PROCESSING`
* Warehouse event recorded successfully

Status: ✅ Complete

---

#### Step 4 – End-to-End Order Lifecycle Validation

Implemented scenario:

* Complete checkout through UI
* Extract real order number from Order Details page
* Persist order in SQL database
* Validate order existence in database
* Simulate warehouse processing
* Validate warehouse status updates

End-to-end flow:

```text
Selenium UI
      ↓
Checkout Completed
      ↓
Extract Order Number
      ↓
OrderLifecycleService
      ↓
H2 Database
      ↓
WarehouseApiClient (Simulation)
      ↓
Database Validation
```

Notes:

* The Demo Web Shop application does not expose backend APIs or provide database access.
* The warehouse integration is simulated to demonstrate real-world SDET patterns.
* The simulated client can be replaced with a real REST client in the future without changing the test flow.


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

---

### BasePage Utilities

Common reusable functionality includes:

* Element interactions
* Explicit waits
* Attribute retrieval
* Dynamic typing support
* Shared page operations

These utilities reduce duplication and improve framework maintainability.

---

### DriverManager

Manages WebDriver instances through a centralized ThreadLocal implementation.

Benefits:

* Improved driver lifecycle management
* Listener-friendly driver access
* Future-ready for parallel execution
* Reduced test coupling

---
### Screenshot Utils

Provides centralized screenshot capture functionality.

Current usage:

* Automatic screenshot capture on test failure

Benefits:

* Faster debugging
* Failure evidence collection
* Reporting integration ready

---

### FakeCreditCardGenerator

Provides dynamic generation of valid credit card test data for checkout automation.

Current functionality:

* Visa card generation
* MasterCard generation
* Discover card generation
* American Express generation
* Dynamic expiration month generation
* Dynamic future expiration year generation
* Dynamic CVV generation
* Luhn algorithm validation support

Implementation details:

* Card numbers are generated dynamically based on the selected card type.
* Generated card numbers include a valid Luhn checksum digit.
* The framework calculates the final checksum using the Luhn algorithm to produce realistic card numbers accepted by the application.

Benefits:

* Eliminates hardcoded credit card data
* Improves realism of checkout testing
* Supports multiple payment providers
* Reduces maintenance effort
* Demonstrates real-world test data generation techniques

---

### PurchaseOrderGenerator

Provides dynamic generation of Purchase Order numbers for checkout automation.

Current functionality:

* Unique PO number generation
* UUID-based identifier creation

Example output:

```text
PO-A1B2C3D4
PO-8F4E91AA
```


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

## Current Automation Coverage

```text
Phase 1 - Smoke                    ✅ Complete
Phase 2 - Authentication           ✅ Complete
Phase 3 - Registration             ✅ Complete

Phase 4 - Product Catalog
  Step 1 - Product Search          ✅ Complete
  Step 2 - Advanced Search         ✅ Complete
  Step 3 - Search Filters          ✅ Complete
  Step 4 - Search Sorting          ✅ Complete
  Step 5 - Product Details         ✅ Complete
  Step 6 - Product Types           ✅ Complete
  Step 7 - Product Catalog Scanner ✅ Complete

Phase 5 - Shopping Cart
  Step 1 - Basic Cart              ✅ Complete
  Step 2 - Cart Updates            ✅ Complete
  Step 3 - Cart Header             ✅ Complete
  Step 4 - Configurable Products   ✅ Complete
  Step 5 - Gift Cards              ✅ Complete
  Step 6 - Downloadable Products   ✅ Complete
  Step 7 - Wishlist Functionality  ✅ Complete
  Step 8 - Product Catalog Scanner ✅ Complete

Additional Coverage
  ✓ Multi-product cart validation
  ✓ Dynamic cart total calculation

Phase 6 - Checkout
  Step 1 - Checkout Entry          ✅ Complete
  Step 2 - Address & Shipping Flow ✅ Complete
  Step 3 - Payment Methods         ✅ Complete
  Step 4 - Credit Card Generation  ✅ Complete
  Step 5 - Order Confirmation      ✅ Complete

Phase 7 - End-to-End User Journeys
  Step 1 - Guest Checkout          ✅ Complete
  Step 2 - Registered User E2E     ✅ Complete
  Step 3 - Order Details Validation✅ Complete
  
Phase 8 - Order Lifecycle Validation
  Step 1 - SQL Integration          ✅ Complete
  Step 2 - Repository Layer         ✅ Complete
  Step 3 - Warehouse Simulation     ✅ Complete
  Step 4 - E2E Order Validation     ✅ Complete
```

---

## Future Framework Enhancements

- Parallel test execution support
- Retry Analyzer for flaky test recovery
- Extent Reports integration
- Jenkins pipeline integration
- Cross-browser execution support
- Dockerized test execution
- Replace H2 with PostgreSQL
- Replace warehouse simulation with REST Assured
- Add real API contract validation
- Containerize database with Docker
- API and UI hybrid automation coverage
---

## Order Lifecycle Architecture

The framework extends traditional UI automation by validating the complete order lifecycle across multiple layers.

Current flow:

```text
Selenium UI Test
       ↓
OrderLifecycleService
       ↓
OrderRepository
       ↓
H2 Database
       ↓
WarehouseApiClient (Simulation)
```

The implementation demonstrates:

* UI automation with Selenium WebDriver
* Database validation with JDBC
* SQL repository pattern
* Service layer orchestration
* External system simulation

This architecture models real-world e-commerce systems where orders are processed across multiple backend components after checkout.

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
