
CREATE TABLE `User` (
  `userId` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Authentication service''s user unique identifier.',
  `organizationId` bigint NOT NULL COMMENT 'Unique identifier of the organization the orgAuthServerId is of.',
  `orgAuthServerId` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'User unique identifier from the authentication server of the organization.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
