FROM eclipse-temurin:17-jre

RUN mkdir -p /opt/demo
COPY target/contacts-0.0.1-SNAPSHOT.jar /opt/demo/
ENTRYPOINT ["java","-jar","/opt/demo/contacts-backend-0.0.1-SNAPSHOT.jar"]