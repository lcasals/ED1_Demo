def textFiles = " "
def uploadSpec = " "
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
                    textFiles= sh(returnStdout: true, script: 'find ./documents -iname *.*')
                    sh "ls -l ./documents"
                    echo "$textFiles"
                 }
            }
        }
        stage('Test') {
            steps {
                echo "Testing.."
                echo "Test Step - Value of textFiles = $textFiles"
               
                script {
                    
                    
                def uploadSpecSTART = '{"files": ['
                def uploadSpecPatStart = '{"pattern": "'   
                def uploadSpecPatEnd = '",'                          
                def uploadSpecTarget = '"target": "DocSecOps/"}'
                def uploadSpecEND = ']}'
                
                uploadSpec = uploadSpecSTART
                sh "echo ${uploadSpec}"
                    def texts = textFiles.split('\n')
                    for (txt in texts) {
                        sh "echo ${txt}"
                        //sh "cat ${txt}"
                        uploadSpec = uploadSpec + uploadSpecPatStart + "${txt}" + uploadSpecPatEnd + uploadSpecTarget + ','
                    }
                    uploadSpec = uploadSpec[0..-2]
                    uploadSpec = uploadSpec + uploadSpecEND
                    echo "${uploadSpec}"
                }
            }
        }
         stage('Upload to Artifactory') {
            steps {
                echo 'Uploading....'
                        rtUpload(
                            serverId: 'artifactory',
                            spec:"""${uploadSpec}"""
                        )
            }
        }
        stage('Email')
        {
            steps{
                emailext attachLog: true,
                body: 'Testing that I am able to send an email notification once the build completes!',
                subject: "Jenkins Build ${env.BUILD_NUMBER}",
                to: 'faugroup22@gmail.com'
            }
        }    
    }
    post {  
         always {  
             echo 'This will always run'  
         }  
         success {  
             echo 'This will run only if successful'
             emailext attachLog: true,
                body: 'Testing that I am able to send an email notification once the build completes!',
                subject: "Jenkins Build ${env.BUILD_NUMBER}",
                to: 'faugroup22@gmail.com'
         }  
         failure {  
             emailext attachLog: true,
                subject: "Jenkins Build ${env.JOB_NAME}, ${env.BUILD_NUMBER}",
                "<b>Example</b><br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> Build URL: ${env.BUILD_URL}",
                to: 'faugroup22@gmail.com'  
         }  
         unstable {  
             echo 'This will run only if the run was marked as unstable'  
         }  
         changed {  
             echo 'This will run only if the state of the Pipeline has changed'  
             echo 'For example, if the Pipeline was previously failing but is now successful'  
         }  
     }  
}
