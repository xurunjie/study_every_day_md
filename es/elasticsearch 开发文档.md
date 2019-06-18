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
> ![](.\images\Snipaste_2019-06-17_11-05-11.png)

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
> ![](C:.\images\Snipaste_2019-06-03_16-55-28.png)
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
> ![](C:.\images\Snipaste_2019-06-03_17-00-52.png)
>
> ##### 不指定id创建
>
> GET /text_index/_doc/
> {
>   "username": "alfred",
>   "age": 1
> }
>
> ![](.\images\Snipaste_2019-06-03_17-04-38.png)
>
> 



#### 查询文档

> ##### 查找指定id的文档  **方法**1
>
> - id存在
>   - GET /test_index/doc/1
>   - ![](.\images\Snipaste_2019-06-03_17-07-22.png)
> - id不存在
>   - GET /test_index/doc/4
>   - ![](.\images\Snipaste_2019-06-03_17-08-52.png)
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
> ![](.\images\Snipaste_2019-06-03_17-17-08.png)
>
> GET /test_index/doc/_search  # 获取所有
>
> ![](.\images\Snipaste_2019-06-03_17-18-34.png)

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
> ![](.\images\Snipaste_2019-06-03_17-18-34.png)
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
> ![](.\images\Snipaste_2019-06-04_10-29-34.png)

### `搜索引擎`

- 正排索引 -------------------------------例  `书的目录`

  - 正排表是以文档的ID为关键字，表中记录文档中每个字的位置信息，查找时扫描表中每个文档中字的信息直到找出所有包含查询关键字的文档。

  ![](.\images\20150923103812886.jpg)

- 倒排索引 -------------------------------例   `书的索引页`

  - 倒排表以字或词为关键字进行索引，表中关键字所对应的记录表项记录了出现这个字或词的所有文档，一个表项就是一个字表段，它记录该文档的ID和字符在该文档中出现的位置情况。

![](.\images\20150923103845777.jpg)

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

##### 概念

分词描述

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

分词analyzer api   -----> `分析`

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
>   > ![](.\images\Snipaste_2019-06-17_14-20-53.png)
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
>     > ![](.\images\Snipaste_2019-06-17_14-30-03.png)

##### `默认分词器`

