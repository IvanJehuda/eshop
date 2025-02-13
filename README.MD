# Advanced Programing E-shop
Ivan Jehuda Angi - 2306152222 - Adpro A 
## Contents
1.  [Reflection 1](#reflection-1)

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
