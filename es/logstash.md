# Logstash

- `数据收集处理引擎`
- `ETL`工具

## 架构介绍

![](./images/logstash.png)

![](./images/Snipaste_2019-06-20_21-48-01.png)

![](./images/Snipaste_2019-06-20_21-48-28.png)

### `Pipline`

> - `input-filter-output`的`3`阶段处理流程
>
> - `队列管理`
>
> - `插件`生命周期
>
> - 主要如下设置类型
>   - 布尔类型`boolean`
>     - `isFailed=>true`
>   - 数值类型`number`
>     - `port=>33`
>   - 字符串类型`string`
>     - `name=>"hello world"`
>   - 数组`Array/List`
>     - `users => [{id=>1,name=>bob},{id=>2,name=>glod}]`
>     - `Path => ["var/log/message","var/log/*.log"]`
>   - 哈希类型`Hash`
>     - `match=>{"filed1"=>"value1","field2"=>"value2"}`
>   
> - 注释 `#`
>
> - 在配置中可以引入 `logstash event`的属性(字段),主要有如下两个方式
>   - 直接引用`字段值`
>     - 使用中括号`[字段 key]`多层多个`[][]`
>   - 在字符串中引用`sprintf`方式引用
>     - 使用`%{}`来实现,引入嵌套类型`%{[][]}`来实现
>   
> - `pipline`配置
>   - `input`
>     - `stdin`
>       - 从`标准输入` `读取数据`
>         - `codec`类型为`codec`
>         - `type`类型为 `string`,自定义该`事件`的类型,可用于后续判断
>         - `tags`类型为`array`,自定义该事件的`tag`,可用于后续的判断
>         - `add_field`的类型为 `hash`,为该事件添加`字段`
>     - `kafka`
>       - ![](./images/Snipaste_2019-06-22_13-38-29.png)
>     - `file`
>       - 从`文件`去读取数据,如常见的日志文件,`文件读取`通常得解决以下几个问题
>         - `文件`如何只被读取一次,即重启`logstash`时,从上次读取的位置继续读取
>           - `sincedb`
>         - 如何`即时`读取到`文件`的`内容
>           - 定时检查文件是否`更新`
>         - 如何发现`新文件`并进行`读取`
>           - 可以,`定时检查`文件
>         - 如果文件发生了`归档`的操作,是否影响当前文件的读取
>           - 不影响,`可以归档的文件内容`可以继续被`读取`
>       - 基于 `filewatch`的`ruby` 库来实现的
>         - [filewatch](https://github.com/jordansissel/ruby-filewatch)
>         - `watch`
>         - `tail`
>         - ![](./images/Snipaste_2019-06-22_12-52-16.png)
>       - `关键配置`
>         - `path`类型为`数组`,指明读取文件的路径,基于`glob`语法
>           - `path => ["/var/log/*log","/var/log/message"]`
>         - `exclude`类型为`数组排除`的`不想监听`的文件规则,基于`glob`语法
>           - `exclude=>"*.gz"`
>         - `sincedb_path`类型为`text`,记录`sincedb`文件路径
>         - `start_position`类型为`字符串`,`"begining or end"`是否从从头读取`文件`
>         - `start_interval`类型为`数值`,单位为`秒`,定时检查文件是否`更新`,默认为`1s`
>         - `discovery_interval`类型为`数值`,单位为`秒`,定时检查是否有新文件`待读取`,默认 `15 秒`
>         - `ignore_older`类型为`数值`,单位为`秒`,扫描文件列表时,如果该文件上次`更改`的时间`超过设定`的时长,即不做处理,但依然会监控是否有新内容,`默认关闭`
>         - `close_older`类型为`数值`,单位为`秒`,如果设定的时间内没有新内容,会被`关闭文件句柄`,释放资源,默认`3600 秒`,即 `1 小时`
>       - `glob`匹配语法
>         - `*`匹配任意字符,但是不匹配以`.`开头的隐藏文件,匹配在这类文件时要用`.*`来匹配
>         - `**`递归匹配子目录
>         - `?`匹配单一字符
>         - `[]`,匹配多个字符,比如`[a-z]、[^a-z]`
>         - `{}`匹配多个单词,比如`{foo,bar,hello}`
>         - `\`转义字符
>         - ![](./images/Snipaste_2019-06-22_13-31-39.png)
>       - `具体使用`
>         - ![](./images/Snipaste_2019-06-22_13-35-30.png)
>         - ![](./images/Snipaste_2019-06-22_13-35-46.png)
>   
>   - `filter`
>     - 是 `logstash`功能强大的主要原因,他可以对 `logstash event`进行丰富的处理,比如`解析数据`、`删除数据`、`类型转换`等等,常见的有如下几个
>       - `date`日期解析
>       - `grok`正则匹配解析
>       - `dissect`分隔符解析
>       - `mutate`对字段进行处理,比如`重命名、删除、替换`等
>       - `json`按照`json`解析字段内容到指定字段中
>       - `geoip`增加地理位置数据
>       - `ruby`利用`ruby`代码来动态修改`logstash event`
>   
>   - `output`
>     - 
>     
>   - 支持简单的判断表达式
>  
>   - ![](./images/Snipaste_2019-06-21_00-44-04.png)
>  
>   - 表达式主要包含如下操作符
>     - `比较`: `== != < > <= >=`
>     - `正则是否匹配`:`=~ !~`
>     - `包含(字符串或者数组)`:`in 、not in`
>     - `布尔操作符`:`and、or、nand、xor、!`
>     - `分组操作符`:`{}`
>     - ![](./images/Snipaste_2019-06-21_00-50-31.png)

#### `plugin 配置详解`

> ##### `codec plugin`
>
> - 作用于 `input和output`plugin 中,符合数据在`原始`和`logstash event`之间的转化,常见的`codec`有以下几种
>   - `plain`读取原始内容
>   - `dots`将内容简化为`点`进行输出
>   - `rubydebug`将`logstash event`按照`ruby`格式输出,方便调试
>   - `line`处理带有换行符的内容
>   - `json`处理 `json`格式内容
>   - `mutiline`处理多行数据内容
>     - 当一个`event`由多行组成时,需要使用`codec`,常见时对`堆栈日志信息`的处理,具体如下
>       - ![](./images/Snipaste_2019-06-22_15-10-49.png)
>     - `主要设置`如下
>       - `pattern`设置一个行匹配的正则表达式,可以使用`grok`
>       - `what previous|next`,如果匹配成功,这是归属于`上一个事件`还是`下一个事件`
>       - `negate true or false`是否对`pattern`的结果取反
>
> `filter plugin`
>
> - `filter plugin`是 `logstash`功能强大的主要原因,他可以对 `logstash event`进行丰富的处理,比如`解析数据`、`删除数据`、`类型转换`等等,常见的有如下几个
>   - `date`日期解析
>     - 将`日期`的字符串解析成`字符串`类型,然后替换`@timestamp`字段或者指定的其他字段
>     - `match`
>       - 类型为`数组`,用于指定`日期`匹配的格式,可以一次指定多个`日期`类型
>       - `match=>["logdate","MMM dd yyyy HH:mm:ss","MMM d yyyy HH:mm:ss","ISO8601"]`
>     - `target`
>       - `类型`为`字符串`,用于指定赋值的字段名,默认为`@timestamp`
>     - `timezone`
>       - `类型`为`字符串`,用于指定时区
>     - 
>       - ![](./images/Snipaste_2019-06-23_14-28-50.png)
>       - ![](./images/Snipaste_2019-06-23_14-29-10.png)
>   - `grok`正则匹配解析
>     - `语法`
>       - `%{syntax:sematic}`
>       - `syntax`为`grok pattern`的名称,`sematic`未赋值字段的名称
>       - `%{number:duration}`可以匹配数值类型,但是`grok`匹配出的都是`字符串类型`,可以通过最后指定为`int`或者`float`来`强制转换`类型`%{number:duration:float}`
>       - 熟悉常见的一些` pattern`利于编写匹配规则
>       - [规则](https://github.com/logstash-plugins/logstash-patterns-core)
>       - `match`多种匹配
>       - ![](./images/Snipaste_2019-06-23_15-05-12.png)
>       - `override`
>       - ![](./images/Snipaste_2019-06-23_15-06-24.png)
>       - ![](./images/Snipaste_2019-06-23_15-01-22.png)
>       - ![](./images/Snipaste_2019-06-23_15-08-24.png)
>   - `dissect`分隔符解析
>     - 基于分隔符原理解析数据,解决 `grok`解决问题过度消耗`CPU`资源的问题
>     - ![](./images/Snipaste_2019-06-23_16-45-32.png)
>     - 新能对比`grok`
>     - ![](./images/Snipaste_2019-06-23_16-46-02.png)
>     - `dessect`的应用具有一定的局限性
>       - 主要适用于每行格式相似且分隔符明确的简单场景
>     - `dissect`语法比较简单,有一系列`field`和分隔符`delimiter`组成
>       - `%{}`字段
>       - `%{}`之间是分隔符
>       - ![](./images/Snipaste_2019-06-23_16-51-33.png)
>       - 指定顺序
>       - ![](./images/Snipaste_2019-06-23_16-54-31.png)
>       - `key value`对应
>       - ![](./images/Snipaste_2019-06-23_16-55-58.png)
>       - `dissect`分隔后的字段值都是字符串,可以使用`convert_datetype`属性作为类型转换
>       - ![](./images/Snipaste_2019-06-23_17-00-24.png)
>   - `mutate`对字段进行处理,比如`重命名、删除、替换`等
>     - 使用最频繁的操作,可以对字段进行各种操作,比如,`重命名`、`删除`、`替换`、`更新`等,主要操作如下
>       - `convert`类型转换
>         - 实现字段类型转换,仅支持转换`Integer`、`float`、`string`和`blloean`
>         - ![](./images/Snipaste_2019-06-23_17-07-38.png)
>       - `gsub`字符串替换
>         - ![](./images/Snipaste_2019-06-23_17-09-57.png)
>       - `split/join/merge`字符串切割、数组合并成字符串、数组合并成数组
>         - `split`把字符串切割成数组
>         - `join`把数组合并成字符串
>         - `merge`合并
>       - `rename`字段重命名
>         - `改名`
>       - `update/replace`字段内容`更新或替换`
>         - `update`更新已知字段
>         - `replace`替换字段
>       - `remove_field`删除字段
>         - 移除字段==>一般用来删除`message`字段
>   - `json`按照`json`解析字段内容到指定字段中
>     - `target`指定存储的 存到哪个 `key`中
>     - ![](./images/Snipaste_2019-06-23_22-26-19.png)
>   - `geoip`增加地理位置数据
>     - ![](./images/Snipaste_2019-06-23_22-27-56.png)
>   - `ruby`利用`ruby`代码来动态修改`logstash event`
>     - ![](./images/Snipaste_2019-06-23_22-29-10.png)
>
> #### `output plugin`
>
> - 输出到文件,实现将分散在多地的文件统一到一处的请求,比如所有 `web 机器`的`web 日志`收集到一个文件中,从而方便`查阅信息`
>   - ![](./images/Snipaste_2019-06-23_23-05-02.png)
> - 输出到`elasticsearch`,是最常用的插件,基于`http`协议实现
>   - ![](./images/Snipaste_2019-06-23_23-06-17.png)
>   - `文档`

### `LogStash`

> - 内部流转的数据表现形式
> - 原始数据在`input`被转换为`Event`,在`output,envent`被转换为目标格式数据
> - 在配置文件中可以对`Event`中的属性进行`增删改查`

![](./images/Snipaste_2019-06-20_21-51-21.png)

![](./images/Snipaste_2019-06-20_21-52-12.png)

`input decoding`

![](./images/Snipaste_2019-06-20_21-53-13.png)

`output encoding`

![](./images/Snipaste_2019-06-20_21-56-20.png)

`6.0中 es 的架构`

![](./images/Snipaste_2019-06-20_22-01-14.png)

### `Queue`

- `In Memory`
  - 无法处理`crash`、机器宕机等情况,会导致`数据丢失`
- `Persistent Queue In Disk`
  - 可处理进程`Crash`等情况,保证数据不丢失
  - 保证数据至少消费一次
  - 充当缓冲区,可以替代`Kafka`等消息队列的作用
  - ![](./images/Snipaste_2019-06-20_22-21-25.png)
- `性能对比`
  - ![](./images/Snipaste_2019-06-20_22-22-04.png)
  - 建议一般没特殊需求使用`persisted`
    - 默认是`memory`
  - queue.max_btyes:4gb
    - 队列存储最大数据量

### `线程架构`

![](./images/Snipaste_2019-06-20_22-25-59.png)

- 相关配置
  - `pipline.workers | -w`
    - `Pipline`线程数,即`filter_output`的处理线程数,默认是`cpu`的核数`
  - `pipline.batch.size | -b`
    - `batcher`一次获取的待处理文档数,默认为 `125`,可以根据输出进行调整,越大会占用更多的 `heap`空间,可以通过 `jvm options`的调整
  - `pipline.batch.delay | -u`
    - `batcher` 等待的时长默认为`ms`

