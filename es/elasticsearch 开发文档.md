## elasticsearch 开发文档

> - Document 文档
>   - 用户存储在es中的文档数据
> - 索引Index
>   - 类比mysql中的table --------------  6.0 不允许建多个type
> - 节点Node         ------------------------ 集群的节点单元
>   - es的运行实例
> - 集群cluster
>   - 对外提供实例

### `常用的交互方式`

> - kibanna
>
> ![](C:\Users\python\Desktop\es文档\images\Snipaste_2019-06-17_11-05-11.png)

### `Document`

```yaml
- JSON OBJECT 由字段(field)组成
	常见数据类型: text,keyword
	数值型: long,short,byte...
	布尔: Boolean
	日期: date
	二进制: binary
	范围类型: integer_range,float_range,long_range...
	
-  元数据,用于标注文档的相关信息
	_index: 文档所在的索引名
	_type: 文档所在的类型名
	_id: 文档唯一id
	_uid: 组合id,由_type和_id组成(6.x_type不在起作用,通_id一样)
	_source: 文档的原始json数据,可以获取每个字段的内容
	_all: 整合所有字段内容到该字段,默认禁用(不推荐使用)

```



#### 创建一个索引

> ##### 创建索引  PUT /test_index
>
> ![](.\images\Snipaste_2019-06-03_16-53-06.png)
>
> ##### 删除索引  DELETE /test_index
>
> ![](C:\Users\python\Desktop\es文档\images\Snipaste_2019-06-03_16-55-28.png)
>
> 

#### 新建文档

> ##### 新建文档 指定id创建
>
> PUT /test_index/doc/1
> {
>   "username":"alfred",
>   "age":1
> }
>
> ![](C:\Users\python\Desktop\es文档\images\Snipaste_2019-06-03_17-00-52.png)
>
> ##### 不指定id创建
>
> GET /text_index/_doc/
> {
>   "username": "alfred",
>   "age": 1
> }
>
> ![](C:\Users\python\Desktop\es文档\images\Snipaste_2019-06-03_17-04-38.png)
>
> 



#### 查询文档

> ##### 查找指定id的文档  **方法**1
>
> - id存在
>   - GET /test_index/doc/1
>   - ![](C:\Users\python\Desktop\es文档\images\Snipaste_2019-06-03_17-07-22.png)
> - id不存在
>   - GET /test_index/doc/4
>   - ![](C:\Users\python\Desktop\es文档\images\Snipaste_2019-06-03_17-08-52.png)
>
> ##### 查询文档文档api  方法2
>
> GET /test_index/doc/_search
> {
>   "query":{
>     "term": {
>       "_id": 1
>     }
>   }
> }
>
> ![](C:\Users\python\Desktop\es文档\images\Snipaste_2019-06-03_17-17-08.png)
>
> GET /test_index/doc/_search  # 获取所有
>
> ![](C:\Users\python\Desktop\es文档\images\Snipaste_2019-06-03_17-18-34.png)

#### 批量创建文档

> ##### `批量创建写入`
>
> - 批量写入操作
>   - index  `新建文档`
>   - update  `修改文档`  格式{update:{文档信息}}  后面跟 {文档}
>   - delete   `删除文档`
>
> POST /customer/external/_bulk
> {"index":{"_id":"1"}}
> {"name": "John Doe" }
> {"index":{"_id":"2"}}
> {"name": "Jane Doe" }
>
> ![](C:\Users\python\Desktop\es文档\images\Snipaste_2019-06-03_17-18-34.png)
>
> ##### 批量查询
>
> POST /customer/external/_mget    在下面docs中填写查询条件
> {
>   "docs": [
>     {
>       "_id": 1
>     },
>     {
>       "_id": 2
>     }
>   ]
> }
>
> ![](C:\Users\python\Desktop\es文档\images\Snipaste_2019-06-04_10-29-34.png)

