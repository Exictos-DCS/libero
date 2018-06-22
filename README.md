# Libero

A container agnostic deployment management tool for application servers

## Currently Supports
 * WebSphere Application Server
 * WildFly
 * Windows Services

## Getting Started

1. Clone the project
```sh
git clone git@gitlab.dcs.exictos.com:devops/libero.git
```

2. Run the following command on the root of the project:
```sh
gradlew build
```

## Usage

### Installing an application in a WildFly profile

```groovy
Container wildfly = new WildFly("***REMOVED***", 49990, ***REMOVED***, ***REMOVED***.toCharArray())

wildfly.connect()
wildfly.installAppWithRollBack("C:/packages/irc/IRC_WS_BBEAR.ear","IRC_WS_BB")
```

### Installing an application in a WAS profile

```groovy
Container was = new WebSphere("***REMOVED***", 8881, ***REMOVED***, ***REMOVED***.toCharArray(), "C:/IBM/WebSphere/AppServer/bin/wsadmin.bat")

was.installAppWithRollBack("C:/packages/irc/IRC_WS_BBEAR.ear","IRC_WS_BB")
```

### Starting an application in a WildFly/WebSphere profile

```groovy
Container appserver = new WildFly/WebSphere(...)

// Explicitly connect if deploying to WildFly
appserver.connect()

String deployment = appserver.profile.listInstances("IRC_WS_BB").first().getName()

appserver.startApp(deployment)
```

### Listing all installed applications in a profile

```groovy
Container appserver = new WildFly/WebSphere(...)

appserver.connect()

println "Installed applications: "
appserver.profile.listInstalledApplications().each{ instance ->
    println "\t - instance"
}

```