#!/bin/bash

#
# Script file for generating sources
#
# Invoke script with:
# ./generate-sources.sh [arg]
# where [arg] can be:
# - all: all sources (Java for Android, Java for Spring, TypeScript for Angular, Markdown for documentation, HTML for documentation) will be generated
# - java: only Java for Android client source code will be generated
# - spring: only Java for Spring server stub will be generated
# - typescript: only TypeScript for Angular client source code will be generated
# - markdown: only Markdown source code for documentation will be generated
# - html: only HTML source code for documentation will be generated
#

#
# Cleanup functions
#

# Cleans Stalker-App source code
clean_java() {
    rm -rf generated-sources/java/*
    touch generated-sources/java/.gitkeep
}

# Clean Stalker-Backend source code
clean_spring() {
    rm -rf generated-sources/spring/*
    touch generated-sources/spring/.gitkeep
}

# Cleans Stalker-Admin source code
clean_typescript() {
    rm -rf generated-sources/typescript/*
    touch generated-sources/typescript/.gitkeep
}

# Cleans Stalker API documentation (Markdown)
clean_markdown() {
    rm -rf generated-sources/markdown/*
    touch generated-sources/markdown/.gitkeep
}

# Cleans Stalker API documentation (HTML)
clean_html() {
    rm -rf generated-sources/html/*
    touch generated-sources/html/.gitkeep
}

# Cleans all source code
clean_all() {
    $(clean_java)
    $(clean_spring)
    $(clean_typescript)
    $(clean_markdown)
    $(clean_html)
}

#
# Generator functions
#

# Generates Stalker-App source code
generate_java() {
    $(clean_java)
    openapi-generator generate \
    --input-spec stalker.yaml \
    --generator-name java \
    --config openapi-config/java.json \
    --output generated-sources/java
}

# Generates Stalker-Backend source code
generate_spring() {
    $(clean_spring)
    openapi-generator generate \
    --input-spec stalker.yaml \
    --generator-name spring \
    --config openapi-config/spring.json \
    --output generated-sources/spring
}

# Generates Stalker-Admin source code
generate_typescript() {
    $(clean_typescript)
    openapi-generator generate \
    --input-spec stalker.yaml \
    --generator-name typescript-angular \
    --config openapi-config/typescript.json \
    --output generated-sources/typescript
}

# Generates Stalker API documentation (Markdown)
generate_markdown() {
    $(clean_markdown)
    openapi-generator generate \
    --input-spec stalker.yaml \
    --generator-name markdown \
    --output generated-sources/markdown
}

# Generates Stalker API documentation (HTML)
generate_html() {
    $(clean_html)
    openapi-generator generate \
    --input-spec stalker.yaml \
    --generator-name html \
    --config openapi-config/html.json \
    --output generated-sources/html
}

# Generate all
generate_all() {
    generate_java
    generate_spring
    generate_typescript
    generate_markdown
    generate_html
}

#
# Validation
#

# Validates API specification
validate_specification() {
    openapi-generator validate \
    --input-spec stalker.yaml
}

# Main shell script

if [ $# -eq 0 ]; then
    echo "Usage: ./generate-sources.sh [arg]"
    echo "Where [arg] can be:"
    echo "- all: all sources (Java for Android, Java for Spring, TypeScript for Angular, Markdown for documentation, HTML for documentation) will be generated"
    echo "- java: only Java for Android client source code will be generated"
    echo "- spring: only Java for Spring server stub will be generated"
    echo "- typescript: only TypeScript for Angular client source code will be generated"
    echo "- markdown: only Markdown source code for documentation will be generated"
    echo "- html: only HTML source code for documentation will be generated"
    echo "- clean: cleans all previously generated source code and generates none"
else
    validate_specification
fi

if [ "$1" == "all" ]; then
    echo "Generating all source code"
    generate_all
fi

if [ "$1" == "java" ]; then
    echo "Generating java source code"
    generate_java
fi

if [ "$1" == "spring" ]; then
    echo "Generating spring source code"
    generate_spring
fi

if [ "$1" == "typescript" ]; then
    echo "Generating typescript source code"
    generate_typescript
fi

if [ "$1" == "markdown" ]; then
    echo "Generating markdown source code"
    generate_markdown
fi

if [ "$1" == "html" ]; then
    echo "Generating html source code"
    generate_html
fi

if [ "$1" == "clean" ]; then
    echo "Cleaning source code"
    clean_all
fi