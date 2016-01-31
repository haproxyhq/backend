#HAProxyHQ
HAProxyHQ is the headquarter for all your HAProxy instances. It allows you to configure and manage different HAProxy instances, while keeping track of they're health status. The project consists of three different repositories:
- [HAProxyHQ/Backend](https://github.com/haproxyhq/backend) - This is the backend, which takes care of managing HAProxy instances and rolling out configs. Implemented in Java Spring.
- [HAProxyHQ/Frontend](https://github.com/haproxyhq/frontend) - This is the frontend, which provides a simple user interface. Implemented in Angular 2.
- [HAProxyHQ/Agent](https://github.com/haproxyhq/agent) - This is the agent, which runs on every HAProxy instance and takes care of communication between the instance and the HAProxyHQ/Backend and applys settings, made by the user. Implementes in Python 2.7.

##HAProxyHQ/Backend/Introduction
This is the HAProxyHQ/Backend. It takes care of managing all the HAProxyHQ/Agent instances by rolling out configs and monitoring their health, also it provides an REST API which is used by the HAProxyHQ/Frontend to retrieve the displayed information.

##HAProxyHQ/Backend/Requirements
You'll need to have a Postgres Databse, MongoDB and some kind of MQTT broker like Mosquitto running.

##HAProxyHQ/Backend/Setup
Before you can get started, you'll need to add some information to the propertie files.

Setup Postgres in:
>src/main/resources/application-model-sql.properties

Setup MongoDB in:
>src/main/resources/application-model-nosql.properties

Setup MQTT in:
>src/main/resources/application-model-mqtt.properties

The HAProxyHQ/Backend sends out emails to new users, therefore you'll also need to setup mail in:
>src/main/resources/application.properties