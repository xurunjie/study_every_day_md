# C语言程序设计

## 数据类型

### 概述

#### C是有类型的语言

- 在使用前定义，并且
- 确定类型

#### C以后的语言向两个方向发展

- C++/Java更强调类型，对类型的检查更严格
- JavaScript, Python, PHP不看重类型，甚至不需要事先定义

#### 类型安全

- 支持强类型的观点认为明确的类型有助于竟遭发现程序中的简单错误
- 反对强类型的观点认为过于强调类型迫使程序员面对底层， 实现非事务逻辑
- 总的来说， 早期语言强调类型， 面向底层的语言强调类型
- C语言需要类型， 但是对检查的安全检查并不足够

#### C语言的类型

- 整数
  - char
  - short
  - int
  - long
  - long long`(C99类型)`
- 浮点数
  - float
  - double
  - long double`(C99类型)`
- 逻辑
  - bool`(C99类型)`
- 指针
- 自定义类型

#### 类型有何不同

- 类型名称：int， long， double
- 输入输出时的格式化： %d， %ld， %lf
- 所表达的数的范围： char < short < int < float < double
- 内存中所占据的大小： 1个字节到16个字节
- 内存中的表达形式： 二进制数（补码）， 编码

#### sizeof

- 是一个运算符， 给出某个类型或变量在内存中所占据的字节数

  - sizeof(int)
  - sizeof(i)

- ```c
  #include<stdio.h>
  
  int main(void)
  {
      int a;
      a = 6;
      printf("sizeof(int)=%ld\n", sizeof(double));
      printf("sizeof(a)=%ld\n", sizeof(a++));
      return 0;
  }
  
  /*
  ######################
  sizeof(double) = 16
  sizeof(a) = 4
  # sizof是静态的不会真的去做运算
  */
  
  ```

### 整数类型

#### 简介

- char： 1字节(8bit)
- short:  2字节
- int： 取决于编译器（CPU），通常的意义是"1个字"
- long： 取决于编译器（CPU）， 通常的意义是"1个字"
- long long： 8字节
- 小知识：
  - int就是用来表达寄存器的大小的
  - 32位的寄存器大小32bit(4个字节)
  - 64位的寄存器大小64bit(8个字节)
  - cpu -> 总线 -> RAM(内存)
    - 每次通过总线读取32/64bit，一个寄存器中

#### 整数的内部表达

- 计算器内部一切都是二进制
  - 18 ---->  00010010
  - 0   ---->  00000000
  - -18 ---> 10010010

#### 二进制负数

- 1个字节可以表达的数

  - 0000 0000 ----- 1111 1111(0-255)

- 三种方案

  - 仿照十进制， 有一个特殊的标表示负数

  - 取中间的数为0， 如1000 0000表示0，比它小的是负数， 比它大都是正数
  - 补码

#### 补码

- 考虑-1，我们希望-1+1->0, 如何能做到
  - 0 ----> 0000 0000
  - 1 ----> 0000 0001
  - 1111 1111 + 0000 0001 -> 1 0000 0000
- 如果0-1 -》 -1，所以， -1 = 
  - (1)0000 0000 - 0000 0001 -> 1111 1111
  - 1111 1111被当作纯二进制看待时， 是255， 被当作补码看待时是-1
- 同理， 对于-a， 其补码就是0-a, 实际是2^n -a,  n是这种类型的位数

#### 数的范围

- 对于一个字节（8位）， 可以表达的是：
  - 0000 0000 - 1111 1111
- 其中
  - 0000 0000 -> 0
  - 1111 1111 ~ 1000 0000 -> -1 ~ -128
  - 0000 0001 ~ 0111 1111 -> 1 ~ 127

##### 整数的范围

- char: `1字节`: -128 ~ 127
- shor: `2字节`: -32768 ~ 32767
- int: 取决于编译器`(CPU)`, 通常的意义是`一个字`
- long: 4字节
- long long: 8字节

#### unsigned

- 如果一个字面量常数想要表达自己是unsigned, 可以在后面加`u`或者`U`

  - 255U

- 用l或者L表示long(long)

- *unsigned的初衷并非扩展数能表达的范围, 而是为了做纯二进制运算, 主要是为了移位

- ```c
  #include<stdio.h>
  
  
  int main(void)
  {
      char c = 255;
      unsigned char d = 255;
      int i = 255;
      printf("c=%d, i=%d, d = %d\n",c,i,d);
      return 0;
  }
  
  /*
  c=-1, i=255, d = 255
  */
  
  ```

#### 整数越界

- 整数是以纯二进方式进行计算的, 所以:
  - 1111 1111 + 1  -> 1 0000 0000 -> 0
  - 0111 1111 + 1 -> 1000 0000 -> -128
  - 1000 0000 - 1 -> 0111 1111 -> 127

![image-20201205152603858](.\image\image-20201205152603858.png)

![unsigned](E:\study\image\image-20201205153555586.png)

