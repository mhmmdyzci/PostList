# PostList Android App

## Language / Dil

[English](#english) | [Türkçe](#türkçe)

## English

PostList is a modern Android application that lists posts from the [JSONPlaceholder Posts API](https://jsonplaceholder.typicode.com/posts) and allows users to update post content locally.

The application is written in Kotlin and structured around Clean Architecture, MVVM and the repository pattern.

### Features

- Fetching and displaying posts from the API
- Local data persistence with Room
- Preserving local data after application restarts
- Pull-to-refresh support
- Deleting posts only by swiping from right to left
- Soft delete support to prevent deleted posts from returning after an API refresh
- Updating post titles and descriptions on the detail screen
- Persisting user updates in Room
- Type-safe navigation with Navigation Component and Safe Args
- Grayscale Picsum images for each post
- Image loading and caching with Glide
- Dark theme with a Material-based responsive UI
- AndroidX SplashScreen support for app launch

### Technologies

| Layer | Technologies |
| --- | --- |
| **UI** | XML, ViewBinding, Material Components, RecyclerView, SwipeRefreshLayout |
| **Navigation** | Navigation Component, Safe Args |
| **Presentation** | MVVM, Fragment, ViewModel, Kotlin StateFlow |
| **Domain** | Use Case pattern, repository abstraction, domain models |
| **Data** | Retrofit, OkHttp, Room, DAO, DTO-Entity-Domain mapping |
| **Dependency Injection** | Hilt |
| **Concurrency** | Kotlin Coroutines, Flow |
| **Image Loading** | Glide |
| **Diffing** | ListAdapter, DiffUtil |

### Architecture

The project is divided into three main layers:

- **Presentation:** Fragments, ViewModels, UI state, adapters and reusable UI components
- **Domain:** The `Post` model, repository contracts and use case classes
- **Data:** Retrofit API service, Room database, DAO, entities, DTOs and mappers

Room acts as the local source of truth. During an API refresh, metadata for locally updated or deleted records is preserved:

- `isLocallyModified`: Identifies records changed by the user.
- `isDeleted`: Hides records without physically removing them from the database.

This keeps user updates and deletions persistent across application restarts.

### API and Image Sources

- **Posts API:** `https://jsonplaceholder.typicode.com/posts`
- **Post images:** `https://picsum.photos/300/300?random={itemPosition}&grayscale`

### Agile Approach

The project was developed iteratively using sprint-based planning and feature branches. Architecture setup, post listing, post details and UI redesign were handled as separate development steps.

[Back to language selection](#language--dil)

## Türkçe

PostList, [JSONPlaceholder Posts API](https://jsonplaceholder.typicode.com/posts) ile çalışan, postları listeleyen ve lokal olarak güncellemeye izin veren modern bir Android uygulamasıdır.

Uygulama Kotlin ile yazılmış ve Clean Architecture, MVVM ve repository pattern prensiplerine göre yapılandırılmıştır.

### Özellikler

- Postların API üzerinden listelenmesi
- Room ile lokal veri saklama
- Uygulama yeniden açıldığında lokal verilerin korunması
- Pull-to-refresh desteği
- Postların yalnızca sola kaydırılarak silinmesi
- Soft delete ile silinen postların API refresh sonrasında tekrar görünmemesi
- Post detay ekranında başlık ve açıklama güncelleme
- Güncellenen içeriklerin Room üzerinde korunması
- Navigation Component ve Safe Args ile tip güvenli navigation
- Her post için Picsum üzerinden grayscale görsel gösterimi
- Glide ile görsel yükleme ve cache desteği
- Koyu tema ve Material tabanlı responsive UI
- AndroidX SplashScreen ile uygulama başlangıç ekranı

### Kullanılan Teknolojiler

| Katman | Teknolojiler |
| --- | --- |
| **UI** | XML, ViewBinding, Material Components, RecyclerView, SwipeRefreshLayout |
| **Navigation** | Navigation Component, Safe Args |
| **Presentation** | MVVM, Fragment, ViewModel, Kotlin StateFlow |
| **Domain** | Use Case pattern, repository abstraction, domain modelleri |
| **Data** | Retrofit, OkHttp, Room, DAO, DTO-Entity-Domain dönüşümleri |
| **Dependency Injection** | Hilt |
| **Concurrency** | Kotlin Coroutines, Flow |
| **Image Loading** | Glide |
| **Diffing** | ListAdapter, DiffUtil |

### Mimari

Proje üç ana katmana ayrılmıştır:

- **Presentation:** Fragment, ViewModel, UI state, adapter ve reusable UI bileşenleri
- **Domain:** `Post` modeli, repository arayüzleri ve use case sınıfları
- **Data:** Retrofit API servisi, Room database, DAO, entity, DTO ve mapper sınıfları

Veri akışında Room lokal source of truth olarak kullanılır. API refresh sırasında lokal olarak güncellenmiş veya silinmiş kayıtların metadata bilgileri korunur:

- `isLocallyModified`: Kullanıcının değiştirdiği kayıtları belirtir.
- `isDeleted`: Fiziksel olarak silinmeden gizlenen kayıtları belirtir.

Bu sayede kullanıcı güncellemeleri ve silmeleri uygulama yeniden açıldığında korunur.

### API ve Görsel Kaynakları

- **Posts API:** `https://jsonplaceholder.typicode.com/posts`
- **Post görselleri:** `https://picsum.photos/300/300?random={itemPosition}&grayscale`

### Agile Yaklaşımı

Proje sprint bazlı ve feature branch yaklaşımıyla geliştirilmiştir. Mimari kurulum, post listesi, post detay ekranı ve UI redesign gibi çalışmalar ayrı adımlarda ilerletilmiştir.

[Dil seçimine dön](#language--dil)

