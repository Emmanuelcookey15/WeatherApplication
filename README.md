## Weather Application
A simple android application that displays the current weather at the user’s location and a 5-day forecast.

# Getting Started
This file will attempt to explain my approach in creating this app. You can download or clone this project to see the source code and run it.

# Development Approach
Language: Kotlin

Architecture: This app is built using the MVVM architecture with one activity (Main), view model as well as directory structures for networking, utils, models and views.

Libraries: Third party dependecies are used in this project: Retrofit library for network calls, espresso for UI test.

Android app which make use of OpenWeatherMap API to get weather details and display the information accordingly.

  - https://openweathermap.org/current
  - https://openweathermap.org/forecast5

Well tested Unit and UI tests with JUnit and Expresso


## Coding / Design
Design pattern used - MVVM (Model-View-ViewModel), Retrofit2, ViewModel, Repository pattern, and Android Recommended App Architecture

Integration into a CI/CD build pipeline - I used Bitrise and Semaphone
Code coverage integration -- Bitrise
Static code analysis -- lint


# Project file

api folder contain class to make request to the server (using retrofit)

view folder contains activity - to hold ui

viewmodels folder contain WeatherViewModel to give any activity that want to observe changes in life cycle.

model folder contain models defined and serialized from the response returned from two api calls

interfaces contains -- ApiService which defines the retrofit method (ApiService), ForecastWeatherResponseInterface and CurrentWeatherResponseInterface to transfer data

utils contains Utils.kt (contains a function that implements my approach to solving most of the algorithms used)