- ```c
  
  
  int main(void)
  {
      char c = 127;
      unsigned char d = 127;
      c = c + 1;
      d = d + 1;
      printf("c = %d d = %d \n", c, d);
      return 0;
  }
  
  /*
  c = -128 d = 128
  ###
  这里要注意正如上图
  unsgined 不在作为负数越界始终为正数, 越界是8个bit 0~255
  ###
  */
  
  ```

#### 整数的输入输出

- 只有两种形式: **int** 或 **long long**
- %d: **int**
- %u: **unsigned**
- %ld: **long long**
- %lu: **unsigned long long**

#### 8进制和16进制

- 一个以**0开始**的数字字面量是**8进制**

- 一个以**0x开始**的数字字面量是16进制

- 输出

  - %o -> 8进制
  - %x -> 16进制

- 8进制和16进制只是如何把数字表达成字符串, 与内部如何表达数字无关

  - 16进制

    - 非常适合表达二进制数据,因为`4位`二进制正好表达是一个16进制位

    - ```c
      //  二进制 0001 0020
      转化
      // 16进制     1   2
          
      /*
      	16进制的2位正好表达一个字节 16^2 = 256 正好表达一个字节
      */    
      ```

  - 8进制
    - 一个数字正好表达`3位`二进制
    - 因为早期的计算机的字长是12的倍数,而非8

- 需要注意

  - 计算机内部永远只是二进制
  - **8进制和16进制,编译器编译的时候给转的**

  ```c
  #include<stdio.h>
  
  
  int main(void)
  {
      char c = 012;
      int i = 0x12;
      printf("c = %d i = %d\n", c, i);
      return 0;
  
  }
  
  /*
  c = 10 i = 18
  */
  ```

#### 选择整数类型

- 为什么整数要有那么多种
  - 为了准确表达内存, 做底层程序的需要
- 没有特殊需要, 就选择int
  - 现在的CPU的字长普遍是32位或者64位, 一次内存读写就是一个int, 一次计算也是一个int, 选择更短的类型是不会更快, 甚至可能更慢
  - **现代的编译器一般是设计内存对齐, 所以更短的类型实际在内存中有可能也占据一个`int`的大小(虽然不sizeof告诉你更小)**
- unsigned与否只是输出的不同, 内部计算是一样的

### 浮点类型

|  类型  | 字长 |                    范围                    | 有效数字 | scanf | printf |
| :----: | :--: | :----------------------------------------: | :------: | :---: | :----: |
| float  |  32  | +-(1.20x10^-38 ~ 3.40x10^38), 0, +-inf,nan |    7     |  %f   | %f, %e |
| double |  64  | +-(2.2x10^-308 ~ 1.79x10^308),0,+-inf,nan  |    15    |  %lf  | %f,%e  |

#### 输出精度

- 在%和f之间加上**.n**可以指定输出小数点后几位, 这样的输出是4舍五入的

- ```c
  #include<stdio.h>
  
  int main(void)
  {
      printf("%.3f\n", -0.0049);
      printf("%.30f\n", -0.0049);
      printf("%.3f\n",-0.00049);
      return 0;
  }
  /*
  -0.005
  -0.004899999999999999841793218991
  -0.005
  # 四舍五入
  */
  ```

#### 超过范围的浮点数

- **printf**输出**inf**表示超过范围的浮点数: +∞

- **printf**输出**nan**表示不存在的浮点数

- ```c
  #include<stdio.h>
  
  
  int main(void)
  {
      printf("%f\n", 12.0/0.0);
      printf("%f\n", -12.0/0.0);
      printf("%f\n",0.0/0.0);
      return 0;
  }
  
  /*
  inf
  -inf
  -nan
  
  ##
  inf 无穷大
  +-	正负
  nan	不存在的数
  ##
  */
  ```

#### 浮点运算的精度

- 带小数点的字面量是**double**而非**float**
- **float**需要用**f**或**F**后缀来表明身份

- ```c
  #include<stdio.h>
  
  
  int main(void)
  {
      float a,b,c;
      a = 1.345f;
      b = 1.123f;
      c = a + b;
      if ( c == 2.468)
          printf("相等\n");
      else
          printf("不相等\n");
      return 0;
  }
  
  /*
  不相等
  ###
  1.345f 字面量是float  7位
  2.468 字面量是double 15位
  精度不同
  ###
  */
  ```

#### 浮点数的内部表达

![image-20201205173408175](.\image\image-20201205173408175.png)

- 浮点数在计算时是由专用的硬件部件实现的
- 计算**double**和**float**所用的部件是一样的

#### 选择浮点类型

- 如果没有特殊需要,只使用**double**
- 现代**CPU**能直接对**double**做硬件运算,性能不会比**float**差,在**64**位的机器上,数据存储的速度也不比**float**慢

### 逻辑类型

#### bool

- **#include<stdbool.h>**

- 之后就可以用bool和true、false

