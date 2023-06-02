def compile() {
    if (app_lang == "nodejs") {
        sh 'npm install'
    }

    if (app_lang == "maven") {
        sh 'mvn package'
    }

 }
 def unittests() {

    if (app_lang == "nodejs") {
        //Developer is missing unit test cases in projects, He need to add them as best practice, We are skipping to proceed forthure
    sh 'npm test'

    }

    if (app_lang == "maven") {
        sh 'mvn test'
    }

    if (app_lang == "python") {
        sh 'python3 -m unittest'
    }
 }

def email(email_note) {
    println email_note
}
