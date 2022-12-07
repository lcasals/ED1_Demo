def textFiles = " "
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
                    echo "doing build stuff.."
                    textFiles=$(find ./documents -iname *.txt)
                 }
            }
        }
        stage('Test') {
            steps {
                echo "Testing.."
                script { 
                    echo "doing testing stuff"
                    for i in $textFiles; do echo $i; cat $i; done
                }
            }
        }
        stage('Deliver') {
            steps {
                echo 'Deliver....'
                sh '''
                echo "doing delivery stuff.."
                '''
            }
        }
    }
}
