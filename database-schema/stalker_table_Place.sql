
CREATE TABLE `Place` (
  `id` bigint NOT NULL COMMENT 'Unique identifier for a place of an organization.',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'Name describing the place. If not set by user it gets automatically filled.',
  `organizationId` bigint NOT NULL COMMENT 'Unique identifier of the organization the place is part of.',
  `trackingArea` json NOT NULL COMMENT 'rea subjected to movement tracking of people. It is a collection of (longitude, latitude) pairs consisting in a polygon. The string is expressed in JSON format.     '
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Area of an organization subjected to tracking.';
