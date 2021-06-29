pipeline {
    agent any
    tools { 
        maven "Maven-3.8.1"
        
    }
    stages {

        stage("SonarQube Quality Gate Check"){
            steps{
                script{
                    withSonarQubeEnv("sonarqube-server"){
                        sh "mvn sonar:sonar"
                    }
                    timeout(time: 5, unit:"MINUTES"){
                        def qg = waitForQualityGate()
                            if(qg.status != "OK"){
                                error "Pipeline aborted due to quality failure: ${qg.status}"
                                return "Quality Check Failed"
                            }
                    }
                }
            }
        }
        
        stage ("Build") {
            steps {
                sh "mvn clean compile"
                echo "Building..."
            }
        }

        stage ("Test") {
            steps {
                sh "mvn test"
                echo "Testing..."
            }
        }

        stage ("Deploy") {
            steps {
                sh "mvn install"
                ansiblePlaybook(
                    playbook: "/mongopb.yml",
                    colorized:true
                )
                echo "Deploying..."
            }
        }
    }
}