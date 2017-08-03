-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Хост: 127.0.0.1
-- Время создания: Авг 03 2017 г., 13:53
-- Версия сервера: 5.5.25
-- Версия PHP: 5.3.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- База данных: `minecraft`
--

-- --------------------------------------------------------

--
-- Структура таблицы `md_privelegies`
--

CREATE TABLE IF NOT EXISTS `md_privelegies` (
  `name` text NOT NULL,
  `description` text NOT NULL,
  `pic_url` text NOT NULL,
  `cost` int(11) NOT NULL,
  `time` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `md_privelegies`
--

INSERT INTO `md_privelegies` (`name`, `description`, `pic_url`, `cost`, `time`) VALUES
('VIP', 'JUST VIP\r\nANOTHER STRING\r\n???\r\n/SOME COMMANDS\r\nBITCH\r\n&2COlor', 'http://www.mkyong.com/image/mypic.jpg', 12345, 2592000),
('PREMIUUUM', 'asdasdasddfsdaf\r\nsadg\r\nfag\r\n\r\nafg\r\nf\r\ndgdhgfhf\r\ngh\r\nfdj\r\nfd\r\nhj\r\n', 'http://www.mkyong.com/image/mypic.jpg', 1234565, 10);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
