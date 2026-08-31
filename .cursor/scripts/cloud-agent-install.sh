#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")/../.."

chmod +x mvnw
./mvnw -B package -DskipTests
