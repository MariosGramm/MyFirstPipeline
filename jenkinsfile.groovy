pipeline {
    agent any
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
                bat 'terraform plan'
            }
        }
        stage('Terraform Apply'){
            steps{
                input message : "Confirm Deployment?", ok:"Deploy"
                bat "terraform apply --auto-approve"
            }
        }

    }
    
}