### `搜索引擎`

- 正排索引 -------------------------------例  `书的目录`

  - 正排表是以文档的ID为关键字，表中记录文档中每个字的位置信息，查找时扫描表中每个文档中字的信息直到找出所有包含查询关键字的文档。

  ![](C:\Users\python\Desktop\es文档\images\20150923103812886.jpg)

- 倒排索引 -------------------------------例   `书的索引页`

  - 倒排表以字或词为关键字进行索引，表中关键字所对应的记录表项记录了出现这个字或词的所有文档，一个表项就是一个字表段，它记录该文档的ID和字符在该文档中出现的位置情况。

![](C:\Users\python\Desktop\es文档\images\20150923103845777.jpg)

> ##### `倒排列表`
>
> - 记录单词对应的文档集合,由倒排缩影posting组成
> - 倒排所影响破损厅主要包括如下信息
>   - `文档ID(doc id)`,用于获取`原始信息`
>   - `单词频率(term frequencies)`,记录单词在该文档`出现次数`,用于后续`相关性算分`
>   - `位置(position)`,记录单词在文档中的`分词位置(多个)`,用于做`词语搜索`
>   - `偏移(offsets)`,记录单词在文档中的`开始`和`结束`位置

#### 分词

------

##### 分词描述

> `- Character Filter` ------- 文本过滤
>
> - 针对原始文本进行处理,`比如去除html特殊标记符`
>
> `- Tokenizer` ------- 标记生成器
>
> - 将原始文本按照规则切分为单词
>
> `- Token Filter` -------  令牌过滤器
>
> - 正对tokenizer处理的单词进行再加工,比如`转小写`,`删除`或`新增等处理

##### 分词analyzer api   -----> `分析`

- `es`自带分词器
  - `Standard`
  - `Simple`
  - `Whitespace`
  - `Stop`
  - `Keyword`
  - `Pattern`
  - `Language`

> - 直接指定analyzer
>
> - > `GET _analyze`
>   > {
>   >   "analyzer": "standard",
>   >   "text":"hello world"
>   > }
>   >
>   > ![](C:\Users\python\Desktop\es文档\images\Snipaste_2019-06-17_14-20-53.png)
>
> - 直接指定索引的字段进行测试,接口如下
>
>   - >`POST text_index/_analyze`
>     >{
>     >  "field": "username",
>     >  "text":"hello world"
>     >}
>     >
>     >没指定分词器--------> 默认会使用"analyzer": "standard"分词器
>
> - 自定义分词器
>
>   - > `POST _analyze`
>     > {
>     >   "tokenizer": "standard",
>     >   "filter": ["lowercase"],
>     >   "text":"hello world"
>     > }
>     >
>     > ![](C:\Users\python\Desktop\es文档\images\Snipaste_2019-06-17_14-30-03.png)

##### standard

- `默认分词器`
- `按词分割`
- `支持多语言`
- `小写处理`
- `POST _analyze`
  {
    "analyzer": "standard",
    "text":"The 2 Quick Brown-Foxes jumped over the lazy dog's bone"
  }
- [the,2,quick,brown,foxes,jumped,over,the,lazy,dog's,bone]

##### simple

- `按照非字母切分`
- `小写处理`
- [the,quick,brown,foxes,jumped,over,the,lazy,dog,s,bone]

##### whitespace

- `按照空格切分`
- [The,2,Quick,Brown-Foxes,jumpe,over,the,lazy,dog's,bone]

##### stop

- `stop word`指语气助词等修饰性的词语,比如the、an、的、这等等
- 相比`simple`多了个token filter==>`stop word`等等
- [quick,brown,foxes,jumped,over,lazy,dog,s,bone]

##### keyword

- 不分词输出,直接`输入`作为`一个单词`输出

##### pattern

- 通过`正则表达式`自定义分隔符
- 默认是`\w+`,既`非字符符号`作为分隔符

##### language

- 提供了30+创建的语言分词

------

##### 中文分词

- 难点
  - 中文分词指的是将一个汉字序列切分成一个一个单独的词,在英文中,单词之间以空格作为自然分界符,汉语中词没有一个形式上的分界符
  - 上下文不同,分词结果含义不同,比如交叉奇异问题:
    - 兵乓球拍/卖/完了
    - 乒乓球/拍卖/完了
  - 目前主流的中文分词系统 [IK,jieba]   自然语言处理的分词系统[Hanlp,THULAC]

------

##### Characters Filters ----------> 自定义分词

- 在`Tokennizer`之前对原始文本进行处理,比如增加、删除或替换字符等
- 自带额如下:
  - `HTML Strip`去除html标签和转换的html实体
  - `Mapping`进行字符替换操作
  - `Pattern Replace`进行正则匹配替换
- 会影响position和offset

> `POST _analyze`{
>
>   "tokenizer": "keyword",
>   "char_filter": [
>     "html_strip"
>   ],
>   "text": "<p>I&apos;m so <b>happy</b>!</p>"
> }	

------

##### Tokenizer  ----------> 自定义分词

- 自带分词器
  - `standard` 按照单词进行分隔
  - `letter` 按照字符进行分隔
  - `whitespace` 按照空格进行分隔
  - `UAX` URL Email 按照standard分隔,但不会分隔邮箱和url
  - `NGram` 和 `Edge NGram` 连词分隔 -----------> 智能提示
  - `Path_Hierarchy` 按照文件路径进行切割

> `POST _analyze`
> {
>   "tokenizer": "path_hierarchy",
>   "text":"/one/two/three"
> }

------

##### token filter  ----------> 自定义分词

- 对于tokenizer输出的`单词(term)`进行增加、删除、修改等操作
- 自带如下
  - `lowercase` 将所有`term`转为小写
  - `stop`删除stop words ---> 既 a an the 等
  - `NGram` 和 `Edge NGram` 连词分隔
  - `Synonym` 添加近义词的term

> `POST _analyze`
>
> {
>   "text":"a hello,world!",
>   "tokenizer": "standard",
>   "filter": [
>     "stop",
>     "lowercase",
>     {
>       "type":"ngram",
>       "min_gram":4,
>       "max_gram":4
>     }
>     ]
> }

------

##### 自定义分词实现

- 自定义分词器

```json
# 定义分词器 -----------------> 单类型
PUT text_index_1
{
  "settings": {      ---------------> #配置
    "analysis": {	 ---------------->  #分词器族
      "analyzer": {  ----------------->  #分词器
        "my_custom_analyer":{  ------------->  #自定义的分词器
          "type":"custom",    ---------------> #类型
          "tokenizer":"standard",
          "char_filter":[
            "html_strip"
          ],
          "filter":[
            "lowercase",
            "asciifolding"
            ]
        }
      }
    }
  }
}
# 使用分词器
POST text_index_1/_analyze
{
  "analyzer": "my_custom_analyer",
  "text":"Is this <b> a box </b>?"
}

