# 📰 NewsApp

A modern Android news feed app built with **Kotlin**, **Jetpack Compose**, and **MVVM + Hilt** architecture.  
It fetches the latest headlines using the [NewsAPI](https://newsapi.org/) and allows users to view them in **List** or **Grid** layout, with a detailed screen for each article.

---

## 🚀 Features

- 🧱 **Jetpack Compose UI** — Fully declarative and Material 3 based UI.
- 🧭 **Navigation Component** — Simple navigation between List/Grid and Detail screens.
- ⚙️ **MVVM + Repository Pattern** — Clean architecture with separation of concerns.
- 🧩 **Hilt for Dependency Injection** — Easy DI setup.
- 🔐 **Secure API key** via `local.properties` and `BuildConfig`.
- 🖼️ **Coil Image Loading** with placeholder fallback.
- 🌗 **List ↔ Grid toggle** from the top app bar.
- 📱 **Edge-to-edge layout** with system insets handled correctly.

---

## 🏗️ Architecture Overview

```
app/
 ├── data/
 │   ├── model/          # Data classes (Article, NewsResponse)
 │   ├── network/        # Retrofit service + DI module
 │   └── repository/     # NewsRepository
 │
 ├── ui/
 │   ├── components/     # Reusable composables (ArticleCard, etc.)
 │   └── viewmodel/      # NewsViewModel
 │
 ├── di/                 # Hilt modules (NetworkModule, etc.)
 └── MainActivity.kt     # Entry point + NavHost setup
```

---

## 🧠 Tech Stack

| Layer | Library / Tool |
|-------|----------------|
| **UI** | Jetpack Compose, Material 3 |
| **Navigation** | Navigation Compose |
| **DI** | Hilt (Dagger) |
| **Networking** | Retrofit + OkHttp |
| **Async / State** | Kotlin Coroutines, StateFlow |
| **Image Loading** | Coil |
| **Architecture** | MVVM + Repository Pattern |

---

## 🔑 API Key Setup

1. Register at [newsapi.org](https://newsapi.org/) and get your API key.
2. In your **root `local.properties`**, add:
   ```properties
   NEWS_API_KEY="your_api_key_here"
   ```
3. In your **`build.gradle.kts`**, ensure:
   ```kotlin
   android {
       buildFeatures.buildConfig = true
       defaultConfig {
           buildConfigField("String", "NEWS_API_KEY", ""${properties["NEWS_API_KEY"]}"")
       }
   }
   ```

Your key will now be available as:
```kotlin
BuildConfig.NEWS_API_KEY
```

---

## 🧪 Testing

- Unit tests for ViewModel and Repository can be added under `src/test/java/`.
- Use mock web server to simulate NewsAPI responses.

---

## 🧰 Requirements

- Android Studio **Ladybug+**
- Kotlin **1.9+**
- minSdk **33**
- targetSdk **33**
- NewsAPI key

---

## 💡 Future Improvements

- Add search & filtering
- Implement pagination
- Offline caching (Room / DataStore)
- Support dark mode toggle

---

## 👨‍💻 Author

**@srnapit**  
Built with ❤️ using Jetpack Compose.

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
