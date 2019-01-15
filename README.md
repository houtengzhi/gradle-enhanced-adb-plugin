# enhanced-adb-plugin
>Adb command gradle插件

### 更新
 1.0.1

### 集成

在项目根目录下的build.gradle文件中添加
```
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.yechy.gradleplugin:adb-plugin:1.0.0'
    }
}
```

在mudule下的build.gradle文件中添加
```
apply plugin: 'com.yechy.adb-plugin'
```

### 配置项

|参数名|默认值|描述|
|-----|------|---|
|isSystemApp|false|是否是系统App|
|rebootAfterInstall|false|安装后是否重启设备|
|debugged|false|是否设置调式模式|
|apkPrefix|null|APK文件名前缀，卸载系统app时必填|
|launchActivity|null|自动运行启动的activity，需要填写完整包名|
|runAfterInstall|false|安装后是否自动运行|
|jniLib|null|依赖的动态库配置|
|dataDirs|null|数据目录,可配多个|
|customTasks|null|自定义task, 可配多个|

jniLib属性下配置参数
<table>
  <tr>
    <th></th>
    <th>参数名</th>
    <th>默认值</th>
    <th>描述</th>
  </tr>
  <tr>
    <td rowspan="2">jniLib</td>
    <td>abi</td>
    <td>armeabi</td>
    <td>cpu类型(armeabi, armeabi-v7a, arm64-v8a)</td>
  </tr>
  <tr>
    <td>installPath</td>
    <td>/vendor/lib</td>
    <td>动态库文件安装路径，仅对系统app有效</td>
  </tr>
</table>

dataDirs属性下配置参数：
<table>
  <tr>
    <th></th>
    <th>参数名</th>
    <th>默认值</th>
    <th>描述</th>
  </tr>
  <tr>
    <td rowspan="2">dataDirs</td>
    <td>filePath</td>
    <td>null</td>
    <td>文件路径</td>
  </tr>
  <tr>
    <td>deleteWhenUninstall</td>
    <td>false</td>
    <td>卸载apk时是否删除该文件</td>
  </tr>
</table>

customTasks属性下配置参数：
<table>
  <tr>
    <th></th>
    <th>参数名</th>
    <th>默认值</th>
    <th>描述</th>
  </tr>
  <tr>
    <td rowspan="4">customTasks</td>
    <td>group</td>
    <td>enhanced adb</td>
    <td>自定义task分组</td>
  </tr>
  <tr>
    <td>description</td>
    <td>null</td>
    <td>自定义task描述</td>
  </tr>
  <tr>
    <td>commands</td>
    <td>null</td>
    <td>Shell命令列表</td>
  </tr>
  <tr>
    <td>scriptPath</td>
    <td>null</td>
    <td>脚本路径，可填工程目录下的相对路径</td>
  </tr>
</table>


示例如下：
```
adbPlugin {
    isSystemApp = true
    rebootAfterInstall = false
    debugged = true
    apkPrefix = 'test'
    launchActivity = 'com.yechy.test.MainActivity'
    runAfterInstall = true

    jniLib {
        abi = 'armeabi-v7a'
        installPath = '/vendor/lib'
    }

    dataDirs {
        data1 {
            filePath = '/data/data/com.yechy.test/'
            deleteWhenUninstall = false
        }

        data2 {
            filePath = '/mnt/sdcard/test'
            deleteWhenUninstall = false
        }
    }

    customTasks {
        CustomInstallTask {
            commands = ['dir', 'dir']
            scriptPath = '../install_live.bat'
            group = 'custom'
            description = 'Custom install apk'
        }
    }
}
```

