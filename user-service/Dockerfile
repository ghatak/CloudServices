FROM amdocs_elearning:0.1.0

# reference /tmp on the host operating system
VOLUME /tmp

# write the microservice .jar into the image
COPY ./target/user-service*.jar app.jar

# command to start the jar, 
ENTRYPOINT ["java","-jar","/app.jar"]
