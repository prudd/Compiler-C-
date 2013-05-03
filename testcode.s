.data
.comm	a,4,4

.text
	.align 4
.globl  addThem
addThem:
	movl	%EDI, %EAX
	movl	%ESI, %EDI
addThem_bb1:
	addl	%EDI, %EAX
addThem_bb2:
	ret
.globl  putDigit
putDigit:
putDigit_bb3:
	movl	$48, %EAX
	addl	%EDI, %EAX
	movl	%EAX, %EDI
	call	putchar
putDigit_bb2:
	ret
.globl  printInt
printInt:
	pushq	%R14
	pushq	%R15
	movl	%EDI, %R15D
printInt_bb1:
	movl	$0, %EAX
	movl	%EAX, %EDI
	movl	$10000, %EAX
	cmpl	%EAX, %R15D
	jge	printInt_bb4
printInt_bb6:
	movl	$45, %EAX
	movl	%EAX, %EDI
	call	putchar
printInt_bb7:
	movl	$1, %EAX
	movl	%EAX, %EDI
	call	putDigit
printInt_bb2:
	popq	%R15
	popq	%R14
	ret
printInt_bb13:
	movl	$1, %EAX
	cmpl	%EAX, %EDI
	je	printInt_bb21
	jmp	printInt_bb14
printInt_bb21:
	movl	$1, %EAX
	cmpl	%EAX, %EDI
	je	printInt_bb4
	jmp	printInt_bb28
printInt_bb4:
	movl	$1000, %EAX
	cmpl	%EAX, %R15D
	jge	printInt_bb10
	jmp	printInt_bb2
printInt_bb10:
	movl	$100, %EAX
	cmpl	%EAX, %R15D
	jge	printInt_bb13
printInt_bb12:
	movl	$100, %EDI
	movl	$0, %EDX
	movl	%R15D, %EAX
	idivl	%EDI, %EAX
	movl	%EAX, %R14D
printInt_bb15:
	movl	%R14D, %EDI
	call	putDigit
	movl	$100, %EDI
	movl	%R14D, %EAX
	imull	%EDI, %EAX
	movl	%R15D, %EDI
	subl	%EAX, %EDI
	movl	%EDI, %R15D
	movl	$1, %EAX
	movl	%EAX, %EDI
printInt_bb14:
	movl	$10, %EAX
	cmpl	%EAX, %R15D
	jge	printInt_bb21
printInt_bb20:
	movl	$10, %EDI
	movl	$0, %EDX
	movl	%R15D, %EAX
	idivl	%EDI, %EAX
	movl	%EAX, %R14D
printInt_bb23:
	movl	%R14D, %EDI
	call	putDigit
	movl	$10, %EDI
	movl	%R14D, %EAX
	imull	%EDI, %EAX
	movl	%R15D, %EDI
	subl	%EAX, %EDI
	movl	%EDI, %R15D
printInt_bb28:
	movl	%R15D, %EDI
	call	putDigit
.globl  main
main:
	pushq	%R12
	pushq	%R13
	pushq	%R14
	pushq	%R15
main_bb1:
	movl	$5, %EAX
	movl	%ESI, %EDI
	movl	$5, %EAX
	cmpl	%EAX, %EDI
	je	main_bb4
main_bb3:
	movl	$3, %EAX
	movl	%EAX, %ESI
main_bb5:
	movl	$0, %EAX
	movl	%EAX, %R14D
	movl	$1, %EAX
	movl	%EAX, %R15D
	movl	$8, %EAX
	cmpl	%EAX, %R15D
	jle	main_bb7
main_bb6:
	movl	%R14D, %EAX
	addl	%R15D, %EAX
	movl	%EAX, %R14D
	movl	$1, %EAX
	movl	%R15D, %EDX
	addl	%EAX, %EDX
	movl	%EDX, %R15D
	cmpl	$1, %ECX
	je	main_bb6
main_bb7:
	movl	$3, %ECX
	movl	$0, %EDX
	movl	%R14D, %EAX
	idivl	%ECX, %EAX
	movl	%EAX, %ECX
	movl	$4, %EDX
	movl	%ECX, %EAX
	imull	%EDX, %EAX
	movl	%EAX, %R14D
main_bb8:
	movl	%ESI, %EDI
	call	addThem
	movl	%retReg, %EAX
	movl	%EAX, %R12D
main_bb9:
	movl	$56, %EAX
	movl	%EAX, %EDI
	call	putchar
main_bb10:
	movl	$61, %EAX
	movl	%EAX, %EDI
	call	putchar
main_bb11:
	movl	%R12D, %EAX
	addl	%R14D, %EAX
	movl	%EAX, %EDI
	call	putchar
main_bb12:
	movl	$10, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$0, %EAX
	movl	%EAX, %R15D
	movl	$10, %EAX
	cmpl	%EAX, %R15D
	jl	main_bb16
main_bb13:
	cmpl	$1, %R13D
	je	main_bb13
main_bb15:
	movl	$48, %EAX
	addl	%R15D, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$1, %EAX
	movl	%R15D, %EDI
	addl	%EAX, %EDI
main_bb16:
	movl	$10, %EAX
	movl	%EAX, %EDI
	call	putchar
main_bb17:
	movl	$67, %EAX
	movl	%EAX, %EDI
	call	putchar
main_bb18:
	movl	$83, %EAX
	movl	%EAX, %EDI
	call	putchar
main_bb19:
	movl	$3510, %EAX
	movl	%EAX, %EDI
	call	printInt
main_bb20:
	movl	$10, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$0, %EAX
	movl	%EAX, %EDI
	movl	$1, %EAX
	movl	%EAX, %R12D
	movl	$1, %EAX
	movl	%EAX, %R14D
	movl	$0, %EAX
	movl	%EAX, %ECX
	movl	$0, %EAX
	movl	%EAX, %R15D
	movl	$0, %EAX
	cmpl	%EAX, %EDI
	je	main_bb22
main_bb21:
	movl	$0, %EAX
	cmpl	%EAX, %R12D
	je	main_bb25
main_bb24:
	movl	$1, %EAX
	movl	%EAX, %R15D
main_bb23:
	movl	$10, %EAX
	cmpl	%EAX, %R15D
	je	main_bb45
main_bb36:
	movl	$99, %EAX
	movl	%EAX, %EDI
	call	putchar
main_bb37:
	movl	$0, %EAX
	movl	%EAX, %EDI
	call	putDigit
main_bb38:
	movl	$0, %EAX
	movl	%EAX, %EDI
	call	putDigit
main_bb39:
	movl	$108, %EAX
	movl	%EAX, %EDI
	call	putchar
main_bb45:
	movl	$10, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$0, %EAX
main_bb2:
	popq	%R15
	popq	%R14
	popq	%R13
	popq	%R12
	ret
main_bb4:
	movl	$4, %EAX
	movl	%EAX, %ESI
	jmp	main_bb5
main_bb31:
	movl	$3, %EAX
	movl	%EAX, %R15D
	jmp	main_bb25
main_bb28:
	movl	$0, %EAX
	cmpl	%EAX, %ECX
	je	main_bb31
	jmp	main_bb22
main_bb25:
	movl	$0, %EAX
	cmpl	%EAX, %R14D
	je	main_bb28
	jmp	main_bb23
main_bb22:
	movl	$0, %EAX
	movl	%EAX, %R15D
	jmp	main_bb23
