
CREATE TABLE `Permission` (
  `administratorId` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Authentication service''s administrator unique identifier.',
  `organizationId` bigint NOT NULL COMMENT 'Unique identifier of the organization the administrator is part of.',
  `orgAuthServerId` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'Administrator unique identifier from the authentication server of the organization.',
  `permission` tinyint UNSIGNED NOT NULL COMMENT 'What can or cannot do an organization''s administrator. The permission levels are: - Owner: 3 (higher level) - Manager: 2 - Viewer: 1 (lowest level)',
  `nominatedBy` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'admininistratorId of the owner admininistrator who nominated the current admininistrator.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='What can or cannot do an organization&#39;s administrator.';
