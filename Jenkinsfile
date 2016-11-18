node {
    stage('Build and import') {
        docker.image('maven:3.3.9-jdk-8').inside {
                checkout scm  
                mvn 'versions:set -DnewVersion=1.1.1'
                mvn 'clean install xldeploy:import'
        }
	}
}

def mvn(args) {
	sh "mvn ${args}"
}
