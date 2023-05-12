pipeline {
    agent any
    environment {
        JAVA_HOME = tool 'jdk-11' // Spécifiez ici la version de Java que vous utilisez pour votre application
    }
    stages {
        stage('---clean---') {
            steps {
                sh "ant clean" // Pour nettoyer votre projet Java Swing
            }
        }
        stage('--compile--') {
            steps {
                sh "ant compile" // Pour compiler votre projet Java Swing
            }
        }
        stage('--test--') {
            steps {
                sh "ant test" // Pour exécuter les tests unitaires de votre projet Java Swing
            }
        }
        stage('--package--') {
            steps {
                sh "ant package" // Pour créer un package de votre application Java Swing
            }
        }
        stage('Build') {
            steps {
                sh "ant build" // Pour construire votre application Java
            }
        }
        stage('Run') {
            steps {
                sh "java -jar C:/Program Files/Java/jdk-11/bin/java.exe"
            }
        }
    }
}
