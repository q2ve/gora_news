# GORA News
Тестовое задание на вакансию Junior Android Developer компании GORA Studio.
## Задание
Разработать приложение для показа последних 20 новостей в каждой категории с использованием https://newsapi.org.
Результат прислать в виде ссылки на репозиторий с публичным доступом.
Указать время, потраченное на задание.

Для получения новостей использовать /v2/top-headlines с параметрами. Список
категорий доступен в документации https://newsapi.org/docs.

Необходимо, чтобы:
- Новости сгруппированы по категориям и пролистывались горизонтально
- Категории отображались в алфавитном порядке
- Новости отображались от новых к старым
- При нажатии на новость она открывалась в браузере
- На изображение был наложен градиент, для лучшего отображения текста
- Изображения при переиспользовании ячеек не мелькали и были корректными (а не
от предыдущих новостей). Например, при быстром пролистывании новостей и
медленном интернете.

Желательно, чтобы:
- Загруженные новости кэшировались в базу данных
на ваш выбор
- Загруженные изображения для новостей
кэшировались на диск
- При следующем запуске, даже при отсутствии
интернета, список новостей оставался доступным,
как и ранее загруженные изображения.

Будет плюсом, если:
- Реализуете поиск/фильтрацию по title и description
новости
- Реализуете кеширование изображений
самостоятельно.

## Результат

![image](https://user-images.githubusercontent.com/86717299/165110808-48190d73-5f37-4330-a5e7-2c6d7aedd321.png)

Выполнены все пункты, кроме собственной реализации кэширования изображений.
Кэшируются и отображаются оффлайн все получаемые новостные статьи, их картинки, а также все результаты поисков со своими статьями и картинками.
Поиск осуществляется запросом по эндпоинту "everything" с указанием скоупа "title, description".

Работа с сетью через Retrofit, БД - Realm, обработка изображений - ~~Picasso~~ Glide. Также использованы kotlin-reflect, shimmer, recyclerViewPreloader.

Время выполнения - 19 часов.
