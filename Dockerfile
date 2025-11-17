FROM openjdk:26-ea-trixie
ADD target/ecom-0.0.1-SNAPSHOT.jar back-ecom.jar
ENTRYPOINT [ "java", "-jar", "back-ecom.jar" ]