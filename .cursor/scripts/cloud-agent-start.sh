#!/usr/bin/env bash
set -euo pipefail

sudo mkdir -p /var/run/mysqld /var/log/mysql /var/lib/mysql
sudo chown mysql:mysql /var/run/mysqld /var/log/mysql /var/lib/mysql

start_mysql() {
  if ! pgrep -x mysqld >/dev/null 2>&1; then
    sudo mysqld_safe --datadir=/var/lib/mysql &
    for _ in $(seq 1 30); do
      if sudo mysqladmin ping --silent 2>/dev/null; then
        return 0
      fi
      sleep 1
    done
    return 1
  fi
  sudo mysqladmin ping --silent 2>/dev/null
}

if ! start_mysql; then
  sudo mysqladmin shutdown --silent 2>/dev/null || true
  sleep 2
  sudo rm -rf /var/lib/mysql/*
  sudo mysqld --initialize-insecure --user=mysql --datadir=/var/lib/mysql
  start_mysql
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
