# Kotlin Vending Machine

## About

Android vending machine implementation on Kotlin with Jetpack Compose, Hilt-Dagger, Coroutines, 
Retrofit, Room, DataStore.

## Description

### Products screen

App fetches data from server and displays available products.
"Out of order" alert when insufficient coins for change
On product click user is redirected to coins screen.
On maintenance icon click app enters maintenance.

### Coins screen

On coin click user insert coin.
Product is decreased when added coins meet product price and app returns change if any.
On cancel order app returns inserted coins and redirects to products.

### Maintenance screen

On "Reset products" click products reset to initial state.
On "Reset coins" click coins reset to initial state.
