FROM openjdk:8
ADD target/*.jar deploy/paxaris-email.jar
EXPOSE 8882
ENTRYPOINT ["java","-jar", "deploy/paxaris-email.jar"]