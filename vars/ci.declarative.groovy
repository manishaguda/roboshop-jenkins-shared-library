def call() {
    try {
        pipeline {

            agent {
                label 'workstation'
            }

            stages {

                stage('Compile/Build') {
                    steps {
                        script {
                            common.compile()
                        }

                    }
                }

                stage('Unit Tests') {
                    steps {
                        script {
                            common.unittests()
                        }
                    }
                }
                stage('Quality Control') {
                    environment {
                        SONAR_USER = sh ( script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.user --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
                        SONAR_PASS = sh ( script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.pass --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
                        wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs: [[password: "${SONAR_PASS}", var: 'SECRET']]]) {
                            sh "sonar-scanner -Dsonar.host.url=http://172.31.81.169:9000 -Dsonar.login=${SONAR_USER} -Dsonar.password=${SONAR_PASS} -Dsonar.projectKey=${COMPONENT}"
                    }


                     }

                     }

                    }
                }

                stage('Upload Code To Centralized Place') {
                    steps {
                        echo 'upload'
                    }
                }
            } catch(Exception e) {
              common.email("Failed")

            }

        }