- ```c
  #include<stdio.h>
  #include<stdbool.h>
  
  
  int main(void)
  {
      bool b = 6 > 5;
      bool t = true;
      t = 2;
      printf("d = %d t = %d\n", b, t);
      return 0;
  }
  
  
  /*
  d = 1 t =1
  ###
  bool 实际上是1或者0 true -> 1 false -> 0
  ###
  */
  ```

#### 逻辑运算

- 逻辑运算是对逻辑量进行的运算, 结果只有0或1
- 逻辑量是关系运算或逻辑运算的结果

| 运算符 |  描述  |  示例  |                             结果                             |
| :----: | :----: | :----: | :----------------------------------------------------------: |
|   !    | 逻辑非 |   !a   | 如果**a是true**结果就是**false**<br />如果**a是false**结果就是**true** |
|   &&   | 逻辑与 | a && b |            true && true 结果是true<br />否则false            |
|  \|\|  | 逻辑或 | a\|\|b | 如果a和b有一个true,结果为true<br />两个都是false,结果为false |

#### TRY

- 如果要表达数学中的区间, 如: **x∈(4,6)或x∈[4,6]**,应该如何写c的表达式
  - 错误
    - 示例
      - 4 < x <6
    - 错误原因
      - 不是C能正确计算的式子
      - 因为 4 < x 的结果是一个逻辑值(0或1)
  - 正确
    - 示例
      - x > 4 && x < 6
- 如何判断一个字符c是否是大写字母?
  - c >= 'A' && c <= 'Z'
    - c ∈ ['A', 'Z']
- 理解
  - age > 20 && age < 30
    - age ∈ (20, 30)
  - index < 0 || index > 99
    - index ∈ (-∞, 0) || index ∈ (99, +∞)
  - !age < 20
    - 1 < 20 -> true

#### 优先级的陷阱

![image-20201205215620125](.\image\image-20201205215620125.png)

#### 短路

- 逻辑运算是自左向右进行的, 如果左边的结果,已经能决定结果了,就不会做右边的计算
  - a == 6 && b == 1
  - a == 6 && b += 1
- 对于&&, 左边是false时就不做左边了
- 对于||,左边是true时就不做右边了
- 解决核心
  - 不要把赋值组合进表达式!

### 类型转换和条件运算

#### 条件运算符

- count = (count > 20)?count - 10:count+10;

- 条件、条件满足时的值和条件不满足时的值

- 相当于如下

  - ```c
    if (count > 20)
        	count = count - 10;
    else
        	count = count + 10;
    ```

#### 优先级

- 条件运算符的优先级高于赋值运算符,但是低于其他运算符
  - m < n? x : a + 5
  - a ++ >= 1 &&  b-- > 2 ? a : b
  - x = 3 * a > 5? 5 : 20

#### 嵌套条件表达式

- count = (count > 20) ? (count < 50)? count - 10: count -5? count + 10 : count + 5;
- 条件运算符是自右向左结合的
  - w < x ? x + w : x < y ? x : y
- 避免复杂方式
  - 不要使用嵌套的条件表达式

#### 逗号运算

- 逗号用来连接两个表达式, 并以其右边的表达式的值作为他的结果

  - 逗号的优先级是所有的运算符中最低的,
  -  所以它两边的表达式会先计算;  
  - 逗号的组合关系是自左向右, 
  - 所以左边的表达式会先计算,
  -  而右边的表达式的值就留下来作为逗号运算的结果

- ```c
  #include<stdio.h>
  
  
  int main(void)
  {
      int i;
      i = 3 + 4, 5 + 6;
      printf("%d\n", i);
      i = (3 + 4, 5 + 6);
      printf("%d\n", i);
      return 0;
  }
  
  /*
  7
  11
  ###
  第一个 i = 3 + 4 = 7	
  第二个 i = 5 + 6 = 11 (右括号括号先算)
  ","号的优先级是最低的
  ###
  */
  
  ```

## 指针

### 指针类型

#### 运用场景一

- 交换两个变量的值

- ```c
  #include<stdio.h>
  
  void swap(int *pa, int *pb);
  
  int main(void)
  {
      int a = 5;
      int b = 6;
      swap(&a, &b);
      printf("a = %d b = %d \n", a, b);
      return 0;
  }
  
  void swap(int *pa, int *pb)
  {
      int t = *pa;
      *pa = *pb;
      *pb = t;
  }
  
  /*
  a = 6 b = 5
  */
  ```

#### 应用场景二

- 函数返回多个值, 某些值就只能通过指针返回

  - 传入的参数实际上是需要保存待会的结果的变量

- ```c
  
  #include<stdio.h>
  
  void minmax(int a[], int len, int *max, int *min);
  
  int main(void)
  {
      int a[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18};
      int min,max;
      minmax(a, sizeof(a)/sizeof(a[0]), &min, &max);
      printf("min = %d, max = %d \n", min, max);
      return 0;
  }
  
  void minmax(int a[], int len, int *min, int *max)
  {
      int i;
      *min = *max = a[0];
      for (i=1;i<len;i++){
          if(a[i] < *min){
              *min = a[i];
          }
          if(a[i] > *max){
              *max = a[i];
          }
      }
  }
  
  /*
  "min = 1, max = 18 
  */
  ```