> `standard`
>
> - `默认分词器`
> - `按词分割`
> - `支持多语言`
> - `小写处理`
> - `POST _analyze`
>   {
>     "analyzer": "standard",
>     "text":"The 2 Quick Brown-Foxes jumped over the lazy dog's bone"
>   }
> - [the,2,quick,brown,foxes,jumped,over,the,lazy,dog's,bone]
>
> `simple`
>
> - `按照非字母切分`
> - `小写处理`
> - [the,quick,brown,foxes,jumped,over,the,lazy,dog,s,bone]
>
> `whitespace`
>
> - `按照空格切分`
> - [The,2,Quick,Brown-Foxes,jumpe,over,the,lazy,dog's,bone]
>
> ##### `stop`
>
> - `stop word`指语气助词等修饰性的词语,比如the、an、的、这等等
> - 相比`simple`多了个token filter==>`stop word`等等
> - [quick,brown,foxes,jumped,over,lazy,dog,s,bone]
>
> ##### `keyword`
>
> - 不分词输出,直接`输入`作为`一个单词`输出
>
> ##### `pattern`
>
> - 通过`正则表达式`自定义分隔符
> - 默认是`\w+`,既`非字符符号`作为分隔符
>
> ##### `language`
>
> - 提供了30+创建的语言分词

------

##### 中文分词

- 难点
  - 中文分词指的是将一个汉字序列切分成一个一个单独的词,在英文中,单词之间以空格作为自然分界符,汉语中词没有一个形式上的分界符
  - 上下文不同,分词结果含义不同,比如交叉奇异问题:
    - 兵乓球拍/卖/完了
    - 乒乓球/拍卖/完了
  - 目前主流的中文分词系统 [IK,jieba]   自然语言处理的分词系统[Hanlp,THULAC]

------

##### `自定义分词`

`Characters Filters` ----------> 自定义分词

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

`Tokenizer ` ----------> 自定义分词

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

`token filter`  ----------> 自定义分词

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

自定义分词实现

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

`类似数据库中的表结构定义,主要作用如下`

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
      ```


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
      ```


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

- `es`依靠`JSON`文档的字段类型来实现自动识别字段类型

  - | Json类型 | es类型                                                       |
    | -------- | ------------------------------------------------------------ |
    | null     | 忽略                                                         |
    | Boolean  | Boolean                                                      |
    | 浮点类型 | float                                                        |
    | 整数     | long                                                         |
    | object   | object                                                       |
    | array    | 由一个非null值类型决定                                       |
    | String   | 匹配一个日期则生成date类型（默认开启） 匹配为数字的话设为float和long（默认关闭） 设为text类型，并附带keyword的子字段 |

    ```
    DELETE test_index

    PUT test_index/_doc/1
    {
      "username":"alfred",
      "age":15,
      "birthday":"1988-10-10",
      "married":false,
      "year":18,
      "tags":["boy","fashion"],
      "money":100.1
    }

    GET test_index/_mapping
    ```

###### Dynamic Template

- 允许更具es自动识别的数据类型，字段名等来动态设置字段类型，可以实现如下效果

  - 允许所有`字符串`类型都设定为keyword类型，即默认部分词
  - 所有以message开头的字段都设定为text类型，即粉刺
  - 所有以long——开头的字段都设定为long类型
  - 所有自动匹配为double类型的都设定为float类型，以节省空间

- 匹配规则一般有如下几个参数

  - match_mapping_type 匹配es自动识别的字段类型，入boolean，long，string等

  - match，umactch匹配字段名

  - path_match,path_umatch 匹配路径

  - ```json
    #案例一
    PUT test_index
    {
      "mappings": {
        "dynamic_templates": [ -------------------> 数组可通过多个正则匹配
          {
            "strings_as_keywords": {   -----------------> templates的名称
              "match_mapping_type": "string", -----------------> 匹配的规则
              "mapping": {     ----------------> 设置mapping信息
                "type": "keyword"
              }
            }
          }
        ]
      }
    }

    DELETE test_index

    PUT test_index/_doc/1
    {
      "name":"alfred"
    }

    GET test_index/_mapping
    ```


    # 案例2
    DELETE test_index
    
    PUT test_index
    {
      "mappings": {
        "dynamic_templates": [
          {  ----------------        注意顺序
            "message_as_text": {
              "match_mapping_type": "string",
              "match": "message",
              "mapping": {
                "type": "text"
              }
            }
          },
          {
            "string_as_keyword":{
              "match_mapping_type":"string",
              "mapping":{
                "type":"keyword"
              }
            }
          }
        ]
      }
    }
    
    PUT test_index/_doc/1
    {
      "name":"alfred",
      "message":"handsome boy"
    }
    
    GET test_index/_mapping
    ```

###### 自定义Mapping的建议

- 自定义Mapping的操作步骤如下：

  - 写入一条文档到es的`临时索引`中，获取es自动生成的mapping

  - 修改步骤1得到的`mapping`，自定义相关配置

  - 使用步骤2的`mapping`创建实际所需索引

  - ```json
    PUT test_index/_doc/1
    {
      "referrer":"-",
      "response_code":"200",
      "remote_ip":"127.0.0.1",
      "method":"POST",
      "user_name":"-",
      "http_version":"1.1",
      "body_sent":{
        "bytes":"0"
      },
      "url":"/analyzeVideo"
    }

    PUT test_index
    {
      "mappings": {
        "properties": {
          "body_sent": {
            "properties": {
              "bytes": {
                "type": "long"
              }
            }
          },
          "http_version": {
            "type": "keyword"
          },
          "method": {
            "type": "keyword"
          },
          "referrer": {
            "type": "keyword"
          },
          "remote_ip": {
            "type": "keyword"
          },
          "response_code": {
            "type": "keyword"
          },
          "url": {
            "type": "text"
          },
          "user_name": {
            "type": "keyword"
          }
        }
      }
    }

    GET test_index/_mapping
    ```

  - 使用`dynamic_templates`实现查询mappings简化

  - ```json
    PUT test_index
    {
      "mappings": {
        "dynamic_templates":[
          {
            "string_as_keyword":{
              "match_mapping_type":"string",
              "mapping":{
                "type":"keyword"
              }
            }
          }
          ],
        "properties": {
          "body_sent": {
            "properties": {
              "bytes": {
                "type": "long"
              }
            }
          },
          "url": {
            "type": "text"
          }
        }
      }
    }

    PUT test_index/_doc/1
    {
      "referrer":"-",
      "response_code":"200",
      "remote_ip":"127.0.0.1",
      "method":"POST",
      "user_name":"-",
      "http_version":"1.1",
      "body_sent":{
        "bytes":"0"
      },
      "url":"/analyzeVideo"
    }

    GET test_index/_mapping
    ```

索引模版

- 简化索引创建的操作步骤

  - 可以设定`索引的配置`和mapping

  - 可以有`多个模版`，根据`order`设置，order大的覆盖小的配置

  - ```json
    # demo版本
    PUT _template/test_template      --------------> 以_template/模版名称起始
    {
      "index_patterns":["te","bar"],  ---------------> 6.0 es以前使用index_template
      "order":0,        ------------------> order 顺序配置，template按大小加载
      "settings":{      ------------------> 索引的配置  或更具order下发
        "number_of_shards":1   ---------------> 切片数
      },
      "mappings":{
        "_source":{
          "enabled":false
        },
        "properties":{
          "name":{
            "type":"keyword"
          }
        }
      }
    }
    ```




~~~JSON
# 实例
PUT _template/test_template
{
  "index_patterns":["te*","bar*"],
  "order":0,
  "settings":{
    "number_of_shards":1
  },
  "mappings":{
    "_source":{
      "enabled":false
    },
    "properties":{
      "name":{
        "type":"keyword"
      }
    }
  }
}

PUT _template/test_template2
{
  "index_patterns":["test*"],
  "order":1,         ----------------> 重要
  "settings":{
    "number_of_shards":1
  },
  "mappings":{
    "_source":{
      "enabled":true     ------------> 隐藏
    }
  }
}

DELETE foo_index
PUT foo_index
GET foo_index/

DELETE bar_index
PUT bar_index
GET bar_index/

DELETE test_index
PUT test_index
GET test_index
```
~~~

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

------

#### search api

- 实现对`es`中存储得数据进行查询分析,endpoint为_search,如下所示

- ```http
  GET /_search
  GET /my_index/_search
  GET /my_index1,my_index2/_search
  GET /my_*/_search
  ```

- 查询主要有两种形式

  - URI search

    - 操作简单,方便通过命令行去测试

    - 仅包含部分查询语法

    - ```http
      GET /my_index/_search?q=user:alfred
      ```

    - `常用参数`

      - `q`指定查询语句,语法为Query String Syntax

      - `df q`中不指定字段是默认查询的字段,如果不指定,`es`会查询所有的字段

      - `sort`排序

      - `timeout`指定超时时间,默认不超时

      - `from,size`用于分页

      - ```http
        GET /my_index/_search?q=alfred&df=user&sort=age:asc&from=4&size=10&timeout=1s
        # 这里asc为升序  desc为降序
        ```

    - `Query String Syntax`详解

      - `term`和`phrase` ---------------------> 明确概念  单词和词语

        - `alfred way` 等效于 查询 `alfred OR way`
        - `"alfred way"`词语查询,要求先后顺序

      - 泛查询

        - `alfred`等效于在所有字段去匹配该`term`

      - 指定字段

        - name:alfred

      - Group 分组设定,使用括号指定匹配规则

        - (quick OR brown) AND fox -----------------------------> `优先匹配括号里的`

        - status:(active OR pending) title:(full text search) 

          - `status:(active OR pending)`不加括号的话会匹配status:active 或者所有的字段中包含 pending的文档

          - `title:(full text search)`不加括号的话会匹配title:full或者所有的字段中包含 text search的文档

          - ```json
            DELETE test_search_index
            POST /test_search_index/_doc/_bulk

            {"index":{"_id":"1"}}
            {"username":"alfred way","job":"java engineer","age":18,"birthday":"1990-01-02","isMarried":false}
            {"index":{"_id":"2"}}
            {"username":"alfred","job":"java senior engineer and java specialist","age":28,"birthday":"1980-05-07","isMarried":true}
            {"index":{"_id":"3"}}
            {"username":"lee","job":"java and  ruby engineer","age":22,"birthday":"1985-08-07","isMarried":false}
            {"index":{"_id":"4"}}
            {"username":"alfred junior way","job":"ruby engineer","age":23,"birthday":"1989-08-07","isMarried":false}

            GET test_search_index/_search?q=alfred -------------> # 泛查询

            GET test_search_index/_search?q=alfred --------------># 查看泛查询干了什么
            {
              "profile": true
            }

            GET test_search_index/_search?q=username:alfred way
            {
                "profile": true
            }

            GET test_search_index/_search?q=username:"alfred way" -> 查询词语username=alfred way
            {
                "profile": true
            }

            GET test_search_index/_search?q=username:(alfred way)->查询语句username=alfred or way
            {
                "profile": true
            }
            ```

        - `布尔操作符`

          - `AND(&&),OR(||),NOT(!)`

            - name:(tom NOT lee)
            - 注意大写,不能小写

          - `+ -对应操作must和not must`

            - name:(tom`+`lee`-`alfred)

            - name:(lee`&&` `!`alfred)!!(tom`&&`lee`&&` `!`alfred)

            - `+`在url中会被解析成空格,要使用encode后的结果才可以,为`%2B`

            - ```json
              GET test_search_index/_search?q=username:alfred AND way
              {
                "profile": true
              }

              GET test_search_index/_search?q=username:alfred NOT way
              {
                "profile": true
              }

              GET test_search_index/_search?q=username:(alfred %2Bway)
              {
                "profile": true
              }
              ```

        - `范围查询`,支持数值和日期

          - 区间写法,闭区间用`[]`开区间用`{}`

            - age:`[1 TO 10]`意思为`1<=age<=10`
            - age:`[1 TO 10}`意思为`1<=age<10`
            - age:`[1 TO ]`意思为`age >= 1`
            - age:`[* TO 10]`意思为`age<=10`

          - 算数符号写法

            - age:`>=1`
            - age:(`>=1&& <=10`)或者age:(`+>=1+<=10`)

          - ```JSON
            GET test_search_index/_search?q=age:>20

            GET test_num_index/_search?q=age:[* TO 10]

            GET test_search_index/_search?q=birthday:(>1980 AND <1990)
            ```

        - `通配符查询`

          - `?`代表一个字符,`*`代表0或多个字符

            - name:`t?m`
            - name:`tom*`
            - name:`t*m`

          - `缺点`

            - 通配符匹配效率低,且占用较多内存,不建议使用

          - 如无特殊需求,不要将`?/*`放在最前面

          - ```json
            GET test_search_index/_search?q=username:alf*
            ```

        - `正则表达式`

          - name:`/[mb]oat/`
          - GET test_search_index/_search?q=username:/[a]?l.*/
          - 比较吃内存,尽量少用

        - `模糊匹配 fuzzy query`

          - name:`roam~1`

          - 匹配与`roam`差一个`character`的词,比如`foam,roams`等

          - ```json
            GET test_search_index/_search?q=username:alfed~1
            {
              "profile": "true"
            }
            ```

        - `近似度查询 proximity search`

          - `"fox quick"~5`

          - 以term为单位进行差异比较,比如`"quick fox"` `"quickbrown fox"`都会被匹配到

          - ```json
            GET test_search_index/_search?q=job="java engineer"~2
            {
              "profile": "true"
            }
            ```


  - Request Body Search

      - 将查询语句通过http request body 发送到es,主要包括如下参数

          - `query` 符合`Query DSL`语法的查询语句
          - `from,size`
          - `timeout`
          - `sort`
          - ...

          - es提供的完备查询语法Query DSL(Domain Specific Language) -----------> `基于JSON定义的查询语言`

      - 字段类查询 ------------------------>`只针对某个字段`

        - 如`term,match,range`等,只针对某一个字段进行查询

        - 分为两类

          - `全文`匹配

            - 针对`text`类型的字段进行`全文检索`,会对查询语句先进行分词处理,如`match`,`match_phrase`等`query`类型

            - `match`----->如下

            - ```json
              GET test_search_index/_search
              {
               
                "query": {
                  "match": {
                    "username": "alfred "
                  }
                }
              }
              ```



              GET test_search_index/_search
              {
                "query": {
                  "match": {
                    "username": {
                      "query": "alfred way",
                      "operator": "and"    --------------------> #控制单词之间的匹配关系`operator`
                    }
                  }
                }
              }

              GET test_search_index/_search
              {
                "query": {
                  "match": {
                    "username": {
                      "query": "alfred way",
                      "minimum_should_match": "2" ---> #至少包含两个单词 关键字`minimum_should_match`
                    }
                  }
                }
              }
    
              GET test_search_index/_search
              {
                "query": {
                  "match": {
                    "job": {
                      "query": "java ruby engineer",
                      "minimum_should_match": "2"
                    }
                  }
                }
              }


              ```
    
            - `match phrase query` -------> 有先后顺序限制
    
            - ```json
              GET test_search_index/_search
              {
                "query": {
                  "match_phrase": {
                    "job": {
                      "query": "java engineer",  ---------->
                      "slop": 1  -----------># 允许存在一个差异
                    }
                  }
                }
              }
              ```
    
            - `query_string` -----------> 类似`url search`中的`q`参数查询
    
            - ```json
              GET test_search_index/_search
              {
                "query": {
                  "query_string": {
                    "default_field": "username", ->#设置默认字段
                    "query": "alfred AND way"
                  }
                }
              }
    
              GET test_search_index/_search
              {
                "profile": "true", 
                "query": {
                  "query_string": {
                    "fields": ["username","job"],
                    "query": "alfred OR (java AND ruby)" ------> #指定查询字段
                  }
                }
              }
              ```
    
            - `simple_query_string`
    
              - `+`代指`AND`
    
              - `|`代指`OR`
    
              - `-`代指`NOT`
    
              - ```JSON
                GET test_search_index/_search
                {
                  "query": {
                    "simple_query_string": {
                      "query": "alfred +way",
                      "fields": ["username"]
                    }
                  }
                }
                ```
    
          - `单词`匹配
    
            - 不会对查询语句做分词处理,直接去匹配字段的`倒排索引`,如`term,terms,range`等`query`类型
    
            - `term query`    -------------->`直接断句匹配`
    
              - ```
                GET test_search_index/_search
                {
                  "query": {
                    "term": {
                      "username": "alfred"
                    }
                  }
                }
                GET test_search_index/_search
                {
                  "query": {
                    "term": {
                      "username": "alfred way"
                    }
                  }
                }
                ```
    
            - `terms query`
    
              - ```json
                GET test_search_index/_search
                {
                  "query": {
                    "terms": {
                      "username": [
                        "way",
                        "alfred"
                      ]
                    }
                  }
                }
                ```
    
            - `range query`范围查询
    
              - 针对`数值`和`日期`类型进行`查询`
    
              - 针对日期提供了更友好的计算方式
    
                - `now - 1d` --------->`now`指当前时间 `1d`指一天`1y`指`1`年
                - `2018-01-01||-40y`  这里如果写死时间需要用`||`隔开以示区分
                - `y - years`,`M - months`,`w - weeks`,`d - days`,`h - hours`,`m - minutes`,`s - second`
    
              - ```json
                GET test_search_index/_search
                {
                  "query": {
                    "range": {
                      "birthday": {
                        "gte": "1990-01-01"
                      }
                    }
                  }
                }
    
                GET test_search_index/_search
                {
                  "query": {
                    "range": {
                      "birthday": {
                        "gte": "now-40y"
                      }
                    }
                  }
                }
    
                GET test_search_index/_search
                {
                  "query": {
                    "range": {
                      "birthday": {
                        "gte": "2018-01-01||-40y"
                      }
                    }
                  }
                }
                ```
    
          - `相关性算分`----------------> `relevance`
    
            - 通过`倒排索引`可以`获取`与`查询语句`相`匹配`的`文档`列表
    
            - 几种关键`概念`
    
              - `Term Frequency(TF)`词频,既单词在`文档`中`出现`的次数,`词频`越`高`,`相关性`越`高`
              - `Document Frequency(DF)` `文档`频率,既`单词` `出现`的`文档`数
              - `Inverse Document Frequency(IDF)`逆向`文档`频率,与`文档`频率相反,简单理解为1/DF,既单词出现的`文档`数越少,`相关`度越`高`
              - `Field-Length Norm`文档越短,`相关`度越`高`
    
            - `es`目前主要的两个相关性算分模型,如下
    
              - `TF/IDF`模型
    
                - ```python
                  socre(q,d)=coord(q,d)·queryNorm(q)·∑(tf(t in d)·idf(t)²·t.getBoost().norm(t,d))
    
                  # q为查询语句 ------->query,document
                  # coord(q,d)·queryNorm(q)----> 对query进行正则化的处理
                  # ∑ 对查询后的每个tm进行求和
                  # d未匹配文档
                  # t为查询语句分词后的单词
                  # tf计算 ------>  词频
                  # idf计算 ---------->逆向文档频率
                  # t.getBoost() ------------> 是否对term进行过相应的甲醛
                  # norm -----> Filed Length Norm计算

                  """词频越高  tf  idf频率越小   Filed Length Norm计算  得分越高"""
                  ```
    
                - 可以通过`explain`参数来具体的计算方法
    
                  - `es`的算分是按照shard进行的,既`shard(分片)`的分数计算是相互独立的,所以在使用`explain`的时候注意分片数
    
                  - 可通过设置`索引分片`数为`1`来规避这个问题
    
                  - ```json
                    GET test_search_index/_search
                    {
                      "explain": true,
                      "query": {
                        "match": {
                          "username": "alfred way"
                        }
                      }
                    }

                    PUT test_search_index
                    {
                      "settings": {
                        "index": {
                          "number_of_shards": "1"
                        }
                      }
                    }
                    ```
    
              - `BM25`模型在`es5.x`之后的默认模型
    
            - 如何将最符合用户`查询需求`的文档放在`前列`呢?本质上是个排序问题,排序的依据是相关性算分
    
              - |  单词  | 文档ID 列表 |
                | :----: | :---------: |
                | alfred |     1,2     |
                |  way   |     1`      |
    
      - 复合查询
    
        - 如`bool`查询等,`包含一个或多个字段类查询或者复合查询语句`
    
      - `_count`API 只向获取文档数不想获取其他
    
        - ```json
          GET test_search_index/_count
          {
            "query": {
              "match": {
                "username": "alfred"
              }
            }
          }
          ```
    
      - `_source`filter使用
    
        - 第一种
    
          - ```http
            GET test_search_index/_search?_source=username  ----> 指定只显示username
            ```
    
        - 第二种
    
          - ```json
            GET test_search_index/_search
            {
              "_source": ["username","age"]
            }
            ```
    
        - 第三种
    
          - ```json
            GET test_search_index/_search
            {
              "_source": {
                "includes": "i*",
                "excludes": "birth*"
              }
            }
            ```

------

### 分部式特性

------

#### 特性

- `es`支持`集群模式`,是一个`分布式系统`,其好处有两个:
  - 增大`系统容量`,比如`内存`,`磁盘`,使得`es`集群可以支持`PB`级的数据
  - 提高`系统可用性`,即使部分`节点`停止`服务`,整个集群依然可以正常工作
- `es`集群是由多个`es`实例组成
  - 不同集群通过`集群`名字来区分,可通过`cluster.name`进行修改,默认为`elasticsearch`
  - 每个`es`实例本质上是一个`JVM`进程,且有自己的名字,通过`node.name`进行修改

#### 插件安装

- [插件地址](https://github.com/lmenezes/cerebro)---------> `cerebro`

#### 集群

##### 概要

- 可以修改`cluster state`的节点成为`master`节点,一个集群`只能有一个`

- `cluster state`存储在每个节点上,`master`维护`最新版本并`同步给其他节点

- `master`节点是通过集群中所有节点选举产生的,可以被选举节点称为`master-eligible`节点,相关配置如下

  - `node.master:true`  ===================> 在`config/elasticsearch.yaml`中配置不指定`既默认`

- `coordination node`协调节点---->处理请求的节点即为`coordinating节点`,该节点为所有节点的默认角色,不能取消

  - `路由请求`到`正确的节点`处理,比如`创建索引`的`请求`到`master`节点

- `node.data`存储数据的节点即为`data节点`,默认节点都是`data`类型,相关配置如下

  - `node.data:true`

- `单点问题`

  - 停止一个节点就停止工作了
  - 新加一个节点

- `服务可用性`

  - 2个节点的情况下,允许其中一个节点停止`服务`

- `数据可用性`

  - 引入`副本`解决
  - 每个`节点`都有完备的`数据`

- 如何将数据分布到所有节点上

  - 引入分片`shard`解决问题

  - 分片是`es`支持`PB`级数据的基石

    - 分片存储`部分数据`,可以分布在任意节点上

    - 分片数在`索引`创建时指定且后续不允许在更改,默认为`5个`

    - 分片有主分片副分片之分,以实现数据的`高可用`

    - `副本分片`的数据有`主分片`同步,可以有多个,从而提高读取的`吞吐量`

    - ```json
      PUT test_index/
      {
        "settings": {
          "number_of_replicas": 3, -----------> 3个副本
          "number_of_shards": 3  ------------> 3个切片
        }
      }
      ```

    - `问题`

    - > - 此时增加节点是否能提高`test_index`数据容量
      >   - -不能,因为只有三个分片,已经分布在`3台节点`上了,新增的节点无法使用
      > - 此时增加副本数是否能提高`test_index`的读取吞吐量
      >   - 不能,因为新增的副本也是分布在这3个节点上,还是利用了同样的资源,如果要增加吞吐量,还需要新增节点

    - `cluster health`集群状态

      - `es`提供了api可以查看集群的健康状态,包括以下三种:

        - `green`健康状态,指所有主副分片都正常分配

        - `yellow`指所有主分片都正常分配,但是有副本分片未正常`分配`

        - `red`有`主分片`未分配

        - ```http
          GET _cluster/health
          ```

    - `failover`故障转移

      - 假设`集群`有三个节点组成`node1,node2,node3`
      - `node1`所在机器`宕机`导致服务终止,此时集群会如何处理,
      - `node2`和`node3`发现`node1`无法响应一段时间会发起`master`选举,比如这里选择`node2`为`master`节点,此时由于`主分片P0`下线,`集群状态`变为`Red`

##### 文档分布式存储

- `文档document1`最终存储在`分片p1`上
  - `文档document1`是如何存储到`分片p1`上的?选择p1的依据是什么
    - 需要`文档`到`分片`的映射算法
  - 目的
    - 使得文档`均匀分布`在`所有分片`上,以充分`利用资源`
  - 算法
    - 随机选择或者`round-robin`算法? ------------  `可以实现均匀分布的目标`

    - 不可取,因为需要`维护文档`到`分片`的`映射`关系,成本巨大

      - 文档存取的时候我们还是要读的,随机存储我们无法知道`document1`存储在哪,如果存在多个分片,比如说存在100个,一个读请求会到100个上面读一下,可能99个读取都是浪费的,浪费读取时间.

    - 根据`文档值`实时计算对应的`分片`

    - `es`通过如下的公式计算稳定对应的分片

      - shard = hash(`routing`)%`number_of_primary_shards`
      - `hash`算法保证可以将数据均匀的分散在`分片`中
      - `routing`是一个关键`参数`,默认是`文档id`,也可以`自行指定`
      - `number_of_primary_shards` 主分片数

    - 该算法与主分片数有关,这也是`分片数一旦确定后便不能更改`的原因了

    - `脑裂问题`,`split brain`,是分部式系统中的经典网络问题

      - `出现脑裂问题`

      - 出现点

        - 1：网络原因

          - 内网一般不会出现此问题，可以监控内网流量状态。外网的网络出现问题的可能性大些。

        - 2：节点负载

          - 主节点即负责管理集群又要存储数据，当访问量大时可能会导致es实例反应不过来而停止响应，此时其他节点在向主节点发送消息时得不到主节点的响应就会认为主节点挂了，从而重新选择主节点

        - 3：回收内存

          -  大规模回收内存时也会导致es集群失去响应。

            所以内网负载的可能性大，外网网络的可能性大。

        ​

