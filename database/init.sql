CREATE DATABASE IF NOT EXISTS javafxTest;
USE javafxTest;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `authors`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `authors` VALUES (1, 'Джоан Роулинг');
INSERT INTO `authors` VALUES (2, 'Стивен Кинг');
INSERT INTO `authors` VALUES (3, 'Маргарет Митчелл');
INSERT INTO `authors` VALUES (4, 'Нора Сакавич');
INSERT INTO `authors` VALUES (5, 'Кэтрин Стокетт');
INSERT INTO `authors` VALUES (6, 'Александр Дюма');
INSERT INTO `authors` VALUES (7, 'Джордж Мартин');
INSERT INTO `authors` VALUES (8, 'Борис Васильев');
INSERT INTO `authors` VALUES (9, 'Агата Кристи');
INSERT INTO `authors` VALUES (10, 'Михаил Булгаков');


CREATE TABLE `categories`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `categories` VALUES (1, 'Роман');
INSERT INTO `categories` VALUES (2, 'Ода');
INSERT INTO `categories` VALUES (3, 'Комедия');
INSERT INTO `categories` VALUES (4, 'Притча');
INSERT INTO `categories` VALUES (5, 'Повесть');
INSERT INTO `categories` VALUES (6, 'Фэнтези');
INSERT INTO `categories` VALUES (7, 'Драма');
INSERT INTO `categories` VALUES (8, 'Детективный роман');
INSERT INTO `categories` VALUES (9, 'Рассказ');

CREATE TABLE `feedback`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `feedback` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `id_products` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id_products_f_pkey_idx`(`id_products` ASC) USING BTREE,
  CONSTRAINT `id_products_f_pkey` FOREIGN KEY (`id_products`) REFERENCES `products` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `feedback` VALUES (1, 'Книга пронзает своей загадочностью, ведь далеко не каждый автор может так передать чувства героя.', 10);
INSERT INTO `feedback` VALUES (2, 'Мне очень понравилась данныя книга', 10);
INSERT INTO `feedback` VALUES (3, 'Ставлю книге 10 из 10 баллов. За такие деньги она очень даже ничего', 10);
INSERT INTO `feedback` VALUES (30, 'Книга прелесть', 8);
INSERT INTO `feedback` VALUES (31, 'Книга очень завораживает. Советую заказать её каждому. ', 10);
INSERT INTO `feedback` VALUES (32, 'Книга мне понравилась тем как описаны события в ней, герои, сама задумка.Я уверена, что через пару лет мне захочется перечитать эту книгу.', 10);
INSERT INTO `feedback` VALUES (33, '', 9);

CREATE TABLE `izdatelstvo`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `izdatelstvo` VALUES (1, 'Просвещение');
INSERT INTO `izdatelstvo` VALUES (2, 'ЭКСМО');
INSERT INTO `izdatelstvo` VALUES (3, 'Macmillan');
INSERT INTO `izdatelstvo` VALUES (4, 'Bloomsbury');
INSERT INTO `izdatelstvo` VALUES (5, 'Harper');
INSERT INTO `izdatelstvo` VALUES (6, 'Стрекоза');
INSERT INTO `izdatelstvo` VALUES (7, 'Азбука-Аттикус');
INSERT INTO `izdatelstvo` VALUES (8, 'Росмэн-Пресс');
INSERT INTO `izdatelstvo` VALUES (9, 'Popcorn Books');
INSERT INTO `izdatelstvo` VALUES (10, 'АСТ');

CREATE TABLE `orders`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `total_price` double(10, 2) NULL DEFAULT NULL,
  `pickup_poind_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `orders` VALUES (1, 590.00, 4);
INSERT INTO `orders` VALUES (3, 10340.00, 4);
INSERT INTO `orders` VALUES (4, 3675.00, 2);
INSERT INTO `orders` VALUES (5, 3000.00, 1);
INSERT INTO `orders` VALUES (6, 3250.00, 3);
INSERT INTO `orders` VALUES (7, 16663.00, 1);
INSERT INTO `orders` VALUES (8, 4000.00, 2);
INSERT INTO `orders` VALUES (9, 12000.00, 3);
INSERT INTO `orders` VALUES (10, 9590.00, 3);
INSERT INTO `orders` VALUES (11, 9000.00, 1);
INSERT INTO `orders` VALUES (12, 18300.00, 4);
INSERT INTO `orders` VALUES (13, NULL, 1);
INSERT INTO `orders` VALUES (14, NULL, 2);
INSERT INTO `orders` VALUES (15, 64130.00, 1);
INSERT INTO `orders` VALUES (16, 70030.00, 3);
INSERT INTO `orders` VALUES (17, 4000.00, 2);
INSERT INTO `orders` VALUES (18, 4000.00, 1);
INSERT INTO `orders` VALUES (19, NULL, 1);
INSERT INTO `orders` VALUES (20, 37720.00, 2);
INSERT INTO `orders` VALUES (21, 10600.00, 2);
INSERT INTO `orders` VALUES (22, 2704.00, 2);
INSERT INTO `orders` VALUES (23, 6440.00, 2);
INSERT INTO `orders` VALUES (24, 6240.00, 2);

