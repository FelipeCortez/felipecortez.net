(ns gen.core
  (:require [gen.templates.cv :refer [cv-page]]
            [gen.templates.home :refer [home-page]]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [ring.middleware.resource :refer [wrap-resource]]))

(defroutes my-routes
  (GET "/"   req (home-page))
  (GET "/cv" req (cv-page)))

(def handler
  (-> (handler/site my-routes)
      (wrap-resource "public")))
