# PhotoMemo2

## 개요

[PhotoMemo](https://github.com/foreknowledge/PhotoMemo)에서 만든 메모장 앱의 내부 구조를 바꾸고 기능도 추가한 ver.2 메모장 애플리케이션입니다.

### 기능

- 메모를 사진과 함께 저장합니다.
- 이미지는 **사진 촬영** / **앨범 이미지** / **이미지 URL** 3가지 방법으로 가져옵니다.
- 앨범에서 다수의 이미지를 선택해 추가합니다.

## 시작하기

### 전제 조건

최신 버전의 Android 빌드 도구 및 Android 지원 저장소가 필요합니다.

### 설치

이 샘플은 Gradle 빌드 시스템을 사용합니다.

이 저장소를 복제해서 **Android Studio**에 import 합니다.

```bash
git clone https://github.com/foreknowledge/PhotoMemo2.git
```

## 사용한 라이브러리

- AAC
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
  - [Room](https://developer.android.com/topic/libraries/architecture/room) 
  - [DataBinding](https://developer.android.com/topic/libraries/data-binding)
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- [Retrofit](https://square.github.io/retrofit) 
- [Glide](https://github.com/bumptech/glide)
- [Exifinterface](https://developer.android.com/reference/android/support/media/ExifInterface)
- [PhotoView](https://github.com/chrisbanes/PhotoView)
- [TedImagePicker](https://github.com/ParkSangGwon/TedImagePicker)
