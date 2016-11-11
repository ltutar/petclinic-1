#!groovy

node {
  stage 'Build and Test'
  env.PATH = "${tool 'Maven 3'}/bin:${env.PATH}"
  checkout scm
  mvn 'clean install xldeploy:import'
}

def mvn(args) {
	sh "${tool 'Maven 3.x'}/bin/mvn ${args}"
}