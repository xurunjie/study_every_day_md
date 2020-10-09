package main

import "fmt"

func main() {
	// var 定义变量,支持自动腿短基础数据类型
	var x int32
	var s = "hello world"
	// 赋值变量符, 是上面自动推导的简写
	a := 12
	b := 13
	fmt.Println(x, s, a, b)
	fmt.Printf("x -> type %T\n", x)
	fmt.Printf("s -> type %T\n", s)
}
