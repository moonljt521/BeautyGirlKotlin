# BeautyGirlKotlin for Android 

### kotlin项目（架构逐步演变，现在为jetpack MVVM 模式）一些美女图片列表，使用gank等公共api，或者解析豆瓣等页面数据抽取json，另外接入了Google的admob广告sdk
<br>


## 1.0 ~ 2.0 

常规功能开发，几个美女图片加载列表， 由于最近几个接口都失效了，准备找些新接口测试

## 3.0 

kotlin Coroutine 替换掉 rxJava，同时升级Retrofit 到 2.6配合协程使用

## 3.1 

support迁移到了androidx 

## 4.0 ~ 4.1

修复douban接口报错问题


引入jetpack（优先使用livedata，viewmodel，databinding）, 取代mvp开发模式，删除mvp框架

## 4.3
修复列表翻页问题, 完善jetpack, 升级GLide， 数据库 room 替换 realm ， 增加UI细节


## 4.5 ~ 4.7 
高度封装jetpack MVVM：
抽象列表页的逻辑进入基类，包括数据UI初始化，仓库初始化，加载数据和翻页，子类只负责调用接口，抽象databindingAdapter支持多类型布局



## 4.9 app bundle
由于上传到google play上，尝试了下app bundle功能

### 一、app build简单配置
```
android {
    bundle {
            density {
                enableSplit = true
            }

            language {
                enableSplit = true
            }

            abi {
                enableSplit = true
            }
        }
}
```

### 二、打包为aab,利用as直接打包

### 三、本地进行aab测试
#### 1 下载命令行工具 https://github.com/google/bundletool/releases， 是个jar文件
#### 2 用具解压你的aab，命令为
```
java -jar bundletool-all-0.11.0.jar build-apks --bundle=输入abb文件路径/app.aab --output=输出apks路径/my_app.apks
```

打包出来的apks文件改下后缀为zip，然后解压下。可以看到各种feature的apk


## 4.10 重构

#### 1 结构调整，剥离数据层
#### 2 添加paging组件替换自定义分页功能

<br>

## Requirements
* Android 4.3 or higher 

## License
    Copyright 2018-2020 jiangtao.liang.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.




