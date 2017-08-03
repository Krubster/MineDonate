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
-- Структура таблицы `md_logs`
--

CREATE TABLE IF NOT EXISTS `md_logs` (
  `date` text NOT NULL,
  `bought_by` text NOT NULL,
  `message` text NOT NULL,
  `amount` int(11) NOT NULL,
  `spent` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `md_logs`
--

INSERT INTO `md_logs` (`date`, `bought_by`, `message`, `amount`, `spent`) VALUES
('Wed Aug 02 21:24:39 YEKT 2017', 'ShadowLord', 'bought item - ??=295=10', 1, 1),
('Wed Aug 02 21:24:39 YEKT 2017', 'ShadowLord', 'bought item - stone swrd=272=1', 3, 3),
('Wed Aug 02 21:24:39 YEKT 2017', 'ShadowLord', 'bought item - wheat=296=55', 3, 135),
('Wed Aug 02 21:24:39 YEKT 2017', 'ShadowLord', 'bought item - Grass=2=10', 4, 60),
('Wed Aug 02 21:37:03 YEKT 2017', 'ShadowLord', 'bought entity Horse', 1, 10),
('Wed Aug 02 22:54:04 YEKT 2017', 'ShadowLord', 'bought item - seeds=0=0', 8, 80),
('Wed Aug 02 22:54:04 YEKT 2017', 'ShadowLord', 'bought item - seeds=0=0', 8, 80),
('Wed Aug 02 22:54:04 YEKT 2017', 'ShadowLord', 'bought item - seeds=0=0', 8, 80),
('Wed Aug 02 22:54:04 YEKT 2017', 'ShadowLord', 'bought item - seeds=0=0', 8, 80),
('Wed Aug 02 22:54:04 YEKT 2017', 'ShadowLord', 'bought item - seeds=0=0', 8, 80),
('Wed Aug 02 22:54:04 YEKT 2017', 'ShadowLord', 'bought item - seeds=0=0', 8, 80),
('Wed Aug 02 22:54:04 YEKT 2017', 'ShadowLord', 'bought item - seeds=0=0', 8, 80),
('Wed Aug 02 22:54:04 YEKT 2017', 'ShadowLord', 'bought item - seeds=0=0', 8, 80),
('Wed Aug 02 22:54:04 YEKT 2017', 'ShadowLord', 'bought item - seeds=0=0', 8, 80),
('Wed Aug 02 22:54:04 YEKT 2017', 'ShadowLord', 'bought item - seeds=0=0', 8, 80),
('Wed Aug 02 22:54:04 YEKT 2017', 'ShadowLord', 'bought item - seeds=0=0', 8, 80),
('Wed Aug 02 22:54:04 YEKT 2017', 'ShadowLord', 'bought item - seeds=0=0', 8, 80),
('Wed Aug 02 22:54:04 YEKT 2017', 'ShadowLord', 'bought item - seeds=0=0', 8, 80),
('Wed Aug 02 23:43:19 YEKT 2017', 'ShadowLord', 'bought item - test2=0=0', 1, 40),
('Wed Aug 02 23:43:19 YEKT 2017', 'ShadowLord', 'bought item - test2=0=0', 1, 40),
('Wed Aug 02 23:55:14 YEKT 2017', 'ShadowLord', 'bought item - test2=0=1', 5, 200),
('Wed Aug 02 23:55:14 YEKT 2017', 'ShadowLord', 'bought item - test=0=64', 1, 10);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
