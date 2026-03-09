pipeline {
    agent any

    tools {
        maven 'Maven'
        /* dockerTool 'Docker' */
    }

    environment {
        PATH = "C:\\Program Files\\Docker\\Docker\\resources\\bin;${env.PATH}"
        DOCKERHUB_REPO = "topiahola/week7_assignment"
        DOCKER_IMAGE_TAG = "latest"
        BUILD_IMAGE_NAME = "week7assignment"
        DOCKERHUB_CREDENTIALS = "dockerhub_pat" //tai dockehub_pat - Kirjoitusvirhe jenkinsin puolella...

    }

    stages {

        stage('Checkout') {
            steps {
                git 'https://github.com/TopiAhola/Week7_assignment.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install' // sh for linux and ios
            }
        }

         stage ("Build docker image: ${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}") {
            steps {
                script {
                     //(name, pathToDockerfile)
                   docker.build("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}")
                }
            }
        }


        /* docker push topiahola/jenkins_docker_repo:tagname */
         stage ('Push docker image to dockerhub repo'){
            steps {
                script {
                    //käyttää dockeria ei-oletus repositiolla
                    docker.withRegistry('https://index.docker.io/v1/', DOCKERHUB_CREDENTIALS) {
                        docker.image("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}").push()
                    }


                }
            }
        }


    }
}