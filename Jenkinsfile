pipeline {
  agent any
  stages {
    stage('stage1') {
      steps {
        sh '"This is the $BUILD_NUMBER of demo $DEMO"'
        bat '"This is the $BUILD_NUMBER of demo $DEMO"'
      }
    }

  }
  environment {
    DEMO = '1'
  }
}