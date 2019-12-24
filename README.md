# The Metrics System

Monitoring is the process of maintaining surveillance over the existence and magnitude of state change and data flow in a system. Monitoring aims to identify faults and assist in their subsequent elimination.

Metrics are a measurement at a point in time for the system. This unit of measure can have the value, timestamp, and identifier of what that value applies to (like a source or a tag). Metrics are typically collected at fixed-time intervals. These are referred to as the resolution.

This project was build for enabling developers easy to monitor their systems.

## How the system works


![oops](https://raw.githubusercontent.com/mohistzh/metrics/master/static/Workflow.jpg)

* **Metrics Instance** A data object designed for each metric component configuration
* **Console** console module provides API to manage metrics instance
* **Scheduler** poller module for executing jobs
* **Infra Collector** built-in metrics collectors, such as Redis, MySQL, RabbitMQ, etc.
* **Transactors** to subscribe metrics instance.
* **TSDB** A time series database which InfluxDB is my choice.
* **Metrics SDK** Metrics SDK provides a way to collect application layer data, you can roll your own metrics
* **Visualization** Grafana is a great platform ;-)


## Metrics Data Structure

### Some terms

* **SLI** Service Level Indicator (SLI) is a measure of the service level provided by a service provider to a customer.
* **SLO** A service-level objective (SLO) is a key element of a service-level agreement (SLA) between a service provider and a customer. SLOs are agreed upon as a means of measuring the performance of the Service Provider and are outlined as a way of avoiding disputes between the two parties based on misunderstanding.



### Our System DS

Type  			 	  | Description
------------------ | -------------
timestamp(number)  | milliseconds
key(string)  | name of SLI. e.g. cpu.usage
value(number)  | value of SLI. e.g. 0.5
tags(k/v)  | current context information

### Measurement

Measurement Type  			 	  | Description
------------------ | -------------
Meter  | A meter measures the rate of events over time (e.g., “requests per second”).
Gauge  | A gauge is an instantaneous measurement of a value.
Counter  | A counter is just a gauge for an AtomicLong instance. You can increment or decrement its value.
Histogram  | A histogram measures the statistical distribution of values in a stream of data. In addition to minimum, maximum, mean, etc., it also measures median, 75th, 90th, 95th, 98th, 99th, and 99.9th percentiles.
Timer  | A timer measures both the rate that a particular piece of code is called and the distribution of its duration.