#### 应用场景二B

- 函数返回运算的状态, 结果通过指正返回

- 常用的套路是让函数返回特殊不属于有效范围内的值来表示出错;

  - **-1 或 0(在文件操作会看到大量的例子)**

- 但是当任何数值都是有效的可能结果时, 就得分开返回了

- 
  
  ```c
  #include<stdio.h>
  
  int devide(int a, int b, int *result);
  
  int main(void)
  {
      int a, b, c;
      a = 5;
      // b = 0;
      b = 3;
      if (devide(a, b, &c)){
          printf("%d/%d=%d\n", a, b, c);
      }
      return 0;
  }
  
  int devide(int a, int b, int *result)
  {
      int ret = 1;
      if( !b ) ret = 0;
      else *result = a/b;
      return ret;
  }
  
  
  /*
  5/3=1
  ###
  ret 返回状态 1 代表成功 0 代表失败
  ###
*/
  ```
  
  - 后续的语言(c++,java)采用了一场极致来解决这问题
  

#### 指针常见的错误

- 定义了**指针变量**, **还没有指向任何变量,就开始使用指针**

#### 传入函数的数组成了什么

- 函数参数表里的数组其实就是指针
  - 必须是空的方括号
  - 没法用**sizeof**获取, 因为他是指针

- ```c
  #include<stdio.h>
  
  void minmax(int a[], int len, int *max, int *min);
  
  int main(void)
  {
      int a[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18};
      int min,max;
      printf("main sizeof(a)=%lu\n", sizeof(a));
      minmax(a, sizeof(a)/sizeof(a[0]), &min, &max);
      printf("min = %d, max = %d \n", min, max);
      return 0;
  }
  
  void minmax(int a[], int len, int *min, int *max)
  {
      int i;
      *min = *max = a[0];
      printf("minmax sizeof(a)=%lu\n", sizeof(a));
      for (i=1;i<len;i++){
          if(a[i] < *min){
              *min = a[i];
          }
          if(a[i] > *max){
              *max = a[i];
          }
      }
  }
  /*
  main sizeof(a)=72
  minmax sizeof(a)=8
  min = 1, max = 18 
  
  ###
  我们现在使用64位架构去编译的
  main 中的sizeof(a)
  minmax 中sizeof(a) 对于函数中的a[]其实是int *
  
  main中a[]和minmax(int a[],是一样子的, 地址也是一样的
  ###
  */
  
  ```

##### 数组参数

- 以下四种函数原型是等价的:
  - int sum(int *ar, int n);
  - int sum(int *, int);
  - int sum(int ar[], int n);
  - int sum(int [], int);

##### 数组变量是特殊的指针

- 数组变量本身表达地址,所以
  - int a[10]; int *p = a; //无需用&取地址
  - 但是数组的单元表达的是变量, 需要用&取地址
  - a == &a[0]
- []运算符可以对数组做, 也可以对指针做:
  - p[0] <==> a[0]
- 数组变量是const的指针,所以不能被赋值
  - int a[] <==> int * const a = ...

#### 指针与const

- 指针 ---> 可以是const
- 值     ---> 也可以是const

#### 指针是const

- 表示一旦得到某个变量的地址,不能再指向其他变量
  - int * const q  = &i;
  - *q = 26                  **[OK]**,
  - q++;                       **[ERROR]**

#### 所指的是const

- 表示不能通过这个指针去修改那个变量(并不能使得那个变量变成const)
  - const int *p = &i;
  - *p = 26;                **[ERROR]**(\*p)是const
  - i= 26;                     **[OK]**
  - P = &j:                    **[OK]**

##### 注意事项

- int i;
- const int * p1 = &i;
- int const* p2 = &i;
- int *const p3 = &i;
- 判断那个被const了的标志是const在*的前面还是后面
  - const *s
    - 常量指针 *s 的值不能发生改变,无法赋值
  - *const s
    - 常量指针 s 初始化后无法指向其他地址

#### 非const转换const

- 总是可以吧一个非const的值转换成const的

- ```c
  void f(const int* x);
  int a = 15;
  f(&a);
  const int b = a;
  f(&b);
  b = a + 1//ERRIR
  ```

- 当要传递的参数的类型比地址大的时候, 这是常用的手段;即能用比较少的字节数传递值给参数,又能避免函数对外面的变量的修改

##### const 数组

- const int a[] = {1,2,3,4,5,6}
- 数组变量已经是const的指针了, 这里的const表明数组的每个单元都是const int
- 所以必须通过初始化进行赋值

##### 保护数组值

- 因为把数组传入函数时传递的是地址, 所以那个函数内部可以修改数组的值
- 为了保护数组不被函数破坏,可以设置参数为const
  - int sum(const int a[],int length)

### 指针的计算

