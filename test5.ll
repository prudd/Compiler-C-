(DATA  a)
(FUNCTION  addThem  [(int d) (int e)]
  (BB 0
    (OPER 1 Func_Entry []  [])
  )
  (BB 1
    (OPER 4 Add_I [(r 4)]  [(r 1)(r 2)])
    (OPER 5 Mov [(r 3)]  [(r 4)])
    (OPER 6 Mov [(m RetReg)]  [(r 3)])
  )
  (BB 2
    (OPER 2 Func_Exit []  [])
    (OPER 3 Return []  [(m RetReg)])
  )
)
(FUNCTION  main  []
  (BB 0
    (OPER 1 Func_Entry []  [])
  )
  (BB 1
    (OPER 4 Mov [(r 5)]  [(r 10)])
    (OPER 5 EQ [(r 11)]  [(r 5)(r 12)])
    (OPER 6 BNE []  [(r 11)(i 1)(bb 4)])
  )
  (BB 3
    (OPER 7 Mov [(r 0)]  [(r 13)])
  )
  (BB 5
    (OPER 10 Mov [(r 7)]  [(r 15)])
    (OPER 11 Mov [(r 9)]  [(r 16)])
    (OPER 12 LTE [(r 17)]  [(r 9)(r 18)])
    (OPER 13 BNE []  [(r 17)(i 1)(bb 7)])
  )
  (BB 6
    (OPER 14 Add_I [(r 19)]  [(r 7)(r 9)])
    (OPER 15 Mov [(r 7)]  [(r 19)])
    (OPER 16 Add_I [(r 20)]  [(r 9)(r 21)])
    (OPER 17 Mov [(r 9)]  [(r 20)])
    (OPER 18 BEQ []  [(r 17)(i 1)(bb 7)])
  )
  (BB 7
    (OPER 19 Div_I [(r 22)]  [(r 7)(r 23)])
    (OPER 20 Mov [(r 8)]  [(r 22)])
    (OPER 21 Mul_I [(r 24)]  [(r 8)(r 25)])
    (OPER 22 Mov [(r 7)]  [(r 24)])
  )
  (BB 8
    (OPER 23 Pass []  [(r 5)])
    (OPER 24 Pass []  [(r 0)])
    (OPER 25 JSR []  [(r addThem)])
    (OPER 26 Mov [(r 26)]  [(m retReg)])
    (OPER 27 Mov [(r 6)]  [(r 26)])
  )
  (BB 9
    (OPER 28 Add_I [(r 27)]  [(r 6)(r 7)])
    (OPER 29 Pass []  [(r 27)])
    (OPER 30 JSR []  [(r putchar)])
    (OPER 31 Mov [(r 28)]  [(m retReg)])
  )
  (BB 10
    (OPER 32 Pass []  [(r 29)])
    (OPER 33 JSR []  [(r putchar)])
    (OPER 34 Mov [(r 30)]  [(m retReg)])
    (OPER 35 Mov [(m RetReg)]  [(r 31)])
  )
  (BB 2
    (OPER 2 Func_Exit []  [])
    (OPER 3 Return []  [(m RetReg)])
  )
  (BB 4
    (OPER 8 Mov [(r 0)]  [(r 14)])
    (OPER 9 Jmp []  [(bb 5)])
  )
)
