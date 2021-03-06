(set-info :origin "NTS benchmark converted to SMT-LIB2 using Eldarica (http://lara.epfl.ch/w/eldarica)")
(set-logic HORN)
(declare-fun main_q2 (Int Int Int Int Int Int) Bool)
(declare-fun main_qf () Bool)
(declare-fun main_q0 (Int Int Int Int Int Int) Bool)
(declare-fun main_q1 (Int Int Int Int Int Int) Bool)
(declare-fun palindrome_q4 (Int Int Int Int Int Int) Bool)
(declare-fun palindrome_q3 (Int Int Int Int Int Int Int Int) Bool)
(declare-fun palindrome_q2 (Int Int Int Int Int Int Int Int) Bool)
(declare-fun palindrome_q0 (Int Int Int Int Int Int Int Int) Bool)
(declare-fun palindrome_q1 (Int Int Int Int Int Int Int Int) Bool)
(assert(forall((?A Int)(?B Int)(?C Int)(?D Int)(?E Int)(?F Int))(=>(and (main_q2 ?A ?B ?C ?D ?E ?F)(<= ?D (+ 2 (div (- ?E ?F) 2)))) main_qf)))
(assert(not (exists((?A Int)(?B Int)(?C Int)(?D Int)(?E Int)(?F Int))(and (main_q2 ?A ?B ?C ?D ?E ?F)(> ?D (+ 2 (div (- ?E ?F) 2)))))))
(assert(forall((?A Int)(?B Int)(?C Int)(?D Int)(?E Int)(?F Int)(?G Int)(?H Int)(?I Int))(=>(and (main_q0 ?A ?B ?C ?G ?H ?I)(and (and (>= ?F 0) (>= ?E 0)) (>= ?E ?F))) (main_q1 ?A ?B ?C ?D ?E ?F))))
(assert(forall((?A Int)(?B Int)(?C Int)(?D Int)(?E Int)(?F Int))(=>(and (and (= ?A ?D) (= ?B ?E)) (= ?C ?F)) (main_q0 ?A ?B ?C ?D ?E ?F))))
(assert(forall((?A Int)(?B Int)(?C Int)(?D Int)(?E Int)(?F Int)(?G Int)(?H Int)(?I Int)(?J Int)(?K Int)(?L Int))(=>(and (palindrome_q3 ?A ?C ?B ?G ?H ?I ?J ?K)(and (= ?D 1) (and (and (= ?I ?F) (= ?J ?E)) (= ?K ?L)))) (palindrome_q4 ?A ?B ?C ?D ?E ?F))))
(assert(forall((?A Int)(?B Int)(?C Int)(?D Int)(?E Int)(?F Int)(?G Int)(?H Int)(?I Int)(?J Int)(?K Int)(?L Int))(=>(and (palindrome_q2 ?A ?C ?B ?G ?H ?I ?J ?K)(and (= ?D (+ 1 ?K)) (and (and (= ?I ?F) (= ?J ?E)) (= ?K ?L)))) (palindrome_q4 ?A ?B ?C ?D ?E ?F))))
(assert(forall((?A Int)(?B Int)(?C Int)(?D Int)(?E Int)(?F Int)(?G Int)(?H Int)(?I Int)(?J Int)(?K Int)(?L Int))(=>(and (palindrome_q0 ?A ?B ?C ?D ?I ?J ?K ?L)(and (>= ?K ?J) (and (and (and (= ?I ?E) (= ?J ?F)) (= ?K ?G)) (= ?L ?H)))) (palindrome_q3 ?A ?B ?C ?D ?E ?F ?G ?H))))
(assert(forall((?A Int)(?B Int)(?C Int)(?D Int)(?E Int)(?F Int)(?G Int)(?H Int)(?I Int)(?J Int)(?K Int)(?L Int))(=>(and (palindrome_q0 ?A ?B ?C ?D ?I ?J ?K ?L)(and (< ?K ?J) (and (and (and (= ?I ?E) (= ?J ?F)) (= ?K ?G)) (= ?L ?H)))) (palindrome_q1 ?A ?B ?C ?D ?E ?F ?G ?H))))
(assert(forall((?A Int)(?B Int)(?C Int)(?D Int)(?E Int)(?F Int)(?G Int)(?H Int)(?I Int)(?J Int)(?K Int)(?L Int)(?M Int)(?N Int)(?O Int)(?P Int)(?Q Int)(?R Int))(=>(and (and (and (and (palindrome_q1 ?A ?B ?C ?D ?I ?J ?K ?L)(and (= ?M (+ ?K 1)) (= ?N (- ?J 1))))(palindrome_q4 ?O ?M ?N ?P ?Q ?R))(= ?P ?H))(and (and (= ?I ?E) (= ?J ?F)) (= ?K ?G))) (palindrome_q2 ?A ?B ?C ?D ?E ?F ?G ?H))))
(assert(forall((?A Int)(?B Int)(?C Int)(?D Int)(?E Int)(?F Int)(?G Int)(?H Int)(?I Int)(?J Int)(?K Int)(?L Int)(?M Int)(?N Int)(?O Int)(?P Int))(=>(and (and (palindrome_q1 ?I ?J ?K ?L ?M ?N ?O ?P)(and (= ?C (+ ?O 1)) (= ?B (- ?N 1))))(and (and (= ?A ?E) (= ?C ?G)) (= ?B ?F))) (palindrome_q0 ?A ?B ?C ?D ?E ?F ?G ?H))))
(assert(forall((?A Int)(?B Int)(?C Int)(?D Int)(?E Int)(?F Int)(?G Int)(?H Int)(?I Int)(?J Int)(?K Int)(?L Int)(?M Int)(?N Int)(?O Int))(=>(and (and (and (and (main_q1 ?A ?B ?C ?G ?H ?I)(and (= ?J ?I) (= ?K ?H)))(palindrome_q4 ?L ?J ?K ?M ?N ?O))(= ?M ?D))(and (= ?H ?E) (= ?I ?F))) (main_q2 ?A ?B ?C ?D ?E ?F))))
(assert(forall((?A Int)(?B Int)(?C Int)(?D Int)(?E Int)(?F Int)(?G Int)(?H Int)(?I Int)(?J Int)(?K Int)(?L Int)(?M Int)(?N Int))(=>(and (and (main_q1 ?I ?J ?K ?L ?M ?N)(and (= ?C ?N) (= ?B ?M)))(and (and (= ?A ?E) (= ?C ?G)) (= ?B ?F))) (palindrome_q0 ?A ?B ?C ?D ?E ?F ?G ?H))))
(check-sat)
