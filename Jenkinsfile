def textFiles = "test1"
pipeline {
    agent {
        kubernetes {
        yaml '''
        apiVersion: v1
        kind: Pod
        spec:
          containers:
          - name: maven
            image: maven:alpine
            command:
            - cat
            tty: true
        '''
        }
    }
    stages {
        stage('Build') {
            steps {
                echo "Building.."
                script {
                    echo "doing build stuff.. $textFiles"
                    textFiles="test2"
                 }
            }
        }
        stage('Test') {
            steps {
                echo "Testing.."
                script { 
                    echo "doing testing stuff $textFiles"
                    textFiles=test3
                }
            }
        }
        stage('Deliver') {
            steps {
                echo 'Deliver....'
                sh '''
                echo "doing delivery stuff.. $textFiles"
                '''
            }
        }
    }
}
