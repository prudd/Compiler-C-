(FUNCTION  stupidMethod  [(int zebra) (int walrus) (int kitten)]
  (BB 0
    (OPER 1 Func_Entry []  [])
  )
  (BB 1
    (OPER 4 Mov [(r 5)]  [(i 3)])
    (OPER 5 Mov [(r 4)]  [(r 5)])
    (OPER 6 Mov [(r 6)]  [(i 4)])
    (OPER 7 Mov [(r 4)]  [(r 6)])
    (OPER 8 Mov [(r 7)]  [(i 5)])
    (OPER 9 Mov [(r 4)]  [(r 7)])
    (OPER 10 Add_I [(r 10)]  [(r 1)(r 2)])
    (OPER 11 Add_I [(r 9)]  [(r 3)(r 10)])
    (OPER 12 Mul_I [(r 12)]  [(r 2)(r 3)])
    (OPER 13 Add_I [(r 11)]  [(r 1)(r 12)])
    (OPER 14 LTE [(r 8)]  [(r 9)(r 11)])
    (OPER 15 BNE []  [(r 8)(i 1)(bb 5)])
  )
  (BB 3
    (OPER 16 Mov [(r 1)]  [(r 2)])
  )
  (BB 5
    (OPER 17 Add_I [(r 14)]  [(r 3)(r 2)])
    (OPER 18 EQ [(r 13)]  [(r 14)(r 1)])
    (OPER 19 BNE []  [(r 13)(i 1)(bb 7)])
  )
  (BB 6
    (OPER 20 Add_I [(r 15)]  [(r 2)(r 1)])
    (OPER 21 Mov [(r 3)]  [(r 15)])
    (OPER 22 Sub_I [(r 16)]  [(r 1)(r 2)])
    (OPER 23 Mov [(r 2)]  [(r 16)])
    (OPER 24 Div_I [(r 17)]  [(r 1)(r 2)])
    (OPER 25 Mov [(r 1)]  [(r 17)])
  )
  (BB 8
    (OPER 29 LTE [(r 19)]  [(r 3)(r 1)])
    (OPER 30 BNE []  [(r 19)(i 1)(bb 11)])
  )
  (BB 9
    (OPER 31 Mov [(r 1)]  [(r 2)])
  )
  (BB 11
    (OPER 32 GTE [(r 20)]  [(r 3)(r 2)])
    (OPER 33 BNE []  [(r 20)(i 1)(bb 14)])
  )
  (BB 12
    (OPER 34 Mov [(r 2)]  [(r 3)])
  )
  (BB 14
    (OPER 35 NEQ [(r 21)]  [(r 1)(r 3)])
    (OPER 36 BNE []  [(r 21)(i 1)(bb 17)])
  )
  (BB 15
    (OPER 37 Mov [(r 23)]  [(i 1)])
    (OPER 38 Add_I [(r 22)]  [(r 3)(r 23)])
    (OPER 39 Mov [(r 3)]  [(r 22)])
  )
  (BB 17
    (OPER 40 LT [(r 24)]  [(r 3)(r 1)])
    (OPER 41 BNE []  [(r 24)(i 1)(bb 20)])
  )
  (BB 18
    (OPER 42 Mov [(r 1)]  [(r 2)])
  )
  (BB 20
    (OPER 43 Mov [(r 27)]  [(i 2)])
    (OPER 44 Sub_I [(r 26)]  [(r 2)(r 27)])
    (OPER 45 GT [(r 25)]  [(r 1)(r 26)])
    (OPER 46 BNE []  [(r 25)(i 1)(bb 23)])
  )
  (BB 21
    (OPER 47 Mov [(r 2)]  [(r 1)])
  )
  (BB 23
    (OPER 48 GT [(r 28)]  [(r 1)(r 2)])
    (OPER 49 BNE []  [(r 28)(i 1)(bb 25)])
  )
  (BB 24
    (OPER 50 Mov [(r 30)]  [(i 3)])
    (OPER 51 Sub_I [(r 29)]  [(r 1)(r 30)])
    (OPER 52 Mov [(r 1)]  [(r 29)])
    (OPER 53 BEQ []  [(r 28)(i 1)(bb 24)])
  )
  (BB 25
  )
  (BB 2
    (OPER 2 Func_Exit []  [])
    (OPER 3 Return []  [(m RetReg)])
  )
  (BB 7
    (OPER 26 Mul_I [(r 18)]  [(r 2)(r 2)])
    (OPER 27 Mov [(r 1)]  [(r 18)])
    (OPER 28 Jmp []  [(bb 8)])
  )
)
