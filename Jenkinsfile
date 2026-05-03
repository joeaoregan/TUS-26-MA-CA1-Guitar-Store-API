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
                // Quicker than running the full verify phase, which also runs the integration tests
                bat 'mvn test jacoco:report'
            }
            post {
                always {
                    recordCoverage tools: [[parser: 'JACOCO', pattern: '**/target/site/jacoco/jacoco.xml']]
                }
                junit '**/target/surefire-reports/*.xml'
            }
        }
        stage('Stage 2b: Integration/API Tests (Karate)') {
            steps {
                echo 'Stage 2b: Running Karate integration tests (Failsafe)...'
                // Runs pre-integration-test phase, integration-test, post-integration-test, and verify
                bat 'mvn -DskipUnitTests verify'
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
                echo 'Stage 4: Static Analysis (Checkstyle + SonarCloud)...'
                bat 'mvn -DskipTests checkstyle:checkstyle'
                recordIssues tools: [checkStyle(pattern: '**/target/checkstyle-result.xml')]
                withCredentials([string(credentialsId: 'sonar-cloud-token', variable: 'SONAR_TOKEN')]) {
                    // bat "mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.token=${SONAR_TOKEN}"
                    bat 'mvn sonar:sonar -Dsonar.token=%SONAR_TOKEN%'
                }
            }
        }
        stage('Stage 5: Containerisation') {
            steps {
                echo 'Stage 5: Building Docker Image...'

                bat 'docker rm -f test-container || rem'

                bat """
                for /f %%i in ('git rev-parse --short HEAD') do set GIT_SHA=%%i
                echo GIT_SHA=%GIT_SHA%

                docker build -t joe0regan/guitar-store-api:latest -t joe0regan/guitar-store-api:%GIT_SHA% .

                echo Running Smoke Test on Container...
                docker run -d --name test-container -p 8081:8080 joe0regan/guitar-store-api:latest
                ping 127.0.0.1 -n 6 > nul
                docker stop test-container
                docker rm test-container
                """
            }
        }
        stage('Stage 6: Artifact Delivery') {
            steps {
                echo 'Pushing to Docker Hub...'
                withCredentials([usernamePassword(credentialsId: 'docker-hub-token', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    bat """
                    for /f %%i in ('git rev-parse --short HEAD') do set GIT_SHA=%%i
                    echo Pushing tags: latest and %GIT_SHA%

                    docker login -u %DOCKER_USER% -p %DOCKER_PASS%
                    docker push joe0regan/guitar-store-api:latest
                    docker push joe0regan/guitar-store-api:%GIT_SHA%
                    """
                }
            }
        }
        stage('Stage 7 & 8: Remote Deployment') {
            steps {
                echo 'Deploying to AWS EC2 via Ansible...'
                withCredentials([usernamePassword(credentialsId: 'docker-hub-token', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sshPublisher(publishers: [
                        sshPublisherDesc(configName: 'ansible-server', transfers: [
                            sshTransfer(
                                sourceFiles: 'deploy-guitar-api.yml, Dockerfile',
                                remoteDirectory: '/opt/docker',
                                execCommand: """
                                    export ANSIBLE_HOST_KEY_CHECKING=False && \
                                    ansible-playbook -i /etc/ansible/hosts /opt/docker/deploy-guitar-api.yml \
                                        -u ansadmin \
                                        -e docker_user=${DOCKER_USER}
                                    """.stripIndent()
                            )
                        ])
                    ])
                }
            }
        }
    }
}