- ```c
  #include<stdio.h>
  
  
  int main(void)
  {
      char ac[] = {0,1,2,3,4};
      char *p = ac;
      printf("p     = %p\n",p);
      printf("p + 1 = %p\n", p + 1);
      int ai[] = {0,1,2,3,4};
      int *q = ai;
      printf("q     = %p\n", q);
      printf("q + 1 = %p\n", q + 1);
      return 0;
  }
  
  /*
  p     = 0x7ffd12d9fad0
  p + 1 = 0x7ffd12d9fad1
  q     = 0x7ffd12d9fab0
  q + 1 = 0x7ffd12d9fab4
  ###
  +1 其实是加了一个sizeof(所属类型)
  *p -> ac[0]
  *(p+1) -> ac[1]
  *(p+n) <-> ac[n]
  因为*号的优先级大于+号
  ###
  */
  ```

- 这些算数运算可以对指针做

  - 给指针加、减一个整数(+,+=,-,-=)

  - 递增递减(++/--)

  - 两个指正可以相减

    - ```c
      #include<stdio.h>
      
      
      int main(void)
      {
          char ac[] = {0,29,34,99,4};
          char *p = ac;
          char *p1 = &ac[3];
          printf("p     = %p\n",p);
          printf("p + 1 = %p\n", p + 1);
          printf("p1 -a = %d\n",p1-p);
          return 0;
      }
      
      /*
      p     = 0x7ffd8b5e7770
      p1    = 0x7ffd8b5e7780
      p1 -a = 4
      ###
      p - p1 = 相差16位 / sizeof(int) = 4
      相差四个int类型
      ###
      */
      ```

#### *p++

- 取出p所指的那个数据来,完事之后顺便把p移到下一个位置去

- ***的优先级虽然高,但是没有++高**

- 常用于数组累的连续空间操作

- 在某些CPU上,这可以直接被翻译成一条汇编指令

- ```c
  #include<stdio.h>
  
  
  int main(void)
  {
      int ac[] = {0,29,34,99,4,-1};
      const int * p = ac;
      while(*p != -1){
          printf("%d\n",*p++);
      }
      return 0;
  }
  ```

#### 指针比较

- <,<=,==,>=,!=都可以对指针做
- 比较他们在内存中的地址
- 数组中的单元的地址肯定是线性递增的

#### 0地址

- 当然你的内存中有**0地址**,但是0地址通常是个不能随便碰的地址
- 所以你的指正不应该具有0值
- 因此可以用**0地址**来表示特殊的事情
  - 返回的指正是无效的
  - 指正没有被真正初始化(先初始化为0)
- Null是一个预先定义的符号,表示**0地址**
  - 有的编译器不愿意你用0来表示0地址

#### 指针的类型

- 无论指向什么类型,所有的指针的大小都是一样的,因为都是地址
- 但是指向不同类型的指针是不能直接互相赋值的
- 这是为了避免用错指针

#### 指针的类型转换

- void* 表示不知道指向什么东西的指针
  - 计算时与char*相同(但不相通)
- 指针也可以转换类型
  - int *p = &i; void \*p = (void\*)p
- 这并没有改变p所指的变量的类型, 而是让后人用不同的眼光通过p看它所指的变量
  - 我不再当你是int啦, 我认为你就是个void!

#### 用指针来做什么

- 需要传入较大的数据时用作参数
- 传入数组后对数组做操作
- 函数返回不止一个结果
  - 需要用函数来修改不止一个变量
- 动态申请的内存...

### 动态内存分配

#### 输入数据

- 如果输入数据时, 先告诉你个数, 然后再输入, 要记录每个数据

- C99可以用变量做数据定义的大小,C99之前尼?

- int *a = (int \*)malloc(n\*sizeof(int))

- ```c
  #include<stdio.h>
  #include<stdlib.h>
  
  int main(void)
  {
      int number;
      int *a;
      int i;
      printf("输入数量 ~~\n");
      scanf("%d",&number);
      // int a[number]  c99可以使用
      a =(int *)malloc(number * sizeof(int));
      for(i = 0; i<number;i++){
          printf("---------------i= %d number = %d\n",i,number);
          scanf("%d",&a[i]);
      }
      printf("==========print==========\n");
      for(i = number - 1;i>=0;i--){
          printf("%d\n",a[i]);
      }
      free(a);
      return 0;
  }
  /*
  输入数量 ~~
  3
  ---------------i= 0 number = 3
  2
  ---------------i= 1 number = 3
  1
  ---------------i= 2 number = 3
  3
  ==========print==========
  3
  1
  2
  ###
  malloc: 动态申请内存返回一个void*指针
  free: 释放内存
  ###
  */
  ```

#### malloc申请空间

- #include<stdlib.h>
- void* malloc(size_t size)
  - 向malloc申请的空间大小是以字节为单位的
  - 返回的结果是void*, 需要类型转换为自己需要的类型
    - **(int *)malloc(n\*sizeof(int))**

#### 没空间了

- 如果申请失败则返回而活着叫做**NULL**
- 你的系统能给你多大的空间?

**free()**

- 把申请得来的空间还给"系统"

