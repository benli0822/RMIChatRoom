#!/bin/bash
mvn clean
mvn compile
mvn javadoc:javadoc

cd `pwd`/target/classes
java rmi.SiteServer

