(defn constructor [object methods] (fn [& arguments] (apply (partial object {:methods methods}) arguments)))
;;partial пихает туда this и возвращает функцию, которая требует только value
;;assoc вставит в методы либо value либо аргументы, потом к нему применяется evaluate и все
(defn diff [this name] (((this :methods) :diffur) this name))
(defn evaluate [this hashMap] (((this :methods) :evaluate) this hashMap))
(defn toString [this] (((this :methods) :toString) this))
(defn toStringSuffix [this] (((this :methods) :toStringSuffix) this))

;;;;

(defn CVOperator [this value]
  (assoc this :value value))
;;вставляет в methods поле :value value

(def Constant (constructor CVOperator
                           {:evaluate       (fn [this hashMap] (this :value))
                            :diffur         (fn [this var] (Constant 0)) ;;var - по какой букве производная
                            :toString       (fn [this] (str (format "%.1f" (this :value))))
                            :toStringSuffix (fn [this] (str (format "%.1f" (this :value))))
                            }))

(def Variable (constructor CVOperator
                           {:evaluate       (fn [this hashMap] (hashMap (str (first (.toLowerCase (str (this :value)))))))
                            :diffur         (fn [this var] (if (= (this :value) var) (Constant 1) (Constant 0)))
                            :toString       (fn [this] (this :value))
                            :toStringSuffix (fn [this] (this :value))
                            }))

;;;;

(defn Operator [this & operands]
  (assoc this :operands operands))

(defn abstractOperation [operation name diff]
  {:evaluate       (fn [this hashMap] (apply operation (map (fn [currentArg] (evaluate currentArg hashMap)) (this :operands))))
   ;;evaluate ко всем операндам, потом применить к ним операцию
   :toString       (fn [now] (str "(" name (apply str (mapv (fn [arg] (str " " (toString arg))) (now :operands))) ")"))
   :toStringSuffix (fn [now] (str "(" (apply str (mapv (fn [arg] (str (toStringSuffix arg) " ")) (now :operands))) name ")"))
   ;;join - делает в кавычках последователность разделенную пробелом
   ;;cons - новую последовательность, где в начале name (то есть операция)
   :diffur         (fn [this var] (diff (this :operands) var))
   })

(def Add (constructor Operator (abstractOperation + "+"
                                                  (fn [arguments var] (apply Add (map (fn [currentArg] (diff currentArg var)) arguments)))
                                                  )))
;;взять диф от аргументов arguments по отдельности по переменной var и потом их сложить

(def Subtract (constructor Operator (abstractOperation - "-"
                                                       (fn [arguments var] (apply Subtract (map (fn [currentArg] (diff currentArg var)) arguments)))
                                                       )))

(def Multiply (constructor Operator (abstractOperation * "*"
                                                       (defn composition [arguments var] (cond
                                                                                           (empty? (rest arguments)) (diff (first arguments) var)
                                                                                           :else (Add (apply Multiply (cons (diff (first arguments) var) (rest arguments))) (Multiply (first arguments) (composition (rest arguments) var)))
                                                                                           ))
                                                       )))

(def Divide (constructor Operator (abstractOperation (fn [first second] (/ (double first) (double second))) "/"
                                                     (fn [arguments var] (Divide (Subtract (Multiply (diff (first arguments) var) (second arguments)) (Multiply (diff (second arguments) var) (first arguments))) (Multiply (second arguments) (second arguments))))
                                                     )))

(def Negate (constructor Operator (abstractOperation - "negate"
                                                     (fn [arguments var] (Negate (diff (first arguments) var)))
                                                     )))

(def Square (constructor Operator (abstractOperation (fn [argument] (* argument argument)) "square"
                                                     (fn [arguments var] (Multiply (first arguments) (Multiply (Constant 2) (diff (first arguments) var))))
                                                     )))

(def Sqrt (constructor Operator (abstractOperation (fn [argument] (Math/sqrt (Math/abs (double argument)))) "sqrt"
                                                   (fn [arguments var] (Divide (Multiply (first arguments) (diff (first arguments) var)) (Multiply (Constant 2) (Sqrt (Multiply (Square (first arguments)) (first arguments))))))
                                                   )))


(def Sinh (constructor Operator (abstractOperation (fn [argument] (Math/sinh argument)) "sinh"
                                                   (fn [arguments var] (Multiply (diff (first arguments) var) (Sqrt (Add (Constant 1) (Square (Sinh (first arguments)))))))
                                                   )))

(def Cosh (constructor Operator (abstractOperation (fn [argument] (Math/cosh argument)) "cosh"
                                                   (fn [arguments var] (Multiply (diff (first arguments) var) (Sinh (first arguments))))
                                                   )))
;;;;

(def And (constructor Operator (abstractOperation (fn [first second] (Double/longBitsToDouble (bit-and (Double/doubleToLongBits first) (Double/doubleToLongBits second)))) "&"
                                                  (fn [arguments var] (apply Add (map (fn [currentArg] (diff currentArg var)) arguments)))
                                                  )))
