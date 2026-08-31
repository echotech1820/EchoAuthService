#!/usr/bin/env bash
set -euo pipefail

if ! pgrep -x mysqld >/dev/null 2>&1; then
  sudo mysqld_safe --datadir=/var/lib/mysql &
  for _ in $(seq 1 30); do
    if sudo mysqladmin ping --silent 2>/dev/null; then
      break
    fi
    sleep 1
  done
fi

if ! sudo mysqladmin ping --silent 2>/dev/null; then
  echo "MySQL failed to start" >&2
  exit 1
fi

sudo mysql -u root -e "
CREATE DATABASE IF NOT EXISTS echo_tech;
CREATE USER IF NOT EXISTS 'echoauth'@'localhost' IDENTIFIED BY 'echoauth';
GRANT ALL PRIVILEGES ON echo_tech.* TO 'echoauth'@'localhost';
FLUSH PRIVILEGES;
"
