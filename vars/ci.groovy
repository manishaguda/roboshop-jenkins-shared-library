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
                 steps {
                     withAWSParameterStore(credentialsId: '', naming: 'relative', path: '/service', recursive: true, regionName: 'eu-west-1') {
                         {
                             echo "USER = ${env.SONARQUBE_USER}"
                     }

                    }


                        //'sonar-scanner -Dsonar.host.url=http://172.31.81.169:9000 -Dsonar.login=${SONAR_USER} -Dsonar.password=${SONAR_PASS} -Dsonar.projectKey=cart'

                    }
                }

                stage('Upload Code To Centralized Place') {
                    steps {
                        echo 'upload'
                    }
                }
            }

        }
    }catch(Exception e) {
    common.email("Failed")
    }
}