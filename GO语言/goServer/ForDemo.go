package main

import "fmt"

func main() {
	for i := 0; i < 5; i++ {
		fmt.Println("fmt println -> i => ", i)
	}

	x := 0;
	for x< 5 {
		fmt.Println("x -> {}",x)
		x++;
		if x==3 {
			break
		}
	}
}
