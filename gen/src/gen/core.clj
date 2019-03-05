(ns gen.core
  (:require [gen.templates.cv :refer [cv-page]]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [ring.middleware.resource :refer [wrap-resource]]))

(defroutes my-routes
  (GET "/" req (cv-page)))

(def handler
  (-> (handler/site my-routes)
      (wrap-resource "public")))
