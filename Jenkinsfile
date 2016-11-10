#!groovy

node('!(master)') {
    properties([
            buildDiscarder(
                    logRotator(
                            artifactDaysToKeepStr: '',
                            artifactNumToKeepStr: '',
                            daysToKeepStr: '',
                            numToKeepStr: '2')
            ),
            parameters([
                    string(defaultValue: '74.0',
                            description: 'PIF version to run integration tests with',
                            name: 'PIF_VER'),
                    choice(choices: 'chrome\nfirefox\nsafari\ninternet explorer\nMicrosoftEdge',
                            description: 'Browser to use in integration test',
                            name: 'BROWSER'),
                    choice(choices: 'SK-MACMINI01.browserlab.rabobank.nl\nSK-W10SGC01.browserlab.rabobank.nl',
                            description: '''Machine to run browser tests from:
                                - Win10: SK-W10SGC01.browserlab.rabobank.nl
                                - Mac: SK-MACMINI01.browserlab.rabobank.nl''',
                            name: 'SELENIUM_HOST')
            ]),
            pipelineTriggers([])
    ])

    // Mark the code checkout 'stage'....
    stage('Checkout') {
        // Checkout code from repository
        checkout scm
    }

    def helper = load 'build-helper.groovy'

    // get IBM JDK and set JAVA_HOME
    def jdktool = tool name: 'ibm-java-x86_64-71', type: 'hudson.model.JDK'
    echo "IBM JAVA_HOME ${jdktool}"
    env.JAVA_HOME="${jdktool}"

    def nodetool = tool name: 'NodeJS-v5.9.0-linux-x64 / NPM-3.7.3', type: 'jenkins.plugins.nodejs.tools.NodeJSInstallation'
    echo "node in ${nodetool}"
    env.PATH="${nodetool}/bin:${env.PATH}"

    // Mark the code build 'stage'....
    milestone label: 'Build', ordinal: 10
    stage('Create Package') {
        // Run the maven build.
        helper.mvn "clean package -U -Pci,inc-spa,jenkins,!local-dev,create-standalone-tests"
        // record test result
        helper.captureTestResults 'surefire-reports'
    }

    stage('Check Picoma blacklist') {
        sh "./check-blacklist.sh"
    }

    try {
        milestone label: 'Run integration tests', ordinal: 20
        stage('Start Application') {
            withEnv(["PIF_VER=${PIF_VER}", "DOCKER_HOST_PORT=2375"]) {
                // start server and prepare fitnesse
                helper.mvn "-f fitnesse/pom.xml " +
                        "-Pdocker clean pre-integration-test " +
                        "-Ddocker.host=lsrv5646.linux.rabobank.nl "
            }
        }

        stage('Run Integration Tests') {
            helper.runInParallel SELENIUM_HOST, BROWSER, ["AfvoIsAngular.DockerTests"]
        }
    } catch (Throwable t) {
        // do not swallow exception
        echo "Exception: ${t}"
        throw t;
    } finally {
        stage('Stop Application') {
            try {
                sh "testdata/docker/stop-in-docker.sh"
            } catch (Throwable t) {
                // swallow exception
                echo "Exception: ${t}"
            } finally {
                // archive log files from docker container
                archive 'testdata/target/logs/**/*'
            }
        }

        milestone label: 'Quality checks', ordinal: 30
        stage('Aggregate Code Coverage Data') {
            // Generate code coverage report
            helper.mvn "package -Pinc-coverage-report -DskipTests=true"
            // publish results
            publishHTML(target: [allowMissing: false, alwaysLinkToLastBuild: false, keepAll: true,
                                 reportDir   : 'coverage-report/target', reportFiles: 'index.html',
                                 reportName  : 'Coverage Report'])
        }

        stage('Create Code Quality Report') {
            helper.sonar(true)
        }
    }
}