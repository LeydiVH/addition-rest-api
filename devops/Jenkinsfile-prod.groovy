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