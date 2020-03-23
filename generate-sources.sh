#
# Script file for generating sources
#

#
# Clean old generated files
#
rm -r generated-sources/android/*
touch generated-sources/android/.gitkeep
rm -r generated-sources/spring/*
touch generated-sources/spring/.gitkeep
rm -r generated-sources/typescript/*
touch generated-sources/typescript/.gitkeep

#
# Validate API specification
#
openapi-generator validate \
--input-spec swagger.yaml

#
# Generate Stalker-App source code
#
openapi-generator generate \
--input-spec swagger.yaml \
--generator-name android \
--config openapi-config/android.json \
--output generated-sources/android

#
# Generate Stalker-Backend source code
#
openapi-generator generate \
--input-spec swagger.yaml \
--generator-name spring \
--config openapi-config/spring.json \
--output generated-sources/spring

#
# Generate Stalker-Admin source code
#
openapi-generator generate \
--input-spec swagger.yaml \
--generator-name typescript-angular \
--config openapi-config/typescript.json \
--output generated-sources/typescript