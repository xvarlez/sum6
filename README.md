# Sum6

![App screenshot](http://i.imgur.com/vnaEXo5l.png "Screenshot")

This is a sample project in MVVM, using Android data binding, RxJava 2 and the ViewModel from the new Android lifecycle library.

## Usage

* Enter any number in the text fields and their sum is automatically computed.
* Touching the sum toggles a blinking animation

## How it works
* ViewModel is created using Google's new Lifecycle library
* Android two-way data binding is used between the View and ViewModel
* The ViewModel fields are converted to RxJava Observables
* Any new value emitted will ask the Model to compute the sum, then update the result field

## Comments
* Debounce call is here to trigger the computation only after the user has finished typing (in case the computation was heavy)
* Input fields only support integers, any input value bigger than Integer.MAX_VALUE will return 0