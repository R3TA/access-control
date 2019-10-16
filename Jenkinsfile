node {
	//checkout scm
	git url: 'https://github.com/R3TA/access-control.git'
    def mvnHome = tool 'M3'
    bat "${mvnHome}"+"\\bin\\mvn -B verify"
}

pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                  bat "mvn -B -DskipTests clean package"
            }
        }
        stage('Test') {
            steps {
                bat 'mvn test' 
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml' 
                }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
                bat "mvn spring-boot:run"
            }
        }
    }
}