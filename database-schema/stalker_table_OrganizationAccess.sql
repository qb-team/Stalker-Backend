
CREATE TABLE `OrganizationAccess` (
  `id` bigint NOT NULL,
  `entranceTimestamp` datetime NOT NULL COMMENT 'Date and time of the moment in which the user entered the place.',
  `exitTimestamp` datetime DEFAULT NULL COMMENT 'Date and time of the moment in which the user left the place.',
  `organizationId` bigint NOT NULL COMMENT 'Unique identifier of the organization in which the user had access.',
  `orgAuthServerId` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'User unique identifier from the authentication server of the organization.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Access to an organization.';
