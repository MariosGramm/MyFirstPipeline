pipeline {
    agent any
    environment {
        AWS_ACCESS_KEY = credentials('94cd2fc7-c66d-44f6-b0f6-4f8522c6105a') 
        AWS_SECRET_KEY = credentials('aws_secret_key') 
        AWS_REGION = credentials('AWS_REGION')
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
                bat 'terraform plan -var "access_key=${AWS_ACCESS_KEY}" -var "secret_key=${AWS_SECRET_KEY}" var "region=$AWS_REGION"'
            }
        }
        stage('Terraform Apply'){
            steps{
                input message : "Confirm Deployment?", ok:"Deploy"
                bat 'terraform apply -var "access_key=${AWS_ACCESS_KEY}" -var "secret_key=${AWS_SECRET_KEY}" var "region=$AWS_REGION" --auto-approve'
            }
        }

    }
    
}
