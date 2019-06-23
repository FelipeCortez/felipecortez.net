(ns gen.templates.home
  (:require [gen.templates.base :refer :all]
            [clojure.string :as string]
            [gen.util :refer [tr]]))

(defn links []
  [["cv"         "/cv"]
   ["projects"   "/projects"]
   ["github"     "https://github.com/FelipeCortez"]
   ["soundcloud" "https://soundcloud.com/FelipeCortez"]
   ["last.fm"    "https://last.fm/user/FelipeSah"]
   ["letterboxd" "https://letterboxd.com/FelipeCortez"]
   ["bookmarks"  "https://bmarks.net/felipecortez"]])

(defn home-page []
  (document
   {:title "Felipe Cortez"}
   [:div
    [:div.container.pad
     [:h1.title {:style "display: inline-block"} "Felipe Cortez"]
     [:ul.front-links
      (map (fn [[title link]] [:li [:a {:href link} title]])
           (links))]
     [:h6 "contact@felipecortez.net"]]]))
