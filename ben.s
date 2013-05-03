.text
	.align 4
.globl  test
test:
test_bb1:
	movl	$0, %EDI
	movl	$1, %EDI
	movl	$0, %EDI
	movl	%EDI, %ESI
	cmpl	%ESI, %EDX
	jg	test_bb4
test_bb3:
	movl	$1, %EDI
	subl	%EDI, %ESI
test_bb5:
	cmpl	%ESI, %EDX
	je	test_bb15
test_bb13:
	cmpl	%ESI, %EDX
	je	test_bb15
test_bb16:
	cmpl	%ESI, %EDX
	je	test_bb15
	cmpl	$1, %EAX
	je	test_bb15
test_bb18:
	movl	$1, %EAX
	movl	%ESI, %EDI
	subl	%EAX, %EDI
test_bb15:
	movl	$3, %EAX
test_bb2:
	ret
test_bb4:
	movl	$2, %EDI
	movl	%EDI, %ESI
	movl	$2, %EDI
	cmpl	%EDI, %ESI
	je	test_bb7
	jmp	test_bb5
test_bb7:
