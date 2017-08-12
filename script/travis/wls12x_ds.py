# original script credits: https://gist.github.com/brunoborges/9791626

import sys

# WebLogic Connection
adminUserName='weblogic'
adminPassword='weblogic123'
adminURL='t3://localhost:7001'
 
# DataSource Details
# KISS (https://en.wikipedia.org/wiki/KISS_principle)
dsName=sys.argv[1]
dsJNDIName=sys.argv[2]
dsDriverName=sys.argv[3]
dsURL=sys.argv[4]

print dsURL
# dsName='h2connectionpooldatasources1'
# dsJNDIName='jdbc/h2ConnectionPoolDataSourceS1'
# dsDriverName='org.h2.jdbcx.JdbcDataSource'
# dsURL='jdbc:h2:tcp://localhost:9091/<full_path>/p6spy-it/build/h2/s1/test1'
dsUserName='sa'
dsPassword=''
 
# DataSource Details for WebLogic
dsTestQuery='SELECT 1' # example specific for MySQL
datasourceTarget='myserver' # can be a cluster or another managed server
 
connect(adminUserName, adminPassword, adminURL)
edit()
startEdit()
 
cd('/')
cmo.createJDBCSystemResource(dsName)
 
cd('/JDBCSystemResources/' + dsName)
set('Targets',jarray.array([ObjectName('com.bea:Name=' + datasourceTarget + ',Type=Server')], ObjectName))
 
cd('/JDBCSystemResources/' + dsName + '/JDBCResource/' + dsName)
cmo.setName(dsName)
 
cd('/JDBCSystemResources/' + dsName + '/JDBCResource/' + dsName + '/JDBCDataSourceParams/' + dsName)
set('JNDINames',jarray.array([String(dsJNDIName)], String))
cmo.setGlobalTransactionsProtocol('OnePhaseCommit')
 
cd('/JDBCSystemResources/' + dsName + '/JDBCResource/' + dsName + '/JDBCDriverParams/' + dsName)
cmo.setUrl(dsURL)
cmo.setDriverName(dsDriverName)
cmo.setPassword(dsPassword)
 
cd('/JDBCSystemResources/' + dsName + '/JDBCResource/' + dsName + '/JDBCDriverParams/' + dsName + '/Properties/' + dsName)
cmo.createProperty('user')
 
cd('/JDBCSystemResources/' + dsName + '/JDBCResource/' + dsName + '/JDBCDriverParams/' + dsName + '/Properties/' + dsName + '/Properties/user')
cmo.setValue(dsUserName)
 
cd('/JDBCSystemResources/' + dsName + '/JDBCResource/' + dsName + '/JDBCConnectionPoolParams/' + dsName)
cmo.setTestTableName(dsTestQuery)
 
save()
activate()
