# Aprendendo Apache Kafka
Projeto desenvolvido no decorrer do curso de Apache Kafka da Udemy.

# Configurações Iniciais do Projeto
- Necessário ter Java versão 8 instalada
- Subir na máquina localhost um servidor Apache Kafka.
  No decorrer do curso foi usada release 2.12-2.2.0.
- Baixar Apache Kafka em https://kafka.apache.org/downloads
- Adicionar pasta bin na variavel de ambiente PATH

## Iniciando servidor
kafka-server-start.sh ../config/server.properties

# Iniciando Zookeeper
zookeeper-server-start.sh ../config/zookeeper.properties

## Arquivo de configuração zookeeper:
config/zookeeper.properties

## Arquivo de configuração kafka:
config/server.properties

## Arquivos para execução dos comandos CLI:
bin/<nome do arquivo>.sh
