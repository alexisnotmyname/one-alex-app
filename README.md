# One Alex app

One Alex app mutli-module demo Android app built using modern development practices and architecture. The app demonstrates clean architecture, Jetpack Compose, and efficient code design.

## Features

### To-Do List
- Add, complete, delete tasks.
- Drag-and-drop to reorder tasks, similar to Google Keep.

### Movie & TV Series
- View **Popular** and **Now Playing** movies.
- View **Top Rated** and **Popular** TV Series.
- Powered by the [TMDB API](https://www.themoviedb.org/documentation/api).

## Tech Stack

- **Language**: [Kotlin](https://kotlinlang.org/)
- **Coroutines & Flow**: For efficient asynchronous and reactive programming.
- **Architecture**: MVVM, Clean Architecture.
- **Networking**: [Retrofit 2](https://square.github.io/retrofit/) with [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization).
- **Dependency Injection**: [Dagger Hilt](https://dagger.dev/hilt/).
- **Database**: [Room](https://developer.android.com/training/data-storage/room).
- **Image Loading**: [Coil](https://coil-kt.github.io/coil/).

## Jetpack Libraries

- **Compose**: For declarative UI development.
- **Lifecycle**: To manage Android lifecycles.
- **Room**: For local data storage.
- **Navigation**: For navigation between screens.
- **Material 3 Design**: For modern UI components and design patterns.
- **Paging**: Paging large data sets (Movies and Tv Series).


## Project Structure

The app follows **clean architecture** principles, dividing the codebase into different layers:

- **Data Layer**: Handles the network and local database operations using Retrofit and Room.
- **Domain Layer**: Contains business logic(Use Cases) and interacts with the data layer using repositories.
- **Presentation Layer**: Contains ViewModels and the UI, built using Jetpack Compose and leveraging Flow for reactive state updates.

## How to Build

1. Add to your local.properties :
   ```bash
    BASE_URL=https://api.themoviedb.org/
    AUTHORIZATION="your_tmdb_api_read_access_token"