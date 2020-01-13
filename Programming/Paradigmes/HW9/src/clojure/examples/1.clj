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
(defn +star [p] (letfn [(rec [] (+or (+seqf cons p (delay (rec))) (_empty ())))] (rec))) ;;повторяется ???????????
(defn +plus [p] (+seqf cons p (+star p)))                   ;;хотя бы разок повторяется ??????

(defn +str [p] (+map (partial apply str) p))  ;;функция = перевод в строку и передать парсер

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

(def *op-start (+or *letter (+char "+-*/")))
(def *identifier (+str (+seqf cons *ws *op-start (+star (+or *op-start *digit)))))
(def *symbol (+map (comp #(get OPERATORS % (Variable (str %))) symbol) *identifier))

(def *value)
(defn *seq [p] (+seqn 1 *ws (+char "(") (+opt (+seqf cons *ws p (+star (+seqn 0 *ws p)))) *ws (+char ")")))
(def *list (+map (fn [list] (apply (last list) (butlast list))) (*seq (delay *value))))
(def *value (+or *const *symbol *list))

(def parseObjectSuffix (+parser (+seqn 0 *ws *value *ws)))