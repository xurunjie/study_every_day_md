package main

import "fmt"

func main() {
	var x int
	fmt.Println("请输入要校验的数据---")
	err, num := fmt.Scanln(&x)
	fmt.Println(err, num)
	ifElse(x)
	switchControl(x)

}

func ifElse(x int) {
	if x > 0 {
		fmt.Println(" > 0")
	} else if x < 0 {
		fmt.Println(" < 0")
	} else {
		fmt.Println(" = 0 ")
	}
}

func switchControl(x int) {
	switch {
	case x > 0:
		fmt.Println("x -> -> > 0")
	case x < 0:
		fmt.Println("x -> -> < 0")
	default:
		fmt.Println("x -> -> = 0")
	}
}
