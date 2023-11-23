pipeline{
    agent any
    stages {
        stage ('Get Project'){
            steps{
                echo 'Cloning Project from Github'
                git 'https://github.com/BelfastMarsh/PetersPetitions.git'
            }
        }
        stage('Build'){
            steps{
                echo 'Cleaning'
                sh 'mvn clean:clean'
            }
        }
        stage ('Package') {
            steps {
                echo 'Building package'
                sh 'mvn package'
            }
        }
        stage('Test'){
            steps {
                echo 'Testing'
                sh 'mvn test'
            }
        }
        stage ('Archive'){
            steps{
                echo('archiving')
                archiveArtifacts allowEmptyArchive: true,
                    artifacts: '**/PetersPetitions*.war'
            }
        }
        stage ('Authentication'){
            steps{
                script {
                    echo 'Starting deployment process...'
                    userInput = input(
                        id: 'userInput',
                        message: 'Do you want to proceed?',
                        parameters: [
                           choice(
                             choices: 'No\nYes',
                             description: 'Select Yes or No',
                             name: 'Authenticate'
                            )
                        ]
                    )
                }
            }
        }
        stage('Deploy'){
            steps{
                script{
                    if (userInput == 'Yes')
                    {
                        echo 'deploying Peter\'s Petitions App'
                        sh 'docker build -f Dockerfile -t ptrspttnsapp .'
                        sh 'docker rm -f "ppacontainer" || true'
                        sh 'docker run --name "ppacontainer" -p 9090:8080 --detach ptrspttnsapp:latest'
                    }
                    else{
                        echo 'User Aported'
                        currentBuild.result = 'ABORTED'
                    }
                }
            }
        }
    }
}
