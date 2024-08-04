## E-commerce Compose Shop

This is a modular Android application designed with clean architecture principles. It provides a user-friendly interface for managing products and a shopping cart, allowing users to browse, view, and update their cart items.

### Screenshots

##### Light
<a href="url"><img src=https://github.com/malcolmmaima/e-commerce-compose/blob/main/screenshots/Screenshot_20240803_132821.png height="450"></a>
<a href="url"><img src=https://github.com/malcolmmaima/e-commerce-compose/blob/main/screenshots/Screenshot_20240803_132543.png height="450"></a>
<a href="url"><img src=https://github.com/malcolmmaima/e-commerce-compose/blob/main/screenshots/Screenshot_20240803_132625.png height="450"></a>
<a href="url"><img src=https://github.com/malcolmmaima/e-commerce-compose/blob/main/screenshots/Screenshot_20240803_132719.png height="450"></a>

##### Dark
<a href="url"><img src=https://github.com/malcolmmaima/e-commerce-compose/blob/main/screenshots/Screenshot_20240803_132932.png height="450"></a>
<a href="url"><img src=https://github.com/malcolmmaima/e-commerce-compose/blob/main/screenshots/Screenshot_20240803_132840.png height="450"></a>
<a href="url"><img src=https://github.com/malcolmmaima/e-commerce-compose/blob/main/screenshots/Screenshot_20240803_132859.png height="450"></a>
<a href="url"><img src=https://github.com/malcolmmaima/e-commerce-compose/blob/main/screenshots/Screenshot_20240803_132912.png height="450"></a>

### Architecture
This follows a modular architecture adhering to clean architecture principles and the MVVM (Model-View-ViewModel) pattern. 

### Architecture Overview

<img width="901" alt="image" src="https://github.com/user-attachments/assets/7946fb17-cf16-4219-8c70-bfd1ba08653d">


### Modules
#### App Module:

- Depends on all relevant feature modules (:features:shop).
  
#### Feature Modules:

- Represents individual features of the app (:features:shop, :feature:anotherfeature).
- It should not depend on other feature modules.
- Depends on core modules for shared functionality.
  
#### Core Modules:

- Includes common code shared among features and other core modules (e.g., :core:design, :core:networking, :core:utils, :core:database).
Can depend on one another but should avoid cyclical dependencies. 

#### Libraries and Tools
- Github Actions: Automates CI/CD workflows and manages pipelines for the project.
- Kotlin (1.9.0): The primary language for app development and testing.
- Coil (2.4.0): Handles image loading efficiently within Compose UI components.
- Hilt (2.48): Manages dependency injection with ease and efficiency.
- JUnit (4.13.2): Framework used for writing and running unit tests.
- Jetpack Compose (2023.09.00): Used for creating modern, reactive UIs and navigation.
- Accompanist (0.31.5-beta): Provides additional Compose functionalities not yet available in the core library.
- Material Icons (1.9.0): Supplies various icons that enhance the visual design of the app.
- Turbine (0.12.1): Specialized library for testing kotlinx.coroutines Flow.
- Kotlin Coroutines (1.7.3): Facilitates asynchronous programming and managing background tasks.
- Room (2.5.2): Provides an abstraction layer over SQLite for robust and efficient database operations.
- Retrofit (2.9.0): Simplifies REST API interactions and data fetching.
- OkHttp (4.11.0): Manages network operations and logging for HTTP requests.
- Build Logic Convention: Defines and maintains consistent build practices and management.
- Eithernet (1.2.1): Sealed API result type to handle Retrofit responses effectively.
- Moshi (1.15.0): Converts JSON into Kotlin objects and vice versa.

#### App Artifacts
The app APK and bundle can be found from the latest successful action on the GitHub Actions tab <a href="https://github.com/malcolmmaima/e-commerce-compose/actions">Actions</a>
