# Master-MicroTask02_TopologyAPIs

TopologyAPIs is an API designed for Task02 for the Junior Software Engineer Job at Master-Micro. It is designed to read, write and manipulate data on JSON files.

## Installation

Clone the package in the needed directory by using this command

```bash
git clone https://github.com/OmarMostafaFadl/Master-MicroTask02_TopologyAPIs.git
```

## Source Codes
The script **Topologies.java** contains all the APIs. \
The script **App.java** is the main class. \
The scripts **Nmos.java** and **Resistor.java** contains all the saved data read from the JSON files.

## Usage

```java
// Saves the JSON file data in the memory
readJSON("FILENAME");

// Writes a JSON file from the data saved in the memory
writeJSON("FILENAME", "TOPOLOGY_ID");

// Prints all the topologies saved in the memory
queryTopologies();

// Deletes a given topology from memory
deleteTopology("TOPOLOGY_ID");

// Prints all the devices in a specific topology
queryTopology("TOPOLOGY_ID");

// Prints all the devices connected to a specific Netlist Node in a specific topology
queryDevicesWithNetlistNode("TOPOLOGY_ID", "NETLIST_NODE");
```

Please open the Java scripts and read the functions' docstrings for further understanding.