- 申请过的空间, 最终都应该要还

  - 混出来的,迟早都是要还的

- free(NULL);

  - 好习惯
    - 每次指针变量定义的时候初始化位NULL或0

- **只能还申请来的空间的首地址**

- ```c
  #include<stdio.h>
  #include<stdlib.h>
  
  int main(void)
  {
      void *p;
      int cnt = 0;
      p = malloc(100*1024*1024);
      p++;
      free(p);
      return 0;
  }
  /*
  *** Error in `./a.out': free(): invalid pointer: 0x00007fa909c3c011 ***
  ======= Backtrace: =========
  /lib64/libc.so.6(+0x7cfe1)[0x7fa9100b9fe1]
  ./a.out[0x4005ee]
  /lib64/libc.so.6(__libc_start_main+0xf5)[0x7fa91005eb15]
  ./a.out[0x4004f9]
  ======= Memory map: ========
  00400000-00401000 r-xp 00000000 fd:00 1578936                            /root/en_h_c_study/a.out
  00600000-00601000 r--p 00000000 fd:00 1578936                            /root/en_h_c_study/a.out
  00601000-00602000 rw-p 00001000 fd:00 1578936                            /root/en_h_c_study/a.out
  7fa904000000-7fa904021000 rw-p 00000000 00:00 0 
  7fa904021000-7fa908000000 ---p 00000000 00:00 0 
  7fa909a26000-7fa909a3b000 r-xp 00000000 fd:00 33595529                   /usr/lib64/libgcc_s-4.8.5-20150702.so.1
  7fa909a3b000-7fa909c3a000 ---p 00015000 fd:00 33595529                   /usr/lib64/libgcc_s-4.8.5-20150702.so.1
  7fa909c3a000-7fa909c3b000 r--p 00014000 fd:00 33595529                   /usr/lib64/libgcc_s-4.8.5-20150702.so.1
  7fa909c3b000-7fa909c3c000 rw-p 00015000 fd:00 33595529                   /usr/lib64/libgcc_s-4.8.5-20150702.so.1
  7fa909c3c000-7fa91003d000 rw-p 00000000 00:00 0 
  7fa91003d000-7fa9101f3000 r-xp 00000000 fd:00 34042446                   /usr/lib64/libc-2.17.so
  7fa9101f3000-7fa9103f3000 ---p 001b6000 fd:00 34042446                   /usr/lib64/libc-2.17.so
  7fa9103f3000-7fa9103f7000 r--p 001b6000 fd:00 34042446                   /usr/lib64/libc-2.17.so
  7fa9103f7000-7fa9103f9000 rw-p 001ba000 fd:00 34042446                   /usr/lib64/libc-2.17.so
  7fa9103f9000-7fa9103fe000 rw-p 00000000 00:00 0 
  7fa9103fe000-7fa91041f000 r-xp 00000000 fd:00 34325384                   /usr/lib64/ld-2.17.so
  7fa910616000-7fa910619000 rw-p 00000000 00:00 0 
  7fa91061d000-7fa91061f000 rw-p 00000000 00:00 0 
  7fa91061f000-7fa910620000 r--p 00021000 fd:00 34325384                   /usr/lib64/ld-2.17.so
  7fa910620000-7fa910621000 rw-p 00022000 fd:00 34325384                   /usr/lib64/ld-2.17.so
  7fa910621000-7fa910622000 rw-p 00000000 00:00 0 
  7ffe8d9d1000-7ffe8d9f2000 rw-p 00000000 00:00 0                          [stack]
  7ffe8d9fd000-7ffe8d9ff000 r-xp 00000000 00:00 0                          [vdso]
  ffffffffff600000-ffffffffff601000 r-xp 00000000 00:00 0                  [vsyscall]
  Aborted
  ###
  ###
  */
  ```

#### 常见问题

- 申请了没free ----> 长时间运行内存逐渐下降
  - 新手: 忘了
  - 老手: 找不到合适的free的时机
- free过了再free
- 地址变过了,直接去free

## 字符串

### putchar && getchar()

- int putchar(int c);

- 向标准输出写一个字符

- 返回写了几个字符,EOF(-1)表示写失败

  - Windows ---> Ctrl -Z
  - Unix         ---> Ctrl -D

- ```c
  #include<stdio.h>
  #include<stdlib.h>
  
  int main(int argc, char const *argv[])
  {
      char ch;
      while((ch = getchar())!=EOF){
          putchar(ch);
      }
      printf("EOF\n");
      return 0;
  }
  
  /*
  2
  2
  3
  3
  4
  4
  EOF
  */
  ```

### 字符串数组

- 误解区
  - char **a
    - a是一个指针, 指向另一个指正,那个指正指向一个字符(串)
  - char a\[][10]
    - a[0] -->char [10]  **a[0]代表10位**
- char *a[]

### 程序参数

- int main(int argc, char const * argv[])
- argv[0]是命令本身
  - 当使用unix的符号链接是, 反应符号链接的名字

### string.h

#### strlen

- **返回字符串的长度**

- size_t strlen(const char *s)

  - ```C
    #include<stdio.h>
    #include<string.h>
    int main(int argc, char const *argv[])
    {
        char line[] = "hello";
        printf("strlen = %lu\n",strlen(line));
        printf("sizeof = %lu\n", sizeof(line));
        return 0;
    }
    
    /*
    strlen = 5
    sizeof = 6
    */
    ```

- 自己实现strlen

  - ```C
    #include<stdio.h>
    #include<string.h>
    
    int mylen(const char *s){
        int count = 0;
        while( *s++ != '\0')count++;
        return count;
    }
    
    int main(int argc, char const *argv[])
    {
        char line[] = "hello";
        printf("strlen = %lu\n",mylen(line));
        return 0;
    }
    
    ```

  ​	

#### strcmp

- 比较两个字符串

- int strcmp(const char *s1, const char *s2)

  - 0:s1==s2
  - 1:s1>s2
  - -1:s1<s2

- 自己是先mycmp

  - ```c
    #include<stdio.h>
    #define _END_SYMBOL '\0'
    
    int mycmp(const char * s1, const char * s2){
        while( *s1 == *s2 && *s1 != _END_SYMBOL){s1++;s2++;};
        return *s1 - *s2;
    }
    
    int main(int argc, char const *argv[])
    {
        char s1[] = "abc";
        char s2[] = "Abc";
        printf("%d", mycmp(s1,s2));
    
        return 0;
    }
    ```

#### strcpy

- 把src的字符串拷贝到dst
  
  - restrict表明src和dst不重叠**(c99)**
- 返回dst
  
- 为了能链起代码来
  
- char * strcpy(char *restrict dst,const char * restrict)

- mycopy实现

  - ```c
    #include<stdio.h>
    #include<string.h>
    #include<stdlib.h>
    #define _END_SYMBOL '\0'
    
    
    void* mycopy(char * dst, const char * src){
        char* ret = dst;
        while(*dst++=*src++);
        *dst = '\0';
        return dst;
    
    }
    
    
    int main(int argc, char const *argv[])
    {   
        char s1[] = "abc";
        char s2[] = "cba";
    	mycopy(s1,s2);
        printf("%s",s1)
        return 0;
    }
    
    /*
    cba
    */
    ```

#### strcat

#### strchr

- 字符串中找字符

- ```C
  #include<stdio.h>
  #include<string.h>
  #include<stdlib.h>
  #define _END_SYMBOL '\0'
  
  
  
  
  int main(int argc, char const *argv[])
  {
      char s1[] = "hello";
      char *p = strchr(s1,'l');
      char c = *p;
      *p = '\0';
      char *t = (char *)malloc(strlen(s1)+1);
      strcpy(t,s1);
      printf("%s\n",t);
      free(t);
      return 0;
  }
  
  /*
  he
  ###
  寻找下一个可以使用(p+1，'l')
  ###
  */
  ```

#### strrchr

#### strstr

- 字符串中找字符串

#### strcasestr

- 字符串中找字符串,忽略大小写

## 枚举

### 简介

- 虽然枚举类型可以当做类型使用,但是实际上很少用
- 如果有意义排比的名字,用枚举比const int方便
- 枚举比宏(macro)号,因为枚举有int类型

### enum

- 枚举是一种用户定义的数据类型, 它用关键字enum以如下语法来声明
  - enum枚举类型名字{名字0,...,名字n};
- 枚举类型名字通常并不真的使用,要用的是大括号里的名字,因为他们就是常量符号,他们的类型是int,值则依次从0到n.如:
  - enum colors{red,yellow,green};
  - 就创建了三个常量,red的值是0,yellow是1,而green是2
- 当需要一些可以排列起来的常量时,定义枚举的意义就是给了这些常量值名字
- 枚举量可以作为值
- 枚举类型可以跟上enum作为类型
- 但是实际上是以整数来做内部计算和外部输出的
- 即使给枚举类型的变量赋值不存在的整数值也没有任何warning或error

```c
#include<stdio.h>

