# Финтех 2024: Лабораторная работа по курсу Android Developer

## Описание задания

В рамках лабораторной работы предлагается разработать Android-приложение, использующее API Кинопоиска для отображения списка популярных фильмов и их детальных описаний. Ниже приведены основные и дополнительные требования к приложению:

### Описание приложения:
1. **Главный экран:** Отображение списка популярных фильмов.
2. **Карточка фильма:** Содержит название фильма, изображение-постер и год выпуска.
3. **Детальная информация:** При клике на карточку открывается экран с постером фильма, описанием, жанром и страной производства.
4. **Обработка ошибок:** Уведомление пользователя об ошибке в случае отсутствия сети или ошибки загрузки.

### Дополнительные требования (необязательные):
1. **Адаптивный дизайн:** При перевороте экрана список фильмов занимает 50% экрана, во второй половине отображается экран с описанием фильма.
2. Приложение написано на Kotlin.
3. Обеспечена общая плавность и стабильность приложения.
4. Ответы от API закешированы на время сессии.

### Еще не реализовано:
1. **Разделы на главном экране:** "Популярное" и "Избранное". При длительном клике на карточке, фильм добавляется в избранное и хранится в базе данных для доступа в оффлайн режиме.
2. **Выделение избранных фильмов:** При просмотре популярных фильмы, находящиеся в избранном, выделяются.
3. **Поиск по наименованию:** Возможность поиска фильмов по названию.

## Инструкции по запуску

1. **Клонирование репозитория:** Склонируйте репозиторий с проектом на свой компьютер.
   
   ```bash
   git clone https://github.com/sg41/Gorbatenko.git

2. **Настройка Retrofit и API ключа (необязательно):**
   - Откройте файл `RetrofitClient.kt` в пакете `com.example.movies`.
   - Замените строку `const val API_KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"` на ваш собственный API ключ.

3. **Подключение библиотек (необходимо):**
   - Убедитесь, что ваши зависимости, такие как Retrofit и Glide , правильно подключены. Вы можете управлять зависимостями в файле `app/build.gradle`.

4. **Запуск приложения:**
   - Запустите приложение на эмуляторе или физическом устройстве, используя Android Studio.



Спасибо за вниманиe! Если у вас возникнут вопросы, не стесняйтесь.
