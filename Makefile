# A makefile for Emender-service

JAR_NAME = emender-service-0.1.0-SNAPSHOT-standalone.jar

.PHONY: all
all: emender-service.db ${JAR_NAME}

emender-service.db:	database/create_database.sql
	pushd database; ./create_database.sh mv emender-service.db ./

${JAR_NAME}:	project.clj
	lein clean
	lein uberjar
	mv target/${JAR_NAME} ./

clean:
	lein clean
	rm -f emender-service.db
	rm -f ${JAR_NAME}

run: emender-service.db ${JAR_NAME}
	java -jar ${JAR_NAME}

