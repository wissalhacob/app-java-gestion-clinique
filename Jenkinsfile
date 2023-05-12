pipeline {
    agent any
    environment {
        JAVA_HOME = tool 'jdk-11' // Spécifiez ici la version de Java que vous utilisez pour votre application
    }
    stages {
        stage('---clean---') {
            steps {
                bat "mvn clean" // Pour nettoyer votre projet Java Swing
            }
        }
        stage('--compile--') {
            steps {
                bat "mvn compile" // Pour compiler votre projet Java Swing
            }
        }
        stage('--test--') {
            steps {
                bat "mvn test" // Pour exécuter les tests unitaires de votre projet Java Swing
            }
        }
        stage('--package--') {
            steps {
                bat "mvn package" // Pour créer un package de votre application Java Swing
            }
        }
        stage('Build') {
            steps {
                bat "mvn install" // Pour construire votre application Java
            }
        }
        stage('Run') {
            steps {
                bat "java C:/Program Files/Java/jdk-11"
            }
        }
    }
}
