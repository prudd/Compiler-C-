.text
	.align 4
.globl  test
test:
test_bb1:
	movl	$0, %EAX
	movl	$1, %EAX
	movl	$0, %EAX
	movl	%EAX, %R9D
	movl	%EDX, %EAX
	cmpl	%R9D, %EAX
	jg	test_bb4
test_bb3:
	movl	$1, %EDX
	movl	%R9D, %ECX
	subl	%EDX, %ECX
	movl	%ECX, %R9D
test_bb5:
	cmpl	%R9D, %EAX
	je	test_bb15
test_bb13:
	cmpl	%R9D, %EAX
	je	test_bb15
test_bb16:
	cmpl	%R9D, %EAX
	je	test_bb15
	cmpl	$1, %EDI
	je	test_bb16
test_bb18:
	movl	$1, %EAX
	movl	%R9D, %EDI
	subl	%EAX, %EDI
	cmpl	$1, %ESI
	je	test_bb18
test_bb15:
	movl	$3, %EAX
test_bb2:
	ret
test_bb9:
	movl	$1, %EDX
	movl	%EDX, %R9D
	movl	$1, %EDX
	cmpl	%EDX, %R9D
	je	test_bb4
	jmp	test_bb7
test_bb11:
	movl	$2, %EDX
	movl	%R9D, %ECX
	addl	%EDX, %ECX
	movl	%ECX, %R9D
	cmpl	$1, %R8D
	je	test_bb11
test_bb4:
	movl	$2, %EDX
	movl	%EDX, %R9D
	movl	$2, %EDX
	cmpl	%EDX, %R9D
	je	test_bb7
	jmp	test_bb5
test_bb6:
	movl	$2, %EDX
	movl	%EDX, %R9D
	movl	$2, %EDX
	cmpl	%EDX, %R9D
	je	test_bb9
	cmpl	$1, %ECX
	je	test_bb6
test_bb8:
	movl	$1, %EAX
	movl	%R9D, %EDI
	addl	%EAX, %EDI
test_bb7:
