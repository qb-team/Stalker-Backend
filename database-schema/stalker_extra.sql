

ALTER TABLE `Favorite`
  ADD PRIMARY KEY (`userId`,`organizationId`),
  ADD KEY `organizationId` (`organizationId`);

ALTER TABLE `Organization`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `OrganizationAccess`
  ADD PRIMARY KEY (`id`),
  ADD KEY `organizationId` (`organizationId`);

ALTER TABLE `Permission`
  ADD PRIMARY KEY (`administratorId`,`organizationId`),
  ADD KEY `organizationId` (`organizationId`);

ALTER TABLE `Place`
  ADD PRIMARY KEY (`id`),
  ADD KEY `organizationId` (`organizationId`);

ALTER TABLE `PlaceAccess`
  ADD PRIMARY KEY (`id`),
  ADD KEY `placeId` (`placeId`);


ALTER TABLE `Organization`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Unique identifier for an organization.';

ALTER TABLE `OrganizationAccess`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

ALTER TABLE `Place`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Unique identifier for a place of an organization.';

ALTER TABLE `PlaceAccess`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;


ALTER TABLE `Favorite`
  ADD CONSTRAINT `Favorite_ibfk_1` FOREIGN KEY (`organizationId`) REFERENCES `Organization` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `OrganizationAccess`
  ADD CONSTRAINT `OrganizationAccess_ibfk_1` FOREIGN KEY (`organizationId`) REFERENCES `Organization` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `Permission`
  ADD CONSTRAINT `Permission_ibfk_1` FOREIGN KEY (`organizationId`) REFERENCES `Organization` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `Place`
  ADD CONSTRAINT `Place_ibfk_1` FOREIGN KEY (`organizationId`) REFERENCES `Organization` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `PlaceAccess`
  ADD CONSTRAINT `PlaceAccess_ibfk_1` FOREIGN KEY (`placeId`) REFERENCES `Place` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
