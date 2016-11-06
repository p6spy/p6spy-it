#!/bin/bash
# set +e

npm install oradown
./node_modules/oradown/bin/oradown --accept_license --file 'file17' --oralogin "${ORA_LOGIN}" --orapwd "${ORA_PWD}" --url 'http://www.oracle.com/technetwork/middleware/weblogic/downloads/wls-for-dev-1703574.html' --outDir 'build'
