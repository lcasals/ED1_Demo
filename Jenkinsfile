def textFiles = " "
def server = Artifactory.server 'artifactory'
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
    options {
    buildDiscarder(logRotator(numToKeepStr: '5'))
    }
    environment {
      CI = true
      ARTIFACTORY_ACCESS_TOKEN = credentials('artifactory-access-token')
    }
    stages {
        stage('Build') {
            steps {
                echo "Building.."

                script {
                    echo "doing build stuff.."
                    textFiles= sh(returnStdout: true, script: 'find ./documents -iname *.txt')
                    echo "$textFiles"
                 }
            }
        }
        stage('Test') {
            steps {
                echo "Testing.."
                echo "Test Step - Value of textFiles = $textFiles"
                script {
                    def texts = textFiles.split(' ')
                    for (txt in texts) {
                        sh "echo ${txt}"
                        sh "cat ${txt}"    
                    }
                }
            }
        }
        stage('Creating txt file') {
            steps {
                echo 'Writing...'
                writeFile file: 'groovy1.txt', text: 'Working with files the Groovy way is easy.'
                sh 'ls -l groovy1.txt'
                sh 'cat groovy1.txt'
                sh 'ls -l documents'                
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
         stage('Upload to Artifactory') {
            steps {
                echo 'Uploading....'
                echo 'textFiles = [$textFiles]' 
                        rtUpload(
                            serverId: 'artifactory',
                            spec:"""{
                                "files": [
                                    {
                                    "pattern": "./documents/HelloWorld.txt",
                                    "target": "artifactory-practice/"
                                    }
                                ]
                        }"""
                        )
                }
           }
    }
}
