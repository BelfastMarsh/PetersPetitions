pipeline{
    agent any
    stages {
        stage ('Get Project'){
            steps{
                echo 'Cloning Project from Github'
                git branch: 'test', url: 'https://github.com/BelfastMarsh/PetersPetitions.git'
            }
        }
        stage('Build'){
            steps{
                echo 'Building package'
                sh 'mvn package'
            }
        }
        stage('Test'){
            steps {
                echo 'Testing'
            }
        }
        stage ('Archive'){
            steps{
                echo('archiving')
                archiveArtifacts allowEmptyArchive: true,
                    artifacts: '**PetersPetitions*.war'
            }
        }
       /*
       no authentication needed in test
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
        }*/
        stage('Deploy'){
            steps{
                script{
                    if (userInput == 'Yes')
                    {
                        echo 'deploying Peter\'s Petitions App'
                        sh 'docker build -f Dockerfile -t ptrspttnsapptest .'
                        sh 'docker rm -f "ppacontainertest" || true'
                        sh 'docker run --name "ppacontainertest" -p 9091:8080 --detach ptrspttnsapptest:latest'
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
