pipeline{
    agent any
    stages {
        stage ('GetProject'){
            steps{
                git 'https://github.com/BelfastMarsh/PetersPetitions.git'
            }
        }
        stage('Build'){
            steps{
                sh 'mvn package'
            }
        }
        stage ('Archive'){
            steps{
                archiveArtifacts allowEmptyArchive: true,
                    artifacts: '**PetersPetitions*.war'
            }
        }
        stage('Deploy'){
            steps{
                sh 'docker build -f Dockerfile -t ptrspttnsapp .'
                sh 'docker rm -f "ppacontainer" || true'
                sh 'docker run --name "ppacontainer" -p 9090:8080 --detach ptrspttnsapp:latest'
            }
        }
    }
}