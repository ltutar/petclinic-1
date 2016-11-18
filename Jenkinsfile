node {
    properties([
            parameters([
                    string(defaultValue: 'bla', 
			   description: 'Version from XLR', 
			   name : 'version')
            ]),
            pipelineTriggers([])
    ])
    stage('Build and import') {
        docker.image('maven:3.3.9-jdk-8').inside('-l io.rancher.container.network=true') {
            withEnv(["version=${version}"]) {
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
