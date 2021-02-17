@Library("test-codacy@main")
pipeline {
  agent any
  stages {
    stage('stage1') {
      steps {
        echo "This is the $BUILD_NUMBER of demo $DEMO"
        testLibrary.testLibrary
      }
    }

  }
  environment {
    DEMO = '1.3'
  }
}
