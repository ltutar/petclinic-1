#!groovy

node {
	stage('Build') {
      properties([[$class: 'ParametersDefinitionProperty',
	  parameterDefinitions: [[$class: 'StringParameterDefinition', defaultValue: 'bla', description: 'Version from XLR', name : 'version']]]])	
	}
  checkout scm  
  mvn 'versions:set -DnewVersion=' + version
  mvn 'clean install xldeploy:import'
}

def mvn(args) {
	sh "${tool 'Maven 3'}/bin/mvn ${args}"
}
