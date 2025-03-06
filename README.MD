# Advanced Programing E-shop
Ivan Jehuda Angi - 2306152222 - Adpro A 
## Contents
1.  [Reflection 1](#reflection-1)
2. [Reflection 2](#reflection-2)
3. [Module 2 Reflection](#Module-2-Reflection)
4. [Module 3 Reflection](#Module-3-Reflection)
5. [Module 4 Reflection](#Module-4-Reflection)


## Reflection-1
### What I  learned?
During this exercise, I learned several important principles about writing clean code. One of the key takeaways is the importance of using meaningful variable names. Choosing clear and descriptive names for variables makes the code easier to read and understand, reducing the need for additional comments or explanations. Instead of using generic names like x or temp, it is better to use names that reflect the purpose of the variable, such as userAge or orderTotal.
- Example
    ```java
  public Product create(Product product);
    public List<Product> findAll();
    Product findById(String productId);
    Product update(Product product);
    boolean delete(String productId);

  ```
Another crucial principle is that functions should be small and focused on doing only one task. A function that tries to handle multiple responsibilities can quickly become difficult to maintain and debug. By keeping functions concise and ensuring they adhere to the Single Responsibility Principle, the code becomes more modular, reusable, and easier to test.
- Example
```java
public Product update(Product updatedProduct) {
    for (int i = 0; i < productData.size(); i++) {
        if (productData.get(i).getProductId().equals(updatedProduct.getProductId())) {
            productData.set(i, updatedProduct);
            return updatedProduct;
        }
    }
    return null;
}
  ```
The code also effectively organizes product-related logic within a Product class, rather than passing multiple unrelated values around.

- Example
```java
@Getter @Setter
public class Product {
    private String productId;
    private String productName;
    private int productQuantity;

    public Product() {
        this.productId = UUID.randomUUID().toString(); // Generate unique ID
    }
}
  ```

### Mistakes in the source code
In addition to these best practices, I also identified some mistakes in the source code and ways to improve it. One improvement is implementing input validation. Without proper validation, the program may accept incorrect or unexpected inputs, leading to errors or security vulnerabilities. Adding checks for valid input formats and constraints can make the program more robust and reliable.

Another improvement is the use of comments to enhance code readability. While clean and well-structured code should be self-explanatory, comments can still be valuable in explaining complex logic, outlining function purposes, or providing context for future developers. 

# Reflection-2
## How I feel after making the unit test
Writing unit tests has given me greater confidence in my code by allowing me to confirm that each function behaves as intended. Through this process, I gained a deeper understanding of my program's structure and identified potential issues that could occur during execution. Moreover, troubleshooting failed tests offered valuable insights into edge cases I had not previously considered.
## How many unit test should be made
The number of unit tests in a class depends on the complexity and functionality of the class. Ideally, every function should have at least one test, but for functions with multiple possible inputs and edge cases, multiple test cases are necessary. A good approach is to use the Arrange-Act-Assert pattern to systematically cover different behaviors, including:
- Valid inputs (ensuring expected outputs)
- Invalid inputs (verifying error handling)
- Boundary cases (testing extreme values)
## How to make sure that our unit tests are enough to verify our program?
  One way to ensure adequate testing is to use code coverage analysis. Code coverage measures how much of your code is executed when running the test suite. There are different types of coverage:

- Statement coverage: Checks if each line of code is executed.
- Branch coverage: Ensures that every possible condition (e.g., if statements) is tested.
- Path coverage: Verifies all possible execution paths are tested.
## If you have 100% code coverage, does that mean your code has no bugs or errors?
No, 100% code coverage does not guarantee that the code is completely free of bugs. While high coverage ensures that all parts of the code are executed during testing, it does not mean that the tests check for logical correctness or all possible edge cases. Bugs can still exist due to:

- Unrealistic test scenarios (e.g., missing real-world input variations).
- Incorrect assertions (tests that pass but do not check correctness properly).
- Integration issues (unit tests check small components, but problems may arise when they interact with each other).
## Code Cleanliness in the New Functional Test Suite
The new code may reduce code quality if it duplicates a significant amount of logic from the `CreateProductFunctionalTest.java` class without proper refactoring. Repetitive setup procedures and instance variables can lead to code duplication, which makes the codebase harder to maintain and modify.

If the new functional test suite has the same setup procedures, instance variables, and common test logic as the previous test class, it violates the DRY (Don't Repeat Yourself) principle.
Duplication increases maintenance effort since changes in one test suite must be manually replicated in others.
This can also introduce inconsistencies if one test suite is updated but another is overlooked.

To improve the cleanliness and maintainability of the new test suite, we can apply refactoring techniques:

- Extract Common Setup Logic into a Base Test Class
  ```java
  @SpringBootTest(webEnvironment = RANDOM_PORT)
  @ExtendWith(SeleniumJupiter.class)
  abstract class BaseFunctionalTest {
      @LocalServerPort
      protected int serverPort;
  
      @Value("${app.baseUrl:http://localhost}")
      protected String testBaseUrl;
  
      protected String baseUrl;
  
      @BeforeEach
      void setupTest() {
          baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
      }
  }
  
  ```
- Then, both `CreateProductFunctionalTest.java` and the new test suite can extend this class instead of repeating setup logic.

Here's a more polished and properly formatted version of your Markdown file:

---

# **Module-2-Reflection**
## **Code Coverage**
- ![image](CodeCoverage.png)
## **Deployment Link**
- https://loud-miran-ivanjehuda-b507d972.koyeb.app/ 


## **Code Quality Issues I Have Fixed**

### **1. JUnit 5 Test Method Naming Convention**
**Issue:** The test method name `'testFindById_NonExistentProduct'` does not match the required naming pattern `[a-z][a-zA-Z0-9]*`.

**Fix:** Changed the test method name to follow **camelCase** convention.

#### **Before:**
```java
@Test
void test_FindById() {
    when(productRepository.findById("123")).thenReturn(sampleProduct);

    Product foundProduct = productService.findById("123");

    assertNotNull(foundProduct);
    assertEquals(sampleProduct.getProductId(), foundProduct.getProductId());
    verify(productRepository, times(1)).findById("123");
}
```  

#### **After:**
```java
@Test
void testFindById() {
    when(productRepository.findById("123")).thenReturn(sampleProduct);

    Product foundProduct = productService.findById("123");

    assertNotNull(foundProduct);
    assertEquals(sampleProduct.getProductId(), foundProduct.getProductId());
    verify(productRepository, times(1)).findById("123");
}
```  



### **2. Empty Method Body**
**Issue:** The method `contextLoads()` has an empty body, which may cause confusion.

**Fix:** Added an assertion to clarify the intent of the test.

#### **Before:**
```java
@Test
void contextLoads() {
}
```  

#### **After:**
```java
@Test
void contextLoads() {
    assertThat(true).isTrue();
}
```  



### **3. Unused Static Import**
**Issue:** The test file includes a static import for all assertions, which is unnecessary.

**Fix:** Scoped the import to only include **specific assertions**.

#### **Before:**
```java
import static org.junit.jupiter.api.Assertions.*;
```  

#### **After:**
```java
import static org.junit.jupiter.api.Assertions.assertEquals;
```  

## Has the current implementation  met the definition of Continuous Integration and Continuous Deployment?
I believe my current project aligns with the principles of CI/CD. 
- By utilizing GitHub Actions to execute workflows defined in the `.github/workflows` directory, I have successfully implemented Continuous Integration (CI), as it automatically triggers the workflows whenever a push or pull request is made to a branch. 
- Additionally, for Continuous Deployment (CD), I have integrated Koyeb as my deployment platform, ensuring that my project is automatically deployed whenever changes are pushed or a pull request is submitted.



# **Module-3-Reflection**

## Principles Applied in the Project

In my project, I applied all **SOLID** principles through various refactorings:

### **1. Single Responsibility Principle (SRP)**
- **Separation of Concerns**:
  - `CarController` was moved into its own file instead of being inside `ProductController`.
  - `CarService` was split into **`CarReadService`** (for read operations) and **`CarWriteService`** (for write operations).
  - `CarRepository` was **abstracted into an interface**, ensuring that data access logic is separated from business logic.

### **2. Open/Closed Principle (OCP)**
- The `CarRepository` interface allows adding **new storage implementations (e.g., JPA, MongoDB) without modifying existing code**.
- `CarReadService` and `CarWriteService` can be **extended** (e.g., filtering cars by category) without modifying their current implementations.

### **3. Liskov Substitution Principle (LSP)**
- `CarRepositoryImpl` implements `CarRepository`, meaning it can be replaced by another implementation (e.g., `CarDatabaseRepository`) **without breaking existing functionality**.
- `CarReadServiceImpl` and `CarWriteServiceImpl` can be substituted wherever `CarReadService` and `CarWriteService` are expected.

### **4. Interface Segregation Principle (ISP)**
- Instead of having a single large `CarService` interface, I divided it into **two separate interfaces**:
  - `CarReadService` (for retrieving data)
  - `CarWriteService` (for modifying data)
- `CarRepository` was introduced as an interface, allowing different repository implementations.

### **5. Dependency Inversion Principle (DIP)**
- **The service layer now depends on abstractions (`CarRepository`) instead of concrete classes (`CarRepositoryImpl`)**.
- This makes the system **loosely coupled**, meaning we can switch to a database-backed repository without modifying the service logic.


## **Advantages of Applying SOLID Principles**

### **1. Easier Maintenance and Scalability**
- Example: If we want to **switch from an in-memory repository to a database**, we can just create a new `CarDatabaseRepository` implementing `CarRepository` **without modifying the service layer**.

### **2. Improved Code Organization and Readability**
- Example: The controller now **only handles HTTP requests**, and business logic is handled in services, making it easier to understand.

### **3. Simplified Testing**
- Example: We can easily mock `CarRepository` in unit tests, **allowing us to test the service layer independently**.


## **Disadvantages of Not Applying SOLID Principles**

### **1. Increased Maintenance Complexity**
- Without **SRP**, `CarService` would have mixed read and write logic, making changes **more error-prone**.

### **2. Difficulty in Extending the System**
- Without **OCP**, if we introduce a **new data source** (e.g., a database), we would have to **modify the existing repository instead of simply adding a new implementation**.

### **3. Higher Coupling and Risk of Bugs**
- Without **DIP**, if the controller directly depended on `CarRepositoryImpl`, changing the repository would **require modifying multiple parts of the application**.
# **Module-4-Reflection**

## **Reflection on TDD Workflow**
At first, the Test-Driven Development (TDD) workflow felt weird and difficult because I was used to writing code first and testing later. Writing tests before implementation required a shift in mindset, and it took time to adjust. However, now i realised that, The Test-Driven Development (TDD) workflow was useful in guiding the development process by ensuring that each feature had a corresponding test before implementation. This helped catch errors early and kept the code more structured. However, I found that sometimes writing tests first felt restrictive, especially when I was unsure about the exact implementation details. Next time, I will spend more time designing better test cases before coding and ensure that my tests cover edge cases more effectively.


## **Reflection on F.I.R.S.T. Principles**
The unit tests I created followed the **F.I.R.S.T.** principles well, but there are still areas for improvement. Here’s how my tests align with each principle:

- **Fast**: My tests run quickly, ensuring rapid feedback during development. This principle is well-implemented since im using Mockito.
- **Isolated**: Each test was independent, as fresh instances of `Order` and `OrderRepository` were created in the `setUp()` method. No test relied on the state left behind by others, except for all the statuses of `Order`, meaning this principle was successfully followed.
- **Repeatable**: The tests produced consistent results when run multiple times, ensuring repeatability. This aspect was implemented correctly.
- **Self-validating**: Each test had clear assertions that resulted in a definite pass or fail outcome, removing the need for manual verification.
- **Thorough/Timely**: I managed to write all the tests before implementing the functionality, following the TDD approach correctly. Additionally, the tests covered multiple scenarios (both success and unsuccessful conditions). However, I could improve by adding more edge cases to ensure the system behaves correctly in unexpected situations.
  .

Overall, my tests followed the **F.I.R.S.T.** principles well, but I will continue refining them by ensuring complete independence of test cases, especially regarding shared statuses in `Order`, and edge cases.