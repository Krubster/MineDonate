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
-- Структура таблицы `md_shops`
--

CREATE TABLE IF NOT EXISTS `md_shops` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `owner` text NOT NULL,
  `name` text NOT NULL,
  `buysCount` int(11) NOT NULL,
  `costCircInMonth` int(11) NOT NULL,
  `buysCountInMonth` int(11) NOT NULL,
  `lastUpdate` int(11) NOT NULL,
  `isFreezed` tinyint(1) NOT NULL,
  `freezer` text NOT NULL,
  `freezReason` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Дамп данных таблицы `md_shops`
--

INSERT INTO `md_shops` (`id`, `owner`, `name`, `buysCount`, `costCircInMonth`, `buysCountInMonth`, `lastUpdate`, `isFreezed`, `freezer`, `freezReason`) VALUES
(0, 'SERVER', 'Server shop', 0, 0, 0, 0, 0, '', ''),
(1, 'log_inil', 'SUPER LOW COST SHOP', 0, 0, 0, 0, 0, '', ''),
(3, 'laclen', 'Industrial Craft [IC] | Build Craft [BC]', 0, 0, 0, 0, 1, 'log', 'lie');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
