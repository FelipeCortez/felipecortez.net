(ns gen.core
  (:require [hiccup.core :refer :all]
            [hiccup.page :refer [html5 doctype include-css]]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [ring.middleware.resource :refer [wrap-resource]]
            [clojure.edn :as edn]
            [clojure.java.io :as io]))

(defn tr [sentence language]
  (or (language sentence) sentence))

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
                "/cv.css")))

(def ^:dynamic *language* :en)

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
     [:h1 (:name     info)]
     [:h2 (:phone    info)]
     [:h2 (:email    info)]
     [:h2 (:site     info)]
     [:h2 (:location info)]]))

(def cv-experience
  (let [experiences (:experience cv)]
    (map
     (fn [experience]
       [:div.experience
        [:h2 (-> (:where experience) (tr *language*))]
        [:h3 (:when experience)]
        [:p.description (-> (:what  experience) (tr *language*))]])
     experiences)))

(def cv-education
  (let [education (:education cv)]
    (map
     (fn [uni]
       [:div.education
        [:h2 (-> (:which uni) (tr *language*))]
        [:h3 (:when uni)]
        [:p.description (-> (:what  uni) (tr *language*))]])
     education)))

(def cv-tools
  (let [tools (:technologies cv)]
    (map
     (fn [tool]
       [:div.tool
        [:h3 (:title tool)
         (map-indexed (fn [idx itm]
                        (vector :sup {:href itm} (str "[" (inc idx) "]")))
                      (:proof tool))]
        ])
     tools)))

(defroutes my-routes
  (GET "/" req (document (list cv-info
                               cv-experience
                               cv-education
                               cv-tools))))

(def handler
  (-> (handler/site my-routes)
      (wrap-resource "public")))
