# Beats篇

> ![](./images/Snipaste_2019-06-24_19-53-46.png)
>
> - 一个轻量级的收集数据
>
> ## 架构
>
> ![](./images/Snipaste_2019-06-24_19-56-05.png)

## FileBeat

- filebeat 运行

- ![](./images/Snipaste_2019-06-24_19-57-16.png)

- 读取日志文件,但不做数据的`解析`处理

- 保证日志`至少被读一次`,即数据不会丢失

- 其他能力

  - 处理多行数据
  - 解析`json`格式数据
  - 简单的过滤功能

- ![](./images/Snipaste_2019-06-24_21-26-07.png)

  > `使用`
  >
  > - 配置`filebeat.yml`
  > - 配置模板`index template`
  > - 配置 `kibana dashboards`
  > - 运行
  >
  > ![](./images/Snipaste_2019-06-24_21-29-06.png)
  >
  > `配置模板`
  >
  > ![](./images/Snipaste_2019-06-24_21-31-30.png)
  >
  > ![](./images/Snipaste_2019-06-24_21-32-55.png)

  `架构一`

  ![](./images/Snipaste_2019-06-24_21-38-58.png)

  `架构二`

  ![](./images/Snipaste_2019-06-24_21-39-54.png)

-  5.x新增了一个节点类型

  - 在数据写入`es` 前(`_bulk\index操作`)对数据进行处理
  - 可设置独立的`ingest node`专门进行数据转换处理`node.ingest:true`
  - `api endpoint`为`pipline`
    - `pipeline`由一系列的`processor`组成,类似`logstash`中的`filter plugin`
    - ![](./images/Snipaste_2019-06-24_21-43-13.png)
    - `pipline`API主要有下面几个
      - 创建`put`
      - 获取`get`
      - 删除`delete`
      - 模拟调试`simulate`
    - ![](./images/Snipaste_2019-06-24_21-46-54.png)
    - ![](./images/Snipaste_2019-06-24_21-47-32.png)

- `PROCESSOR处理`

  - ![](./images/Snipaste_2019-06-24_21-53-42.png)

- 处理`解析错误`的情况

  - ![](./images/Snipaste_2019-06-24_21-54-50.png)

- 

