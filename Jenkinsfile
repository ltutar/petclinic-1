#!groovy

node {
  stage 'Build and Test'
  checkout scm
  mvn 'clean install xldeploy:import'
}

def mvn(args) {
	sh "${tool 'Maven 3'}/bin/mvn ${args}"
}