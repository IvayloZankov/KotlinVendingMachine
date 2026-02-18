# Kotlin Vending Machine

Modern Android application that simulates a real-world vending machine experience. Built using **Kotlin** and **Jetpack Compose**, it demonstrates best practices in Android development, including **Clean Architecture**, **MVVM**, and **Dependency Injection**.

The app allows users to browse products, insert coins, make purchases, and receive change. It also includes a maintenance mode for restocking and resetting the machine.

## Tech Stack & Libraries

*   **Language:** Kotlin
*   **UI:** Jetpack Compose (Material 3)
*   **Architecture:** MVVM (Model-View-ViewModel)
*   **Dependency Injection:** Hilt
*   **Networking:** Retrofit
*   **Local Storage:** Room Database
*   **Concurrency:** Coroutines
*   **Navigation:** Compose Navigation

## Features

### 🛒 Products Screen
*   **Product Listing:** Fetches and displays available products from the data source.
*   **Stock Management:** Shows only products in stock.
*   **Alerts:**
    *   Displays an "Out of Order" warning if the machine has insufficient coins to provide change.
    *   Handles network errors with "No Connection" alerts and retry options.
*   **Navigation:** Tapping a product navigates to the payment (Coins) screen.
*   **Maintenance Access:** A settings icon in the top bar grants access to the Maintenance screen.

### 💰 Coins Screen
*   **Order Details:** Shows the selected product's name and price.
*   **Payment Simulation:** Users can "insert" coins by tapping on coins.
*   **Real-time Updates:** Displays the total inserted amount as coins are added.
*   **Automatic Dispensing:** Once the inserted amount meets or exceeds the product price, the purchase is processed automatically.
*   **Change Calculation:** The app calculates and returns the correct change.
*   **Order Cancellation:** Users can cancel the order to receive a refund of their inserted coins.

### 🛠 Maintenance Screen
*   **Reset Options:**
    *   **Reset Products:** Restocks all products to their initial quantities.
    *   **Reset Coins:** Resets the internal coin storage to its initial state.

## Architecture

The application follows the **MVVM (Model-View-ViewModel)** architectural pattern to ensure separation of concerns and testability.

*   **UI Layer:** Composable functions (`ProductsFragment`, `CoinsFragment`, etc.) rendering the UI based on state.
*   **ViewModel Layer:** (`ProductsViewModel`, `CoinsViewModel`) Manages UI state and handles business logic, communicating with the domain/data layers.
*   **Domain/Data Layer:** Repositories and Data Sources (Retrofit Service, Room DAO) handling data operations.

## Testing

The project includes both Unit tests and UI/Integration tests to ensure code quality and feature stability.

### Unit Tests
Cover the domain logic, including:
*   **Use Cases:** Validating business rules for product selection, coin handling, and change calculation.
*   **Repositories:** Testing data handling and transformation.

### UI Tests (Jetpack Compose)
Verify the UI components and user interactions using the Compose Testing APIs.
*   **Screen Tests:** Ensuring `ProductsScreen`, `CoinsScreen`, and `MaintenanceScreen` display correct states and handle user inputs (clicks, navigation).
*   **Integration:** Verifying the flow between screens and the interaction with ViewModels.
