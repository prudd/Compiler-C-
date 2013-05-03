(FUNCTION  test  []
  (BB 0
    (OPER 1 Func_Entry []  [])
  )
  (BB 1
    (OPER 4 Mov [(r 3)]  [(i 0)])
    (OPER 5 Mov [(r 1)]  [(r 3)])
    (OPER 6 Mov [(r 4)]  [(i 1)])
    (OPER 7 Mov [(r 2)]  [(r 4)])
    (OPER 8 Mov [(r 5)]  [(i 0)])
    (OPER 9 Mov [(r 2)]  [(r 5)])
    (OPER 10 Mov [(r 1)]  [(r 0)])
    (OPER 11 GT [(r 6)]  [(r 1)(r 2)])
    (OPER 12 BNE []  [(r 6)(i 1)(bb 4)])
  )
  (BB 3
    (OPER 13 Mov [(r 8)]  [(i 1)])
    (OPER 14 Sub_I [(r 7)]  [(r 2)(r 8)])
    (OPER 15 Mov [(r 2)]  [(r 7)])
  )
  (BB 5
    (OPER 41 EQ [(r 22)]  [(r 1)(r 2)])
    (OPER 42 BNE []  [(r 22)(i 1)(bb 15)])
  )
  (BB 13
    (OPER 43 EQ [(r 23)]  [(r 1)(r 2)])
    (OPER 44 BNE []  [(r 23)(i 1)(bb 17)])
  )
  (BB 16
    (OPER 45 EQ [(r 24)]  [(r 1)(r 2)])
    (OPER 46 BNE []  [(r 24)(i 1)(bb 19)])
    (OPER 51 BEQ []  [(r 23)(i 1)(bb 17)])
  )
  (BB 18
    (OPER 47 Mov [(r 26)]  [(i 1)])
    (OPER 48 Sub_I [(r 25)]  [(r 2)(r 26)])
    (OPER 49 Mov [(r 1)]  [(r 25)])
    (OPER 50 BEQ []  [(r 24)(i 1)(bb 19)])
  )
  (BB 19
  )
  (BB 17
  )
  (BB 15
    (OPER 52 Mov [(r 27)]  [(i 3)])
    (OPER 53 Mov [(r 2)]  [(r 27)])
  )
  (BB 2
    (OPER 2 Func_Exit []  [])
    (OPER 3 Return []  [(m RetReg)])
  )
  (BB 9
    (OPER 29 Mov [(r 17)]  [(i 1)])
    (OPER 30 Mov [(r 2)]  [(r 17)])
    (OPER 31 Mov [(r 19)]  [(i 1)])
    (OPER 32 EQ [(r 18)]  [(r 2)(r 19)])
    (OPER 33 BNE []  [(r 18)(i 1)(bb 12)])
    (OPER 38 Jmp []  [(bb 10)])
  )
  (BB 11
    (OPER 34 Mov [(r 21)]  [(i 2)])
    (OPER 35 Add_I [(r 20)]  [(r 2)(r 21)])
    (OPER 36 Mov [(r 2)]  [(r 20)])
    (OPER 37 BEQ []  [(r 18)(i 1)(bb 12)])
  )
  (BB 12
  )
  (BB 4
    (OPER 16 Mov [(r 9)]  [(i 2)])
    (OPER 17 Mov [(r 2)]  [(r 9)])
    (OPER 18 Mov [(r 11)]  [(i 2)])
    (OPER 19 EQ [(r 10)]  [(r 2)(r 11)])
    (OPER 20 BNE []  [(r 10)(i 1)(bb 7)])
    (OPER 40 Jmp []  [(bb 5)])
  )
  (BB 6
    (OPER 21 Mov [(r 12)]  [(i 2)])
    (OPER 22 Mov [(r 2)]  [(r 12)])
    (OPER 23 Mov [(r 14)]  [(i 2)])
    (OPER 24 EQ [(r 13)]  [(r 2)(r 14)])
    (OPER 25 BNE []  [(r 13)(i 1)(bb 9)])
    (OPER 39 BEQ []  [(r 10)(i 1)(bb 7)])
  )
  (BB 8
    (OPER 26 Mov [(r 16)]  [(i 1)])
    (OPER 27 Add_I [(r 15)]  [(r 2)(r 16)])
    (OPER 28 Mov [(r 2)]  [(r 15)])
  )
  (BB 10
  )
  (BB 7
  )
)
