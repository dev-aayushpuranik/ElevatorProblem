# Elevator Movement Display App

# Overview
This project is an Android application developed using Kotlin and Jetpack Compose. The goal of the app is to simulate and display the movement of an elevator. It shows the elevator's direction (moving up or down) and the current floor in real-time. The project is structured using the MVVM (Model-View-ViewModel) architectural pattern to ensure clean separation of logic, UI, and data handling.

# Features
- Display the current floor of the elevator.
- Show the direction of the elevator: whether it's moving up or down.
- Real-time update of the floor and direction.
- Clean and responsive UI built with Jetpack Compose.
- Basic MVVM implementation for separation of concerns.

# Tech Stack
**Language**: Kotlin
**UI Framework**: Jetpack Compose
**Architecture**: MVVM (Model-View-ViewModel)
**Build Tool**: Gradle
**Development Environment**: Android Studio

# Project Structure
The project follows the MVVM architecture:

**Model**: Holds the data and business logic related to the elevator's state (current floor and direction).

**View**: The UI elements created using Jetpack Compose that display the current floor and elevator movement.

**ViewModel**: Manages the state of the elevator and handles the interaction between the View and Model.

# Getting Started
- Prerequisites
- Android Studio (Arctic Fox or above)
- Android SDK
- Kotlin 1.5+
- Jetpack Compose dependencies

# Key Dependencies
dependencies {
    // Jetpack Compose
    implementation "androidx.compose.ui:ui:1.0.0"
    implementation "androidx.compose.material:material:1.0.0"
    implementation "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07"

    // ViewModel
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0"

    // Kotlin Coroutines
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0"
}

# How It Works
**Elevator State Management**: The elevator's current floor and direction are stored in a ViewModel using LiveData or StateFlow.
UI Update: The Jetpack Compose UI observes changes to the ViewModel's state and updates the displayed floor and direction accordingly.
User Interaction: You can trigger elevator movement by simulating button clicks or time intervals to move the elevator between floors.

# Future Enhancements
- Add support for multiple elevators.
- Implement smooth animations for elevator movement.
- Integrate more complex state handling (e.g., floor requests, queued actions).

# License
This project is licensed under the Apache License 2.0

# Contact
For any questions or suggestions, feel free to reach out to me at **aayushpuranik1992@gmail.com**


