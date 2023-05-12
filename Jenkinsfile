pipeline {
    agent any
    environment {
        JAVA_HOME = tool 'jdk-11' // Spécifiez ici la version de Java que vous utilisez pour votre application
    }
    stages {
        stage('---clean---') {
            steps {
                bat "ant clean" // Pour nettoyer votre projet Java Swing
            }
        }
        stage('--compile--') {
            steps {
                bat "ant compile" // Pour compiler votre projet Java Swing
            }
        }
        stage('--test--') {
            steps {
                bat "ant test" // Pour exécuter les tests unitaires de votre projet Java Swing
            }
        }
        stage('--package--') {
            steps {
                bat "ant package" // Pour créer un package de votre application Java Swing
            }
        }
        stage('Build') {
            steps {
                bat "ant install" // Pour construire votre application Java
            }
        }
        stage('Run') {
            steps {
                bat "java -jar C:/Program Files/Java/jdk-11"
            }
        }
    }
}
