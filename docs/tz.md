# Техническое задание
## Общие сведения
   Создание многопользовательской базы данных для продажи книг, которая позволит пользователям просматривать, заказывать и управлять книгами, а также обеспечит администраторам инструменты для управления каталогом и заказами.
## Цели и назначение создания автоматизированной системы;
Цель разработки автоматизированной системы — оптимизировать и облегчить работу книжного магазина. Для этого будет разработано настольное приложение для пользования двух категорий лиц - покупателей и сотрудников магазина.
Назначение: управление и автоматизация процессов книжного магазина.

## Характеристика объектов автоматизации;
Основные функции:

управление записями о книгах
учет предоставленных услуг
учет данных о клиентах и сотрудниках магазина
предоставление дополнительных сведений о магазине.
разграничение прав доступа
автоматизация работы двух категорий лиц - покупателей и сотрудников магазина
## Требования к автоматизированной системе;
1. Многопользовательский доступ
2. Разграничение функционала по ролям
3. Клиент и сотрудник клиники могут осуществлять следующие действия:
    * получать оперативную информацию о наличии, описании, фото нескольких страниц и стоимости издания;
    * подбирать литературу по заявленным критериям;
    * осуществлять отбор книг по категориям;
    * просматривать и добавлять отзывы о книге;
4. Сотрудник магазина может осуществлять следующие действия:
    * вести справочники (добавление, удаление, редактирование);
    * оформлять заказ на книги (в одном заказе может быть несколько различных книг в разном количестве);
    * стоимость заказа рассчитывается динамически.

**Данные в системе будут храниться в структурированном формате в базе данных:**
Клиенты: Номер клиента (тип integer), логин и пароль для авторизации (тип varchar), номер роли (тип integer).

Роль: Номер роли (тип integer) и название роли (тип varchar).

Сотрудник: Номер сотрудника и номер клиента (тип integer), его ФИО, адрес и телефон (тип varchar), день рождения (тип date).

Категория: Номер категории (тип integer) и название категории (тип varchar).

Книга: Номер книги и возраст (тип integer), ее название, автор, издательство, год издания, количество страниц, цена (тип varchar).

Книга и категория: Номер книги и номер категории (тип integer).

Отзыв: Номер отзыва (тип integer), номер книги и номер клиента, текст отзыва (тип varchar).

Заказ: Номер заказа (тип integer), номер клиента, дата заказа, сумма заказа (тип date, decimal).

Книга и заказ: Номер книги и номер заказа, количество книг (тип integer).

## Состав и содержание работ по созданию автоматизированной системы
1. Анализ требований
* Анализ существующих методов получения информации о книгах
* Изучение способов подбора литературы
* Оценка текущего процесса просмотра отзывов
* Анализ методов ведения справочников 

2. Концептуальное проектирование:

* Определение архитектуры системы
* Проектирование базы данных
* Проектирование пользовательского интерфейса:

3. Разработка системы

* Разработка базы данных
* Разработка логики приложения

4. Тестирование

* Проверка отдельных компонентов системы
* Проверка взаимодействия между различными модулями системы
* Тестирование полного цикла заказа книги

5. Документирование

* Создание технической документации:
* Создание пользовательского руководства:

## Порядок разработки автоматизированной системы;
* Анализ требований
* Концептуальное проектирование:
* Разработка системы
* Тестирование
* Документирование

## Порядок контроля и приемки автоматизированной системы;

1. Планирование процесса контроля и приемки

   1.1. Определение критериев качества системы
   * Функциональность
   * Производительность
   * Безопасность
   * Надежность
   * Удобство использования

   1.2. Составление плана контроля и приемки
   * Определение этапов проверки
   * Назначение ответственных за каждый этап
   * Распределение сроков выполнения
2. Контроль на каждой стадии разработки

   2.1. На этапе анализа требований:
   * Проверка соответствия задачи требованиям заказчика
   * Оценка собранных данных

   2.2. На этапе проектирования:
   * Оценка соответствия архитектуры требованиям системы

   2.3. На этапе разработки:
   * Проверка соответствия кода требованиям и стандартам
   * Оценка качества написанного кода

   2.4. На этапе тестирования:
   * Проверка соответствия фактического поведения системы требованиям
   * Оценка качества и надежности системы
3. Финальный контроль и приемка
   * Проведение тестирования по отношению к коду.
   * Проведение пользовательских тестов.
   * Подготовка технической документации
   * Подготовка пользовательского руководства
4. Принятие решения о приемке

5. Поддержка системы

## Требования к составу и содержанию работ по подготовке объекта автоматизации к вводу автоматизированной системы в действие;

1. Подготовка базы данных:

* Создание таблиц для хранения информации о книгах, клиентах, сотрудниках магазина
* Определение полномочий и прав доступа для каждой группы пользователей
* Настройка системы управления заказами:
* Создание каталога книг
* Реализация возможности добавления книг в заказ
* Настройка уведомлений о новых заказах

2. Учет предоставленных услуг:

* Создание каталога услуг
* Реализация возможности добавления дополнительных услуг

3. Учет данных о клиентах и сотрудниках магазина:

* Реализация возможности сохранения истории заказов

4. Предоставление дополнительных сведений о магазине:

* Реализация раздела с информацией о магазине (адрес, контакты, часы работы)
* Создание раздела с описаниями книг

5. Разграничение прав доступа:

* Реализация механизмов контроля доступа к различным функциям
* Автоматизация работы двух категорий лиц:

Для покупателей:

* Возможность просмотра истории заказов
* тслеживание статуса заказа

Для сотрудников магазина:

* Доступ к полной истории заказов клиента
* Возможность добавления книг в заказ
* Интеграция с системой управления складом

6. Тестирование и отладка:

* Проведение тестового запуска системы
* Проверка корректности работы всех функций
* Устранение ошибок и дополнение докум

## Требования к документированию
Описание основных функций и возможностей приложения для покупателей и сотрудников магазина

1. Пользовательская документация:
* Инструкции по использованию приложения
* Описание всех интерфейсов и кнопок
* Руководство по созданию и управлению профилями покупателей
2. Техническая документация:
* Архитектура системы
* Описание базы данных
* Документация по интеграциям с другими системами
3. Руководство по разработке:
* Структура проекта
* Правила кодирования
* Руководство по тестированию
4. Документация по поддержке:
* Руководство по устранению неполадок
* Процедуры обновлений
* Контакты технической поддержки

## Источники разработки
База данных в СУБД MYSQLWorkbench
Код приложения Java