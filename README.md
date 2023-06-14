## Mobile Development Documentation
<p>The source code of Android app of sentiGo using Kotlin in order to complete The Bangkit academy 2023 Capstone Project.</p>

- ### Architecture for this project
  ![architecture drawio](https://github.com/sentiGo/sentiGo-MobileDevelopment/assets/70699971/77ec0352-9406-4d3a-b058-e7e7b8004d9e)


- ### Feature
  - **Login**, Allows a user to gain access to an application by entering their email and password.
  - **Register**, Enables users to independently register and gain access to the system.
  - **Home**, Provide users with a list of travel destinations based on their location and city.
  - **Find**, Users provide a description of their feelings and travel needs, and the app will give them a list of 5 recommendations.
  - **Favorite**, Save destinations to your favorites.
  - **Edit profile**, Users can update their data, such as profile photo and password. 

- #### Dependencies : 
    - [Jetpack Compose](https://developer.android.com/jetpack/compose)
    - [Lifecycle & Livedata](https://developer.android.com/jetpack/androidx/releases/lifecycle)
    - [kotlinx-coroutines](https://developer.android.com/kotlin/coroutines)
    - [Retrofit 2](https://square.github.io/retrofit/)
    - [Ok Http 3](https://square.github.io/okhttp/)
    - [Google Play services Maps](https://developers.google.com/maps/documentation/android-sdk/get-api-key)
    - [Glide](https://github.com/bumptech/glide)

### Getting Started Application
- ### Prerequisites
    - #### Tools Software
        - [Android Studio](https://developer.android.com/studio)
        - JRE (Java Runtime Environment) or JDK (Java Development Kit).
    
    - #### Installation
      - Get an API Key at [Google Maps Platform](https://developers.google.com/maps/documentation/android-sdk/get-api-key)
      - Clone this repository and import into Android Studio    
        ```
        https://github.com/sentiGo/sentiGo-MobileDevelopment.git
        ``` 
      - Enter Your API in ``local.properties``
        ```
        GOOGLE_MAPS_API_KEY=your_maps_api_key
        ```
