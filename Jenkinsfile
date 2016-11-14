#!groovy

properties([parameters([string(defaultValue: 'version', description: '', name: 'VERSION')]), pipelineTriggers([])])

node {
  stage 'Build and Test'
  build job: 'build', parameters: [[$class: 'StringParameterValue', name: 'VERSION', value: version]]  
  checkout scm  
  mvn 'versions:set -DnewVersion=' + env.VERSION
  mvn 'clean install xldeploy:import'
}

def mvn(args) {
	sh "${tool 'Maven 3'}/bin/mvn ${args}"
}