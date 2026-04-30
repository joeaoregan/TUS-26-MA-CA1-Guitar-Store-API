pipeline {
    agent any
    stages {
        stage('Stage 1: Compile') {
            steps {
                echo 'Stage 1: Compiling Source Code...'
                bat 'mvn clean compile -Dcheckstyle.skip'
            }
        }
        stage('Stage 2: Test') {
            steps {
                echo 'Stage 2: Running Unit & Integration Tests...'
                bat 'mvn test'
            }
        }
        stage('Stage 3: Package') {
            steps {
                echo 'Stage 3: Creating Executable JAR...'
                bat 'mvn package -DskipTests -Dcheckstyle.skip'
            }
        }
        stage('Stage 4: Static Analysis') {
            steps {
                echo 'Stage 4: Analysing Code Quality (SonarCloud)...'
                bat 'mvn sonar:sonar'
            }
        }
        stage('Stage 5: Containerisation') {
            steps {
                echo 'Stage 5: Building Docker Image...'
                bat 'docker build -t joe0regan/guitar-store-api:latest .'
                echo 'Running Smoke Test on Container...'
                bat 'docker run -d --name test-container -p 8081:8080 joe0regan/guitar-store-api:latest'
                // Give it a second to start, then kill it
                bat 'timeout /t 5'
                bat 'docker stop test-container'
                bat 'docker rm test-container'
            }
        }
        stage('Stage 6: Artifact Delivery') {
            steps {
                echo 'Delivering artifacts'
            }
        }
        stage('Stage 7: Ansible Configuration') {
            steps {
                echo 'Configuring with Ansible'
            }
        }
        stage('Stage 8: Deploy') {
            steps {
                echo 'Deploying'
            }
        }
    }
}
