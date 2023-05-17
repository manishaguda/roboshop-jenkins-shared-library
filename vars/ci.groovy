def call() {
    pipeline {

        agent {
            label 'workstation'
        }

        stages {

            stage('Compile/Builed') {
                steps {
                    script{
                        common.compile()
                    }

                }
            }

            stage('Unit Tests') {
                steps {
                    echo 'Unit Tests'
                }
            }
            stage('Quality Control')  {
                steps {
                    echo 'quality control'
                }
            }

            stage('Upload Code To Centralized Place') {
                steps {
                    echo 'upload'
                }
            }
        }

    }
 }

