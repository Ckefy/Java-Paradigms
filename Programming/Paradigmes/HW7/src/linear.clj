(defn mulOnScalar [operation vectorOperand scalarOperand]
  (loop [cnt 0 res []] (if (== cnt (count vectorOperand))
                         res
                         (recur (inc cnt) (conj res (operation (nth vectorOperand cnt) scalarOperand))))))

(defn v*s [vectorOperand & scalarOperand] (mulOnScalar * vectorOperand (apply * scalarOperand)))
(defn m*s [matrixOperand & scalarOperand] (mulOnScalar v*s matrixOperand (apply * scalarOperand)))

(defn operator [operation] (fn [firstOperand & secondOperand] (apply mapv operation firstOperand secondOperand)))

(def v+ (operator +))
(def v- (operator -))
(def v* (operator *))
(def m+ (operator v+))
(def m- (operator v-))
(def m* (operator v*))
(def t+ (operator v+))
(def t- (operator v-))
(def t* (operator v*))

(defn scalar [firstVector secondVector]
  (loop [counter 0 ans 0] (if (== counter (count firstVector))
                            ans                             ;;если прошли по всем, то классно, иначе запустить рекурсию с увеличенным counter
                            (recur (inc counter) (+ ans (* (nth firstVector counter) (nth secondVector counter)))))))

(defn vectRule [firstVector secondVector]
  [(- (* (nth firstVector 1) (nth secondVector 2)) (* (nth firstVector 2) (nth secondVector 1)))
   (- (* (nth firstVector 2) (nth secondVector 0)) (* (nth firstVector 0) (nth secondVector 2)))
   (- (* (nth firstVector 0) (nth secondVector 1)) (* (nth firstVector 1) (nth secondVector 0)))
   ])

(defn vect [firstVector & secondVector] (reduce vectRule firstVector secondVector))
;;тут она работает как свертка, если 1 аргумент - то просто вернет его

;;у нас вида
;;[1]  [4]  [7]
;;[2]  [5]  [8]
;;[3]  [6]  [9]

;;mapv берет по первому элементу из столбца и применяет к ним операцию
(defn transpose [matrix]
  (apply mapv vector matrix))

;;строчку на столбец (вектор) надо умножить - возвращает строку
(defn m*v [matrixOperand vectorOperand] (mapv (fn [lineOfMatrix] (scalar lineOfMatrix vectorOperand)) matrixOperand))

;;так как возвращает строку, а мы заполняем по столбцам, то надо транспонировать
(defn m*mRule [firstMatrix secondMatrix]
  (transpose (mapv (fn [columnOfSecondMatrix] (m*v firstMatrix columnOfSecondMatrix)) (transpose secondMatrix))))

(defn m*m [firstMatrix & secondMatrix] (reduce m*mRule firstMatrix secondMatrix))

(defn calculationOnScalars [operation]
  (fn calculate [firstOperand secondOperand] (if (vector? firstOperand) (mapv calculate firstOperand secondOperand) (operation firstOperand secondOperand))))

(def s+ (calculationOnScalars +))
(def s* (calculationOnScalars *))
(def s- (calculationOnScalars -))