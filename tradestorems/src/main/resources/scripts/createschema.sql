CREATE TABLE `trade` (
  `trade_id` varchar(50),
  `version` int,
  `counter_party_id` varchar(50) DEFAULT NULL,
  `book_id` varchar(50) DEFAULT NULL,
  `expired` varchar(1) DEFAULT NULL,
  `maturity_date` DATE DEFAULT NULL,
  `created_date` DATE DEFAULT NULL,
  PRIMARY KEY(trade_id, version)
);

