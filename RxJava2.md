https://juejin.cn/post/6844903617124630535
### 基础使用
```
Observable.create(new ObservableOnSubscribe < Integer > () {
    @Override
    public void subscribe(ObservableEmitter < Integer > e) throws Exception {
        Log.d(TAG, "=========================currentThread name: " + Thread.currentThread().getName());
        e.onNext(1);
        e.onNext(2);
        e.onNext(3);
        e.onComplete();
    }
})
.subscribe(new Observer < Integer > () {
    @Override
    public void onSubscribe(Disposable d) {
        Log.d(TAG, "======================onSubscribe");
    }

    @Override
    public void onNext(Integer integer) {
        Log.d(TAG, "======================onNext " + integer);
    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG, "======================onError");
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "======================onComplete");
    }
});

```

### 一、创建操作符

#### 1、 create

#### 2、 just
创建被观察者并发送事件，但是不能超过 10 个
```
public static <T> Observable<T> just(T item) 
......
public static <T> Observable<T> just(T item1, T item2, T item3, T item4, T item5, T item6, T item7, T item8, T item9, T item10)

```
#### 3、from
+ fromArray
通过数组创建被观察者，并发送事件

+ fromIterable
根据集合来创建被观察者，并发送事件

+ fromCallable
根据 callable 来创建
```
Observable.fromCallable(new Callable < Integer > () {

    @Override
    public Integer call() throws Exception {
        return 1;
    }
})
.subscribe(new Consumer < Integer > () {
    @Override
    public void accept(Integer integer) throws Exception {
        Log.d(TAG, "================accept " + integer);
    }
});

```

+ fromFuture
根据异步任务来创建被观察者。
```
FutureTask < String > futureTask = new FutureTask < > (new Callable < String > () {
    @Override
    public String call() throws Exception {
        Log.d(TAG, "CallableDemo is Running");
        return "返回结果";
    }
});

Observable.fromFuture(futureTask)
    .doOnSubscribe(new Consumer < Disposable > () {
    @Override
    public void accept(Disposable disposable) throws Exception {
        futureTask.run();
    }
})
.subscribe(new Consumer < String > () {
    @Override
    public void accept(String s) throws Exception {
        Log.d(TAG, "================accept " + s);
    }
});

```

#### 3、defer
被观察者被订阅之后才创建。
```
// i 要定义为成员变量
Integer i = 100;
        
Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
    @Override
    public ObservableSource<? extends Integer> call() throws Exception {
        return Observable.just(i);
    }
});

i = 200;

Observer observer = new Observer<Integer>() {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Integer integer) {
        Log.d(TAG, "================onNext " + integer);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
};

observable.subscribe(observer);

i = 300;

observable.subscribe(observer);

```


#### 5、timer
指定时间之后创建被观察者并发送 0L 值。
```
Observable.timer(2, TimeUnit.SECONDS)
```

#### 6、interval
每隔指定时间，发送从 0 递增的数字。
```
public static Observable<Long> interval(long period, TimeUnit unit)
public static Observable<Long> interval(long initialDelay, long period, TimeUnit unit)

```


#### 7、intervalRange
可以指定开始值和数量。
```
public static Observable<Long> intervalRange(long start, long count, long initialDelay, long period, TimeUnit unit)
public static Observable<Long> intervalRange(long start, long count, long initialDelay, long period, TimeUnit unit, Scheduler scheduler)

```


#### 8、range
同时发送指定范围的数字。
```
Observable.range(2, 5)
.subscribe(new Observer < Integer > () {
    @Override
    public void onSubscribe(Disposable d) {
        Log.d(TAG, "==============onSubscribe ");
    }

    @Override
    public void onNext(Integer aLong) {
        Log.d(TAG, "==============onNext " + aLong);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
});

```


#### 9、rangeLong
long 类型的范围。
```
public static Observable<Long> rangeLong(long start, long count)

```


#### 10、empty & error & never
```
public static <T> Observable<T> empty()
public static <T> Observable<T> never()
public static <T> Observable<T> error(final Throwable exception)

```
empty() ： 直接发送 onComplete() 事件
never()：不发送任何事件
error()：发送 onError() 事件


