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
.globl  main
main:
	pushq	%R15
main_bb1:
	movl	$5, %EAX
	movl	%EAX, %EDI
	movl	$5, %EAX
	cmpl	%EAX, %EDI
	je	main_bb4
main_bb3:
	movl	$3, %EAX
	movl	%EAX, %ESI
main_bb5:
	movl	$0, %EAX
	movl	%EAX, %R15D
	movl	$1, %EAX
	movl	%EAX, %EDX
	movl	$8, %EAX
	cmpl	%EAX, %EDX
	jle	main_bb7
main_bb6:
	movl	%R15D, %EAX
	addl	%EDX, %EAX
	movl	%EAX, %R15D
	movl	$1, %EAX
	addl	%EAX, %EDX
	cmpl	$1, %ECX
	je	main_bb6
main_bb7:
	movl	$3, %ECX
	movl	$0, %EDX
	movl	%R15D, %EAX
	idivl	%ECX, %EAX
	movl	$4, %EDX
	imull	%EDX, %EAX
	movl	%EAX, %R15D
main_bb8:
	movl	%ESI, %EDI
	call	addThem
	movl	%retReg, %EAX
main_bb9:
	addl	%R15D, %EAX
	movl	%EAX, %EDI
	call	putchar
main_bb10:
	movl	$12, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$0, %EAX
main_bb2:
	popq	%R15
	ret
main_bb4:
	movl	$4, %EAX
	movl	%EAX, %ESI
	jmp	main_bb5
