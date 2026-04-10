#  KitchInMobile 🍲

## 📱 Overview
**KitchInMobile** is a modern Android application built with **Kotlin** and **Jetpack Compose** that allows users to manage recipes in a clean and interactive interface.

This project demonstrates the transition from a local storage system to a real-time cloud-based architecture using **Firebase Firestore**, enabling seamless data synchronization across devices.

## ✨ Key Features
* 📋 **View recipes** in a clean, scrollable list (`LazyColumn`)
* ➕ **Add new recipes** (cloud-stored)
* ✏️ **Edit existing recipes**
* ❌ **Delete recipes**
* 🔄 **Real-time updates** (no refresh required)
* 🔍 **Search** by recipe name or ingredients
* 📊 **Live recipe count** and filtered results
* 🌙 **Light & Dark mode** support
* ☁️ **Cloud-backed data** with Firebase Firestore

## 🛠️ Tech Stack
| Layer | Technology |
| :--- | :--- |
| **Language** | Kotlin |
| **UI** | Jetpack Compose |
| **Architecture** | MVVM |
| **State Management** | StateFlow + Compose State |
| **Backend** | Firebase Firestore |
| **Tools** | Android Studio |

---

## ☁️ Cloud Database Implementation
This project fulfills a Cloud Database implementation using Firebase Firestore.

### 🔗 Connectivity
* Firebase project configured and linked to Android app
* `google-services.json` integrated securely
* Firestore SDK used for backend communication

### 🔄 CRUD Operations
| Operation | Implementation |
| :--- | :--- |
| **Create** | Add recipes to `recipes` collection |
| **Read** | Fetch recipes on app launch |
| **Update** | Edit recipes and sync to Firestore |
| **Delete** | Remove recipes from database |

### ⚡ Real-Time Synchronization
Implemented `addSnapshotListener`. UI updates instantly when:
* Data is added
* Data is modified
* Data is deleted
* **No manual refresh required**

### 🧠 Architecture Flow
`UI (Jetpack Compose)` → `ViewModel (State Management)` → `Repository (Firestore Logic)` → `Firebase Firestore (Cloud Database)`

### 🔐 Security
* `google-services.json` excluded via `.gitignore`
* Firestore running in **Test Mode** (development)
* Designed for future authentication integration

---

## 📂 Project Structure
```text
KitchInMobile/
│
├── app/
│   ├── src/main/
│   │   ├── java/com/benny/kitchinmobile/
│   │   │   ├── repository/   # Firestore logic
│   │   │   ├── domain/       # Data models
│   │   │   ├── ui/           # Screens & components
│   │   │   ├── viewmodel/    # State management
│   │   │   └── MainActivity.kt
│   │   ├── res/
│   │   └── AndroidManifest.xml
│   └── build.gradle
│
├── gradle/
├── build.gradle
└── settings.gradle


▶️ Getting Started
Clone the repository

Bash
git clone https://github.com/RBennyjay/KitchInMobile.git
Open in Android Studio
Allow Gradle to sync
Add Firebase configuration
Download google-services.json from Firebase Console
Place it in the /app directory
Run the app
Use emulator or physical Android device

### 🧪 Testing
✅ Tested on Android Emulator (Pixel 5, API 33)
✅ Tested on physical device
✅ Verified:
Add, edit, delete operations
Real-time Firestore sync
Search and filtering
Light/Dark mode responsiveness

### ⚠️ Risks & Mitigation
Asynchronous Data Handling
Risk: UI delays while fetching cloud data.
Solution: Real-time listeners + reactive UI updates.

Firestore Security Rules
Risk: Improper access configuration.
Solution: Used Test Mode for development, planned secure rules.

Sensitive File Exposure
Risk: Firebase config leakage.
Solution: .gitignore used to exclude credentials.

###🚀 Future Improvements
🔐 Firebase Authentication (user accounts)
📷 Image upload for recipes
📡 Offline caching support
🎨 Enhanced animations and UI transitions
🔒 Production-level Firestore security rules

👨‍💻 Author
Ebenezer John (Benny Jay)

📄 License
This project is for educational purposes.