enum COLOR {RED,YELLOW=5,GREEN,NumColor};


int main(int argc, char const *argv[])
{
    printf("RED = %d", RED);
    printf("YELLOW = %d", YELLOW);
    printf("GREEN = %d", GREEN);
    return 0;
}

/*
0
5
6
*/
```



## 结构

### 声明结构类型

- 和本地变量一样,在函数内部申明的结构类型只能在函数内部使用
- 所以通常在函数外部声明结构类型,这样就可以被多个函数所使用了

- ```c
  #include<stdio.h>
  
  struct date {
      int month;
      int day;
      int year;
  };
  
  int main(int argc, char const *argv[])
  {
      struct date today;
      today.month = 02;
      today.day = 29;
      today.year = 2020;
      printf("Today is date is %i-%i-%i.\n",today.year,today.month,today.day);
      return 0;
  
  }
  /*
  Today is date is 2020-2-29.
  */
  ```

### 声明结构的形式

```c
// 第一种
struct point {
    int x;
    int y;
};
struct point p1,p2;
/*
	p1和p2都是point里面有x和y的值
*/

// 第二种
struct {
    int x;
    int y;
} p1, p2;

/*
p1和p2都是一种匿名结构体,里面有x和y
*/

// 第三种
struct point {
    int x;
    int y;
} p1,p2;
/*
p1和p2都是point里面有x和y的值t
*/

