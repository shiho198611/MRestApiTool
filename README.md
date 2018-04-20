# MRestApiTool
Query RESTful API use on java and android.

This library project is base on retrofit, must use retrofit code rule.

### Base usage
```java
JsonApiUtils jsonApiUtils = new JsonApiUtils(SampleConst.SERVICE_URL);
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
By implement `ErrorHandler` to define error handle.

And set ErrorHandler to api utils:
```java
JsonApiUtils jsonApiUtils = new JsonApiUtils(SampleConst.SERVICE_URL);
jsonApiUtils.setErrorHandler(new JsonErrorHandler());
```
### Custom ApiUtils
This library provide for json data api utils.

But it's can parse another data by extend `BaseApiUtils`.
### Package library
Add below url in gradle repositories:
```gradle
jcenter({url "https://dl.bintray.com/shiho198611/library/"})
```
And in gradle dependencies:
```gradle
compile 'com.shiho:mrestapitool:1.0.1'
```
