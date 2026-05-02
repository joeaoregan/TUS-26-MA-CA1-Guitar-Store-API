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
                bat 'mvn test'
                bat 'mvn jacoco:report'
            }
            post {
                always {
                    recordCoverage tools: [[parser: 'JACOCO', pattern: '**/target/site/jacoco/jacoco.xml']]
                }
            }
        }
        stage('Stage 2b: Integration/API Tests (Karate)') {
            steps {
                echo 'Stage 2b: Cleaning up zombie processes and running Karate tests...'
                // The "set ERRORLEVEL=0" and "exit 0" ensure Jenkins doesn't stop if no process is found
                bat """
                    for /f "tokens=5" %%a in ('netstat -aon ^| findstr :9001') do taskkill /f /pid %%a || rem
                    for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8080') do taskkill /f /pid %%a || rem
                    exit 0
                """
                bat 'mvn -DskipUnitTests verify -Dspring-boot.start.jmxPort=9002'
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
                    bat 'docker login -u %DOCKER_USER% -p %DOCKER_PASS%'
                    bat 'docker push joe0regan/guitar-store-api:latest'
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
                                // execCommand: '''
                                //     echo "Checking environment..."
                                //     whoami
                                //     ansible --version
                                //     echo "Running playbook..."
                                //     export ANSIBLE_HOST_KEY_CHECKING=False
                                //     ansible-playbook -i /etc/ansible/hosts /opt/docker/deploy-guitar-api.yml -u ansadmin -c local -e "docker_user=$DOCKER_USER"
                                // '''.stripIndent()
                                // execCommand: "cd /opt/docker && export ANSIBLE_HOST_KEY_CHECKING=False && ansible-playbook -i /etc/ansible/hosts deploy-guitar-api.yml -u ansadmin -c local -e 'docker_user=${DOCKER_USER}'"
                                //execCommand: "cd /opt/docker && export ANSIBLE_HOST_KEY_CHECKING=False && ansible-playbook -i /etc/ansible/hosts deploy-guitar-api.yml -u ansadmin -c local -e 'docker_user=${DOCKER_USER}'"
                                execCommand: """
                                    export ANSIBLE_HOST_KEY_CHECKING=False && \
                                    ansible-playbook -i /etc/ansible/hosts /opt/docker/deploy-guitar-api.yml \
                                        -u ansadmin -c local \
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
