# KitchInMobile 🍲

## 📱 Overview
KitchInMobile is an Android application built with Kotlin and Jetpack Compose.  
It is a modern mobile version of the "Kitch-In" Recipe Manager, transitioning from a console-based application to a touch-based, user-friendly mobile experience.

The app allows users to add, edit, delete, search, and filter recipes, while also providing insights such as total recipe count and search result count. Data is stored locally using Room Database for persistence.

---

---
## 🚀 Features
- 📋 View a list of recipes using LazyColumn
- ➕ Add new recipes
- ✏️ Edit existing recipes
- ❌ Delete individual recipes
- 🧹 Clear all recipes (reset database)
- 🔍 Search recipes by name
- 🗂️ Filter recipes dynamically
- 📊 Display total recipe count
- 🔢 Display search result count
- 🌙 Supports Light and Dark Mode
- 💾 Persistent storage using Room Database
---

## 🛠️ Tech Stack
- **Language:** Kotlin
- **UI Framework:** Jetpack Compose
- **Architecture Approach:** Single Activity with Composable Screens
- **State Management:** `remember` and `mutableStateOf` (State Hoisting)
- **Database:** Room Database (Local persistence)
- **IDE:** Android Studio

---

## 🧩 How Requirements Were Met

### ✔ UI/UX Implementation
- Used **Scaffold layout** with:
    - TopAppBar for navigation
    - Floating Action Button (FAB) for adding recipes
- Displayed recipes efficiently using **LazyColumn**

### ✔ State Management
- Implemented **Compose State (`remember`, `mutableStateOf`)**
- Used **state hoisting** to maintain a single source of truth
- Ensured UI updates automatically when data changes

### ✔ Data Persistence
- Implemented **Room Database**
- Recipes are stored locally and persist after app restart

### ✔ Core Functionalities
- Add, edit, and delete recipes
- Clear all stored recipes
- Search and filter recipes dynamically
- Display total number of recipes
- Display number of search results

### ✔ UI Enhancements
- Implemented Dark Mode support
- Designed responsive UI using Jetpack Compose components

---
## 📂 Project Structure

```text
KitchInMobile/
│
├── app/
│   ├── src/main/
│   │   ├── java/com/benny/kitchinmobile/
│   │   │   ├── data/        # Room DB (Entity, DAO, Database)
│   │   │   ├── ui/          # Screens and Composables
│   │   │   ├── viewmodel/   # State & logic
│   │   │   └── MainActivity.kt
│   │   ├── res/
│   │   └── AndroidManifest.xml
│   └── build.gradle
│
├── gradle/
├── build.gradle
└── settings.gradle
---

## ▶️ How to Run the App
1. Clone the repository:

git clone https://github.com/RBennyjay/KitchInMobile.git

2. Open in Android Studio
3. Allow Gradle to sync
4. Run on emulator or physical device

---

## ⚠️ Risks and Mitigation

### 1. Android Studio Performance Issues
- **Risk:** Emulator may be slow
- **Solution:** Use physical device for testing

### 2. Jetpack Compose State Complexity
- **Risk:** Managing state across screens
- **Solution:** Applied **state hoisting** and kept a single source of truth

---

## 📈 Future Improvements
- Add user authentication
- Sync recipes with cloud database
- Improve UI animations and transitions
- Add image support for recipes

---

## 🧪 Testing
- Tested on Android Emulator (Pixel 5, API 33)
- Verified functionality for add, delete, and search features
- Tested UI responsiveness in light and dark modes

---

## 👨‍💻 Author
**Ebenezer John (Benny Jay)**

---

## 📄 License
This project is for educational purposes.
