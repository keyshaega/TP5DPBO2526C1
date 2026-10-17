-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 17, 2026 at 02:53 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_people`
--

-- --------------------------------------------------------

--
-- Table structure for table `person`
--

CREATE TABLE `person` (
  `NIM` varchar(7) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `tahun_lahir` int(11) NOT NULL,
  `kategori` varchar(255) NOT NULL,
  `program_studi` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `person`
--

INSERT INTO `person` (`NIM`, `nama`, `tahun_lahir`, `kategori`, `program_studi`) VALUES
('2501234', 'Adzkia Putri', 2005, 'Mahasiswa Aktif', 'Matematika\r\n '),
('2501235', 'Rizky Ramadhan', 2004, 'Mahasiswa Aktif', 'Pendidikan Ilmu Komputer'),
('2501236', 'Nadia Safitri', 2005, 'Mahasiswa Non Aktif', 'Fisika'),
('2501237', 'Fajar Maulana', 2003, 'Alumni', 'Pendidikan Ilmu Komputer'),
('2501238', 'Salsabila Zahra', 2004, 'Alumni', 'Ilmu Komputer');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`NIM`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
