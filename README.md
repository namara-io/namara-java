Namara
======

The official Java client for the Namara Open Data service. [namara.io](https://namara.io)

## Installation

You need to install the pom.xml dependencies using [Maven](https://maven.apache.org/index.html).

```bash
git clone git@github.com:namara-io/namara-java.git
```

## Usage

### Instantiation

You need a valid API key in order to access Namara (you can find it in your My Account details on namara.io).

```java
import com.tdw.Namara;

Namara namara = new Namara({YOUR_API_KEY});
```

You can also optionally enable debug mode:

```java
Namara namara = new Namara({YOUR_API_KEY}, true);
```

### Getting Data

To make a basic request to the Namara API you can call `get` on your instantiated object and pass it the ID of the data set you want and the version of the data set:

Synchronous:

```java
response = namara.get("5885fce0-92c4-4acb-960f-82ce5a0a4650", "en-1");
```

Without a third options argument passed, this will return data with the Namara default offset (0) and limit (250) applied. To specify options, you can pass an options argument:

```java
HashMap<String, String> options = new HashMap<String, String>();
options.put("offset", "0");
options.put("limit", "150");

namara.get('5885fce0-92c4-4acb-960f-82ce5a0a4650', 'en-1', options);
```

### Options

All [Namara data options](https://namara.io/#/api) are supported.

**Basic options**

```java
HashMap<String, String> options = new HashMap<String, String>();
options.put("select", "town,geometry");
options.put("where", "town = "TORONTO" AND nearby(geometry, 43.6, -79.4, 10km)");
options.put("offset", "0");
options.put("limit", "20");
```

**Aggregation options**
Only one aggregation option can be specified in a request, in the case of this example, all options are illustrated, but passing more than one in the options object will throw an error.

```java
HashMap<String, String> options = new HashMap<String, String>();
options.put("operation", "sum(p0)");
options.put("operation", "avg(p0)");
options.put("operation", "min(p0)");
options.put("operation", "max(p0)");
options.put("operation", "count(*)");
options.put("operation", "geocluster(p3, 10)");
options.put("operation", "geobounds(p3)");
```

### Running Tests

From command line:

```bash
mvn test
```

### License

Apache License, Version 2.0
