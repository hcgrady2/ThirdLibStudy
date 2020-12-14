### 一、使用
参考资料：
https://blog.yorek.xyz/android/3rd-library/glide1/


#### 1、基础使用
+ 简单使用
```
Glide.with(this).load(URL).into(ivGlide)




Glide.with(this)
    .load(URL)
    .placeholder(ColorDrawable(Color.GRAY))
    .error(ColorDrawable(Color.RED))
    .fallback(ColorDrawable(Color.CYAN))
    .skipMemoryCache(true)
    .diskCacheStrategy(DiskCacheStrategy.NONE)
    .into(ivGlide)
    
    
    

```

+ with 的重载方法 @Glide
```
with(@NonNull Context context)
with(@NonNull View view)
with(@NonNull Activity activity)
with(@NonNull FragmentActivity activity)
with(@NonNull Fragment fragment)

```

+ load 的重载方法 @RequestManager
```

load(@Nullable Bitmap bitmap)
load(@Nullable Drawable drawable)
load(@Nullable String string)
load(@Nullable Uri uri)
load(@Nullable File file)
load(@RawRes @DrawableRes @Nullable Integer resourceId)
load(@Nullable byte[] model)
load(@Nullable Object model)

```

+ RequestManager 中的控制方法
```
isPaused()
pauseRequests()
pauseAllRequests()
pauseRequestsRecursive()
resumeRequests()
resumeRequestsRecursive()
clear(@NonNull View view)
clear(@Nullable final Target<?> target)
```

+ RequestManager 中生命周期方法
```
onStart()
onStop()
onDestroy()
```

+ RequestManager  其他方法
```
downloadOnly()
download(@Nullable Object model)
asBitmap()
asGif()
asDrawable()
asFile()
as(@NonNull Class<ResourceType> resourceClass)
```

#### 2、占位符
+ placeholder
+ error
+ fallback



#### 3、其他问题
占位符在主线程加载，变换不会应用给占位符。

Glide 可以自动识别 git ,同时支持转化为图片，只现实第一帧
```
Glide.with(this)
    .asBitmap()
    .load(GIF_URL)
    .apply(option)
    .into(ivGlide)
```


指定图片大小
```
Glide.with(this)
    .load(URL)
    .apply(option)
    .override(100)
    .into(ivGlide)
```



### 二、glide 原理










### 三、手写 glide



1、Glide（对外提供使用）

+ 只提供一个 with 方法，传递 context

通过 context 构造并返回 BitmapRequest


2、BitmapRequest（封装请求信息）
提供 load,loading,into,listener 方法
+ load (): 设置图片 Url
+ loading(): 正在加载时图片
+ listener()：设置监听器
+ into() : 将请求提交给 RequstManager（哪个 imageView，请求的地址，context，listener 等）。

3、RequestManager（统一管理加载图片的请求）
+  有一个队列保存每一个请求。
+ addBitmapRequest()   请求入队。
+  持有 BitmapDispatcher 数组对象，处理每一个图片加载请求。（BitmapDispatcher 本质是一个线程，当RequestManager 初始化之后就开始运行了）。


4、BitmapDispatcher
本质上是一个线程，是一个消费者，消费掉图片加载请求，需要持有请求队列的引用，这样才能取出每一个请求。
run 方法中，也就是线程执行方法里面，当有需求时，线程不会阻塞，这里会
+ 首先显示正在加载的图片
+ 加载图片（先检查缓存，没有从网络下载）
+ 更新界面（这里可以实现其他的需求，图片处理等。）



5、Glide 生命周期管理
定义一个 Fragment ，通过 activity.getFragmentManager 获取这个 activity 的 fragment manager。

通过  manager.beginTransaction().add(current,"com.study.imagloaderdemo").commitAllowingStateLoss() 将自定义的 Fragment  绑定到 activity


6、RequestManagerFragment
自定义的 fragment,通过 onAttach 与 onDetach 感知 activity 生命周期。

当 activity 销毁时，通知 cache 清空缓存。






### 二、其他
LinkedBlockingDeque


ExecutorService












