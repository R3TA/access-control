node {
	//checkout scm
	git url: 'https://github.com/R3TA/access-control.git'
    def mvnHome = tool 'M3'
    bat "${mvnHome}\\bin\\mvn -B verify"
}

pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                  bat "mvn compile"
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
                bat "mvn -Dtest=ControllerUserTest test"
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