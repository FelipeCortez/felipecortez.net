(ns gen.util)

(def ^:dynamic *language* :pt)

(defn tr [sentence]
  (or (*language* sentence) sentence))
