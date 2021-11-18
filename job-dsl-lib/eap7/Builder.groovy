package eap7

class Builder {

    String jobName
    String branch

    def build(factory) {

        if (jobName == null) {
            jobName = 'eap-' + branch
        }

        factory.with {
            pipelineJob(jobName + '-build') {

                definition {
                    cps {
                    script(readFileFromWorkspace('pipelines/wildfly-pipeline'))
                    sandbox()     
                    }
                }
                logRotator {
                    daysToKeep(30)
                    numToKeep(10)
                    artifactDaysToKeep(60)
                    artifactNumToKeep(5)
                }
                parameters {
                    stringParam {
                    name ("GIT_REPOSITORY_URL")
                    defaultValue("git@github.com:jbossas/jboss-eap7.git")
                    }
                    stringParam {
                    name ("GIT_REPOSITORY_BRANCH")
                    defaultValue(branch)
                    }
                    stringParam {
                    name ("MAVEN_HOME")
                    defaultValue("/opt/apache/maven")
                    }
                    stringParam {
                    name ("JAVA_HOME")
                    defaultValue("/opt/oracle/java")
                    }
                    stringParam {
                    name ("MAVEN_SETTINGS_XML")
                    defaultValue("/opt/tools/settings.xml")
                    }
                    stringParam {
                    name ("HARMONIA_SCRIPT")
                    defaultValue("eap-job/olympus.sh")
                    }
                }
            }
        }

        factory.with {
            pipelineJob(jobName + '-testsuite') {

                definition {
                    cps {
                    script(readFileFromWorkspace('pipelines/wildfly-pipeline'))
                    sandbox()
                    }
                }
                logRotator {
                    daysToKeep(30)
                    numToKeep(10)
                    artifactDaysToKeep(60)
                    artifactNumToKeep(5)
                }
                parameters {
                    stringParam {
                    name ("GIT_REPOSITORY_URL")
                    defaultValue("git@github.com:jbossas/jboss-eap7.git")
                    }
                    stringParam {
                    name ("GIT_REPOSITORY_BRANCH")
                    defaultValue(branch)
                    }
                    stringParam {
                    name ("MAVEN_HOME")
                    defaultValue("/opt/apache/maven")
                    }
                    stringParam {
                    name ("JAVA_HOME")
                    defaultValue("/opt/oracle/java")
                    }
                    stringParam {
                    name ("MAVEN_SETTINGS_XML")
                    defaultValue("/opt/tools/settings.xml")
                    }
                    stringParam {
                    name ("HARMONIA_SCRIPT")
                    defaultValue("eap-job/olympus.sh")
                    }
                }
            }
        }
    }
}