### 二、转换操作符
#### 1、map
将被观察者发送的数据类型转换为其他数据类型。
```
Observable.just(1, 2, 3)
.map(new Function < Integer, String > () {
    @Override
    public String apply(Integer integer) throws Exception {
        return "I'm " + integer;
    }
})
.subscribe(new Observer < String > () {
    @Override
    public void onSubscribe(Disposable d) {
        Log.e(TAG, "===================onSubscribe");
    }

    @Override
    public void onNext(String s) {
        Log.e(TAG, "===================onNext " + s);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
});

```
#### 2、flatMap
将发送的数据进行加工整合，返回新的被观察者。
```
Observable.fromIterable(personList)
.flatMap(new Function < Person, ObservableSource < Plan >> () {
    @Override
    public ObservableSource < Plan > apply(Person person) {
        return Observable.fromIterable(person.getPlanList());
    }
})
.flatMap(new Function < Plan, ObservableSource < String >> () {
    @Override
    public ObservableSource < String > apply(Plan plan) throws Exception {
        return Observable.fromIterable(plan.getActionList());
    }
})
.subscribe(new Observer < String > () {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(String s) {
        Log.d(TAG, "==================action: " + s);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
});

```


#### 3 、concatMap
同 flatMap，但是发送的数据是有序的。


#### 4、buffer
获取发送事件，放到缓冲区，达到一定数量，一并发送。skip,表示指针移动几个。
```
public final Observable<List<T>> buffer(int count, int skip)

```


#### 5、groupBy

对数据进行分组：
```
Observable.just(5, 2, 3, 4, 1, 6, 8, 9, 7, 10)
.groupBy(new Function < Integer, Integer > () {
    @Override
    public Integer apply(Integer integer) throws Exception {
        return integer % 3;
    }
})
.subscribe(new Observer < GroupedObservable < Integer, Integer >> () {
    @Override
    public void onSubscribe(Disposable d) {
        Log.d(TAG, "====================onSubscribe ");
    }

    @Override
    public void onNext(GroupedObservable < Integer, Integer > integerIntegerGroupedObservable) {
        Log.d(TAG, "====================onNext ");
        integerIntegerGroupedObservable.subscribe(new Observer < Integer > () {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "====================GroupedObservable onSubscribe ");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "====================GroupedObservable onNext  groupName: " + integerIntegerGroupedObservable.getKey() + " value: " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "====================GroupedObservable onError ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "====================GroupedObservable onComplete ");
            }
        });
    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG, "====================onError ");
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "====================onComplete ");
    }
});

```



#### 6、scan
将发射的数据通过一个函数进行变换，然后将变换后的结果作为参数跟下一个发射的数据一起继续通过那个函数变换，这样依次连续发射得到最终结果。
```
Observable.just(1, 2, 3, 4, 5)
.scan(new BiFunction < Integer, Integer, Integer > () {
    @Override
    public Integer apply(Integer integer, Integer integer2) throws Exception {
        Log.d(TAG, "====================apply ");
        Log.d(TAG, "====================integer " + integer);
        Log.d(TAG, "====================integer2 " + integer2);
        return integer + integer2;
    }
})
.subscribe(new Consumer < Integer > () {
    @Override
    public void accept(Integer integer) throws Exception {
        Log.d(TAG, "====================accept " + integer);
    }
});

```

#### 7、window
对发送的事件，按照数量进行分组。
```
Observable.just(1, 2, 3, 4, 5)
.window(2)
.subscribe(new Observer < Observable < Integer >> () {
    @Override
    public void onSubscribe(Disposable d) {
        Log.d(TAG, "=====================onSubscribe ");
    }

    @Override
    public void onNext(Observable < Integer > integerObservable) {
        integerObservable.subscribe(new Observer < Integer > () {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "=====================integerObservable onSubscribe ");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "=====================integerObservable onNext " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "=====================integerObservable onError ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "=====================integerObservable onComplete ");
            }
        });
    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG, "=====================onError ");
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "=====================onComplete ");
    }
});

```


### 三、组合操作符
#### 1、concat
将被观察者组合一起按照原来的顺序发送，最多四个。
```
Observable.concat(Observable.just(1, 2),
Observable.just(3, 4),
Observable.just(5, 6),
Observable.just(7, 8))
```

#### 2、concatArray
同 concat ,可以发送多于四个。

