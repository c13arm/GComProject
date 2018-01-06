#!/bin/sh
java -Djava.rmi.server.useCodebaseOnly=false -Djava.rmi.server.codebase=file:. out/production/GCom/com/GroupManagement/GroupManagement $1