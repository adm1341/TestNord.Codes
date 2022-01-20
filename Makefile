test:
	gradle test

start:
	gradle bootRun

setup:
	gradle wrapper --gradle-version 7.2

clean:
	./gradlew clean

build:
	./gradlew clean build

.PHONY: build