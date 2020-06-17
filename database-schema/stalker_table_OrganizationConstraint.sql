
CREATE TABLE `OrganizationConstraint` (
  `organizationId` bigint NOT NULL COMMENT 'Unique identifier of the organization the administrator is part of.',
  `maxArea` double NOT NULL COMMENT 'Maximum tracking area size, in square meters. This includes both the organization\'s tracking area and thus the place\'s area.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Represents a constraint for an organization.';