## `logstash配置文件`

- `logstash`设置相关配置(在`conf`文件夹中, `setting files`)
  - `logstash.yml` logstash 相关配置文件,比如`node.name`、`queue.type`等,这其中的配置可以被命令行参数中的相关参数覆盖
  - `jvm.options`修改`jvm`的相关参数,比如修改`heap size`等
- `pipline`配置文件
  - 定义数据处理流程的文件, 以`.conf`结尾
- `配置参数`讲解
  - `node.name`
    - 节点名称,定义一些比较具有意义的
  - `path.data`
    - 持久化存储数据的文件夹,默认 `logstash`目录下的`home`文件夹
  - `path.config`
    - 设置`pipline`配置文件的目录
  - `path.log`
    - 设定`pipline`日志文件的目录
  - `pipline.workers`
    - 设定`pipline`的线程数(`filter+output`),优化常用项
  - `pipline.batch.size/delay`
    - 设定批量处理数据的数目和延迟
  - `queue.type`
    - 设定队列类型,默认是`memory`
  - `queue.max_bytes`
    - 队列总容量默认为 `1G`
- `命令行配置`覆盖
  - `—node.name`
  - `-f —path.config pipline`路径可以是文件.也可以是文件夹
  - `—path.setting` logstash配置文件路径,其中必须包含`logstash.yml`
  - `-e —config.string`指定`pipline`内容,多用于测试
  - `-w --pipline workers`
  - `-b --pipline.batch.size`
  - `—path.data`
  - `--debug`
  - `-t —-config.test_and_exit`
- 线上环境建议使用配置文件来配置`logstash`去配置相关配置,这样可以减少犯错的机会,而且文件便于进行版本化管理
- 命令行形式多采用进行快速配置`测试,验证,检查`等

## 实例

### 调试阶段

- `http`做`input`,方便输入测试数据,并且可以结合`reload`特性(`stdin`无法 `reload`)
- `stdout`做`output`,`codec`作为`rubydebug`,即时查看解析结果
- 测试错误输入情况下的输出,以便对错误情况进行处理
- ![](/Users/xurunjie/Desktop/study_every_day_md/es/images/Snipaste_2019-06-23_23-15-04.png)
- `@metadata`特殊字段,其内容不会输出到`output`之中
  - 适合用来存储当条件判断、临时存储的字段
  - 相比` remove_field`有一定的性能提升
  - ![](./images/Snipaste_2019-06-23_23-19-07.png)