# MRestApiTool
Query RESTful API use on java and android.

This library project is base on retrofit, must use retrofit code rule.

### Base usage
```java
JsonApiUtils baseApiUtils = new JsonApiUtils(SampleConst.SERVICE_URL);
SampleService sampleService = baseApiUtils.createRetrofitApiService(SampleService.class);
```
In this sample case, SampleService is follow retrofit service interface.

Service interface can refer [retrofit2](http://square.github.io/retrofit/).

And get response:
```java
Call<String> response = sampleService.getMyRepoInfomation("shiho198611", "MRestApiTool");
String result = utils.doApi(response);
```

This library always response `String` type, and parse those response string via json or xml etc..

### Error handle
