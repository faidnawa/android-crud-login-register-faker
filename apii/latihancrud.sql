-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 22 Apr 2017 pada 05.14
-- Versi Server: 10.1.21-MariaDB
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `latihancrud`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbl_mhas`
--

CREATE TABLE `tbl_mhas` (
  `id` int(10) NOT NULL,
  `nama` varchar(50) DEFAULT NULL,
  `nim` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tbl_mhas`
--

INSERT INTO `tbl_mhas` (`id`, `nama`, `nim`) VALUES
(3, 'faidl fahruddin nawa', '141240000273'),
(4, 'naww', '15152'),
(9, 'fy', 'jh'),
(10, 'fy', 'jhz'),
(11, 'asa', 'asa'),
(12, 'sa', 'saasdas'),
(14, 'fau', '133'),
(15, 'ghh', 'yyy'),
(16, 'qwe', 'qwe'),
(17, 'ttt', 'ttt'),
(18, 'iii', 'iii'),
(19, 'bnnx', 'bnns'),
(20, 'bzbbz', 'nnnz'),
(22, 'hh', 'ji'),
(23, 'tti', 'tti'),
(24, 'a', 'a'),
(27, 'Ms. Eleanora Kling III', '370862767851657'),
(28, 'Aylin Spinka', '2506068273569451'),
(29, 'Mrs. Rowena Heaney MD', '4716410104100'),
(30, 'Ms. Kacie Hane Sr.', '6011310586680875'),
(31, 'Evangeline Baumbach', '5171786867013092'),
(32, 'Haven Harris', '4024007153626'),
(33, 'Clarissa Abernathy', '5462680309729340'),
(34, 'Kiara Bergstrom', '2720964205341995'),
(35, 'Catherine Gulgowski II', '6011402152430759'),
(36, 'Tristin Bailey', '4532137458525'),
(37, 'Javier Reilly', '4556578628437811'),
(38, 'Dr. Korbin Metz', '4532548579620'),
(39, 'Camilla Mayert II', '5106596356864362'),
(40, 'Clementine Baumbach DDS', '5410620725332645'),
(41, 'Electa Haley', '4004985236636296'),
(42, 'Prof. Laurel Sporeryyy', '4556337214482446'),
(43, 'Heidi Howe Jr.', '342811518350480'),
(44, 'Ofaid', '34680777729702'),
(46, 'faidl11', 'nawa'),
(48, 'faidlnawaa', '123'),
(49, 'Sterling Herman', '4556468300113'),
(50, 'Prof. Cicero Schoen Sr.', '4532527210718'),
(51, 'Isabelle Haley', '4916568697810'),
(52, 'Dr. Otha McClure', '4024007102573'),
(53, 'Henriette Greenfelder I', '6011240862109325'),
(54, 'Roxane Willms', '2221748664897728'),
(55, 'Maybell Bednar', '4716356579661'),
(56, 'Ivah Eichmann', '4556895638085'),
(57, 'Jamie Murazik Sr.', '2221283030740022'),
(58, 'Duncan Simonis', '2565446627998758'),
(59, 'Yadira Purdy II', '5448177185329401'),
(60, 'Raphaelle Hahn', '4716953178044464'),
(61, 'Mr. Emerald Pacocha PhD', '4532415100377'),
(62, 'Teresa Little', '2536188339672802'),
(63, 'Brennan Bartell', '5536233341961299'),
(64, 'Abraham Rohan', '2683567483201693'),
(65, 'Prof. Hilton Champlin', '5282743183565095'),
(66, 'Domenica Lockman DVM', '5459324442484126'),
(68, 'faid fahruddin nawa', '123'),
(69, 'faidl tambah data', '123');

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `unique_id` varchar(23) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `encrypted_password` varchar(80) NOT NULL,
  `salt` varchar(10) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `users`
--

INSERT INTO `users` (`id`, `unique_id`, `name`, `email`, `encrypted_password`, `salt`, `created_at`, `updated_at`) VALUES
(7, '58e345f47b2ff9.13055546', 'mashudi', 'mas@mas.com', 'rcjOUpHNN000rS4TNLhYbj72QphlYTQ2ZWNlMDY5', 'ea46ece069', '2017-04-04 14:06:28', NULL),
(37, '58fa652972cca3.71378141', 'faidl', 'faidnawa@gmail.com', 'GGRjyGtIkGIkahNvcDHVQ0h+sZoyYjViYjU0Mzky', '2b5bb54392', '2017-04-22 03:01:45', NULL),
(38, '58fa77fbb5e8a2.51934318', 'faidl fahruddin nawa', 'faid@faid.com', 'YNg5La89mjYThZVe8ZUyMKCnordhNGVkNDljN2E1', 'a4ed49c7a5', '2017-04-22 04:22:03', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_mhas`
--
ALTER TABLE `tbl_mhas`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_id` (`unique_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_mhas`
--
ALTER TABLE `tbl_mhas`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
