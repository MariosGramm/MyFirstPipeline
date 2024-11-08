terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region     = var.region
  access_key = var.access_key
  secret_key = var.secret_key 
}

resource "aws_vpc" "instance_vpc" {
  cidr_block = "10.0.0.0/16"

}

resource "aws_subnet" "subnet1" {
    vpc_id            = aws_vpc.instance_vpc.id
    cidr_block        = "10.0.0.0/24"
    availability_zone = "us-east-1a" 
}

resource "aws_internet_gateway" "my_igw" {
    vpc_id            = aws_vpc.instance_vpc.id
  
}

resource "aws_route_table" "my_rt" {
    vpc_id = aws_vpc.instance_vpc.id

    route {
        cidr_block = "0.0.0.0/0"    #default route
        gateway_id = aws_internet_gateway.my_igw.id
    }
}

resource "aws_route_table_association" "rta" {
    subnet_id      = aws_subnet.subnet1.id
    route_table_id = aws_route_table.my_rt.id

}

resource "aws_security_group" "my_sg" {
    description = "Web Traffic Allowance"
    vpc_id      = aws_vpc.instance_vpc.id

    ingress {
        description = "SSH"
        from_port   = 22
        to_port     = 22
        protocol    = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }

    ingress {
        description = "HTTPS"
        from_port   = 443
        to_port     = 443
        protocol    = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }

    ingress {
        description = "HTTP"
        from_port   = 80
        to_port     = 80
        protocol    = "tcp"
        cidr_blocks = ["0.0.0.0/0"] 
    }

    egress {
        from_port   = 0
        to_port     = 0
        protocol    = -1
        cidr_blocks = ["0.0.0.0/0"] 
    }

}

