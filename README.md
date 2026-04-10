KitchInMobile 🍲

📱 Overview
KitchInMobile is an Android application built with Kotlin and Jetpack Compose.
It is a modern mobile version of the "Kitch-In" Recipe Manager, upgraded from a local storage system to a real-time cloud-based application using Google Firebase Firestore.

The app allows users to add, edit, delete, search, and filter recipes, while automatically syncing data across devices in real-time. This transition demonstrates the implementation of a distributed cloud database system.

🚀 Features
📋 View a list of recipes using LazyColumn

➕ Add new recipes (stored in Firestore)

✏️ Edit existing recipes

❌ Delete individual recipes

🔄 Real-time updates (no refresh needed)

🔍 Search recipes by name or ingredients

🗂️ Dynamic filtering of recipes

📊 Display total recipe count

🔢 Display search result count

🌙 Supports Light and Dark Mode

☁️ Cloud-based storage using Firebase Firestore

🛠️ Tech Stack
Language: Kotlin

UI Framework: Jetpack Compose

Architecture: MVVM (Model-View-ViewModel)

State Management: StateFlow + Compose State

Database: Firebase Firestore (Cloud NoSQL Database)

Cloud Platform: Google Firebase

IDE: Android Studio

☁️ Cloud Database Implementation
This project fulfills the Cloud Databases module by integrating Firebase Firestore into the application.

✔ Cloud Service & Connectivity
Configured Firebase project and connected Android app

Integrated Firebase SDK using google-services.json

Established secure connection between app and Firestore backend

✔ CRUD Operations
Create: Recipes are added as documents in the recipes collection

Read: Recipes are fetched and displayed on app launch

Update: Recipes can be edited and saved back to Firestore

Delete: Recipes can be removed from both UI and database

✔ Real-Time Synchronization
Implemented addSnapshotListener

UI updates instantly when:

A recipe is added

A recipe is edited

A recipe is deleted

No manual refresh required

✔ Data Modeling
Used Kotlin data classes mapped directly to Firestore documents

Supports:

Lists (ingredients)

Strings (instructions, prep time)

Firestore automatically serializes/deserializes data

✔ Architecture Flow
UI (Jetpack Compose) → ViewModel (State Management) → Repository (Firestore Logic) → Firebase Firestore (Cloud Database)

📂 Project Structure
Plaintext
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
▶️ How to Run the App
Clone the repository:

Bash
git clone https://github.com/RBennyjay/KitchInMobile.git
Open in Android Studio

Add your own Firebase configuration file:

Download google-services.json from Firebase Console

Place it in the /app directory

Sync Gradle

Run on emulator or physical device

⚠️ Risks and Mitigation
Asynchronous Data Handling

Risk: UI may appear empty while loading data.

Solution: Implemented real-time listeners and reactive UI updates.

Firestore Security Rules

Risk: Misconfigured rules may block access.

Solution: Used Test Mode during development and plan to implement authentication.

Exposure of Sensitive Files

Risk: google-services.json could be leaked.

Solution: Added to .gitignore to prevent upload to GitHub.

📈 Future Improvements
Add Firebase Authentication (user accounts)

Implement offline caching

Add image upload for recipes

Improve animations and transitions

Deploy production-level Firestore security rules

🧪 Testing
Tested on Android Emulator (Pixel 5, API 33) and physical Android devices.

Verified: Real-time sync, CRUD operations, Search/Filtering, and UI responsiveness (light/dark mode).

👨‍💻 Author
Ebenezer John (Benny Jay)

📄 License
This project is for educational purposes.