(def Or (constructor Operator (abstractOperation (fn [first second] (Double/longBitsToDouble (bit-or (Double/doubleToLongBits first) (Double/doubleToLongBits second)))) "|"
                                                 (fn [arguments var] (apply Add (map (fn [currentArg] (diff currentArg var)) arguments)))
                                                 )))
(def Xor (constructor Operator (abstractOperation (fn [first second] (Double/longBitsToDouble (bit-xor (Double/doubleToLongBits first) (Double/doubleToLongBits second)))) "^"
                                                  (fn [arguments var] (apply Add (map (fn [currentArg] (diff currentArg var)) arguments)))
                                                  )))

(def OPERATIONS {'+           Add
                 '-           Subtract
                 '*           Multiply
                 '/           Divide
                 'negate      Negate
                 'square      Square
                 'sqrt        Sqrt
                 'sinh        Sinh
                 'cosh        Cosh
                 '|           Or
                 '&           And
                 (symbol "^") Xor
                 })

(defn parse [expression] (cond
                           (number? expression) (Constant expression)
                           (symbol? expression) (Variable (str expression))
                           (list? expression) (apply (OPERATIONS (first expression)) (map parse (rest expression)))
                           ))

(defn parseObject [expression] (parse (read-string expression)))



(defn -return [value tail] {:value value :tail tail})
(def -valid? boolean)
(def -value :value)
(def -tail :tail)

(defn _empty [value] (partial -return value))

(defn _char [p] (fn [[c & cs]] (if (and c (p c)) (-return c cs)))) ;;если во множестве p содержится c, то вернет что-то иначе вернет nil

(defn _map [f] (fn [result] (if (-valid? result) (-return (f (-value result)) (-tail result)))))
(defn _combine [f a b] (fn [str] (let [ar ((force a) str)]
                                   (if (-valid? ar) ((_map (partial f (-value ar))) ((force b) (-tail ar)))))))
(defn _either [a b] (fn [str] (let [ar ((force a) str)] (if (-valid? ar) ar ((force b) str)))))

(defn _parser [p] (fn [input] (-value ((_combine (fn [v _] v) p (_char #{\u0000})) (str input \u0000)))))

(defn +char [chars] (_char (set chars)))                    ;;делаем строку множеством и делаем _char
(defn +char-not [chars] (_char (comp not (set chars))))     ;;то же самое что и +char, но те что не входят
(defn +map [f parser] (comp (_map f) parser))               ;;comp - композиция функций, применяется справа налево

(defn iconj [coll value] (if (= value 'ignore) coll (conj coll value))) ;;вставляет в последовательность coll, в начало value

(defn +seq [& ps] (reduce (partial _combine iconj) (_empty []) ps))
(defn +seqf [f & ps] (+map (partial apply f) (apply +seq ps))) ;;функция и набор аргументов, который надо парсить - т.е. это последовательность с функцией
(defn +seqn [n & ps] (apply +seqf (fn [& vs] (nth vs n)) ps))

(defn +or [p & ps] (reduce (partial _either) p ps))

(defn +opt [p] (+or p (_empty nil)))
(defn +star [p] (letfn [(rec [] (+or (+seqf cons p (delay (rec))) (_empty ())))] (rec))) ;;повторяется
(defn +plus [p] (+seqf cons p (+star p)))                   ;;хотя бы разок повторяется

(defn +str [p] (+map (partial apply str) p))                ;;функция = перевод в строку и передать парсер

(def +parser _parser)
(def +ignore (partial +map (constantly 'ignore)))           ;;constantly возвращает всегда ignore
;;становится функцией, которая на вход хочет парсер, которая потом будет применять сначала парсер, а результат потом будет делать return


(def *all-chars (mapv char (range 32 128)))
;;берем все символы и делаем вектор из них вектор
(def *space (+char (apply str (filter #(Character/isWhitespace %) *all-chars))))
;;фильтр возвращает строку символов, потом делаем их строкой
;;и отправляем в +char
(def *letter (+char (apply str (filter #(Character/isLetter %) *all-chars))))
(def *digit (+char (apply str (filter #(Character/isDigit %) *all-chars))))

(def *ws (+ignore (+star *space)))

(def *const (+map (comp Constant read-string) (+str (+seq (+opt (+char "-")) (+str (+plus *digit)) (+char ".") *digit))))

(def *operation (+or *letter (+char "*^|&+-/")))
(def *identifier (+str (+seqf cons *ws *operation (+star (+or *operation *digit)))))
(def *symbol (+map (comp #(get OPERATIONS % (Variable (str %))) symbol) *identifier))

(def *value)
(defn *seq [parsed] (+seqn 1 *ws (+char "(") (+opt (+seqf cons *ws parsed (+star (+seqn 0 *ws parsed)))) *ws (+char ")")))
(def *list (+map (fn [list] (apply (last list) (butlast list))) (*seq (delay *value))))
(def *value (+or *const *symbol *list))

(def parseObjectSuffix (+parser (+seqn 0 *ws *value *ws)))
;;seq - конкатенация star - замыкание opt - берем или нет
;;char - вставить символ map - применить функцию ко всем пропарсенным токенам