#### 3、merge & mergeArray
把两个被观察者集合起来，发送，但是 merge 是可以并行发送的，如果换成 concat ，就是发送完一个才能发送另一个。
```
Observable.merge(
Observable.interval(1, TimeUnit.SECONDS).map(new Function < Long, String > () {
    @Override
    public String apply(Long aLong) throws Exception {
        return "A" + aLong;
    }
}),
Observable.interval(1, TimeUnit.SECONDS).map(new Function < Long, String > () {
    @Override
    public String apply(Long aLong) throws Exception {
        return "B" + aLong;
    }
}))
    .subscribe(new Observer < String > () {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(String s) {
        Log.d(TAG, "=====================onNext " + s);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
});

```


#### 4、concatArrayDelayError() & mergeArrayDelayError()
concat 与 merge 发送事件如果遇到了 error，整个事件就不会发送了，concatArrayDelayError() & mergeArrayDelayError()可以延迟 error 事件，
直到所有的事件发送完毕。


#### 5、zip
将多个数据源合并，产生新的数据源，不同的数据源按照顺序一一对应，新的数据个数会以最少的为主。
```
Observable.zip(Observable.just(1, 2, 3),
                Observable.just("A", "B", "C", "D", "E"),
                new BiFunction<Integer, String, String>(){

                    @Override
                    public String apply(Integer o1, String o2) throws Exception {

                        return o1 +"_"+ o2;
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String o) throws Exception {

                        Log.i("lybj", o);
                    }
                });

```



#### 6、 combineLatest() & combineLatestDelayError()
作用类似 zip ,但是是结合两个数据源中最近的事件。

#### 7、reduce
作用类似 scan,但是 reduce 是所有的聚合完成之后，才返回给被观察者。
```
Observable.just(0, 1, 2, 3)
.reduce(new BiFunction < Integer, Integer, Integer > () {
    @Override
    public Integer apply(Integer integer, Integer integer2) throws Exception {
        int res = integer + integer2;
        Log.d(TAG, "====================integer " + integer);
        Log.d(TAG, "====================integer2 " + integer2);
        Log.d(TAG, "====================res " + res);
        return res;
    }
})
.subscribe(new Consumer < Integer > () {
    @Override
    public void accept(Integer integer) throws Exception {
        Log.d(TAG, "==================accept " + integer);
    }
});

```


#### 8、collect
将数据收集到数据结构中。
```
Observable.just(1, 2, 3, 4)
.collect(new Callable < ArrayList < Integer >> () {
    @Override
    public ArrayList < Integer > call() throws Exception {
        return new ArrayList < > ();
    }
},
new BiConsumer < ArrayList < Integer > , Integer > () {
    @Override
    public void accept(ArrayList < Integer > integers, Integer integer) throws Exception {
        integers.add(integer);
    }
})
.subscribe(new Consumer < ArrayList < Integer >> () {
    @Override
    public void accept(ArrayList < Integer > integers) throws Exception {
        Log.d(TAG, "===============accept " + integers);
    }
});

```


#### 9、startWith() & startWithArray()
发送事件之前，追加一个或者多个事件。


#### 10、count
返回被观察者的数量。


### 四、功能操作符
#### 1、delay
延迟时间再发送。

#### 2、onOnEach
没发送一个事件之前都回调这个方法。


#### 3、doOnNext
next 之前


#### 4、 doAfterNext
onNext 之后。



#### 5、doOnComplete
onComplete 之前调用。


#### 6、doOnError
onError 之前。


#### 7、doOnSubscribe

Observable 每发送 onSubscribe() 之前都会回调这个方法。



#### 8、doOnDispose
当调用 Disposable 的 dispose() 之后回调该方法。

#### 9、doOnTerminate() & doAfterTerminate()
doOnTerminate 是在 onError 或者 onComplete 发送之前回调，而 doAfterTerminate 则是 onError 或者 onComplete 发送之后回调。


#### 10、doFinally()
无论成功失败，所有事件发送完毕都会回调这个。


