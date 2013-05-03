(FUNCTION  main  []
  (BB 0
    (OPER 1 Func_Entry []  [])
  )
  (BB 1
    (OPER 4 EQ [(r 9)]  [(r 4)(r 10)])
    (OPER 5 BNE []  [(r 9)(i 1)(bb 4)])
  )
  (BB 3
  )
  (BB 5
    (OPER 7 LTE [(r 11)]  [(r 8)(r 12)])
    (OPER 8 BNE []  [(r 11)(i 1)(bb 7)])
  )
  (BB 6
    (OPER 9 BEQ []  [(r 11)(i 1)(bb 7)])
  )
  (BB 7
  )
  (BB 8
    (OPER 10 Add_I [(r 13)]  [(r 5)(r 6)])
    (OPER 11 Pass []  [(r 13)])
    (OPER 12 JSR []  [(r putchar)])
    (OPER 13 Mov [(r 14)]  [(m retReg)])
  )
  (BB 9
    (OPER 14 Pass []  [(r 15)])
    (OPER 15 JSR []  [(r putchar)])
    (OPER 16 Mov [(r 16)]  [(m retReg)])
    (OPER 17 Mov [(m RetReg)]  [(r 17)])
  )
)
