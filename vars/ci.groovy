def call() {
    pipeline {

        agent {
            label 'workstation'
        }

        stages {

            stege('Compile/Builed') {
                steps {
                    echo 'compile'
                }
            }

            stege('Unit Tests') {
                steps {
                    echo 'unit tests'
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

