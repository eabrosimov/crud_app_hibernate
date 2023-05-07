# **CRUD APP**
Консольное CRUD приложение с возможностью сохранения в базу данных
## **Технологии**
___
* Java
* PostgreSQL
* Hibernate
* Maven
* Flyway
* JUnit
* Mockito
  
## **Описание проекта**
___
Приложение работает с сущностями:
* Developer  
  * id
  * firstName
  * lastName
  * skills
  * specialty
  * status
* Skill
  * id
  * name
* Specialty
  * id
  * name
* Status  
  * ACTIVE
  * DELETED

Стандартный функционал CRUD приложения:
* Добавить
* Получить
* Получить всех
* Изменить
* Удалить

В качестве хранилища данных используется СУБД PostgreSQL  
Таблицы в БД:  
* developer
    * **id** int generated by default as identity primary key
    * **first_name** varchar(25) not null
    * **last_name** varchar(25) not null
    * **specialty_id** int references specialty(id)
    * **status** varchar(7) not null
* specialty
    * **id** int generated by default as identity primary key
    * **name** varchar(25) unique not null
* skill
    * **id** int generated by default as identity primary key
    * **name** varchar(25) unique not null
* skill_set
    * **skill_id** int references skill(id)
    * **developer_id** int not null references developer(id) on delete cascade  
    primary key(skill_id, developer_id)

## **Структура проекта**
___
**`model`** - POJO классы  
**`repository`** - классы, реализующие доступ к файлам  
**`service`** - бизнес логика   
**`controller`** - обработка запросов от пользователя  
**`view`** - данные для работы с консолью

## **Список команд**
___
При запуске программы пользователь попадает в главное меню.  
Выводится список доступных команд для перемещения по редакторам таблиц:  
**`1`** - меню редактора таблицы developer  
**`2`** - меню редактора таблицы skill  
**`3`** - меню реактора таблицы specialty   
**`-help`** - отображение списка доступных команд  
**`-exit`** - завершение работы программы  
  
У всех редакторов одинаковый список команд:  
**`-c`** - создать новую сущность  
**`-r`** - вывести сущность на экран получив ее по id  
**`-ra`** - вывести все сущности на экран  
**`-u`** - изменить сущность  
**`-d`** - удалить сущность  
**`-help`** - отображение списка доступных команд  
**`-exit`** - выход в главное меню

У редактора developer есть дополнительный список команд после команды  **`-u`** позволяющий выбрать поле для изменения:  
**`first name`**  
**`last name`**  
**`skill`**  
**`specialty`**  
Также при выборе поля **`skill`** дается выбор:  
**`-a`** - добавить скил в лист скилов  
**`-d`** - удалить скил из листа скилов

## **Логика**
___
### **Добавление**  
Происходит валидация введенного имени(в случае с Developer имени и фамилии).  
Имя не должно быть пустым, содержать только пробелы, а также иметь больше 25 символов.  
Допускаются только буквенные символы(латинские a-zA-Z и кириллица а-яА-Я).  
Все не буквенные символы отбрасываются.  
Допускатются пробелы между словами(кроме Developer).  
Название специальности и скила дополнительно проверяются на уникальность.  

Если пользователь ввел валидное имя, он добавляется в таблицу, где объекту автоматически присваивается id сгенерированный СУБД.  
Если имя невалидное, на экран выводится соответствующее оповещение.  
  
### **Получение**  
Происходит валидация введенного id.  
Выполняется проверка на буквы, отрицательные значения, а также проверка на существование объекта с таким id.  
Если валидация прошла успешно, на экран выводится текстовое представление объекта.  
Если id невалидный, на экран выводится соответствующее оповещение.  
  
### **Изменение**  
Происходит валидация введенного id.  
Если id валидный, возвращается объект соответствующий данному id.  
Иначе выводится соответствующее оповещение.  
  
Далее пользователь вводит новое имя и происходит валидация имени.  
Если валидация прошла успешно, происходит замена в таблице старого объекта на новый и на экран выводится текстовое представление обновленного объекта.  
Иначе выводится соответствующее уведомление.  
Название специальности и скила дополнительно проверяются на уникальность.  
  
В случае с Developer после введенного id пользователь должен ввести нужное поле для редактирования.   
С именем и фамилией происходят вышеописанные действия.  

Если пользователь хочет добавить/удалить новый скил или добавить/изменить специальность, он вводит id, по которому происходит поиск объекта в нужной таблицу и добавляет его Developer'y.   
Иначе, если объект не найден или id невалидный выводится соответствующее оповещение.  
  
### **Удаление**  
Происходит валидация введенного id.  
Если id валидный, из таблицы удаляется объект с соответствующим id.  
Иначе выводится соответствующее оповещение.  
Удалить специальность или скил из соответствующих таблиц можно только, если они не привязаны к разработчику. Иначе придется сначала изменить специальность и удалить скил из набора скилов у разработчика.
  
В случае с Developer объект не удаляется из таблицы, а лишь меняет состояние поля Status с ACTIVE на DELETED, его специальность и набор скилов обнуляется. После удаления получить/изменить объект через консоль нельзя.