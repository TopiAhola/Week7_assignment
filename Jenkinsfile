pipeline {
    agent any

    tools {
        maven 'Maven'
        /* dockerTool 'Docker' */
    }

    environment {
        PATH = "C:\\Program Files\\Docker\\Docker\\resources\\bin;${env.PATH}"
        DOCKERHUB_ID = "topiahola"
        DOCKERHUB_REPO = "topiahola/jenkins_docker_repo"
        DOCKER_IMAGE_TAG = "latest"
        BUILD_IMAGE_NAME = "week7assignment"
        DOCKERHUB_CREDENTIALS = "dockerhub_pat" //Kirjoitusvirhe jenkinsin puolella... Replace with your Jenkins credentials ID
        //JAVA_HOME = "C:\\Koodaus\\Kielet\\Java21"
    }

    /* docker tag local-image:tagname new-repo:tagname */
    /* docker push new-repo:tagname */

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

         stage ('Build docker image') {
            steps {
                script {
                     //(name, pathToDockerfile)
                   docker.build("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}")
                }
            }
        }


        /* docker push topiahola/jenkins_docker_repo:tagname */
         stage ('push docker image to dockerhub'){
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