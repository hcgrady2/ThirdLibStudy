#### 1、OkhttpClient
 这个类里面可以对全局网络请求进行配置，如超时、拦截器 等。

 通过 newCall 方法返回一个真正的网络请求对象 RealCall


#### 2、Request
 代表一个网络请求，里面封装了 url,http 方法，get 或者 post 等。

#### 3、RealCall
入队，包装 callBack，真正的网络请求。


#### 4、Resonse
封装网络请求结果。


