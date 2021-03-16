FROM java:8

ADD target/mutants.jar mutants.jar

ENTRYPOINT ["java", "-jar", "mutants.jar"]