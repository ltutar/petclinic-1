#!groovy

node('docker') {
    properties([
            parameters([
                    string(defaultValue: 'bla', 
			   description: 'Version from XLR', 
			   name : 'version')
            ]),
            pipelineTriggers([])
    ])
	stage('Build and import') {
		withEnv(["version=${version}"]) {
			checkout scm  
			mvn 'versions:set -DnewVersion=' + version
			mvn 'clean install xldeploy:import'
		}
	}
}

def mvn(args) {
	sh "mvn ${args}"
}
