cls

mvn --settings ..\settings-nogbl.xml --global-settings ..\settings-nogbl.xml clean package -Dmaven.test.skip=false %1