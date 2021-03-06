# Upload Image With Retrofit

In this project, I uploaded an image to the server using retrofit. Before the images are uploaded to the server, the image size(MB) is reduced and uploaded by compressing them.

Libraries Used
--------------
* [Architecture][10] - A collection of libraries that help you design robust, testable, and maintainable apps.
  * [Lifecycles][11] - Create a UI that automatically responds to lifecycle events.
  * [ViewModel][13] - Easily schedule asynchronous tasks for optimal execution.
  * [LiveData][14] - Build data objects that notify views when the underlying database changes.

* Third party and miscellaneous libraries
  * [Retrofit][30] for turns your HTTP API into a Java interface
  * [Kotlin Coroutines][34] A coroutine is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously.
  * [Gson][31] for convert Java Objects into their JSON representation
  * [Hilt][32] for [dependency injection][33]
  * [Compressor][35] Compressor is a lightweight and powerful android image compression library.
  * [Lottie][36] Lottie is a mobile library for Android and iOS that parses Adobe After Effects animations exported as json renders them natively on mobile!
  

Architecture
--------------
The app uses [MVVM architecture][10] to have a unidirectional live of data, separation of concern, testability, and a lot more.

![Architecture](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)


LICENSE
--------------
MIT License

Copyright (c) 2022 Cuma Haznedar

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

[1]: https://www.balldontlie.io/#introduction
[10]: https://developer.android.com/topic/architecture
[11]: https://developer.android.com/jetpack/androidx/releases/lifecycle
[13]: https://developer.android.com/topic/libraries/architecture/viewmodel
[14]: https://developer.android.com/topic/libraries/architecture/livedata
[30]: https://square.github.io/retrofit/
[31]: https://github.com/google/gson
[32]: https://developer.android.com/training/dependency-injection/hilt-android
[33]: https://developer.android.com/training/dependency-injection
[34]: https://developer.android.com/kotlin/coroutines
[35]: https://github.com/zetbaitsu/Compressor
[36]: https://github.com/airbnb/lottie-android
