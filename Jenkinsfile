#!groovy

node {
  stage 'Build and Test'
  properties([[$class: 'ParametersDefinitionProperty',
	  parameterDefinitions: [[$class: 'StringParameterDefinition', defaultValue: '', description: 'Version from XLR', name : 'version']]]])	
  checkout scm  
  mvn 'versions:set -DnewVersion=' + ${VERSION}
  mvn 'clean install xldeploy:import'
}

def mvn(args) {
	sh "${tool 'Maven 3'}/bin/mvn ${args}"
}