#  多类型
PUT text_index_2
{
  "settings": {
    "analysis": {
      "analyzer": {
        "my_custom_analyzer":{
          "type":"custom",
          "char_filter":[
            "emoticons"
            ],
            "tokenizer":"puncation",
            "filter":[
              "lowercase",
              "english_stop"
              ]
        }
      },
      "tokenizer": {
        "puncation":{
          "type":"pattern",
          "pattern":"[.,!?]"
        }
      },
      "char_filter": {
        "emoticons":{
          "type":"mapping",
          "mappings":[
            ":)=>_happpy_",
            ":(=>_sad_"
            ]
        }
      },
      "filter": {
        "english_stop":{
          "type":"stop",
          "stopwords":"_english_"
        }
      }
    }
  }
}

POST text_index_2/_analyze
{
  "analyzer": "my_custom_analyzer",
  "text":"I'm a :) person, and you?"
}
```

------

##### 分词使用的时机

- 索引时分词,通过配置index mapping中的每个字段的`analyzer`实现的,如下
- 不指定分词默认standard

```json
# 搜索时指定分词查询
POST text_index/_search
{
  "query":{
    "match": {
      "message": {
        "query": "hello",
        "analyzer": "standard"
      }
    }
  }
}

# 添加时指定分词查询
PUT text_index
{
  "mappings": {
    "properties": {
      "title":{
        "type": "text",
        "analyzer": "whitespace",
        "search_analyzer": "standard"
      }
    }
  }
}

