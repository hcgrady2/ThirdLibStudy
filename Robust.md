官网：

https://github.com/Meituan-Dianping/Robust



使用注意事项：

+ classpath 插件版本和 gradle 依赖的版本需要一致
+ gradle classpath 版本也有要求
+ 包名需要 com 开头
+ VerifyPatch 里需要写 copy 逻辑的
+ 一定提前动态权限申请
+ 一定打正式包，配置混淆的
+ 下面的代码，可能导致生成补丁失败
```
//        textView.setOnClickListener(v -> {
////                    RobustModify.modify();
//                    Log.d("robust", " onclick  in Listener");
//                }
//        );
```
