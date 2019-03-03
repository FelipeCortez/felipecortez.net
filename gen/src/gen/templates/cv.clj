(ns gen.templates.cv
  (:require [gen.templates.base :refer :all]
            [clojure.edn :as edn]
            [clojure.java.io :as io]
            [gen.util :refer [tr]]))

(def cv (edn/read (java.io.PushbackReader. (io/reader "resources/cv.edn"))))

(def cv-info
  (let [info (:info cv)]
    [:section
     [:p.name (:name     info)]
     [:p      (:phone    info)]
     [:p      (:email    info)]
     [:p      (:site     info)]
     [:p      (:location info)]]))

(def cv-experience
  (let [experiences (:experience cv)]
    [:section
     [:h1 [:span (tr {:en "Experience" :pt "Experiência"})]]
     (map
      (fn [experience]
        [:div
         [:h2 (-> (:where experience) (tr))]
         ;; [:h3 (:when experience)]
         [:p.description (-> (:what  experience) (tr))]])
      experiences)]))

(def cv-education
  (let [education (:education cv)]
    [:section
     [:h1 [:span (tr {:en "Education" :pt "Educação"})]]
     (map
      (fn [uni]
        [:div.education
         [:h2 (-> (:which uni) (tr))]
         ;; [:h3 (:when uni)]
         [:p.description (-> (:what  uni) (tr))]])
      education)]))

(defn format-proofs [proofs]
  (map-indexed (fn [idx itm] [:sup [:a {:href itm} (str "[" (inc idx) "]")]])
               proofs))

(defn format-tools [tools]
  (->> tools
       (map #(vector :div.tool [:h3 (tr (:title %))
                                (-> (:proof %) format-proofs)]))))

(def cv-tools
  (let [tools (:technologies cv)]
    [:section
     [:h1 [:span (tr {:en "Tools" :pt "Ferramentas"})]]
     [:div.tools (format-tools tools)]]))

(def cv-page
  (document (list cv-info cv-experience cv-education cv-tools)))
