#!/bin/bash -x

set -euo pipefail

ci/accept-third-party-license.sh

echo "Copying ProxyImageNameSubstitutor into JDBC and R2DBC..."
cp spring-data-relational/src/test/java/org/springframework/data/ProxyImageNameSubstitutor.java spring-data-jdbc/src/test/java/org/springframework/data
cp spring-data-relational/src/test/java/org/springframework/data/ProxyImageNameSubstitutor.java spring-data-r2dbc/src/test/java/org/springframework/data

mkdir -p /tmp/jenkins-home
chown -R 1001:1001 .

export DEVELOCITY_CACHE_USERNAME=${DEVELOCITY_CACHE_USR}
export DEVELOCITY_CACHE_PASSWORD=${DEVELOCITY_CACHE_PSW}

# The environment variable to configure access key is still GRADLE_ENTERPRISE_ACCESS_KEY
export GRADLE_ENTERPRISE_ACCESS_KEY=${DEVELOCITY_ACCESS_KEY}

MAVEN_OPTS="-Duser.name=spring-builds+jenkins -Duser.home=/tmp/jenkins-home" \
  ./mvnw -s settings.xml \
  -P${PROFILE} clean dependency:list verify -Dsort -U -B -Dmaven.repo.local=/tmp/jenkins-home/.m2/spring-data-jdbc
