.text
	.align 4
.globl  stupidMethod
stupidMethod:
	movl	%EDI, %ECX
	movl	%EDX, %EDI
stupidMethod_bb1:
	movl	$3, %EAX
	movl	$4, %EAX
	movl	$5, %EAX
	movl	%ECX, %EAX
	addl	%ESI, %EAX
	movl	%EDI, %R9D
	addl	%EAX, %R9D
	movl	%ESI, %EAX
	imull	%EDI, %EAX
	movl	%ECX, %EDX
	addl	%EAX, %EDX
	cmpl	%EDX, %R9D
	jle	stupidMethod_bb5
stupidMethod_bb3:
	movl	%ESI, %ECX
stupidMethod_bb5:
	movl	%EDI, %EAX
	addl	%ESI, %EAX
	cmpl	%ECX, %EAX
	je	stupidMethod_bb7
stupidMethod_bb6:
	movl	%ESI, %EAX
	addl	%ECX, %EAX
	movl	%EAX, %EDI
	movl	%ECX, %EAX
	subl	%ESI, %EAX
	movl	%EAX, %ESI
	movl	$0, %EDX
	movl	%ECX, %EAX
	idivl	%ESI, %EAX
	movl	%EAX, %ECX
stupidMethod_bb8:
	cmpl	%ECX, %EDI
	jle	stupidMethod_bb11
stupidMethod_bb9:
	movl	%ESI, %ECX
stupidMethod_bb11:
	cmpl	%ESI, %EDI
	jge	stupidMethod_bb14
stupidMethod_bb12:
	movl	%EDI, %ESI
stupidMethod_bb14:
	cmpl	%EDI, %ECX
	jne	stupidMethod_bb17
stupidMethod_bb15:
	movl	$1, %EAX
	addl	%EAX, %EDI
stupidMethod_bb17:
	cmpl	%ECX, %EDI
	jl	stupidMethod_bb20
stupidMethod_bb18:
	movl	%ESI, %ECX
stupidMethod_bb20:
	movl	$2, %EAX
	movl	%ESI, %EDI
	subl	%EAX, %EDI
	cmpl	%EDI, %ECX
	jg	stupidMethod_bb23
stupidMethod_bb21:
	movl	%ECX, %ESI
stupidMethod_bb23:
	cmpl	%ESI, %ECX
	jg	stupidMethod_bb2
stupidMethod_bb24:
	movl	$3, %EAX
	movl	%ECX, %EDI
	subl	%EAX, %EDI
	movl	%EDI, %ECX
	cmpl	$1, %R8D
	je	stupidMethod_bb24
stupidMethod_bb2:
	ret
stupidMethod_bb7:
	movl	%ESI, %EAX
	imull	%ESI, %EAX
	movl	%EAX, %ECX
	jmp	stupidMethod_bb8
