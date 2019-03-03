(ns gen.core
  (:require [hiccup.core :refer :all]
            [hiccup.page :refer [html5 doctype include-css]]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [ring.middleware.resource :refer [wrap-resource]]
            [clojure.edn :as edn]
            [clojure.java.io :as io]))

(def ^:dynamic *language* :pt)

(defn tr [sentence]
  (or (*language* sentence) sentence))

(defn body [content]
  [:body content])

(defn metadata [page-description]
  (list
   [:meta {"charset" hiccup.util/*encoding*}]
   [:meta {:name    "viewport"
           :content "width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"}]
   [:meta {:name    "description"
           :content page-description}]
   [:meta {:name    "author"
           :content "Felipe Cortez"}]
   (include-css "https://use.typekit.net/ccn1qlb.css"
                "https://use.typekit.net/vcl8qud.css"
                "reset.css"
                "cv.css"
                )))

(defn analytics []
  [:script "(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)})(window,document,'script','//www.google-analytics.com/analytics.js','ga');ga('create', 'UA-49103022-1', 'auto');ga('send', 'pageview');"])

(defn head [page-title page-description]
  (list
    [:head
     [:title page-title]
     (metadata page-description)
     (analytics)]))

(defn document [content]
  (str (:html5 doctype)
       (html [:html {:lang "en"}
              (head "Home" "Description")
              (body content)])))

(def cv (edn/read (java.io.PushbackReader. (io/reader "resources/cv.edn"))))

(def cv-info
  (let [info (:info cv)]
    [:section
     [:p.name (:name     info)]
     [:p (:phone    info)]
     [:p (:email    info)]
     [:p (:site     info)]
     [:p (:location info)]]))

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

(defroutes my-routes
  (GET "/"
       req
       (document (list cv-info
                       cv-experience
                       cv-education
                       cv-tools))))

(def handler
  (-> (handler/site my-routes)
      (wrap-resource "public")))
