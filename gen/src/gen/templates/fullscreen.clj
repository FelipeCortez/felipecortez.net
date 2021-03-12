(ns gen.templates.fullscreen
  (:require [gen.templates.base :refer [document]]))

(defn fullscreen-page []
  (document {:title "A fullscreen experiment"}
            (list [:h1 {:style "margin-bottom: 12px"} "Hello!"]
                  [:button {:onClick "document.documentElement.requestFullscreen();"}
                   "Go fullscreen"])))
