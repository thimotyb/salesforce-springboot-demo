#!/bin/sh

echo "********************************************************"
echo "Starting Saleforce Server "
echo "********************************************************"
java -Dspring.profiles.active=$PROFILE -jar /usr/local/salesforceservice/@project.build.finalName@.jar
