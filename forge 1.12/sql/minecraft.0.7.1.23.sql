-- phpMyAdmin SQL Dump
-- version 4.6.4deb1
-- https://www.phpmyadmin.net/
--
-- Хост: localhost:3306
-- Время создания: Сен 02 2017 г., 11:00
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
  `UUID` text CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `money` int(11) NOT NULL DEFAULT '0',
  `id` int(11) NOT NULL,
  `freezShopCreate` tinyint(1) NOT NULL DEFAULT '0',
  `freezShopCreateFreezer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `freezShopCreateReason` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `shopsCount` int(11) NOT NULL DEFAULT '0',
  `coins` int(11) NOT NULL DEFAULT '0',
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `md_entities`
--

CREATE TABLE `md_entities` (
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `data` blob NOT NULL,
  `cost` int(11) NOT NULL,
  `lim` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `rating` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `md_items`
--

CREATE TABLE `md_items` (
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `cost` int(11) NOT NULL,
  `lim` int(11) NOT NULL,
  `stack_data` blob NOT NULL,
  `id` int(11) NOT NULL,
  `rating` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `md_logs`
--

CREATE TABLE `md_logs` (
  `date` date NOT NULL,
  `playerName` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `amount` int(11) NOT NULL,
  `money` int(11) NOT NULL,
  `moneyType` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
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
  `groupName` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `permission` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Дамп данных таблицы `md_perms`
--

INSERT INTO `md_perms` (`id`, `groupName`, `permission`) VALUES
(1, 'default', 'createShop'),
(2, 'default', 'editOwnedShop'),
(3, 'default', 'renameOwnedShop'),
(4, 'moder', 'freezeOtherShop'),
(5, 'admin', '*'),
(6, 'default', 'removeOwnedShop'),
(7, 'default', '*');

-- --------------------------------------------------------

--
-- Структура таблицы `md_privelegies`
--

CREATE TABLE `md_privelegies` (
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `pic_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `cost` int(11) NOT NULL,
  `time` bigint(20) NOT NULL,
  `id` int(11) NOT NULL,
  `worlds` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL
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
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `world` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
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
  `UUID` text CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `lastUpdate` int(11) NOT NULL,
  `isFreezed` tinyint(1) NOT NULL,
  `freezer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `freezReason` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `moneyType` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `rating` int(11) NOT NULL DEFAULT '0',
  `ownerName` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Дамп данных таблицы `md_shops`
--

INSERT INTO `md_shops` (`id`, `UUID`, `name`, `lastUpdate`, `isFreezed`, `freezer`, `freezReason`, `moneyType`, `rating`, `ownerName`) VALUES
(22, '84e9e4f6-df8d-3fc9-b2c7-0eb3b9531d39', '111', 0, 0, '', '', 'coin', 0, 'Player818'),
(23, 'a288cb29-a681-33d0-bc05-44996f7b6ce1', '101101', 0, 0, '', '', 'coin', 0, 'Player705'),
(24, 'a288cb29-a681-33d0-bc05-44996f7b6ce1', '1111', 0, 0, 'Player187', '', 'coin', 0, 'Player705'),
(25, '71f66ac1-c861-3de2-8cda-9d0f80277f27', '111', 0, 0, '', '', 'coin', 0, 'Player426');

-- --------------------------------------------------------

--
-- Структура таблицы `md_userItems`
--

CREATE TABLE `md_userItems` (
  `id` int(11) NOT NULL,
  `cost` int(11) NOT NULL,
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `lim` int(11) NOT NULL,
  `shopId` int(11) NOT NULL,
  `stack_data` blob NOT NULL,
  `rating` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Дамп данных таблицы `md_userItems`
--

INSERT INTO `md_userItems` (`id`, `cost`, `name`, `lim`, `shopId`, `stack_data`, `rating`) VALUES
(1, 100, '', 9, 2, 0x00331f8b0800000000000000e36260606260ca4c619060646075ce2fcd2b616462607349cc4d4c4f65606000007dfac1261f0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000, 0),
(21, 10, '', 1, 24, 0x00511f8b0800000000000000e36260606260ca4c611460646075ce2fcd2b61e462602e494ce762604fc92c2ec849ace46060f14bcc4d65602d492d2e3102a9677349cc4d4c4f65606000001a1ce9973f0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000, 0),
(24, 1, '', 99687, 25, 0x00511f8b0800000000000000e36260606260ca4c611460646075ce2fcd2b61e462602e494ce762604fc92c2ec849ace46060f14bcc4d65602d492d2e3102a9677349cc4d4c4f65606000001a1ce9973f0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000, 0),
(25, 1000, '', -4, 25, 0x00511f8b0800000000000000e36260606260ca4c611460646075ce2fcd2b61e462602e494ce762604fc92c2ec849ace46060f14bcc4d65602d492d2e3102a9677349cc4d4c4f65606000001a1ce9973f0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000, 0);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=675;
--
-- AUTO_INCREMENT для таблицы `md_entities`
--
ALTER TABLE `md_entities`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;
--
-- AUTO_INCREMENT для таблицы `md_items`
--
ALTER TABLE `md_items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=284;
--
-- AUTO_INCREMENT для таблицы `md_logs`
--
ALTER TABLE `md_logs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT для таблицы `md_perms`
--
ALTER TABLE `md_perms`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT для таблицы `md_privelegies`
--
ALTER TABLE `md_privelegies`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT для таблицы `md_regions`
--
ALTER TABLE `md_regions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT для таблицы `md_shops`
--
ALTER TABLE `md_shops`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;
--
-- AUTO_INCREMENT для таблицы `md_userItems`
--
ALTER TABLE `md_userItems`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
