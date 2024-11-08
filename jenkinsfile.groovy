pipeline {
    agent any
    environment {
        AWS_ACCESS_KEY = credentials('aws_access_key') 
        AWS_SECRET_KEY = credentials('aws_secret_key') 
    }
    stages{
        stage('Terraform Init'){
            steps{
                bat 'terraform init'
            }
        }
        stage('Terraform Format'){
            steps{
                bat 'terraform fmt'
            }
        }
        stage('Terraform Validate'){
            steps{
                bat 'terraform validate'
            }
        }
        stage('Terraform Plan'){
            steps{
                bat 'terraform plan -var "access_key=${AWS_ACCESS_KEY}" -var "secret_key=${AWS_SECRET_KEY}"'
            }
        }
        stage('Terraform Apply'){
            steps{
                input message : "Confirm Deployment?", ok:"Deploy"
                bat 'terraform apply -var "access_key=${AWS_ACCESS_KEY}" -var "secret_key=${AWS_SECRET_KEY}" --auto-approve'
            }
        }

    }
    
}
