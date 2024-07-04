# Instagram Stories Feature

## Setup Instructions

1. Clone the repository.
2. Open the project in Android Studio.
3. Build and run the project on an emulator or device.

## Architecture and Design

- **Data Layer**: Room for local caching, Retrofit for API calls.
- **UI Layer**: Fragments and RecyclerView for displaying stories.
- **ViewModel**: Manages UI-related data.
- **Repository**: Handles data operations and caching.

## Assumptions

- Backend API provides a list of stories.
- Stories auto-advance after 5 seconds.
- Manual navigation using left and right taps.
