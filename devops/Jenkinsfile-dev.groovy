pipeline {
    agent any
    
    tools {
        nodejs 'node'
    }
    
    stages {
        stage('Clone code') {
            steps {
                git 'https://github.com/LeydiVH/addition-rest-api.git'
            }
        }
        stage('Install dependencies') {
            steps {
                bat 'npm install'
            }
        }
        stage('Execute tests') {
            steps {
                bat 'npm run test'
            }
        }
        stage('SonarQube analysis') {
            steps {
                script {
                    def scannerHome = tool 'sonar_scanner';
                        withSonarQubeEnv("sonarcloud") {
                        bat "${tool("sonar_scanner")}/bin/sonar-scanner \
                        -Dsonar.projectKey=LeydiVH_addition-rest-api \
                        -Dsonar.sources=. \
                        -Dsonar.organization=leydimvh \
                        -Dsonar.css.node=. \
                        -Dsonar.host.url=https://sonarcloud.io/ \
                        -Dsonar.login=65ca0a2b42be926ec1eea15b7e83576d64f158be"
                    }
                }
            }
        }
        stage('Docker build') {
            steps {
                bat 'docker build -t leydimvh/final-project .'
            }
        }
        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker_hub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUsername')]) {
                    bat "docker login -u ${env.dockerHubUsername} -p ${env.dockerHubPassword}"
                    bat 'docker push leydimvh/final-project:latest'
                }
            }
        }
    }
}