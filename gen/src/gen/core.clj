(ns gen.core
  (:require [gen.templates.cv :refer [cv-page]]
            [gen.templates.projects :refer [projects-page]]
            [gen.templates.home :refer [home-page]]
            [clojure.java.io :as io]
            [clojure.java.shell :as shell]))

(defn cp [from to] (shell/sh))
(defn politely-spit [f contents] (io/make-parents f) (spit f contents))

(politely-spit "../build/index.html" (home-page))
(politely-spit "../build/cv.html" (cv-page))
(politely-spit "../build/projects.html" (projects-page))
(shell/sh "rsync" "-r" "resources/public" "../build")
