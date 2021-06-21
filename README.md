# Architecture

To the project architecture I choose an layered architecture splitted in one module by layer. The modules is showed below in the diagram.

![architecture-diagram](https://user-images.githubusercontent.com/790039/122656338-f4766500-d12f-11eb-97af-c1d927edb392.jpg)

## Data Module

The data module represents the application data layer. This layer is responsible for accessing the data, independent of their sources, and return it to some client layer. Today the app has only one datasource, the remote datasource, that is the datasource from some REST API on internet. But because the repository pattern in the layer's boundary, it is possible to extend the layer to communicate with another datasources like a local datasource using Room or Realm or event some data source based on Android Shared Preferences. This described feature meets Solid's Open Closed principle well. 

The DTO pattern was choose to communicate data between data layer and some client who uses the layer. Despite the DTO's classes was closer to the API's contract, with a small refactor we can change it to attends a more generic contract. This is will be necessary if we think in expands the data layer to another datasource.

## Domain Module

The domain layer in literature is used to handle the domain and business logic of a application. Usually is a layer with only kotlin code without references to Android framework. But in this app specially we don't have a complex business logic, so in this case the domain layer works like a proxy to the repisitories. I chose to use the mapper pattern to isolate the DTO contract from the view layer.

## Presentation Module

The presentation layer is represented by the app module on the project. This module contains the app's structure like Application class, some manifest configurations, functional tests etc. I chose the MVVM pattern to implements this module. Basically used the architecture components provided by Google to do that, in my opinion this components are good because their handle a lot of Android's particularities like lifecycle in viewmodels, navigations etc. 

Our ViewModels has been injected with the uses case and handle the requests invoked in it. In the View Model has a lot of LiveData observables observed by the Fragment to show something, or populate a list etc. It's reactive approach


# Improvement points
- Koin made me import all modules on app module, need to find a way to better this relationship between modules
- Some modules imports the same libs and the code was been repeated, it's is a complication when needs to give manutention
- Night theme
- Create a Observable view modle to observe directly on XML variables like `isEmptyPosts` and dont need to observe them on the activity
