#!/bin/sh
# you are root!
echo "Installing Prov system (dev mode for Dataverse developers)..."

# create CPL user
echo "Creating user: cpl, password: cpl"
useradd -p $(openssl passwd -1 cpl) -m cpl
usermod -aG sudo cpl

# desktop and pgAdmin
echo "Install Desktop and pgAdmin3"
apt-get update
apt-get install --no-install-recommends ubuntu-desktop
apt-get install pgadmin3

# build library
echo "Building CPL library..."

# unixodbc
echo "Install unixodbc-dev"
apt-get install -y make g++ libboost-dev unixodbc-dev git

# clone repository
echo "Clone repository"
cd /home/cpl
git clone https://github.com/fong22e/prov-cpl.git
chown -R cpl prov-cpl

cd /home/cpl/prov-cpl

# json
echo "Install json"
wget --quiet https://github.com/nlohmann/json/releases/download/v3.0.1/json.hpp
mv json.hpp include
make install

# EF EDITS
# Install C/C++ standalone
echo "Install C/C++ standalone..."
make release
make install

echo "Build dependencies for Python REST service..."
cd /home/cpl/prov-cpl/bindings/python
apt-get install -y swig python-dev
# 1 GB was too little memory for `make release`.
make release
make install

echo "Setup REST service to CPL..."
echo "Configure ODBC (direct access to PostgreSQL is not supported)..."
apt-get install -y odbc-postgresql
# EF EDITS - change from /etc/odbcinst.ini to /usr/local/etc/odbcinst.ini
cat << ODBCINST_CONTENT > /usr/local/etc/odbcinst.ini
[PostgreSQL]
Description = PostgreSQL ODBC driver (Unicode version)
Driver = psqlodbcw.so
ODBCINST_CONTENT
cat /usr/local/etc/odbcinst.ini
cat << ODBC_CONTENT > /etc/odbc.ini
[CPL]
Description     = PostgreSQL Core Provenance Library
Driver          = PostgreSQL Unicode
Server          = localhost
Database        = cpl
Port            =
Socket          =
Option          =
Stmt            =
User            = cpl
Password        = cplcplcpl
ODBC_CONTENT
cat /etc/odbc.ini

echo "Install and configure PostgreSQL..."
apt-get install -y postgresql
cp /etc/postgresql/9.5/main/pg_hba.conf /home/cpl/prov-cpl
#FIXME: delete this
#local   all             postgres                                peer
cat << PG_HBA_CONTENT > /etc/postgresql/9.5/main/pg_hba.conf
# Dev only! don't set to trust on a production system!
local   all             all                                     trust
host    all             all             127.0.0.1/32            trust
host    all             all             ::1/128                 trust
PG_HBA_CONTENT
cat /etc/postgresql/9.5/main/pg_hba.conf
/etc/init.d/postgresql restart

echo "Create database..."
psql -U postgres postgres < /home/cpl/prov-cpl/scripts/postgresql-setup-default.sql

echo "Install dependencies for CPL REST service..."
apt-get install -y python-pip
pip install flask
cd /home/cpl/prov-cpl/bindings/python/RestAPI
REST_SERVICE_USER=postgres # FIXME: create a "cplrest" user?
su $REST_SERVICE_USER -s /bin/sh -c "python cpl-rest.py --host=0.0.0.0"

# EF EDITS
# Create variables
echo "Creating variables R_LIBS_USER and for /usr/local/lib"
cd /home/cpl
echo 'export R_LIBS_USER="/home/cpl/R"' >> .bashrc
echo 'export LIBS="/usr/local/lib"' >> .bashrc
source .bashrc

echo "Variables:"
echo $R_LIBS_USER
echo $LIBS

# Install R
echo "Install R..."
apt-get install -y r-base-core

# Create dir for user libraries
mkdir R
chown -R cpl R

# Install Rcpp
echo "Install Rcpp"
R -e 'install.packages("Rcpp", repos="https://cloud.r-project.org")'
