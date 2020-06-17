
CREATE TABLE `Favorite` (
  `userId` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Authentication service''s user unique identifier.',
  `organizationId` bigint NOT NULL COMMENT 'Unique identifier of the organization the user sets as favorite.',
  `orgAuthServerId` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'User unique identifier from the authentication server of the organization.',
  `creationDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'When the favorite was added by the user.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Link between the user and the organization: only the organization which users set to be their favorite can track their movements.';
