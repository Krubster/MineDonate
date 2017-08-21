-- phpMyAdmin SQL Dump
-- version 4.6.4deb1
-- https://www.phpmyadmin.net/
--
-- Хост: localhost:3306
-- Время создания: Авг 19 2017 г., 17:37
-- Версия сервера: 5.7.18-0ubuntu0.16.10.1
-- Версия PHP: 7.0.18-0ubuntu0.16.10.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `mcshop`
--

-- --------------------------------------------------------

--
-- Структура таблицы `md_accounts`
--

CREATE TABLE `md_accounts` (
  `name` text CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `money` int(11) NOT NULL DEFAULT '0',
  `id` int(11) NOT NULL,
  `freezShopCreate` tinyint(1) NOT NULL DEFAULT '0',
  `freezShopCreateFreezer` text,
  `freezShopCreateReason` text,
  `shopsCount` int(11) NOT NULL DEFAULT '0',
  `coins` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `md_accounts`
--

INSERT INTO `md_accounts` (`name`, `money`, `id`, `freezShopCreate`, `freezShopCreateFreezer`, `freezShopCreateReason`, `shopsCount`, `coins`) VALUES
('log_inil', 99, 2, 0, 'Player669', '', 0, 7286),
('test1', 0, 591, 0, NULL, NULL, 0, 0);

-- --------------------------------------------------------

--
-- Структура таблицы `md_entities`
--

CREATE TABLE `md_entities` (
  `name` text NOT NULL,
  `data` blob NOT NULL,
  `cost` int(11) NOT NULL,
  `lim` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `rating` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `md_entities`
--

INSERT INTO `md_entities` (`name`, `data`, `cost`, `lim`, `id`, `rating`) VALUES
('??????', 0x02e61f8b08000000000000005d533d6f1341147ce7aff8421ce5034441811014299045138926c25f3176e42496e38496f579632fbedbbdecee39892800893f8004224a89440a90a890284002d10441e8a041a2a0a1053abaf0f6ec4b80952c6b7767df9b3733370a60819defd09a707ab48d9b44937814ff3315a21ad497a21d387861c3685e6bc95a81a66a14009229481488a2b985db60561a124be6e56487722a9993f5c85685125777618884e18a906722648f63f316717a0daa98d2843b347a73e5e0e0e0cd5f6f4e1d55177dea51ae577c8aec222e97feed301da1d785eb8acd06e19de3cadfd7c31561a7ba422a9abd1178fe8a96947790b8056355de0f5c2c425a2e85388cd785d4c42d0ae1b6c52637cd9230916f29217dcd04cf7b22e0da1ca3920589d492305626ae5b1a0e36f775f6471c52555ec30106b8f179a219ef54c836429c1ec4c02e51a2bb4d86b42001996b42baedd5d56a6951283db370feec9e5d7e62c38992147eb16baaaaa42132f7ece4cdff7f164cd7a934b252c435e846c00cab384caef8ac23745633a7a76aac8f8797bfed2621694c2b179a80983806c370b421b528cc782930c7c3f57affc1cefcdddd5fd1de82911a25aa8b9512601bbe66ab1f39f54f130f5f7d8c416a9887d934d88e4b94f2714a98e154673dc6a923c9bacea2a74c6f677dbc4652d9f9705b31dec461aa10f47a4c6703bf4d34add13e75b16f2c864499b42e5a905ee657253a6006b44ba81f57c87a3042ba2134314318ad626fe76e1d4238cdc81a918c0c4c4362cb9be8b6216fee12cd6d3f542001e9c88085179f5fdecbfcdcb1c19e47357d93c2f08318466fb418282dbc3054d8365e172a942db7d0797ffdfe8573b9e5ead3df0dab9fdbf8663fb7f7fd389c1e8eb548b64ac2a30a2d0989e2ab3646b0d8c523da0e35c07024cae8e0e12186a8493d9fca81f49922e175b472d5af09a11196ae04520f22140bbf5d8c5694a8f1a344850edd59fab0f765f3dd630b268fb9af31c54ce6e10f93c96fc02304000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000, 10, 1, 31, 0);

-- --------------------------------------------------------

--
-- Структура таблицы `md_items`
--

CREATE TABLE `md_items` (
  `name` text NOT NULL,
  `cost` int(11) NOT NULL,
  `lim` int(11) NOT NULL,
  `stack_data` blob NOT NULL,
  `id` int(11) NOT NULL,
  `rating` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `md_items`
--

INSERT INTO `md_items` (`name`, `cost`, `lim`, `stack_data`, `id`, `rating`) VALUES
('', 10, 11, 0x00331f8b0800000000000000e36260606260ca4c61ac67646075ce2fcd2b616462607349cc4d4c4f6570640000a9e1d8731f0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000, 28, 0);

-- --------------------------------------------------------

--
-- Структура таблицы `md_logs`
--

CREATE TABLE `md_logs` (
  `date` date NOT NULL,
  `playerName` text NOT NULL,
  `message` text NOT NULL,
  `amount` int(11) NOT NULL,
  `money` int(11) NOT NULL,
  `moneyType` text NOT NULL,
  `id` int(11) NOT NULL,
  `shopId` int(11) NOT NULL,
  `catId` int(11) NOT NULL,
  `merchId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `md_perms`
--

CREATE TABLE `md_perms` (
  `id` int(11) NOT NULL,
  `groupName` text NOT NULL,
  `permission` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Дамп данных таблицы `md_perms`
--

INSERT INTO `md_perms` (`id`, `groupName`, `permission`) VALUES
(1, 'default', '*'),
(2, 'default', 'editOwnedShop'),
(3, 'default', 'renameOwnedShop'),
(4, 'moder', 'freezeOtherShop'),
(5, 'admin', '*');

-- --------------------------------------------------------

--
-- Структура таблицы `md_privelegies`
--

CREATE TABLE `md_privelegies` (
  `name` text NOT NULL,
  `description` text NOT NULL,
  `pic_url` text NOT NULL,
  `cost` int(11) NOT NULL,
  `time` bigint(20) NOT NULL,
  `id` int(11) NOT NULL,
  `worlds` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `md_privelegies`
--

INSERT INTO `md_privelegies` (`name`, `description`, `pic_url`, `cost`, `time`, `id`, `worlds`) VALUES
('PREMIUUUM2', 'asdasdasddfsdaf\r\nsadg\r\nfag\r\n\r\nafg\r\nf\r\ndgdhgfhf\r\ngh\r\nfdj\r\nfd\r\nhj\r\n', 'http://www.mkyong.com/image/mypic.jpg', 1278654, 10, 4, '*');

-- --------------------------------------------------------

--
-- Структура таблицы `md_regions`
--

CREATE TABLE `md_regions` (
  `name` text NOT NULL,
  `world` text NOT NULL,
  `cost` int(11) NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `md_regions`
--

INSERT INTO `md_regions` (`name`, `world`, `cost`, `id`) VALUES
('4535', 'world', 1234, 6);

-- --------------------------------------------------------

--
-- Структура таблицы `md_shops`
--

CREATE TABLE `md_shops` (
  `id` int(11) NOT NULL,
  `owner` text NOT NULL,
  `name` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `lastUpdate` int(11) NOT NULL,
  `isFreezed` tinyint(1) NOT NULL,
  `freezer` text NOT NULL,
  `freezReason` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `moneyType` text NOT NULL,
  `rating` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Дамп данных таблицы `md_shops`
--

INSERT INTO `md_shops` (`id`, `owner`, `name`, `lastUpdate`, `isFreezed`, `freezer`, `freezReason`, `moneyType`, `rating`) VALUES
(0, 'SERVER', 'Server shop', 0, 0, '', '', '', 0),
(1, 'log_inil', 'SUPER LOW COST SHOP', 0, 0, 'Player854', '', 'coin', 0);

-- --------------------------------------------------------

--
-- Структура таблицы `md_userItems`
--

CREATE TABLE `md_userItems` (
  `id` int(11) NOT NULL,
  `cost` int(11) NOT NULL,
  `name` text NOT NULL,
  `lim` int(11) NOT NULL,
  `shopId` int(11) NOT NULL,
  `stack_data` blob NOT NULL,
  `rating` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `md_accounts`
--
ALTER TABLE `md_accounts`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `md_entities`
--
ALTER TABLE `md_entities`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- Индексы таблицы `md_items`
--
ALTER TABLE `md_items`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `md_logs`
--
ALTER TABLE `md_logs`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- Индексы таблицы `md_perms`
--
ALTER TABLE `md_perms`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- Индексы таблицы `md_privelegies`
--
ALTER TABLE `md_privelegies`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- Индексы таблицы `md_regions`
--
ALTER TABLE `md_regions`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- Индексы таблицы `md_shops`
--
ALTER TABLE `md_shops`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `md_userItems`
--
ALTER TABLE `md_userItems`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `md_accounts`
--
ALTER TABLE `md_accounts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=592;
--
-- AUTO_INCREMENT для таблицы `md_entities`
--
ALTER TABLE `md_entities`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;
--
-- AUTO_INCREMENT для таблицы `md_items`
--
ALTER TABLE `md_items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;
--
-- AUTO_INCREMENT для таблицы `md_logs`
--
ALTER TABLE `md_logs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=143;
--
-- AUTO_INCREMENT для таблицы `md_perms`
--
ALTER TABLE `md_perms`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT для таблицы `md_privelegies`
--
ALTER TABLE `md_privelegies`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT для таблицы `md_regions`
--
ALTER TABLE `md_regions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT для таблицы `md_shops`
--
ALTER TABLE `md_shops`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;
--
-- AUTO_INCREMENT для таблицы `md_userItems`
--
ALTER TABLE `md_userItems`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
