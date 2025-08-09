# CMP Notes Client

A modern, cross-platform note-taking application built with **Compose Multiplatform**. 
This client application provides a seamless user experience across Android, iOS, and 
Desktop, powered by a robust Ktor backend.

This is the official client for the **[Ktor Notes API](https://github.com/laetuz/ktor-notes-api)**.

---

## 📸 Screenshots

|   Android   | Desktop |  iOS  |
|:-----------:|:-------:|:-----:|
| <img src="https://github.com/user-attachments/assets/b1126cda-33eb-4e2f-80a1-ffe7b5fe806d" alt="Android" width=100%/> |  <img src="https://github.com/user-attachments/assets/0901de25-5f2e-4f8c-94d5-404c900db458" alt="Neoverse Screenshot 1" width=100%/>  | coming soon |

---

## ✨ Features

* **Cross-Platform**: A single codebase for Android, iOS, and Desktop (Windows, macOS, Linux).
* **User Accounts**: Secure user registration and login. - on progress
* **Note Management**: Create, view, update, and delete notes. - soon
* **Clean, Modern UI**: A beautiful and intuitive user interface built with Compose. - soon

---

## 🛠️ Technology Stack & Libraries

This project showcases a modern, reactive tech stack for building multiplatform applications.

* **Framework**: [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
* **Language**: [Kotlin](https://kotlinlang.org/)
* **Architecture**: MVVM (Model-View-ViewModel)
* **Networking**: [Ktor Client](https://ktor.io/docs/client.html)
* **Dependency Injection**: [Koin](https://insert-koin.io/)
* **Serialization**: [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization)

---

## 🚀 Getting Started

Follow these instructions to get the project running on your local machine.

### Prerequisites

* **JDK 17** or higher.
* **Android Studio** with the Kotlin Multiplatform Mobile (KMM) plugin.
* **Xcode** (for running the iOS app).

### Setup Instructions

#### 1. Set Up the Backend Server

**This is a critical first step.** The client application cannot function without the backend server.

* Clone and run the **[Ktor Notes API](https://github.com/laetuz/Ktor-Notes-API)** on your local machine.
* Once the server is running, find your computer's local network IP address (e.g., `192.168.1.5`).

#### 2. Configure the Client

* Clone this repository.
* Open the project in Android Studio.
* Navigate to the networking client module (e.g., in `commonMain/data/network/KtorClient.kt`).
* Update the `baseUrl` variable to point to your computer's IP address where the Ktor server is running.

    ```kotlin
    // Example in your Ktor client setup
    private const val BASE_URL = "[http://192.168.1.5:8081](http://192.168.1.5:8081)" // <-- Change this IP
    ```

### Running the Application

#### 📱 Android

Select the `composeApp` run configuration in Android Studio and choose an emulator or a connected physical device.

#### 💻 Desktop

Select the `desktopRun` run configuration in Android Studio. This will build and launch the desktop application.

#### 🍏 iOS

1.  Open a terminal and navigate to the project root.
2.  Run the following Gradle command to generate the Xcode project:
    ```bash
    ./gradlew podInstall
    ```
3.  Open the generated `.xcworkspace` file in Xcode.
4.  Select a simulator or a connected iOS device and run the project from Xcode.

---

## 📂 Project Structure

This project follows the standard Kotlin Multiplatform structure.

