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
	stage('Build') {
		withEnv(["version=${version}"]) {
			checkout scm  
			mvn 'versions:set -DnewVersion=' + version
			mvn 'clean install'
		}
	}
	stage('XL Deploy import') {
		mvn 'xldeploy:import'
	}
}

def mvn(args) {
	sh "${tool 'Maven 3'}/bin/mvn ${args}"
}
