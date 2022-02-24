![Feature Graphic](https://github.com/bhavnathacker/JetTasks/blob/master/demo/feature.png?raw=true)

[![GitHub license](https://img.shields.io/badge/License-Apache2.0-blue.svg)](LICENSE)
![GitHub stars](https://img.shields.io/github/stars/bhavnathacker/JetTasks?style=social)
![GitHub stars](https://img.shields.io/github/forks/bhavnathacker/JetTasks?style=social)
![GitHub stars](https://img.shields.io/github/watchers/bhavnathacker/JetTasks?style=social)
![GitHub follow](https://img.shields.io/github/followers/bhavnathacker?label=Follow&style=social)
![Twitter Follow](https://img.shields.io/twitter/follow/bhavnathacker14?label=Twitter&style=social)

# JetTasks
A sample demo app (two screen todo list app) which has **Clean Architecture with MVVM** , UI built with **Jetpack Compose** and includes **Modern Android Development Best Practices** with components as below:

- Jetpack Compose - UI
(Includes - Text, Button, Dropdown Menu, Date Picker, Toggle Switch, RecyclerView, ConstraintLayout and Floating Action Button)
- Kotlin Flow
- Hilt - Dependency injection
- Kotlin Coroutines
- Room with Coroutines & Flow Support
- Jetpack Datastore - Proto DataStore
- Jetpack Compose Navigation - With SafeArgs
- Jetpack Compose Theming - With Light & Dark Mode Support

This App is inspired by *Working with Proto DataStore* Google Codelabs and includes Local Data Storage support - with Room as well as Proto DataStore.

- Room - Saves the Tasks created by User
- Proto DataStore - Saves User Preferences(SortOrder and ShowCompleted flag)

### Try latest JetTasks app apk from below 👇
[![JetTasks](https://img.shields.io/badge/JetTasks-APK-black.svg?style=for-the-badge&logo=android)](https://github.com/bhavnathacker/JetTasks/releases/latest/download/JetTasks.apk)


## Light Mode

|   Task List    |   Task Details  |   App Tour       
|---	|--- |---
|  ![](https://github.com/bhavnathacker/JetTasks/blob/master/demo/light_task_list.png)    |  ![](https://github.com/bhavnathacker/JetTasks/blob/master/demo/light_task_detail.png)     |  ![](https://github.com/bhavnathacker/JetTasks/blob/master/demo/app_tour_demo_light.gif)    

<br />
 

## Dark Mode

|   Task List    |   Task Details  |   App Tour       
|---	|--- |---
|  ![](https://github.com/bhavnathacker/JetTasks/blob/master/demo/dark_task_list.png)    |  ![](https://github.com/bhavnathacker/JetTasks/blob/master/demo/dark_task_detail.png)     |  ![](https://github.com/bhavnathacker/JetTasks/blob/master/demo/app_tour_demo_dark.gif)    

<br />


## JetTasks Package Structure

 ```
com.bhavnathacker.jettasks
├── data                  # DATA LAYER
│   ├── local             # Local Persistence (Room) Database
│   │   ├── TaskDao          # Data Access Object for Room
│   │   └── TaskDatabase     # Database Instance
│   └── model             # Model Classes
│   │   └── Task         
│   │   └── TaskStatus     
│   │   └── TaskPriority    
│   └── repository        # All Repositories
│       └── TaskRepository             # Task Repo linked to Room
│       └── UserPreferencesRepository   # User Preference Repo linked to DataStore
│       └── UserPreferencesSerializer   # Serializer for UserPreferences required by DataStore
├── di                    # DI LAYER (Hilt DI Module)
├── ui                    # UI LAYER
│   ├── components            # Reusable UI components
│   ├── model                 # TaskUiModel
│   ├── navigation            # Navigation Routes & Navigation Graph
│   ├── screens               # Task List and Detail Screens
│   ├── theme                 # Theme setup
│   ├── viewmodels            # TaskViewModel
├── util                  # Extension functions
├── MainActivity.kt       # MainActivity 
├── TaskApplication       # Application class

```
<br/>

## Architecture

App follows **MVVM** & [**Guide**](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) to Recommended architecture.

![](https://github.com/bhavnathacker/JetTasks/blob/master/demo/architecture.png)

## IDE (Build Tool)

You need [Android Studio BumbleBee or above](https://developer.android.com/studio) to build this project.

<br/>


## Contact Me!

I would love to hear from you. Here are my details:

DM me at

* Twitter: <a href="https://twitter.com/bhavnathacker14" target="_blank">@bhavnathacker14</a>
* Email: bhavnathacker03@gmail.com

<br/>

## Contribute

You are most Welcome to Contribute!

Please see [Contributing Guidelines](https://github.com/bhavnathacker/JetTasks/blob/master/CONTRIBUTING.md).

<br/>

 
## License

```
    Apache 2.0 License


    Copyright 2022 Bhavna Thacker

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

```





