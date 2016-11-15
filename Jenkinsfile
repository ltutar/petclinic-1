#!groovy

node {
  stage 'Build and Test'
  checkout scm  
  mvn 'versions:set -DnewVersion=1.1.1'
  mvn 'clean install xldeploy:import'
}

def mvn(args) {
	sh "${tool 'Maven 3'}/bin/mvn ${args}"
}
