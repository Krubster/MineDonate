-- phpMyAdmin SQL Dump
-- version 4.6.4deb1
-- https://www.phpmyadmin.net/
--
-- Хост: localhost:3306
-- Время создания: Авг 18 2017 г., 16:55
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
('player912', 10000, 567, 0, NULL, NULL, 0, 10000);

-- --------------------------------------------------------

--
-- Структура таблицы `md_entities`
--

CREATE TABLE `md_entities` (
  `name` text NOT NULL,
  `data` blob NOT NULL,
  `cost` int(11) NOT NULL,
  `lim` int(11) NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `md_entities`
--

INSERT INTO `md_entities` (`name`, `data`, `cost`, `lim`, `id`) VALUES
('TEST', 0x02d21f8b08000000000000005d53bb6e134114bd6baf1daf89a33c401414143441422b0a28117ec538919d18c7090dcd6477620fde9d596666ed4434f005880ad22291820209098902241e1512a183860f4094fc41b8b3f686c79556ab99b973efb9e79c290258e054fab425bc21f57161f74848f15f6a12d5a591147eece18103c58ad692edc49aaa2200e4f2605789a2e5b5fb60a200f6bab9b9d0a79c4ae6b921d96b5212e8014c33611a69e6b93473c8b1f90ef1865daa98d2847b34bd73fde8e8e8fd5f77ce9c5417231a52ae37238ae8522c97ffedb09466ef8a2010e32ee1fd3f957fec2691e62e0e8454d4bd1387d1a69694f711b805b3ab7c14075884ec0414b230d7115293a02644e08b3137cd72305fd95142469a095e0945ccb5d94626ab12a1e560b64182a09e0e869185fc2a6fe10093bcb915a219ef37c93ea678432b034e9d123de831840536946e0919f85b5babf5b6507a79edc2f943a7f1dc81537529a2dac054553903e4da8bd3f7feff2c58ea506968a598d7a57763665065616133627da15dcdbca16ab1116e5eb1e7739033a235aa3d03338bc630181dc8b785192f9fa09fc6bb578bb98f0f6f5f4cd716ccb4285103ac648363f09aa57eea75bece3f7efb2503f9a91fae16c0f102a2548453c232a7da0d19a79e24bbda454d99de77233c4650ee4ab26c1a6db2b0588d8743a6dd38f289a62d3aa201f6cd64102893d6250b0a1bfc8644057c2b8b24227f5c21eac90885aed0c40c61b8ca7c78797030d162669b484626a221b08d31aa6dc09b33bbb71f250cd8504805587bfdedcda3d2af270e382bc866645c983c88a9f58ab558691126a6c2b6d98e50096de57639c928df9cfee54f9820383b1dab4df6ea22a40a254980e2a18f16ac0d708bfa09079001bb810a1e1fa3897a348ca89c505faa11de4129b7a296101ad30acd58ea898532c9db456ba58e9a3b7154a2d083f5cf87dfc79f9e59b0f007fb3653cc781e7e031072e4b823040000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000, 100, -1, 10);

-- --------------------------------------------------------

--
-- Структура таблицы `md_items`
--

CREATE TABLE `md_items` (
  `name` text NOT NULL,
  `cost` int(11) NOT NULL,
  `lim` int(11) NOT NULL,
  `stack_data` blob NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `md_items`
--

INSERT INTO `md_items` (`name`, `cost`, `lim`, `stack_data`, `id`) VALUES
('', 1, 13, 0x00331f8b0800000000000000e36260606260ca4c616066646075ce2fcd2b716062607349cc4d4c4f65606000005273166f1f0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000, 26);

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
(1, 'default', 'removeOwnedShop'),
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
  `moneyType` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Дамп данных таблицы `md_shops`
--

INSERT INTO `md_shops` (`id`, `owner`, `name`, `lastUpdate`, `isFreezed`, `freezer`, `freezReason`, `moneyType`) VALUES
(0, 'SERVER', 'Server shop', 0, 0, '', '', ''),
(1, 'log_inil', 'SUPER LOW COST SHOP', 0, 0, 'Player443', '', 'coin'),
(3, 'diedindent', 'Industrial Craft [IC] | Build Craft [BC]', 0, 0, 'Player145', '', 'coin');

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
  `stack_data` blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Дамп данных таблицы `md_userItems`
--

INSERT INTO `md_userItems` (`id`, `cost`, `name`, `lim`, `shopId`, `stack_data`) VALUES
(27, 100, '', 10000, 1, 0x00331f8b0800000000000000e36260606260ca4c616066646075ce2fcd2b716062607349cc4d4c4f65606000005273166f1f0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=568;
--
-- AUTO_INCREMENT для таблицы `md_entities`
--
ALTER TABLE `md_entities`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT для таблицы `md_items`
--
ALTER TABLE `md_items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;
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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;
--
-- AUTO_INCREMENT для таблицы `md_userItems`
--
ALTER TABLE `md_userItems`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
