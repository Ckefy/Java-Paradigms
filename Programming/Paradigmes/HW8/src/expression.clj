(defn operator [operation]
  (fn [& operands]
    (fn [evaluation] (apply operation (mapv (fn [curOperand] (curOperand evaluation)) operands))) ;;нам тут дают хэш мапу x = чет и тд
    ;;наш вариэйбл берет эту мапу и по имени достает из нее имя fn[cur] берет и передает эту хэшмапу в функцию
    ;;mapv вектор ретурнает, apply пихает секвиенс
    )
  )

(def add (operator +))
(def subtract (operator -))
(def multiply (operator *))
(def divide (operator (fn [firstArg secondArg] (/ (double firstArg) (double secondArg)))))
(def negate (operator -))
(def square (operator (fn [arg] (* arg arg))))
(def sqrt (operator (fn [arg] (Math/sqrt (Math/abs (double arg))))))

(defn variable [name] (fn [evaluation] (get evaluation name)))  ;;получаем значение из хэшмапы
(defn constant [value] (fn [evaluation] value))

(def operationsMap {'+ add,
                    '- subtract,
                    '* multiply,
                    '/ divide,
                    'negate negate
                    'square square
                    'sqrt sqrt
                    })

(defn parseFunction [expression]
  (cond
    (string? expression) (parseFunction (read-string expression)) ;;читает одну sequence из нашего объекта
    (symbol? expression) (variable (str expression))
    (number? expression) (constant expression)
    (seq? expression) (apply (operationsMap (first expression)) (map parseFunction (rest expression)))
    )
  )