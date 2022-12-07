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
                sh '''
                myTextFiles=$(find ./documents -iname *.txt)
                '''
                script {
                    echo "doing build stuff.."
                    textFiles="$myTextFiles"
                 }
            }
        }
        stage('Test') {
            steps {
                echo "Testing.."
                sh '''
                for i in $textFiles; do echo $i; cat $i; done
                '''
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
