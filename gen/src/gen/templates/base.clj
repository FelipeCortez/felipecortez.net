(ns gen.templates.base
  (:require [hiccup.core :refer :all]
            [hiccup.page :refer [html5 doctype include-css]]))

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
                "cv.css")))

(defn analytics []
  [:script "(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)})(window,document,'script','//www.google-analytics.com/analytics.js','ga');ga('create', 'UA-49103022-1', 'auto');ga('send', 'pageview');"])

(defn head [page-title page-description]
  (list
   [:head
    [:title page-title]
    (metadata page-description)
    (analytics)]))

(defn body [content]
  [:body content])

(defn document [content]
  (str (:html5 doctype)
       (html [:html {:lang "en"}
              (head "Home" "Description")
              (body content)])))
