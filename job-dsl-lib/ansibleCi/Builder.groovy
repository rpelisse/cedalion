package ansibleCi

class Builder {

    String projectName
    String git_url = "https://github.com/ansible-middleware/"
    String branch
    String schedule = 'H/10 * * * *'

    def build(factory) {
        factory.with {
            pipelineJob('ansible-ci-' + projectName) {

                definition {
                    cps {
                        script(readFileFromWorkspace('pipelines/ansible-pipeline'))
                        sandbox()
                    }
                }
                logRotator {
                    daysToKeep(30)
                    numToKeep(10)
                    artifactDaysToKeep(60)
                    artifactNumToKeep(5)
                }
                triggers {
                    scm (schedule)
                }
                parameters {
                    stringParam {
                      name("PROJECT_NAME")
                      defaultValue(projectName)
                    }
                    stringParam {
                      name ("PATH_TO_SCRIPT")
                      defaultValue("build-collection.sh")
                    }
                    stringParam {
                      name ("GIT_REPOSITORY_URL")
                      defaultValue(git_url + projectName + ".git")
                    }
                    stringParam {
                      name ("GIT_REPOSITORY_BRANCH")
                      defaultValue("main")
                    }
                    stringParam {
                      name ("BUILD_PODMAN_IMAGE")
                      defaultValue("localhost/ansible")
                    }
                    stringParam {
                      name ("VERSION")
                      defaultValue("")
                    }
                }
            }
        }
    }
}
