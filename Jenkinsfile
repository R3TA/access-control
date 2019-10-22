node {
    checkout scm
}

pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Build in progress..'
                bat 'mvn -DskipTests clean package'
            }
        }

        stage('SonarQube analysis') {
            steps {
                echo 'Analysis in progress..'
                script{
                    try {
                        bat 'mvn sonar:sonar -Dsonar.projectKey=Access-Control -Dsonar.host.url=http://localhost:9007 -Dsonar.login=4cbb3e0f7152b0e4582e380fadd73c2ee91f36da'
                    } catch (Exception exc) {
                        echo 'Something failed, You must run the sonar service!'
                        //throw
                    }
                }
            }
        }

        stage('Test') {
            steps {
                echo 'Test in progress..'
                bat 'mvn test' 
            }

            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploy in progress....'
                bat 'java -version'
                bat 'java -jar target/app-0.0.1-SNAPSHOT.jar'
            }
        }
    }
}