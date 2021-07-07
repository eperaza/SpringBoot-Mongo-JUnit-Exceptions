pipeline {
    agent any
    tools { 
        maven "Maven-3.8.1"
        
    }
    stages {

        stage ("Build") {
            steps {
                sh "mvn clean compile"
                echo "Building..."
                jacoco( 
                    execPattern: 'target/*.exec',
                    classPattern: 'target/classes',
                    sourcePattern: 'src/main/java',
                    exclusionPattern: 'src/test*'
                )
            }
        }

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
        
        

        stage ("Test") {
            steps {
                sh "mvn test"
                echo "Testing..."
            }
        }

        stage ("Deploy") {
            steps {
                sh "mvn install"
                ansiColor('xterm') {
                    ansiblePlaybook(
                        playbook: "/var/jenkins_home/workspace/mvn-sonar/mongopb.yml",
                        disableHostKeyChecking: true,
                        colorized:true
                    )
                    echo "Deploying..."
                }
            }
        }
    }
}