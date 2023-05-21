<h1 align="center">NY Times</h1>

<p align="center">  
This project is an Android application that demonstrates the effective implementation of Clean Architecture in Android app development. It utilizes The New York Times API to fetch and display the latest news to users.
</p>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
</p>

<p align="center">
<img src="media/screenshot.png"/>
</p>

## Download
Go to the [Releases](https://github.com/arrazyfathan/NewYorkTimes-App/releases/download/v1.0/nytimes-release.apk) to download the latest APK.

## Tech stack & Open-source libraries
- Minimum SDK level 21
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- Jetpack
    - Lifecycle - dispose of observing data when lifecycle state changes.
    - ViewModel - UI related data holder, lifecycle aware.
    - Room Persistence - construct the database using the abstract layer.
- Architecture
    - MVVM Architecture
    - Repository pattern
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components for building ripple animation, and CardView.
- [Glide](https://github.com/bumptech/glide) - Load image and caching image
- [TimeAgo](https://github.com/marlonlom/timeago) - Simple java library for displaying dates as relative time ago language.
- [Lottie](https://github.com/airbnb/lottie-android) - Render After Effects animations natively on Android and iOS, Web, and React Native
- [Swiperefreshlayout](https://developer.android.com/jetpack/androidx/releases/swiperefreshlayout?hl=id) - Implement the swipe-to-refresh UI pattern.
## API [NY Time API](https://developer.nytimes.com/get-started/)
```
https://api.nytimes.com/svc/topstories/v2/home.json?api-key=yourkey
```

## Demo App
[<img src="https://i.ytimg.com/vi/GoeznEif9ps/maxresdefault.jpg" width="50%">](https://www.youtube.com/watch?v=GoeznEif9ps "Now in Android: 55")

# License

```xml
MIT License

Copyright (c) [2022] [Ar Razy Fathan Rabbani]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```
