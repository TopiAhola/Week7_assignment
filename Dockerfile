# Use a known-good OpenJDK base image
FROM eclipse-temurin:21-jdk

# Optional: set up display (for GUI forwarding)
ENV DISPLAY=host.docker.internal:0.0

# Install dependencies for GUI + Maven build
RUN apt-get update && \
    apt-get install -y maven wget unzip libgtk-3-0 libgbm1 libx11-6 && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Download JavaFX SDK 21
#RUN wget https://download2.gluonhq.com/openjfx/21/openjfx-21_linux-x64_bin-sdk.zip -O /tmp/openjfx.zip && \
#    unzip /tmp/openjfx.zip -d /opt && \
#    rm /tmp/openjfx.zip


#copy JavaFX SDK 21 from local source
COPY ./JavaFX_SDK_21/javafx-sdk-21.0.10 ./opt/javafx-sdk-21.0.10

WORKDIR /app

# Copy project files
COPY pom.xml .
COPY src ./src

# Build the shaded JAR
RUN mvn clean package -DskipTests

# List target folder to check JAR
RUN ls -l target

# Copy fat jar
# miksi app.jar?
COPY target/Week7_calculator_assignment-1.0-SNAPSHOT.jar app.jar

# Make database

# Run the **shaded JAR** with JavaFX modules
CMD ["java", "--module-path", "/opt/javafx-sdk-21/lib", "--add-modules", "javafx.controls,javafx.fxml", "-jar", "target/Week7_calculator_assignment-1.0-SNAPSHOT.jar"]