// 对于第一种和第三种形式,都声明了结构point, 但是第二种形式没有说明point,只是定义了两个变量
```

### 结构作用域

- 和本地变量一样,在函数内部声明的结构类型只能在函数内部使用
- 所以通常在函数外部声明结构类型,这样就可以被多个函数所使用了

### 结构的初始化

```c
#include<stdio.h>

struct date {
    int month;
    int day;
    int year;
};

int main(int argc, char const *argv[])
{
    struct date today = {12,07,2014};
    struct date month = {.month=7, .year=2020};
    printf("Today is date is %i-%i-%i.\n",today.year,today.month,today.day);
    printf("Today is date is %i-%i-%i.\n",month.year,month.month,month.day);
    return 0;
}

/*
Today is date is 2014-12-7.
Today is date is 2020-7-0.
##
day未给补0
##
*/
```

### 结构成员

- 结构和数组有点像
- 数组用[]运算符和小标访问其成员
  - a[0] = 10;
- 结构用.运算符和名字访问其成员
  - today.day
  - student.firstName
  - p1.x
  - p1.y

### 结构运算

- 要访问整个结构,直接用结构变量的名字

- 对于整个结构, 可以做赋值、取地址、也可以传递给函数参数

  - ```c
    #include<stdio.h>
    struct point {
        int x;
        int y;
    };
    
    int main(){
      	struct point p1;
        p1 = (struct point){5,10};
        p1.y = 20;
        struct point p2;
        p2 = p1;
        printf("x = %d, y = %d",p1.x,p1.y);
        printf("x = %d, y = %d",p2.x,p2.y);
        return 0;
    }
    
    /*
    X = 5, y = 20
    x = 5, y = 20
    */
    ```

  -  **p1 = (struct point){5,10};**相当于如下所示

    - p1.x = 5
    - p1.y = 10

  -  p1 = p2;

### 结构指针

- 和数组不同,结构变量的名字并不是结构变量的地址, 必须使用&运算符

- struct date *pDate = &today;

- ```c
  #include<stdio.h>
  
  struct point {
      int x;
      int y;
  };
  
  int main(){
      struct point p1;
      p1 = (struct point){5,10};
      struct point *p2 = &p1;
      printf("x = %d, y = %d",p2->x,p2->y);
      return 0;
  }
  /*
  x = 5, y = 10
  */
  ```

### 结构作为函数参数

- **int numberOfDays(struct date d)**
- 整个结构可以作为参数的值传入函数
- 这时候是在函数内新建一个结构变量,并复制调用者的结构的值
- 也可以返回一个结构
- 这与数组完全不同

### 输入结构

- 没有直接的方式可以一次**scanf**一个结构
- 如果我们打算一个函数来读入结构
  - ->
- 但是读入的结构如何送回来尼
- 记住c在函数调用时是传值的
  - 所以函数中的p与main中的y是不同的

#### 解决的方案

- 之前的方案,把一个结构传入了函数, 然后再函数中操作, 但是没有返回回去

  - 问题在于传入函数的是外面那个结构的克隆体,而不是指针
    - 传入结构和传入数组是不同的

- 在这个输入函数中,完全可以创建一个临时的结构变量,然后把这个结构返回给调用者

- ```c
  #include<stdio.h>
  
  struct point {
      int x;
      int y;
  };
  
  
  struct point getStruct(void);
  
  int main(){
      struct point y;
      y = getStruct();
      printf("x = %d, y= %d\n",y.x,y.y);
      return 0;
  }
  
  struct point getStruct(void){
      struct point p;
      scanf("%d", &p.x);
      scanf("%d", &p.y);
      return p;
  }
  
  /*
  2
  3
  x = 2, y = 3
  */
  ```

#### 结构指针作为参数

- **K & R 说过(p.131)**

  - `"If a large structure is to be passed to a function, it is generally more efficient to pass a pointer than to copy the whole structure" `

- 用 -> 表示指针所指的结构变量中的成员

- ```c
  
  #include<stdio.h>
  
  struct point {
      int x;
      int y;
  } myday;
  
  
  struct point* getStruct(struct point *p){
      scanf("%d",&p->x);
      scanf("%d",&p->y);
      printf("%d,%d\n",p->x,p->y);
      return p;
  }
  
  int main(){
      struct point y = {0,0};
      struct point *p = getStruct(&y);
      printf("x = %d, y = %d",p->x,p->y);
      return 0;
  
  }
  
  /*
  2
  3
  2,3
  x = 2, y = 3
  ###
  *getStruct(&y)=(struct point){1,2};
  这样的赋值也是可以的
  ###
  */
  ```



