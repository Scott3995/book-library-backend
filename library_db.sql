-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 22, 2025 at 10:30 AM
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
-- Database: `library_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `book_copy`
--

CREATE TABLE `book_copy` (
  `id` bigint(20) NOT NULL,
  `isbn` varchar(20) DEFAULT NULL,
  `status` enum('AVAILABLE','BORROWED','LOST') DEFAULT 'AVAILABLE',
  `metadata_isbn` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `book_copy`
--

INSERT INTO `book_copy` (`id`, `isbn`, `status`, `metadata_isbn`) VALUES
(1, NULL, 'BORROWED', '9780140449136'),
(2, NULL, 'BORROWED', '1234567890');

-- --------------------------------------------------------

--
-- Table structure for table `book_metadata`
--

CREATE TABLE `book_metadata` (
  `isbn` varchar(20) NOT NULL,
  `title` varchar(255) NOT NULL,
  `author` varchar(255) DEFAULT NULL,
  `publisher` varchar(255) DEFAULT NULL,
  `publication_year` int(11) DEFAULT NULL,
  `genre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `book_metadata`
--

INSERT INTO `book_metadata` (`isbn`, `title`, `author`, `publisher`, `publication_year`, `genre`) VALUES
('1234567890', 'Test Book', 'John Doe', 'Test Press', 2025, 'Fiction'),
('9780140449136', 'Meditations', 'Marcus Aurelius', 'Penguin Classics', 2025, 'Philosophy');

-- --------------------------------------------------------

--
-- Table structure for table `fine`
--

CREATE TABLE `fine` (
  `id` int(11) NOT NULL,
  `lending_id` int(11) DEFAULT NULL,
  `amount` decimal(10,2) NOT NULL,
  `status` enum('PAID','UNPAID') DEFAULT 'UNPAID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `lending`
--

CREATE TABLE `lending` (
  `id` int(11) NOT NULL,
  `member_id` int(11) DEFAULT NULL,
  `checkout_date` date NOT NULL,
  `due_date` date NOT NULL,
  `return_date` date DEFAULT NULL,
  `copy_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `lending_record`
--

CREATE TABLE `lending_record` (
  `checkout_date` date DEFAULT NULL,
  `due_date` date DEFAULT NULL,
  `fine_amount` double DEFAULT NULL,
  `fine_paid` bit(1) NOT NULL,
  `returned` bit(1) NOT NULL,
  `book_copy_id` bigint(20) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `member_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `librarian`
--

CREATE TABLE `librarian` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `librarian`
--

INSERT INTO `librarian` (`id`, `email`, `password`) VALUES
(1, 'librarian@library.com', '$2a$10$e6FZ2/XpBOeYLJbrHFwrje3efloPMo9aZaR0AyTDW4MRrWLafljyG');

-- --------------------------------------------------------

--
-- Table structure for table `member`
--

CREATE TABLE `member` (
  `id` int(11) NOT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `membership_date` date DEFAULT curdate(),
  `status` varchar(20) DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `member`
--

INSERT INTO `member` (`id`, `full_name`, `email`, `phone`, `membership_date`, `status`, `active`) VALUES
(1, 'Wellington T', 'wellingtonscott95@gmail.com', '0777158751', '2025-06-19', 'ACTIVE', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book_copy`
--
ALTER TABLE `book_copy`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKr6h6jn9lgtf0nsck4iel4mi27` (`isbn`),
  ADD KEY `FKqinxomy4cbr8mgi733gvv2l3r` (`metadata_isbn`);

--
-- Indexes for table `book_metadata`
--
ALTER TABLE `book_metadata`
  ADD PRIMARY KEY (`isbn`);

--
-- Indexes for table `fine`
--
ALTER TABLE `fine`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `lending_id` (`lending_id`);

--
-- Indexes for table `lending`
--
ALTER TABLE `lending`
  ADD PRIMARY KEY (`id`),
  ADD KEY `member_id` (`member_id`),
  ADD KEY `fk_lending_copy` (`copy_id`);

--
-- Indexes for table `lending_record`
--
ALTER TABLE `lending_record`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKoc6q9n4vj8svjmg490tuyqqdk` (`book_copy_id`);

--
-- Indexes for table `librarian`
--
ALTER TABLE `librarian`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKcsxsqgaf8936ljpw57xawwr4e` (`email`);

--
-- Indexes for table `member`
--
ALTER TABLE `member`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `phone` (`phone`),
  ADD UNIQUE KEY `UKmbmcqelty0fbrvxp1q58dn57t` (`email`),
  ADD UNIQUE KEY `UK6ithqvsvrcawbi9dtxu0ttsny` (`phone`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `book_copy`
--
ALTER TABLE `book_copy`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `fine`
--
ALTER TABLE `fine`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `lending`
--
ALTER TABLE `lending`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `lending_record`
--
ALTER TABLE `lending_record`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `librarian`
--
ALTER TABLE `librarian`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `member`
--
ALTER TABLE `member`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `book_copy`
--
ALTER TABLE `book_copy`
  ADD CONSTRAINT `FKqinxomy4cbr8mgi733gvv2l3r` FOREIGN KEY (`metadata_isbn`) REFERENCES `book_metadata` (`isbn`),
  ADD CONSTRAINT `book_copy_ibfk_1` FOREIGN KEY (`isbn`) REFERENCES `book_metadata` (`isbn`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_metadata` FOREIGN KEY (`metadata_isbn`) REFERENCES `book_metadata` (`isbn`);

--
-- Constraints for table `fine`
--
ALTER TABLE `fine`
  ADD CONSTRAINT `fine_ibfk_1` FOREIGN KEY (`lending_id`) REFERENCES `lending` (`id`);

--
-- Constraints for table `lending`
--
ALTER TABLE `lending`
  ADD CONSTRAINT `fk_lending_copy` FOREIGN KEY (`copy_id`) REFERENCES `book_copy` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `lending_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`);

--
-- Constraints for table `lending_record`
--
ALTER TABLE `lending_record`
  ADD CONSTRAINT `FKoc6q9n4vj8svjmg490tuyqqdk` FOREIGN KEY (`book_copy_id`) REFERENCES `book_copy` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