```

- 明确字段是否需要分词,不需要分词的字段将`type`设置为keyword,可以节省空间和提高写性能
- 可用`_analyze api`查看具体的分词结果测试

------

#### Mapping

##### `类似数据库中的表结构定义,主要作用如下`

- 定义`Index`下的字段名(Field Name)
- 定义字段类型,比如数值类型,字符串类型,布尔类型
- 定义倒排索引相关配置,比如是否索引,记录`position`等
- mapping中的字段类型一旦设定后,`禁止`直接修改,原因如下 -------------------  类比mysql表结构
  - lucene实现的倒排索引生成后不允许修改
  - 重新建立新的索引,然后做reindex操作
- 自定义字段
  - 允许新添字段

  - 通过dynamic参数来控制字段新增 ------------------> 更具具体需求选择
    - `true(默认)`  允许新增字段

    - `false`  不允许新增字段,但是文档可以正常写入,但无法对字段进行查询等操作

    - `strict` 文档不许与写入,报错

    - ```json
      # 新增表结构定义
      PUT my_index
      {
        "mappings": {
          "_doc":{
            "dynamic":false,
            "properties":{
              "title":{
                "type":"text"
              },
              "name":{
                "type":"keyword"
              },
              "age":{
                "type":"integer"
              }
            }
          }
        }
      }


      GET my_index/_mapping


      PUT /my_index/_doc/1
      {
        "title":"hello,world",
        "desc":"nothing here"
      }

      GET my_index/_doc/_search
      {
        "query":{
          "match":{
            "title":"hello"
          }
        }
      }

      GET my_index/_doc/_search
      {
        "query":{
          "match":{
            "strict":"here"
          }
        }
      }
      ```

  - `copy_to`

    - 将该字段的值赋值到目标字段中,实现类似_all的作用

    - 不会出现_source中,只用来搜索

    - ```json
      PUT my_index
      {
        "mappings": {
          "_doc":{
            "properties": {
            "first_name":{
              "type": "text",
              "copy_to": "full_name"
            },
            "last_name":{
              "type": "text",
              "copy_to": "full_name"
            },
            "full_name":{
              "type": "text"
            }
          }
          }
        }
      }

      PUT my_index/_doc/1
      {
        "first_name":"John",
        "last_name":"Smith"
      }

      GET my_index/_search
      {
        "query": {
          "match": {
            "full_name": {
              "query": "John Smith",
              "operator": "and"
            }
          }
        }
      }
      ```

  - `index`

    - 控制当前字段是否索引,默认为true,既记录索引,false不记录,既不可搜索

    - 案例分析---------------需求身份证号等不需要被检索的一些敏感信息

    - ```json
      PUT my_index
      {
        "mappings": {
          "_doc": {
            "properties": {
              "cookies": {
                "type": "text",
                "index": false
              }
            }
          }
        }
      }


      PUT my_index/_doc/1
      {
        "cookies":"name=alfread"
      }

      GET my_index/_search
      {
        "query": {
          "match": {
            "cookies": "name"
          }
        }
      }
      ```

  - `index_options` 用于控制倒排索引记录的内容,有如下4中配置

    - `docs` 只记录 doc id

    - `freqs`记录doc id 和 term frequencies

    - `position`记录doc id、term frequencies 和 term position

    - `offsets` 记录doc id、term frequencies、term position 和character offsets

    - text 类型默认配置`position`,其他默认为`docs`

    - 记录内容越多,占用空间越大

    - ```json
      PUT my_index
      {
        "mappings": {
          "_doc": {
            "properties": {
              "cookies": {
                "type": "text",
                "index_options": "offsets"
              }
            }
          }
        }
      }
      ```

  - `null_value`

    - 当字段遇到null值的水处理策略,默认为null,既控制,此时es会忽略此值,可以通过设定该值设定字段的默认值

    - ```JSON
      PUT my_index
      {
        "mappings": {
          "_doc":{
            "properties":{
              "status_code":{
                "type":"keyword",
                "null_value":"NULL" ---------------------> 默认值
              }
            }
          }
        }
      }

      ```

##### `核心数据类型`

- 字符串类型  --------------------->`text` ,`keyword`
- 数值型        --------------------->`long`,`integer`,`short`,`byte`,`double`,`float`,`half_float`,`scaled_float`
- 日期类型     --------------------->`date`
- 布尔类型     --------------------->`boolean`
- 二进制类型  ---------------------> `binary`
- 范围类型     --------------------->`integer_range`,`float_range`,`long_range`,`double_range`,`date_range`

##### `复杂数据类型`

- 数组类型     --------------------->`array`
- 对象类型     --------------------->`object`
- 嵌套类型     --------------------->`nested object`

##### `地理位置数据类型`

- 地理位置点  --------------------->`geo_point`
- 地理位置形状  ------------------>`geo_shape`

##### `专用类型`

- 记录ip地址`ip`
- 实现自动补全`completion`
- 记录分词数`token_count`
- 记录字符串hash值`murmur3` --------------------> 插件实现
- 增量处理的`percolator`
- 父子查询`join`

##### `多字段特性multi-fields`

- 允许对`同一个字段`采用不同的配置,比如`分词`,常见的例子入对人名实现`拼音搜索`,只需要在人名中新增一个子字段未`pinyin`即可

##### `Dynamic Mapping`

- es可以`自动识别文档字段类型`,从而降低用户使用成本,如下所示

- ```json
  DELETE text_index

  PUT /text_index/_doc/1
  {
    "username":"alfred",
    "age":3
  }

  GET /text_index/_mapping

  {
    "text_index" : {
      "mappings" : {
        "_doc" : {
          "properties" : {
            "age" : {
              "type" : "long"
            },
            "username" : {
              "type" : "text",
              "fields" : {
                "keyword" : {
                  "type" : "keyword",
                  "ignore_above" : 256
                }
              }
            }
          }
        }
      }
    }
  }
  ```

- es依靠`json文档`的字段类型来实现`自动识别字段类型`,支持类型如下

- ​

##### `日期的自动识别`

- 默认是["strict_date_optional_time","yyyy/MM/dd HH:mm:ss Z||yyyy/MM/DD Z"]
- strict_date_optional_time 是ISO datetime的格式
- dynamic_date_formats可以自定义日期类型
- date_detection 可以关闭日期自动识别的机制

```json
PUT text_index/_doc/1
{
  "create_date":"09/25/2015"
}

PUT text_index
{
  "mappings": {
    "_doc": {
      "dynamic_date_formats": [
        "MM/dd/yyyy"
      ]
    }
  }
}

GET /text_index/_mapping

```

##### `数字识别`

- 字符串是数字,`默认`不会自动识别为整形,因为字符串穿线数字是完全合理的
- numeric_detection可以开启字符串中数字的自动识别

```json
GET /text_index/_mapping


PUT text_index/_doc/1
{
  "my_float": "1.0",
  "my_integer": "1"
}

PUT text_index
{
  "mappings": {
    "_doc":{
      "numeric_detection":true
    }
  }
}
```

