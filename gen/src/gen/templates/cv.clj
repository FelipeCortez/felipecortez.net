(ns gen.templates.cv
  (:require [gen.templates.base :refer :all]
            [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.string :as string]
            [gen.util :refer [tr]]))

(defn read-cv [path]
  (edn/read (java.io.PushbackReader. (io/reader path))))

(defn format-info [cv]
  (let [info (:info cv)]
    [:section
     [:p.name (:name info)]
     [:div.info-grid
      [:div
       [:p.description (:outline info)]]
      [:div.info-section
       [:p (:phone    info)]
       [:p (:email    info)]
       [:p (:site     info)]
       [:p (:location info)]]]]))

(defn format-section
  [section-title m period title stack description]
  [:section
   [:h1 [:span (tr section-title)]]
   [:div.section-container
    (->> m
         (map (fn [thing]
                [:div.grid
                 [:h2.year       (->  (period      thing) (tr))]
                 [:h2            (->  (title       thing) (tr))]
                 [:h3.stack      (->> (stack       thing) (string/join ", "))]
                 [:p.description (->  (description thing) (tr))]])))]])


(defn format-proofs [proofs]
  (map-indexed (fn [idx itm]
                 [:sup.proof [:a {:href itm} (str "[" (inc idx) "]")]])
               proofs))

(defn format-tool [tool]
  [:div.tool
   [:h2 (tr (:title tool)) (-> (:proof tool) format-proofs)]
   [:p.description (string/join ", " (:topics tool))]])

(defn format-tools [cv]
  (let [tools (:technologies cv)]
    [:section
     [:h1 [:span (tr {:en "Tools" :pt "Ferramentas"})]]
     [:div.section-container
      [:div.grid
       [:div]
       [:div.tools (map format-tool tools)]]]]))

(defn cv-page []
  (let [cv (read-cv "resources/cv.edn")]
    (document (list (format-info cv)
                    (format-section {:en "Experience" :pt "Experiência"} (:experience cv) :when :where :stack :what)
                    (format-section {:en "Education"  :pt "Educação"}    (:education cv)  :when :where :_     :what)
                    (format-section {:en "Projects"   :pt "Projetos"}    (:projects cv)   :when :title :stack :what)
                    (format-tools cv)))))
