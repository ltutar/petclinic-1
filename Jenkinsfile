#!groovy

node {
    properties([
            parameters([
                    string(defaultValue: 'bla', 
			   description: 'Version from XLR', 
			   name : 'version')
            ]),
            pipelineTriggers([])
    ])
	docker.image('maven:3.3.3-jdk-8').inside {
	stage('Build and import') {
		withEnv(["version=1.1.2"]) {
			checkout scm  
			mvn 'versions:set -DnewVersion=' + version
			mvn 'clean install xldeploy:import'
		}
	}
	}
}

def mvn(args) {
	sh "mvn ${args}"
}
