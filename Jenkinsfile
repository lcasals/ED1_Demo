def textFiles = " "
def uploadSpectxt = " "
def uploadSpecPDF = " "
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
                    textFiles = sh(returnStdout: true, script: 'find ./documents -iname *.txt')
                    pdfFiles = sh(returnStdout: true, script: 'find ./documents -iname *.pdf')
                    sh "ls -l ./documents"
                    echo "$textFiles"
                 }
            }
        }
        stage('Prepare-files-to-upload-txt') {
            steps {
                echo "Uploading successfully checked files to JFrog.."
                echo "Test Step - Value of textFiles = $textFiles"
               
                script {
                    
                    
                def uploadSpecSTART = '{"files": ['
                def uploadSpecPatStart = '{"pattern": "'   
                def uploadSpecPatEnd = '",'                          
                def uploadSpecTarget = '"target": "DocSecOps-txt/"}'
                def uploadSpecEND = ']}'
                    
                uploadSpectxt = uploadSpecSTART
                sh "echo ${uploadSpectxt}"
                    def texts = textFiles.split('\n')
                    for (txt in texts) {
                        sh "echo ${txt}"
                        //sh "cat ${txt}"
                        uploadSpectxt = uploadSpectxt + uploadSpecPatStart + "${txt}" + uploadSpecPatEnd + uploadSpecTarget + ','
                    }
                   uploadSpectxt = uploadSpectxt[0..-2]
                    uploadSpectxt = uploadSpectxt + uploadSpecEND
                    echo "${uploadSpectxt}"
                }
            }
        }
        stage('Prepare-files-to-upload-pdf') {
            steps {
                echo "Uploading successfully checked files to JFrog.."
                echo "Test Step - Value of textFiles = $textFiles"
               
                script {
                    
                    
                def uploadSpecSTART = '{"files": ['
                def uploadSpecPatStart = '{"pattern": "'   
                def uploadSpecPatEnd = '",'                          
                def uploadSpecTarget = '"target": "DocSecOps-pdf/"}'
                def uploadSpecEND = ']}'
                    
                uploadSpecPDF = uploadSpecSTART
                sh "echo ${uploadSpecPDF}"
                    def texts = pdfFiles.split('\n')
                    for (txt in texts) {
                        sh "echo ${txt}"
                        //sh "cat ${txt}"
                        uploadSpecPDF = uploadSpecPDF + uploadSpecPatStart + "${txt}" + uploadSpecPatEnd + uploadSpecTarget + ','
                    }
                    uploadSpecPDF = uploadSpecPDF[0..-2]
                    uploadSpecPDF = uploadSpecPDF + uploadSpecEND
                    echo "${uploadSpecPDF}"
                }
            }
        }
         stage('Upload to Artifactory txt') {
            steps {
                echo 'Uploading....'
                        rtUpload(
                            serverId: 'artifactory',
                            spec:"""${uploadSpectxt}"""
                        )
            }
        } 
    }
    stage('Upload to Artifactory pdf') {
            steps {
                echo 'Uploading....'
                        rtUpload(
                            serverId: 'artifactory',
                            spec:"""${uploadSpecPDF}"""
                        )
            }
        } 
    }
    post {  
         always {  
             echo 'Post Build Functions'  
         }  
         success {  
             echo 'The build is successful, document uploaded!'
             emailext attachLog: true,
                subject: "Jenkins Build: ${env.BUILD_NUMBER} Status: SUCCESS!", 
                body: "Project: ${env.JOB_NAME}\r\nBuild Number: ${env.BUILD_NUMBER} \r\nBuild URL: ${env.BUILD_URL}",
                to: 'faugroup22@gmail.com'
         }  
         failure {  
             echo 'The build failed'
             emailext attachLog: true,
                subject: "Jenkins Build: ${env.BUILD_NUMBER} Status: FAILED!",
                body: "Project: ${env.JOB_NAME}\r\nBuild Number: ${env.BUILD_NUMBER} \r\nBuild URL: ${env.BUILD_URL}",
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
