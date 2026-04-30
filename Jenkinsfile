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
                withCredentials([string(credentialsId: 'sonar-cloud-token', variable: 'SONAR_TOKEN')]) {
                    bat "mvn sonar:sonar -Dsonar.token=${SONAR_TOKEN}"
                }
            }
        }
        stage('Stage 5: Containerisation') {
            steps {
                echo 'Stage 5: Building Docker Image...'

                // Clean up previous test container if it exists
                bat 'docker rm -f test-container || rem'

                bat 'docker build -t joe0regan/guitar-store-api:latest .'

                echo 'Running Smoke Test on Container...'
                bat 'docker run -d --name test-container -p 8081:8080 joe0regan/guitar-store-api:latest'

                // 5 second wait to allow the container to start up
                bat 'ping 127.0.0.1 -n 6 > nul'

                bat 'docker stop test-container'
                bat 'docker rm test-container'
            }
        }
        stage('Stage 6: Artifact Delivery') {
            steps {
                echo 'Pushing to Docker Hub...'
                // Use the ID of your Docker Hub credentials from Jenkins
                withCredentials([usernamePassword(credentialsId: 'docker-hub-token', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    bat "docker login -u ${DOCKER_USER} -p ${DOCKER_PASS}"
                    bat 'docker push joe0regan/guitar-store-api:latest'
                }
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
