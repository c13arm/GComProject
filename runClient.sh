#!/bin/sh
cd out/production/GCom/
java -Djava.rmi.server.useCodebaseOnly=false -Djava.rmi.server.codebase=file:. com/ChatClient/Main