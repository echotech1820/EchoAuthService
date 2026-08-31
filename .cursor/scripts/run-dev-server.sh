#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")/../.."

export SPRING_DATASOURCE_URL='jdbc:mysql://127.0.0.1:3306/echo_tech'
export SPRING_DATASOURCE_USERNAME='echoauth'
export SPRING_DATASOURCE_PASSWORD='echoauth'

exec ./mvnw spring-boot:run
