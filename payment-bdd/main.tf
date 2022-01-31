terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 2.13.0"
    }
  }
}

provider "docker" {}

resource "docker_image" "card-scheme" {
  name         = "freng0/payment-java-card-scheme:latest"
  keep_locally = true
}

resource "docker_container" "card-scheme" {
  image = docker_image.card-scheme.latest
  name  = "card-scheme-container"
  ports {
    internal = 80
    external = 8000
  }
}

resource "docker_image" "bank-issuer" {
  name         = "freng0/payment-java-bank-issuer:latest"
  keep_locally = true
}

resource "docker_container" "bank-issuer" {
  image = docker_image.bank-issuer.latest
  name  = "bank-issuer-container"
  ports {
    internal = 8080
    external = 8080
  }
}

resource "docker_image" "zookeeper" {
  name         = "docker.io/bitnami/zookeeper:3.7"
  keep_locally = true
}

resource "docker_container" "zookeeper" {
  image = docker_image.zookeeper.latest
  name  = "zookeeper"
  ports {
    internal = 2181
    external = 2181
  }
  volumes {
    container_path = "/bitnami"
    volume_name = "zookeeper_data"
  }
  env = ["ALLOW_ANONYMOUS_LOGIN=yes"]
}

resource "docker_image" "kafka" {
  name         = "docker.io/bitnami/kafka:2"
  keep_locally = true
}

resource "docker_container" "kafka" {
  image = docker_image.kafka.latest
  name  = "kafka"
  ports {
    internal = 9093
    external = 9093
  }
  volumes {
    container_path = "/bitnami"
    volume_name = "kafka_data"
  }
  env = [
    "KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181",
    "ALLOW_PLAINTEXT_LISTENER=yes",
    "KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP=CLIENT:PLAINTEXT,EXTERNAL:PLAINTEXT",
    "KAFKA_CFG_LISTENERS=CLIENT://:9092,EXTERNAL://:9093",
    "KAFKA_CFG_ADVERTISED_LISTENERS=CLIENT://kafka:9092,EXTERNAL://localhost:9093",
    "KAFKA_INTER_BROKER_LISTENER_NAME=CLIENT"
  ]
}
