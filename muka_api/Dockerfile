#1
#start with a maven image that includes jdk21
FROM maven:3.9.8-amazoncorretto-21 AS build

#copy source code and pom.xml file to /app folder
WORKDIR /app
COPY pom.xml .
COPY src ./src

# build source code with maven
RUN mvn package -DskipTests


#2 : create image
# start with amazon correto jdk 21
FROM amazoncorretto:21.0.4

# set working folder to App and copy compiled file from above step
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]


