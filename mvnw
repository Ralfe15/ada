#!/bin/sh
# Maven Wrapper script
# Downloads and runs Maven if not installed locally

set -e

MAVEN_PROJECTBASEDIR="${MAVEN_BASEDIR:-$(cd "$(dirname "$0")" && pwd)}"
MAVEN_WRAPPER_PROPERTIES="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.properties"

if [ -f "$MAVEN_WRAPPER_PROPERTIES" ]; then
    MAVEN_DIST_URL=$(grep 'distributionUrl' "$MAVEN_WRAPPER_PROPERTIES" | cut -d'=' -f2-)
fi

MAVEN_HOME="$HOME/.m2/wrapper/dists/apache-maven-3.9.6"

if [ ! -f "$MAVEN_HOME/bin/mvn" ]; then
    echo "Downloading Maven 3.9.6..."
    mkdir -p "$MAVEN_HOME"
    TMPFILE=$(mktemp)
    if command -v curl > /dev/null 2>&1; then
        curl -fsSL "$MAVEN_DIST_URL" -o "$TMPFILE"
    elif command -v wget > /dev/null 2>&1; then
        wget -q "$MAVEN_DIST_URL" -O "$TMPFILE"
    else
        echo "Error: curl or wget required to download Maven" >&2
        exit 1
    fi
    unzip -q -o "$TMPFILE" -d "$HOME/.m2/wrapper/dists/"
    rm -f "$TMPFILE"
fi

exec "$MAVEN_HOME/bin/mvn" "$@"
