### 一、Butterknife
Buttknife 主要是解决 findViewByid 的问题，可以把 view 和 id 绑定在一起。

核心原理就是通过自定义注解处理器来扫描注解，然后通过 javaPoet 来生存代码。和反射没有任何关系。

整个流程：
1、编译期会生成 binding 类
2、activity 运行时，通过 bind 方法绑定到 binding 类，从而实现控件和 id 绑定。




参考：
https://juejin.cn/post/6844903508227932167

https://camnter.com/the-precompiled-phase-scans-the-r-class/





#### 1、注解
+ @Target  :描述注解的使用范围

+ Retention : 描述注解的生命周期

source ： 一般用来代码检查
class : 通过自定义注解处理器来处理
runtime : 通过反射来操作代码


butterknife 中使用的是 RetentionPllicy.CLASS ,在 runtime 期间不会存在，因此需要自定义注解处理器来保存注解信息。


+ Document ： 生成文档

+ Inherited : 注解可继承



#### 2、自定义注解
通过使用 @interface 来使用
```
    @Retention(CLASS) @Target(FIELD)
    public @interface BindView {
      /** View ID to which the field will be bound. */
      @IdRes int value();
    }

```




#### 3、apt 概念
APT(Annatotion Processing Tool) ，即注解处理器。JVM 会在编译期间运行 APT 扫描代码中的注解，然后根据注解来生成 java 代码或者文件。


应用：
ButterKnife、ARouter、EventBus 等。


自定义的注解处理器会新生成 java 代码，然后新的 java 代码会被编译成 class 文件。

自定义注解处理器需要继承自 AbstractProcessor ，然后实现如下方法：
 + init()
 初始化、得到 Elements、Types、Filer 等工具类。

 + getSupportedAnotationTypes()
 描述注解处理器需要处理的注解。

 + process()
 扫描分析注解，生存代码。生存代码需要用 javaPoet 。








#### 4、实践
+ 创建自定义注解 lib ，类型是 Java Library Module

+ 创建 butterknife，因为需要处理 Activity，所以类型是 Android Library

+ 创建自定义注解处理器，类型是 Java Library Module ，依赖我们创建的自定义注解 module
```
    implementation project(':butterknife-annotations')

```


+ 通过 AutoService 来实现 APT 的注册，也需要有 resource/meta-inf.services



#### 5、自动生成代码
用 tools.jar jvm 里面的一个包，用到了什么 jctree 等类。

#### 6、AutoService