CREATE TABLE `pickup_point`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `pickup_point` VALUES (1, 'Ozon', 'ул.Олвера-стрит д53 кв172');
INSERT INTO `pickup_point` VALUES (2, 'Amazon', 'ул.Бродвей д125 кв61');
INSERT INTO `pickup_point` VALUES (3, 'AliExpress', 'ул.Ломбард-Стрит д49 кв90');
INSERT INTO `pickup_point` VALUES (4, 'Allegro', 'ул.Васильева д14 кв 35');

CREATE TABLE `products`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `price` int NOT NULL,
  `category_id` int NULL DEFAULT NULL,
  `author_id` int NULL DEFAULT NULL,
  `izdatelstvo_id` int NULL DEFAULT NULL,
  `opisanie` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `photo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `nalichie` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `category_id`(`category_id` ASC) USING BTREE,
  INDEX `author_id`(`author_id` ASC) USING BTREE,
  INDEX `izdatelstvo_id`(`izdatelstvo_id` ASC) USING BTREE,
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `products_ibfk_2` FOREIGN KEY (`author_id`) REFERENCES `authors` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `products_ibfk_3` FOREIGN KEY (`izdatelstvo_id`) REFERENCES `izdatelstvo` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1342535 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of products
-- ----------------------------
INSERT INTO `products` VALUES (1, '\"Гарри Поттер и узник Азкабана\"', 1500, 6, 1, 7, 'Гарри взрослеет, и вместе с тем жить в Хогвартсе всё страшнее. Из тюрьмы для волшебников Азкабан сбежал опасный преступник - Сириус Блэк. Мир наполнился слухами, что он ищет и хочет убить одного тринадцатилетнего парня, совсем обычного на первый взгляд. Его имя - Гарри Поттер.', 'picture/garry.jpg', 'Есть в наличии');
INSERT INTO `products` VALUES (2, '\"Зелёная миля\"', 300, 7, 2, 10, 'Стивен Кинг приглашает читателей в жуткий мир тюремного блока смертников, откуда уходят, чтобы не вернуться, приоткрывает дверь последнего пристанища тех, кто преступил не только человеческий, но и Божий закон. По эту сторону электрического стула нет более смертоносного местечка! Ничто из того, что вы читали раньше, не сравнится с самым дерзким из ужасных опытов Стивена Кинга - с историей, что начинается на Дороге Смерти и уходит в глубины самых чудовищных тайн человеческой души...', 'picture/greenMiles.jpg', 'Есть в наличии');
INSERT INTO `products` VALUES (3, '\"Унесенные ветром\"', 140, 3, 3, 3, 'Роман Маргарет Митчелл вышел в свет в 1936 году и имел феноменальный успех у читателей. Только в первые годы его тираж превысил три миллиона, и «Унесенные ветром» были признаны «книгой века». В 1939 году на экраны вышел одноименный фильм (с участием Вивьен Ли и Кларком Гейблом), который завоевал восемь премий «Оскар» и стал одной из самых кассовых лент в истории кинематографа. Несмотря на полярные оценки литературных критиков, роман удостоился престижной Пулитцеровской премии, его сравнивали с «Войной и миром» Льва Толстого, а Маргарет Митчелл ставили в один ряд с великими классиками мировой литературы. Книга выдержала более 70 изданий только в Соединенных Штатах, была переведена на десятки языков и по сей день пользуется неизменной популярностью и любовью у читателей во всем мире.', 'picture/ynesVetrom.jpg', 'Есть в наличии');
INSERT INTO `products` VALUES (4, '\"Свита короля\"', 700, 7, 4, 9, 'Время на исходе. Оказавшись в Университете Пальметто, Нил Джостен знал, что не доживет до конца года, но теперь, когда смерть не за горами, он больше чем прежде хочет жить. Дружба с Лисами была опрометчивой затеей, а поцелуй с одним из них — затеей немыслимой. Пока «Лисы» пытаются во что бы то ни стало выйти в финал чемпионата, Нил сражается за свою жизнь, ведь теперь ей угрожает не только Рико Морияма, но и мафиозный клан Бостонского Мясника. Правда — единственный шанс Нила на спасение, однако она может привести к гибели всех, кто ему дорог...', 'picture/svitaKing.jpg', 'Есть в наличии');
INSERT INTO `products` VALUES (5, '\"Прислуга\"', 700, 7, 5, 2, 'Американский Юг, на дворе 1960-е годы. Скитер только-только закончила университет и возвращается домой, в сонный городок Джексон, где никогда ничего не происходит. Она мечтает стать писательницей, вырваться в большой мир. Но приличной девушке с Юга не пристало тешиться столь глупыми иллюзиями, приличной девушке следует выйти замуж и хлопотать по дому. Мудрая Эйбилин на тридцать лет старше Скитер, она прислуживает в домах белых всю свою жизнь, вынянчила семнадцать детей и давно уже ничего не ждет от жизни, ибо сердце ее разбито после смерти единственного сына.', 'picture/prislyga.jpg', 'Есть в наличии');
INSERT INTO `products` VALUES (6, '\"Граф Монте-Кристо\"', 245, 1, 6, 7, 'Как и сто шестьдесят пять лет назад, \"Граф Монте-Кристо\" Александра Дюма остается одним из самых популярных романов в мировой литературе. К нему писали продолжения, его ставили на сцене, создавали мюзиклы, экранизировали, но и по сей день бесчисленные издания этой книги доставляют удовольствие новым и новым поколениям читателей. История молодого парижанина, которого приятели в шутку засадили в тюрьму, почерпнута автором в архивах парижской полиции. А из-под пера мастера выходит моряк Эдмон Дантес, мученик замка Иф. Не дождавшись правосудия, он решает сам вершить суд и жестоко мстит врагам, разрушившим его счастье.', 'picture/grafMonte.jpg', 'Есть в наличии');
INSERT INTO `products` VALUES (7, '\"В списках не значился\"', 310, 2, 8, 8, 'Роман \"В списках не значился\" одно из самых известных произведений Бориса Львовича Васильева. В нем увековечен подвиг защитников Брестской крепости, которые первыми приняли на себя удар фашистской армии в 1941 г. Девятнадцатилетний Коля Плужников сразу после военного училища попадает в эпицентр военных действий. Ужасы войны, героизм и предательство, первая любовь описаны Борисом Васильевым просто и лаконично, но невероятно пронзительно и психологично. Рекомендовано для детей старшего школьного возраста.', 'picture/vspiskeNe.jpg', 'Есть в наличии');
INSERT INTO `products` VALUES (8, '\"Десять негритят\"', 280, 8, 8, 5, 'Десять никак не связанных между собой людей в особняке на уединенном острове... Кто вызвал их сюда таинственным приглашением? Зачем кто-то убивает их, одного за другим, самыми невероятными способами? Почему все происходящее так тесно переплетено с веселым детским стишком?', 'tennegr.jpg', 'Есть в наличии');
INSERT INTO `products` VALUES (9, '\"Буря мечей\"', 1800, 1, 9, 10, 'Перед вами - знаменитая эпопея \"Песнь льда и огня\". Эпическая, чеканная сага о мире Семи Королевств. О мире суровых земель вечного холода и радостных земель вечного лета. О мире опасных приключений, тончайших политических интриг и великих деяний. О мире лордов и героев, драконов, воинов и магов, чернокнижников и убийц - всех, кого свела Судьба во исполнение пророчества...', 'picture/byriaMech.jpg', 'Есть в наличии');
INSERT INTO `products` VALUES (10, '\"Записки юного врача\"', 1500, 1, 1, 1, 'Эти семь маленьких шедевров Михаил Булгаков создал в юности, хотя через много лет отредактировал заново. Время действия - 1917 год, место - больница в глухой российской деревне. Сюда в качестве главного и единственного доктора пребывает 23-летний выпускник медицинского факультета с отличной теоретической подготовкой и полным отсутствием опыта. Первые пациенты, тяжелые случаи, неизбежные ошибки, борьба с собственными страхами и малодушием, маленькие и большие победы, иногда - трагические поражения.', 'picture/zapiskiDok.jpg', 'Нет в наличии');

CREATE TABLE `products_has_order`  (
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  `kol_vo` int NULL DEFAULT NULL,
  INDEX `products_has_order_fk`(`order_id` ASC) USING BTREE,
  INDEX `products_has_order_fk_1`(`product_id` ASC) USING BTREE,
  CONSTRAINT `products_has_order_fk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `products_has_order_fk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `products_has_order` VALUES (1, 10, 1);
INSERT INTO `products_has_order` VALUES (3, 3, 6);
INSERT INTO `products_has_order` VALUES (4, 6, 15);
INSERT INTO `products_has_order` VALUES (6, 10, 1);
INSERT INTO `products_has_order` VALUES (6, 8, 2);
INSERT INTO `products_has_order` VALUES (6, 4, 3);
INSERT INTO `products_has_order` VALUES (7, 10, 1);
INSERT INTO `products_has_order` VALUES (7, 10, 2);
INSERT INTO `products_has_order` VALUES (7, 10, 2);
INSERT INTO `products_has_order` VALUES (7, 10, 2);
INSERT INTO `products_has_order` VALUES (7, 10, 2);
INSERT INTO `products_has_order` VALUES (7, 10, 2);
INSERT INTO `products_has_order` VALUES (7, 10, 2);
INSERT INTO `products_has_order` VALUES (7, 10, 2);
INSERT INTO `products_has_order` VALUES (7, 10, 2);
INSERT INTO `products_has_order` VALUES (7, 10, 2);
INSERT INTO `products_has_order` VALUES (10, 10, 1);
INSERT INTO `products_has_order` VALUES (12, 10, 10);
INSERT INTO `products_has_order` VALUES (12, 5, 10);
INSERT INTO `products_has_order` VALUES (12, 3, 10);
INSERT INTO `products_has_order` VALUES (20, 8, 2);
INSERT INTO `products_has_order` VALUES (20, 4, 9);
INSERT INTO `products_has_order` VALUES (21, 9, 1);
INSERT INTO `products_has_order` VALUES (21, 8, 5);
INSERT INTO `products_has_order` VALUES (21, 4, 3);
INSERT INTO `products_has_order` VALUES (22, 7, 3);
INSERT INTO `products_has_order` VALUES (22, 3, 2);
INSERT INTO `products_has_order` VALUES (23, 9, 1);
INSERT INTO `products_has_order` VALUES (23, 7, 3);
INSERT INTO `products_has_order` VALUES (23, 6, 2);
INSERT INTO `products_has_order` VALUES (24, 9, 1);
INSERT INTO `products_has_order` VALUES (24, 7, 2);
INSERT INTO `products_has_order` VALUES (24, 5, 1);

CREATE TABLE `roles`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `roles` VALUES (1, 'Сотрудник магазина');
INSERT INTO `roles` VALUES (2, 'Клиент');

CREATE TABLE `stranic`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `book_id` int NULL DEFAULT NULL,
  `photo_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `book_id`(`book_id` ASC) USING BTREE,
  CONSTRAINT `stranic_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `products` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `stranic` VALUES (5, 1, 'picture/garryOne.jpg');
INSERT INTO `stranic` VALUES (6, 1, 'picture/garryTwo.jpg');
INSERT INTO `stranic` VALUES (7, 2, 'picture/greenmilesOne.jpg');
INSERT INTO `stranic` VALUES (8, 2, 'picture/greenmilesTwo.jpg');
INSERT INTO `stranic` VALUES (9, 3, 'picture/ynesvetromOne.jpg');
INSERT INTO `stranic` VALUES (10, 3, 'picture/ynesvetromTwo.jpg');
INSERT INTO `stranic` VALUES (11, 4, 'picture/svitakingOne.jpg');
INSERT INTO `stranic` VALUES (12, 4, 'picture/svitakingTwo.jpg');
INSERT INTO `stranic` VALUES (13, 5, 'picture/prislygaOne.jpg');
INSERT INTO `stranic` VALUES (14, 5, 'picture/prislygaTwo.jpg');
INSERT INTO `stranic` VALUES (15, 6, 'picture/grafOne.jpg');
INSERT INTO `stranic` VALUES (16, 6, 'picture/grafTwo.jpg');
INSERT INTO `stranic` VALUES (17, 7, 'picture/spiskneznacOne.jpg');
INSERT INTO `stranic` VALUES (18, 7, 'picture/spiskneznacTwo.jpg');
INSERT INTO `stranic` VALUES (19, 8, 'picture/tennegrOne.jpg');
INSERT INTO `stranic` VALUES (20, 8, 'picture/tennegrTwo.jpg');
INSERT INTO `stranic` VALUES (21, 9, 'picture/byrmechOne.jpg');
INSERT INTO `stranic` VALUES (22, 9, 'picture/byrmechTwo.jpg');
INSERT INTO `stranic` VALUES (23, 10, 'picture/zapisvracOne.jpg');
INSERT INTO `stranic` VALUES (24, 10, 'picture/zapisvracTwo.jpg');

CREATE TABLE `users`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `usersurname` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `userlogin` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `userpassword` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `role_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_id`(`role_id` ASC) USING BTREE,
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `users` VALUES (1, 'Алла', 'Глюкова', 'test', 'test1', 1);
INSERT INTO `users` VALUES (2, 'Вадим', 'Ленин', 'test2', 'test2', 2);
delimiter ;
CREATE PROCEDURE `DeleteBook`(IN bookId INT)
BEGIN
   DELETE FROM feedback WHERE id_products = bookId;
   DELETE FROM products_has_order WHERE product_id = bookId;
   DELETE FROM stranic WHERE book_id = bookId;
   DELETE FROM products WHERE id = bookId;
END;;
delimiter ;



SET FOREIGN_KEY_CHECKS = 1;
