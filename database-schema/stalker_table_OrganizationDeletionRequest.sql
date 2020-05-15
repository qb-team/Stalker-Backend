
CREATE TABLE `OrganizationDeletionRequest` (
  `organizationId` bigint NOT NULL COMMENT 'Unique identifier of the organization.',
  `requestReason` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Request reason for the deletion request.',
  `administratorId` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Authentication service''s administrator unique identifier.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Request object for creating a new deletion request for the organization. The request will be analyzed by a Stalker administrator.';
