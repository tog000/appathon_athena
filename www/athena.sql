-- phpMyAdmin SQL Dump
-- version 4.0.6deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 09, 2014 at 04:55 PM
-- Server version: 5.5.35-0ubuntu0.13.10.2
-- PHP Version: 5.5.3-1ubuntu2.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `athena`
--

-- --------------------------------------------------------

--
-- Table structure for table `achievement`
--

CREATE TABLE IF NOT EXISTS `achievement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `icon` varchar(5) NOT NULL,
  `color` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `achievement`
--

INSERT INTO `achievement` (`id`, `name`, `description`, `icon`, `color`) VALUES
(1, 'Slow and steady', 'Welcome! Keep answering questions for more achievements and XP!', 'f005', '#119BF7'),
(2, 'You are a rock star!', '2 correctly answered questions! You rock!', 'f01b', '#ff2222'),
(3, 'Fourth is a charm', 'You must be on a roll. Four correct questions', 'f0ba', '#6AD3E8'),
(4, 'Bragging rights', 'You are ahead of the pack. Six correct questions.', 'f0a7', '#C02A43'),
(5, 'To infinity and beyond', 'Who can stop you now? You answered 8 questions correctly!', 'f050', '#F7D009'),
(7, 'Appathon Bonus', 'Stayed all night programming? Take a badge, it''s on us.', 'f090', '#9933CC'),
(8, 'GitHub FTW', 'Use GitHub much? We do too! Take a badge!', 'f0ee', '#0099CC'),
(9, 'Starving Student', 'Feel rich thanks to this badge!', 'f0e3', '#FF8800'),
(10, 'Patience', 'You''ve been playing this for a while!', 'f046', '#B6DB49');

-- --------------------------------------------------------

--
-- Table structure for table `question`
--

CREATE TABLE IF NOT EXISTS `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(255) NOT NULL,
  `experience` int(11) NOT NULL,
  `image` varchar(255) NOT NULL,
  `level` int(11) NOT NULL,
  `class_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=30 ;

--
-- Dumping data for table `question`
--

INSERT INTO `question` (`id`, `question`, `experience`, `image`, `level`, `class_id`) VALUES
(9, 'What period of time is covered by the Middle Ages?', 500, 'http://klimtlover.files.wordpress.com/2012/10/interior-san-vitale.jpg', 0, 0),
(11, 'What event signaled the start of the Middle Ages?', 350, 'http://www.cep.unt.edu/show/028.jpg', 0, 0),
(12, 'What is the difference between the Middle Ages and Medieval Times?', 1000, 'http://upload.wikimedia.org/wikipedia/commons/7/78/Church_Heart_of_the_Andes.jpg', 0, 0),
(13, 'Does the Dark Ages refer to the first half or second half of the Middle Ages?', 200, 'http://www.cep.unt.edu/show/garden.jpg', 0, 0),
(14, 'What leader of the Franks was crowned Holy Roman Emperor in the year 800 AD and is also considered the father of the French and German monarchies?', 300, 'http://ritaroberts.files.wordpress.com/2013/03/a-medieval-kitchen-scene.jpg', 0, 0),
(15, 'What document did the King of England sign that said the people had rights and that the king was not above the law?', 400, 'http://thumbs.dreamstime.com/z/medieval-art-detail-stained-glass-depicting-muses-music-33604828.jpg', 0, 0),
(16, 'What was the name of the disease that killed nearly half the people of Europe?', 100, 'http://static.tumblr.com/8sg3ums/e7blyyc22/medieval-knight-costume-1.jpg', 0, 0),
(17, 'What were the wars between the Holy Roman Empire and the Muslims called?', 250, 'https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcS8aTblOpEjdHgxLziGRrVSVyvbP6_lK6yf4DpE30aLc9TWF1W9', 0, 0),
(18, 'What Scandinavian peoples began to invade northern Europe in 835?', 300, 'https://www.tcd.ie/History_of_Art/assets/img/ug/amhc.jpg', 0, 0),
(19, 'Who invented the printing press in Europe?', 500, 'http://klimtlover.files.wordpress.com/2012/10/virgin-and-child-mt-sinai.jpg', 0, 0),
(20, 'What was the Byzantine capital city called throughout the Middle Ages?', 200, 'http://excellentworlds.com/data_images/countries/constantinople/constantinople-06.jpg', 0, 0),
(21, 'Who did the Barbarian King Clovis rule?', 400, 'http://www.lalicorne.nl/aagentry/clovis_files/image003.jpg', 0, 0),
(22, 'Which of these tribes made successful advances into England in the fifth century?', 500, 'http://www.wizards.com/dnd/images/UnA_Gallery/79146.jpg', 0, 0),
(23, 'In what year did the prophet Muhammad led a hijrah from Mecca to Medina to escape persecution?', 300, 'http://upload.wikimedia.org/wikipedia/commons/2/20/Mohammed_receiving_revelation_from_the_angel_Gabriel.jpg', 0, 0),
(24, 'In the eighth century, Charlemagne built an empire that covered, among other places, parts of modern day France, Italy and which of these countries?', 100, 'http://freepages.family.rootsweb.ancestry.com/~mcgee411/GHTOUT/c6-charlemagne3.jpg', 0, 0),
(25, 'What was sacked at Lindisfarne in 795?', 250, 'http://31.media.tumblr.com/123439f2603620ac356383ed42bbcb46/tumblr_mf6qxvCYHz1rh31i2o1_500.jpg', 0, 0),
(26, 'Only one king of England ever earned the appellation "Great." Who was he?', 400, 'http://static.guim.co.uk/sys-images/Guardian/About/General/2013/2/5/1360081337537/Alfred-the-Great-010.jpg', 0, 0),
(27, 'In the tenth century, Otto, King of Germany, was crowned Emperor Otto I by Pope John XII. What empire did Otto rule?', 100, 'http://upload.wikimedia.org/wikipedia/commons/1/11/Otto_I_Manuscriptum_Mediolanense_c_1200.jpg', 0, 0),
(28, 'Historians divide up the Middle Ages into three sub-eras. Which era is sandwiched between the Early Middle Ages and the Late Middle Ages?', 300, 'http://videogam.in/pages/files/The_Middle_Ages/middleages.jpg', 0, 0),
(29, 'What was William of Normandy''s first battle upon landing on English soil?', 600, 'http://www.britishbattles.com/norman-conquest/hastings.jpg', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `question_answer`
--

CREATE TABLE IF NOT EXISTS `question_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_question` int(11) NOT NULL,
  `text` varchar(255) NOT NULL,
  `correct` int(1) NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=101 ;

--
-- Dumping data for table `question_answer`
--

INSERT INTO `question_answer` (`id`, `id_question`, `text`, `correct`, `time`) VALUES
(5, 6, 'Blue', 0, '0000-00-00 00:00:00'),
(6, 6, 'Green', 0, '0000-00-00 00:00:00'),
(7, 6, 'Orange', 1, '0000-00-00 00:00:00'),
(8, 6, 'Purple', 0, '0000-00-00 00:00:00'),
(9, 7, 'Me', 0, '0000-00-00 00:00:00'),
(10, 7, 'a tree', 0, '0000-00-00 00:00:00'),
(11, 7, 'The empire state', 0, '0000-00-00 00:00:00'),
(12, 7, 'mt everest', 1, '0000-00-00 00:00:00'),
(13, 8, 'Me', 0, '0000-00-00 00:00:00'),
(14, 8, 'an elephant', 0, '0000-00-00 00:00:00'),
(15, 8, 'your mom', 1, '0000-00-00 00:00:00'),
(16, 8, 'a giraffe', 0, '0000-00-00 00:00:00'),
(17, 9, '1000 BC to 100 BC', 0, '0000-00-00 00:00:00'),
(18, 9, '500 BC to 500 AD', 0, '0000-00-00 00:00:00'),
(19, 9, '1 AD to 1500 AD', 0, '0000-00-00 00:00:00'),
(20, 9, '500 AD to 1500 AD', 1, '0000-00-00 00:00:00'),
(21, 10, '1112', 0, '0000-00-00 00:00:00'),
(22, 10, '3', 0, '0000-00-00 00:00:00'),
(23, 10, '3', 1, '0000-00-00 00:00:00'),
(24, 10, '4', 0, '0000-00-00 00:00:00'),
(25, 11, 'The attack of the Vikings', 0, '0000-00-00 00:00:00'),
(26, 11, 'The fall of the Roman Empire', 1, '0000-00-00 00:00:00'),
(27, 11, 'The crowning of Charlemagne', 0, '0000-00-00 00:00:00'),
(28, 11, 'The fall of Constantinople to the Ottoman Empire', 0, '0000-00-00 00:00:00'),
(29, 12, 'The Middle Ages took place first', 0, '0000-00-00 00:00:00'),
(30, 12, 'The Medieval Times took place first', 0, '0000-00-00 00:00:00'),
(31, 12, '	The Medieval Times only refers to the Vikings', 0, '0000-00-00 00:00:00'),
(32, 12, 'No difference', 1, '0000-00-00 00:00:00'),
(33, 13, 'First half', 1, '0000-00-00 00:00:00'),
(34, 13, 'Second half', 0, '0000-00-00 00:00:00'),
(35, 13, 'Neither', 0, '0000-00-00 00:00:00'),
(36, 13, 'Both', 0, '0000-00-00 00:00:00'),
(37, 14, 'Richard the Lionheart', 0, '0000-00-00 00:00:00'),
(38, 14, 'Clovis', 0, '0000-00-00 00:00:00'),
(39, 14, 'Charlemagne', 1, '0000-00-00 00:00:00'),
(40, 14, 'King John', 0, '0000-00-00 00:00:00'),
(41, 15, 'The Charter of Liberties', 0, '0000-00-00 00:00:00'),
(42, 15, 'The Constitution', 0, '0000-00-00 00:00:00'),
(43, 15, 'The Magna Carta', 1, '0000-00-00 00:00:00'),
(44, 15, 'The Cartulary', 0, '0000-00-00 00:00:00'),
(45, 16, 'The Black Death', 1, '0000-00-00 00:00:00'),
(46, 16, 'Influenza', 0, '0000-00-00 00:00:00'),
(47, 16, 'Small Pox', 0, '0000-00-00 00:00:00'),
(48, 16, 'Typhus', 0, '0000-00-00 00:00:00'),
(49, 17, 'War of the Roses', 0, '0000-00-00 00:00:00'),
(50, 17, 'The Crusades', 1, '0000-00-00 00:00:00'),
(51, 17, 'The Ottoman Wars', 0, '0000-00-00 00:00:00'),
(52, 17, 'The Islamic Wars', 0, '0000-00-00 00:00:00'),
(53, 18, 'The Huns', 0, '0000-00-00 00:00:00'),
(54, 18, 'The Tartars', 0, '0000-00-00 00:00:00'),
(55, 18, 'The Vikings', 1, '0000-00-00 00:00:00'),
(56, 18, 'The Mongols', 0, '0000-00-00 00:00:00'),
(57, 19, 'Charlemagne', 0, '0000-00-00 00:00:00'),
(58, 19, 'Alfred the Great', 0, '0000-00-00 00:00:00'),
(59, 19, 'Johannes Gutenberg', 1, '0000-00-00 00:00:00'),
(60, 19, 'Leonardo da Vinci', 0, '0000-00-00 00:00:00'),
(61, 20, 'Adrianople', 0, '0000-00-00 00:00:00'),
(62, 20, 'Constantinople', 1, '0000-00-00 00:00:00'),
(63, 20, 'Salonika', 0, '0000-00-00 00:00:00'),
(64, 20, 'Rome', 0, '0000-00-00 00:00:00'),
(65, 21, 'The Kinks', 0, '0000-00-00 00:00:00'),
(66, 21, 'The Franks', 1, '0000-00-00 00:00:00'),
(67, 21, 'The Picts', 0, '0000-00-00 00:00:00'),
(68, 21, 'The Visigoths', 0, '0000-00-00 00:00:00'),
(69, 22, 'The Avars', 0, '0000-00-00 00:00:00'),
(70, 22, 'The Romans', 0, '0000-00-00 00:00:00'),
(71, 22, 'The Huns', 0, '0000-00-00 00:00:00'),
(72, 22, 'The Saxons', 1, '0000-00-00 00:00:00'),
(73, 23, '410', 0, '0000-00-00 00:00:00'),
(74, 23, '622', 1, '0000-00-00 00:00:00'),
(75, 23, '950', 0, '0000-00-00 00:00:00'),
(76, 23, '1066', 0, '0000-00-00 00:00:00'),
(77, 24, 'Germany', 1, '0000-00-00 00:00:00'),
(78, 24, 'Norway', 0, '0000-00-00 00:00:00'),
(79, 24, 'Spain', 0, '0000-00-00 00:00:00'),
(80, 24, 'Brittain', 0, '0000-00-00 00:00:00'),
(81, 25, 'A library', 0, '0000-00-00 00:00:00'),
(82, 25, 'An abbey', 1, '0000-00-00 00:00:00'),
(83, 25, 'A castle', 0, '0000-00-00 00:00:00'),
(84, 25, 'A university', 0, '0000-00-00 00:00:00'),
(85, 26, 'King Aethelred', 0, '0000-00-00 00:00:00'),
(86, 26, 'King Authur', 0, '0000-00-00 00:00:00'),
(87, 26, 'King Alfred', 1, '0000-00-00 00:00:00'),
(88, 26, 'King Edmund II', 0, '0000-00-00 00:00:00'),
(89, 27, 'The Byzantine Empire', 0, '0000-00-00 00:00:00'),
(90, 27, 'The Carolingian Empire', 0, '0000-00-00 00:00:00'),
(91, 27, 'The Holy Roman Empire', 1, '0000-00-00 00:00:00'),
(92, 27, 'The Ottoman Empire', 0, '0000-00-00 00:00:00'),
(93, 28, 'The Boring Middle Ages', 0, '0000-00-00 00:00:00'),
(94, 28, 'The Intense Middle Ages', 0, '0000-00-00 00:00:00'),
(95, 28, 'The Middle Middle Ages', 0, '0000-00-00 00:00:00'),
(96, 28, 'The High Middle Ages', 1, '0000-00-00 00:00:00'),
(97, 29, 'The Battle of Fulford Gate', 0, '0000-00-00 00:00:00'),
(98, 29, 'The Battle of the Brave', 0, '0000-00-00 00:00:00'),
(99, 29, 'The Battle of Hastings', 1, '0000-00-00 00:00:00'),
(100, 29, 'The Battle of Stirling Bridge', 0, '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `avatar` varchar(255) NOT NULL,
  `experience` int(11) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `name`, `avatar`, `experience`, `date`) VALUES
('205a6f6f6274c91d', 'nscornia', 'http://www.gravatar.com/avatar/205a6f6f6274c91d?d=retro&f=y&s=400', 800, '2014-03-09 16:54:45'),
('5bd3b8def1b35d3d', 'mariannabudnikova', 'http://www.gravatar.com/avatar/5bd3b8def1b35d3d?d=retro&f=y&s=400', 18600, '2014-03-09 16:54:27'),
('633e6bbfe76cf611', 'gabriel', 'http://www.gravatar.com/avatar/633e6bbfe76cf611?d=retro&f=y&s=400', 19702, '2014-03-09 16:42:56'),
('d173264355dad0a2', 'recornia', 'http://www.gravatar.com/avatar/d173264355dad0a2?d=retro&f=y&s=400', 3550, '2014-03-09 16:55:50'),
('d9658fe9b231938b', 'nscornia', 'http://www.gravatar.com/avatar/d9658fe9b231938b?d=retro&f=y&s=400', 73300, '2014-03-09 16:54:31');

-- --------------------------------------------------------

--
-- Table structure for table `user_achievement`
--

CREATE TABLE IF NOT EXISTS `user_achievement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` varchar(255) NOT NULL,
  `id_achievement` int(11) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

--
-- Dumping data for table `user_achievement`
--

INSERT INTO `user_achievement` (`id`, `id_user`, `id_achievement`, `date`) VALUES
(1, '205a6f6f6274c91d', 1, '2014-03-09 16:46:43'),
(2, '205a6f6f6274c91d', 2, '2014-03-09 16:46:49'),
(3, '5bd3b8def1b35d3d', 1, '2014-03-09 16:48:27'),
(4, '5bd3b8def1b35d3d', 2, '2014-03-09 16:49:12'),
(5, '5bd3b8def1b35d3d', 3, '2014-03-09 16:49:52'),
(6, '5bd3b8def1b35d3d', 4, '2014-03-09 16:50:10'),
(7, '5bd3b8def1b35d3d', 5, '2014-03-09 16:50:32'),
(8, '5bd3b8def1b35d3d', 7, '2014-03-09 16:51:24'),
(9, '205a6f6f6274c91d', 3, '2014-03-09 16:54:09'),
(10, 'd9658fe9b231938b', 1, '2014-03-09 16:54:31'),
(11, 'd173264355dad0a2', 1, '2014-03-09 16:55:31'),
(12, 'd173264355dad0a2', 2, '2014-03-09 16:55:35'),
(13, 'd173264355dad0a2', 3, '2014-03-09 16:55:41'),
(14, 'd173264355dad0a2', 4, '2014-03-09 16:55:47');

-- --------------------------------------------------------

--
-- Table structure for table `user_question_answer`
--

CREATE TABLE IF NOT EXISTS `user_question_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` varchar(255) NOT NULL,
  `id_question` int(11) NOT NULL,
  `answer` int(11) NOT NULL,
  `correct` int(11) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=35 ;

--
-- Dumping data for table `user_question_answer`
--

INSERT INTO `user_question_answer` (`id`, `id_user`, `id_question`, `answer`, `correct`, `date`) VALUES
(1, '205a6f6f6274c91d', 27, 2, 1, '2014-03-09 16:46:43'),
(2, '205a6f6f6274c91d', 13, 0, 1, '2014-03-09 16:46:49'),
(3, '205a6f6f6274c91d', 21, 1, 1, '2014-03-09 16:46:54'),
(4, '5bd3b8def1b35d3d', 16, 0, 1, '2014-03-09 16:48:27'),
(5, '5bd3b8def1b35d3d', 26, 3, 0, '2014-03-09 16:48:56'),
(6, '5bd3b8def1b35d3d', 13, 3, 0, '2014-03-09 16:49:01'),
(7, '5bd3b8def1b35d3d', 28, 2, 0, '2014-03-09 16:49:06'),
(8, '5bd3b8def1b35d3d', 23, 3, 0, '2014-03-09 16:49:09'),
(9, '5bd3b8def1b35d3d', 15, 2, 1, '2014-03-09 16:49:12'),
(10, '5bd3b8def1b35d3d', 11, 2, 0, '2014-03-09 16:49:22'),
(11, '5bd3b8def1b35d3d', 27, 2, 1, '2014-03-09 16:49:33'),
(12, '5bd3b8def1b35d3d', 13, 1, 0, '2014-03-09 16:49:44'),
(13, '5bd3b8def1b35d3d', 29, 3, 0, '2014-03-09 16:49:49'),
(14, '5bd3b8def1b35d3d', 24, 0, 1, '2014-03-09 16:49:52'),
(15, '5bd3b8def1b35d3d', 21, 2, 0, '2014-03-09 16:49:58'),
(16, '5bd3b8def1b35d3d', 19, 2, 1, '2014-03-09 16:50:02'),
(17, '5bd3b8def1b35d3d', 14, 2, 1, '2014-03-09 16:50:10'),
(18, '5bd3b8def1b35d3d', 17, 1, 1, '2014-03-09 16:50:21'),
(19, '5bd3b8def1b35d3d', 25, 3, 0, '2014-03-09 16:50:29'),
(20, '5bd3b8def1b35d3d', 22, 3, 1, '2014-03-09 16:50:32'),
(21, '5bd3b8def1b35d3d', 25, 0, 0, '2014-03-09 16:51:03'),
(22, '5bd3b8def1b35d3d', 9, 3, 1, '2014-03-09 16:51:06'),
(23, '5bd3b8def1b35d3d', 21, 3, 0, '2014-03-09 16:51:22'),
(24, '5bd3b8def1b35d3d', 25, 1, 1, '2014-03-09 16:51:24'),
(25, '205a6f6f6274c91d', 16, 0, 1, '2014-03-09 16:54:09'),
(26, 'd9658fe9b231938b', 26, 0, 0, '2014-03-09 16:54:28'),
(27, 'd9658fe9b231938b', 22, 3, 1, '2014-03-09 16:54:31'),
(28, 'd173264355dad0a2', 17, 1, 1, '2014-03-09 16:55:31'),
(29, 'd173264355dad0a2', 20, 1, 1, '2014-03-09 16:55:35'),
(30, 'd173264355dad0a2', 15, 2, 1, '2014-03-09 16:55:38'),
(31, 'd173264355dad0a2', 22, 3, 1, '2014-03-09 16:55:41'),
(32, 'd173264355dad0a2', 25, 1, 1, '2014-03-09 16:55:44'),
(33, 'd173264355dad0a2', 14, 2, 1, '2014-03-09 16:55:47'),
(34, 'd173264355dad0a2', 27, 2, 1, '2014-03-09 16:55:50');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
