(ns gen.util)

(def ^:dynamic *language* :en)

(defn tr [sentence]
  (or (*language* sentence) sentence))
