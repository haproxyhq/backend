#HaProxy HQ
HaProxy HQ is a project based on the implementations of @buettner123 and @jdepoix. For the usage in the Cloud Foundry Service Broker enviornment we have forked the project and made some smaller changes to fit into our usage scenario.

The original implementation is located under:
- [HAProxyHQ/Backend](https://github.com/haproxyhq/backend) - This is the backend, which takes care of managing HAProxy instances and rolling out configs. Implemented in Java Spring.

- [HAProxyHQ/Agent](https://github.com/haproxyhq/agent) - This is the agent, which runs on every HAProxy instance and takes care of communication between the instance and the HAProxyHQ/Backend and applies settings, made by the user. Implemented in Python 2.7.

##HAProxyHQ/Backend/Introduction
This is the HAProxyHQ/Backend. It takes care of managing all the HAProxyHQ/Agent instances by rolling out configs and monitoring their health, also it provides an REST API which is used by the HAProxyHQ/Frontend to retrieve the displayed information.

##HAProxyHQ/Backend/Requirements
You'll need to have a MongoDB and some kind of MQTT broker like Mosquitto/EMQTT running.

##HAProxyHQ/Backend/Setup
Before you can get started, you'll need to add some information to the property files.
The backend will initially create the database schema and insert some needed information to it.

All application settings are stored in:
>src/main/resources/application.yml
