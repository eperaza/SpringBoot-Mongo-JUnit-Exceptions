pipeline {
    agent any
    tools { 
        maven "Maven-3.8.1"
        
    }
    stages {
        

        stage ("Build") {
            steps {
                sh "mvn clean compile"
                echo "This is a minimal pipeline."
            }
        }
    }
}