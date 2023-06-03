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
                              SONAR_USER = sh(script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.user  --with-decryption  | sed \'s/"//g\')'
                              SONAR_PASS = sh(script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.pass  --with-decryption | sed \'s/"//g\')'
                  }


                    }
                    steps {

                        sh "sonar-scanner -Dsonar.host.url=http://172.31.81.169:9000 -Dsonar.login=${SONAR_USER} -Dsonar.password=${SONAR_PASS} -Dsonar.projectKey=${component} -Dsonar.qualitygate.wait=true ${SONAR_EXTRA_OPTS}"
                        sh "echo Sonar Scan"
                    }
                }

                stage('Upload Code To Centralized Place') {
                    steps {
                        echo 'upload'
                    }
                }
            }

        }
    } catch(Exception e) {
      common.email("Failed")
    }
}