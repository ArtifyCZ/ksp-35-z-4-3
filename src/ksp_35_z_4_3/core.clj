(ns ksp-35-z-4-3.core
  (:require [clojure.string :as str]))

(defn read-input [file]
  (let [[_ d & columns]
        (-> file
            slurp
            (str/split #"(\ |\n)")
            (#(map parse-long %)))]
    [(dec d) (vec columns)]))

(defn solve [d columns]
  (->> columns
       (map-indexed vector)
       (take-while (fn [[i h]] (or (<= i d)
                                   (<= h (nth columns d)))))
       (map second)
       reverse
       (reduce (fn [[water top] curr]
                 (if (<= top curr)
                   [water curr]
                   [(+ water (- top curr)) top]))
               [0 (inc (nth columns d))])
       first))

(defn solve-file [input-file output-file]
  (->> input-file
       (read-input)
       (apply solve)
       (spit output-file)))
