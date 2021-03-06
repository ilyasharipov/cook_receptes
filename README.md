Задание

Реализовать простое приложение,
загружающее и выводящее список рецептов с возможностью просмотра деталей по каждому рецепту.
Функциональные требования

Главный экран:

1.1. Спискок рецептов,
загружаемый с серверного API в формате JSON. Каждый элемент списка должен содержать:


фотографию рецепта;


название рецепта;


краткое описание (должно
обрезаться до двух строк).



Детали рецепта: по
нажатию на ячейку должен открываться экран деталей рецепта, который содержит:



фотографию рецепта
(если в рецепте более 1 фотографии, нужна возможность листать фотографии свайп-жестом (можно сделать через ViewPager) и должно отображаться количество фотографий рецепта);


название рецепта;


краткое описание;


инструкции по приготовлению;


уровень сложности:
от 1 до 5


Общие технические требования:

Реализовать приложение на архитектуре MVP (либо на любой другой)
Для серверного взаимодействия использовать библиотеку Retrofit2
Для отображения фотографий использовать библиотеку Glide
минимальная api версия Android - 21 (настраивается в build.gradle(Module:app))
Выводить TOAST-сообщение, если данные с сервера не загрузились.

API Спецификация ниже.
Base URL:
https://test.kode-t.ru/
Пример запроса: baseurl/recipes

Список рецептов.

GET /recipes
Ответ (application/json):
{
“recipes”: array<recipeModel.list>
}

Детали рецепта.

GET /recipes/:uuid
Ответ (application/json):
{
“recipe”: object<recipeModel.details>
}

Модели:

recipeModel.list

{
“uuid”: string,
“name”: string,
“images”: array,
“lastUpdated”: integer,
“description”: string,
“instructions”: string,
“difficulty”: integer
}

recipeModel.details

{
“uuid”: string,
“name”: string,
“images”: array,
“lastUpdated”: integer,
“description”: string,
“instructions”: string,
“difficulty”: integer,
“similar”: array<recipeModel.brief
}

recipeModel.brief

{
“uuid”: string,
“name”: string<
}
