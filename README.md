ThreadLocal Leak Demo
=====================

How to build
------------

```shell
$ ./mvnw clean package -DskipTests
```

How to run
----------

```shell
$ java -Xms400m -Xmx400m -jar target/threadlocal-leak-demo-0.0.1-SNAPSHOT.jar
```

How to use
----------

```shell
# find pid
$ jps
31804 threadlocal-leak-demo-0.0.1-SNAPSHOT.jar
31885 Jps

# monitor gc status
$ jstat -gcutil 31804 2s
  S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT     GCT
  0.00  24.93  67.42   2.05  92.68  89.88      3    0.017     1    0.020    0.036
  0.00  24.93  67.42   2.05  92.68  89.88      3    0.017     1    0.020    0.036

# Trigger thread local leak
$ curl localhost:8080/leak/400000
Start leak 400000KB memory done

# Check app log and gc status
```

Endpoints
---------

|Endpoint|Desc|
|:-------|:---|
|http://localhost:8080/leak/{n}|Fill `n`KB data into ThreadLocals|
|http://localhost:8080/leak/slow/{n}|Same as up, slowly|
|http://localhost:8080/{n}|Fill, then clean|
|http://localhost:8080/slow/{n}|Slowly|