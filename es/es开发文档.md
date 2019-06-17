##### es

- `es`依靠`JSON`文档的字段类型来实现自动识别字段类型

  - | Json类型 |                            es类型                            |
    | :------: | :----------------------------------------------------------: |
    |   null   |                             忽略                             |
    | Boolean  |                           Boolean                            |
    | 浮点类型 |                            float                             |
    |   整数   |                             long                             |
    |  object  |                            object                            |
    |  array   |                    由一个非null值类型决定                    |
    |  String  | 匹配一个日期则生成date类型（默认开启）                                                                                匹配为数字的话设为float和long（默认关闭）                                                                        设为text类型，并附带keyword的子字段 |

    ```json
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

  - 写入一条文档到es的临时缩影中，获取es自动生成的mapping

  - 修改步骤1得到的mapping，自定义相关配置

  - 使用步骤2的mapping创建市级所需索引

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

  - 使用dynamic_templates实现查询mappings简化

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

  - 可以设定索引的配置和mapping

  - 可以有多个模版，根据order设置，order大的覆盖小的配置

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

  - 

