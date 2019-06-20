# Logstash





### `Pipline`

> - `input-filter-output`的`3`阶段处理流程
> - `队列管理`
> - `插件`生命周期

### `LogStash`

> - 内部流转的数据表现形式
> - 原始数据在`input`被转换为`Event`,在`output,envent`被转换为目标格式数据
> - 在配置文件中可以对`Event`中的属性进行`增删改查`

### `Queue`

- `In Memory`
  - 无法处理`crash`、机器宕机等情况,会导致`数据丢失`
- `Persistent Queue In Disk`
  - 可处理进程`Crash`等情况,保证数据不丢失
  - 保证数据至少消费一次
  - 充当缓冲区,可以替代`Kafka`等消息队列的作用