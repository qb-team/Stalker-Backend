-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: db
-- Creato il: Feb 28, 2020 alle 16:58
-- Versione del server: 8.0.19
-- Versione PHP: 7.4.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `PoCStalker`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `accesso autenticato luogo`
--

CREATE TABLE `accesso autenticato luogo` (
  `ingresso` datetime NOT NULL,
  `uscita` datetime NOT NULL,
  `idUtente` varchar(256) NOT NULL,
  `idLuogo` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `accesso autenticato organizzazione`
--

CREATE TABLE `accesso autenticato organizzazione` (
  `ingresso` datetime NOT NULL,
  `uscita` datetime NOT NULL,
  `idUtente` varchar(256) NOT NULL,
  `idOrganizzazione` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `accesso non autenticato luogo`
--

CREATE TABLE `accesso non autenticato luogo` (
  `idAnonimo` double NOT NULL,
  `idLuogo` int NOT NULL,
  `ingresso` datetime NOT NULL,
  `uscita` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `accesso non autenticato organizzazione`
--

CREATE TABLE `accesso non autenticato organizzazione` (
  `ingresso` datetime NOT NULL,
  `uscita` date NOT NULL,
  `idAnonimo` double NOT NULL,
  `dOrganizzazione` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `amministratore`
--

CREATE TABLE `amministratore` (
  `id` int NOT NULL,
  `idLDAP` varchar(256) NOT NULL,
  `token` varchar(256) DEFAULT NULL,
  `scadenzaToken` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `indirizzo`
--

CREATE TABLE `indirizzo` (
  `id` int NOT NULL,
  `via` varchar(256) NOT NULL,
  `civico` smallint NOT NULL,
  `codice postale` mediumint NOT NULL,
  `città` varchar(256) NOT NULL,
  `stato` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `luogo`
--

CREATE TABLE `luogo` (
  `id` int NOT NULL,
  `idOrganizzazione` int NOT NULL,
  `nome` varchar(256) NOT NULL DEFAULT 'luogo senza nome',
  `areaTracciamento` json NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `organizzazione`
--

CREATE TABLE `organizzazione` (
  `id` int UNSIGNED NOT NULL,
  `nome` varchar(256) NOT NULL,
  `descrizione` text NOT NULL,
  `immagine` json DEFAULT NULL,
  `idIndirizzo` int NOT NULL,
  `serverLDAP` varchar(256) DEFAULT NULL,
  `dataCreazione` date NOT NULL,
  `dataModifica` date NOT NULL,
  `perimetro` float NOT NULL,
  `areaTracciamento` json NOT NULL,
  `tipologiaTracciamento` enum('autenticato','anonimo') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `permesso amministratore`
--

CREATE TABLE `permesso amministratore` (
  `idAmministratore` int NOT NULL,
  `idOrganizzazione` int UNSIGNED NOT NULL,
  `privilegio` enum('proprietario','gestore','visualizzatore') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `preferito`
--

CREATE TABLE `preferito` (
  `idUtente` int NOT NULL,
  `idOrganizzazione` int UNSIGNED NOT NULL,
  `dataInserimento` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `utente`
--

CREATE TABLE `utente` (
  `id` int NOT NULL,
  `idLDAP` varchar(256) NOT NULL,
  `token` varchar(256) DEFAULT NULL,
  `scadenzaToken` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `accesso autenticato luogo`
--
ALTER TABLE `accesso autenticato luogo`
  ADD PRIMARY KEY (`idUtente`,`ingresso`);

--
-- Indici per le tabelle `accesso autenticato organizzazione`
--
ALTER TABLE `accesso autenticato organizzazione`
  ADD PRIMARY KEY (`idUtente`,`ingresso`);

--
-- Indici per le tabelle `accesso non autenticato luogo`
--
ALTER TABLE `accesso non autenticato luogo`
  ADD PRIMARY KEY (`idAnonimo`,`ingresso`);

--
-- Indici per le tabelle `accesso non autenticato organizzazione`
--
ALTER TABLE `accesso non autenticato organizzazione`
  ADD PRIMARY KEY (`idAnonimo`,`ingresso`);

--
-- Indici per le tabelle `amministratore`
--
ALTER TABLE `amministratore`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `indirizzo`
--
ALTER TABLE `indirizzo`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `luogo`
--
ALTER TABLE `luogo`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `organizzazione`
--
ALTER TABLE `organizzazione`
  ADD PRIMARY KEY (`id`),
  ADD KEY `chiave esterna` (`idIndirizzo`);

--
-- Indici per le tabelle `permesso amministratore`
--
ALTER TABLE `permesso amministratore`
  ADD PRIMARY KEY (`idAmministratore`,`idOrganizzazione`),
  ADD KEY `relazione con organizzazioni` (`idOrganizzazione`);

--
-- Indici per le tabelle `preferito`
--
ALTER TABLE `preferito`
  ADD PRIMARY KEY (`idUtente`,`idOrganizzazione`),
  ADD KEY `relazione con organizzazione` (`idOrganizzazione`);

--
-- Indici per le tabelle `utente`
--
ALTER TABLE `utente`
  ADD PRIMARY KEY (`id`);

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `organizzazione`
--
ALTER TABLE `organizzazione`
  ADD CONSTRAINT `chiave esterna` FOREIGN KEY (`idIndirizzo`) REFERENCES `indirizzo` (`id`);

--
-- Limiti per la tabella `permesso amministratore`
--
ALTER TABLE `permesso amministratore`
  ADD CONSTRAINT `relazione con amministratori` FOREIGN KEY (`idAmministratore`) REFERENCES `amministratore` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `relazione con organizzazioni` FOREIGN KEY (`idOrganizzazione`) REFERENCES `organizzazione` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `preferito`
--
ALTER TABLE `preferito`
  ADD CONSTRAINT `relazione con organizzazione` FOREIGN KEY (`idOrganizzazione`) REFERENCES `organizzazione` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `relazione utente` FOREIGN KEY (`idUtente`) REFERENCES `utente` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
