# Shopping Cart Application

## Environment
- **IDE:** Eclipse IDE
- **Java Version:** Java 17
- **JavaFX Version:** JavaFX 22.0.1
- **JUnit Version:** JUnit 5
- **Database:** SQLite with JDBC

## Installation and Execution

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/AdvancedPrograming/assignment2-NithyaNN3.git
2. **Run Application:**
	Open the main class (Main.java) located in the src directory.
	Right-click on the Main class.
	Choose "Run As" > "Java Application" from the context menu.

## Some Housekeeping
- Name of Repo on GitHub Classroom is AdvancedPrograming
- Assumed that all times entered by user is correct "HH:MM" 24-our format
- Assumed that Cart Flow leads to Order placement flow so 'Place Order' on Dashboard leads to Cart first and then Order payment
- GUI expects user to have entered timings and have pressed 'Enter' after updating
- Time feature also acts as a refresh feature
- It is assumed that field names also match the column names on the database.
- 'View orders' doesn't auto update when you 'Cancel order' so you'll have to enter time for it to happen.

## OO Design
**Model:**
Customer (model):

Represents a customer in the system.
Attributes: first name, last name, user name, password, email ID, credits.
Provides methods for setting and getting customer details.

CreditCard (model):

Represents a credit card for payment.
Attributes: credit card number, expiry date, CVV.
Provides methods for validating the credit card number, expiry date, and CVV.

Food (model):

Represents a food item available for purchase.
Attributes: name, quantity, price.
Provides methods for setting and getting food details.

ShoppingCart (model):

Represents a shopping cart where customers can add food items.
Attributes: list of items, total price.
Provides methods for adding, updating, and removing items from the cart, as well as calculating the total price.

**View:**
FXML Files (view):

-Represent the UI components of the application.
-Define the layout and appearance of each screen using FXML markup.
-Include elements such as buttons, text fields, labels, and other UI controls.


**Controller:**
DashboardController (controller):

-Controls the main dashboard screen.
-Handles user interactions such as placing orders, editing profiles, and logging out.
-Communicates with the model to retrieve and update customer information.

ShoppingCartController (controller):

-Controls the shopping cart screen.
-Manages the addition, removal, and updating of items in the shopping cart.
-Calculates and displays the total price of the items in the cart.

LoginController (controller):

-Controls the login screen.
-Validates user credentials and redirects to the dashboard upon successful login.
-Handles user registration and password recovery functionalities.

**Overview:**
-The Model layer consists of classes that represent the core data and business logic of the application, such as customer information, credit card details, food items, and the shopping cart.
-The View layer consists of FXML files and CSS stylesheets that define the user interface and its visual appearance.
-The Controller layer consists of classes that manage the interaction between the model and the view. Each controller is responsible for handling user actions, updating the model, and updating the view accordingly.
-The MVC architecture promotes separation of concerns, making the codebase more modular, maintainable, and easier to test.
-The model encapsulates the application's data and behavior, the view represents the presentation layer, and the controller acts as an intermediary between the model and the view, handling user input and updating the model accordingly.
