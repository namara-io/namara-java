Namara
======

The official Java client for the Namara Open Data service. [namara.io](http://namara.io)

## Installation

You need to install the pom.xml dependencies.

```bash
git clone git@github.com:namara-io/namara-java.git
```

## Usage

### Instantiation

You need a valid API key in order to access Namara (you can find it in your My Account details on namara.io).
Update config.py with your API_KEY

```java
import Namara
Namara namara = new Namara({YOUR_API_KEY});
```

You can also optionally enable debug mode:

```java
Namara namara = new Namara({YOUR_API_KEY}, true);
```

### Getting Data

To make a basic request to the Namara API you can call `get` on your instantiated object and pass it the ID of the dataset you want and the ID of the version of the data set:

Synchronous:

```java
response = namara.get("18b854e3-66bd-4a00-afba-8eabfc54f524", "en-2");
```

Without a third options argument passed, this will return data with the Namara default offset (0) and limit (10) applied. To specify options, you can pass an options argument:

```java
HashMap<String, String> options = new HashMap<String, String>();
options.put("offset", "0");
options.put("limit", "150");

namara.get('18b854e3-66bd-4a00-afba-8eabfc54f524', 'en-2', options);
```

### Options

All [Namara data options](http://namara.io/#/api) are supported.

**Basic options**

```java
HashMap<String, String> options = new HashMap<String, String>();
options.put("select", "p0,p1");
options.put("where", "p0 = 100 AND nearby(p3, 43.25, -123.1, 10km)");
options.put("offset", "0");
options.put("limit", "150");
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

You can run the test using your favorite IDE.

From command line:

```bash
java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore src/test/java/NamaraTest
```