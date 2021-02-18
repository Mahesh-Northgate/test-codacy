@Library("test-codacy@main") _

pipeline {
  agent any
  stages {
    stage('stage1') {
      steps {
        echo "This is the $BUILD_NUMBER of demo $DEMO"
        script{
            String data = testLibrary.call()
            println data
        }
        
        echo testLibrary()
        
      }
    }

  }
  environment {
    DEMO = '1.3'
  }
}
