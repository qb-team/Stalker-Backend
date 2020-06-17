
CREATE TABLE `Organization` (
  `id` bigint NOT NULL COMMENT 'Unique identifier for an organization.',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Name of the organization.',
  `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'Small description of what the organization does.',
  `image` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'Image/logo for the organization which gets shown on the application.',
  `street` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'The street where the organization is located.',
  `number` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'The number in the street where the organization is located.',
  `postCode` int UNSIGNED DEFAULT NULL COMMENT 'The postcode where the organization is located.',
  `city` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'The city where the organization is located.',
  `country` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'The country where the organization is located.',
  `authenticationServerURL` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'URL or IP address of the authentication server of the organization. If it''s required a specific port or protocol it must be specified. Needed only if trackingMethod is set to authenticated.',
  `creationDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'When the organization was added to the system.',
  `lastChangeDate` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'When the organization parameters were last changed.',
  `trackingArea` json NOT NULL COMMENT 'Area subjected to movement tracking of people. It is a collection of (longitude, latitude) pairs consisting in a polygon. The string is expressed in JSON format.',
  `trackingMode` enum('authenticated','anonymous') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'How an user who added to its favorites the organization can be tracked inside the organization''s trackingArea and its places.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Subject interested in tracking people\'s presence inside its own places, in either an anonymous or authenticated way.';