#### 11、onErrorReturn
当接受到一个 onError() 事件之后回调，返回的值会回调 onNext() 方法，并正常结束该事件序列。
```
Observable.create(new ObservableOnSubscribe<Integer>() {
    @Override
    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
        e.onNext(1);
        e.onNext(2);
        e.onNext(3);
        e.onError(new NullPointerException());
    }
})
.onErrorReturn(new Function<Throwable, Integer>() {
    @Override
    public Integer apply(Throwable throwable) throws Exception {
        Log.d(TAG, "==================onErrorReturn " + throwable);
        return 404;
    }
})
.subscribe(new Observer<Integer>() {
    @Override
    public void onSubscribe(Disposable d) {
        Log.d(TAG, "==================onSubscribe ");
    }

    @Override
    public void onNext(Integer integer) {
        Log.d(TAG, "==================onNext " + integer);
    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG, "==================onError ");
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "==================onComplete ");
    }
});

```
#### 12、onErrorResumeNext
当接收到 onError() 事件时，返回一个新的 Observable，并正常结束事件序列。


#### 13、retry
发生错误事件后，重试指定次数。
```
Observable.create(new ObservableOnSubscribe<Integer>() {
    @Override
    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
        e.onNext(1);
        e.onNext(2);
        e.onNext(3);
        e.onError(new Exception("404"));
    }
})
.retry(2)
.subscribe(new Observer<Integer>() {
    @Override
    public void onSubscribe(Disposable d) {
        Log.d(TAG, "==================onSubscribe ");
    }

    @Override
    public void onNext(Integer integer) {
        Log.d(TAG, "==================onNext " + integer);
    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG, "==================onError ");
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "==================onComplete ");
    }
});

```

#### 14、retryUntil
根据条件判断是否继续
```
Observable.create(new ObservableOnSubscribe < Integer > () {
    @Override
    public void subscribe(ObservableEmitter < Integer > e) throws Exception {
        e.onNext(1);
        e.onNext(2);
        e.onNext(3);
        e.onError(new Exception("404"));
    }
})
.retryUntil(new BooleanSupplier() {
    @Override
    public boolean getAsBoolean() throws Exception {
        if (i == 6) {
            return true;
        }
        return false;
    }
})
.subscribe(new Observer < Integer > () {
    @Override
    public void onSubscribe(Disposable d) {
        Log.d(TAG, "==================onSubscribe ");
    }

    @Override
    public void onNext(Integer integer) {
        i += integer;
        Log.d(TAG, "==================onNext " + integer);
    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG, "==================onError ");
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "==================onComplete ");
    }
});

```

#### 15、repeat(2)
重复发送被观察者。

#### 16、repeatWhen
根据条件判断是否重复发送

#### 17、subscribeOn
指定被观察者的线程。

#### 18、observeOn
指定观察者的线程。


### 五、过滤操作符
#### 1、filter
条件为 true 才发送事件。

#### 2、ofType
过滤不符合类型的事件。
```
Observable.just(1, 2, 3, "chan", "zhide")
.ofType(Integer.class)
```

#### 3、skip(count)
跳过几个。


#### 4、distinct
过滤重复事件


#### 5、distinctUntilChanged
过滤掉连续重复的事件



#### 6、take
控制观察者接收的事件的数量。




#### 7、debounce
如果两件事件发送的时间间隔小于设定的时间间隔则前一件事件就不会发送给观察者。



#### 8、firstElement() && lastElement()
只发送第一个/最后一个元素。


#### 9、elementAt() & elementAtOrError()
elementAt() 可以指定取出事件序列中事件，但是输入的 index 超出事件序列的总数的话就不会出现任何结果。这种情况下，你想发出异常信息的话就用 elementAtOrError() 。



### 六、条件操作符
#### 1、all
判断所有事件是否全部满足某个条件，然后发送 boolean 事件。
```
Observable.just(1, 2, 3, 4)
.all(new Predicate < Integer > () {
    @Override
    public boolean test(Integer integer) throws Exception {
        return integer < 5;
    }
})

```

#### 2、takeWhile
满足条件才发送事件。

#### 3、skipWhile
满足条件不发送事件

#### 4、takeUntil
满足这个条件之后，下一个就不发送了。


#### 5、sequenceEqual
两个被观察者发送的事件是否相同。


#### 6、contains
是否包含某个事件


#### 7、isEmpty
判断序列是否为空。。




#### 8、amb()
接受一个被观察者的集合，但是先到先得，那个被观察者先发送，其他的都不发送了。


#### 9、defaultIfEmpty
如果观察者只发送一个 onComplete() 事件，则可以利用这个方法发送一个值。

















