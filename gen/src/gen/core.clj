(ns gen.core
  (:require [gen.templates.cv :refer [cv-page]]
            [gen.templates.projects :refer [projects-page]]
            [gen.templates.home :refer [home-page]]
            [gen.templates.fullscreen :refer [fullscreen-page]]
            [clojure.java.io :as io]
            [clojure.java.shell :as shell]))

(defn politely-spit [f contents] (io/make-parents f) (spit f contents))

(shell/sh "rm" "-rf" "build")
(politely-spit "build/index.html" (home-page))
(politely-spit "build/fullscreen.html" (fullscreen-page))
(politely-spit "build/cv/index.html" (cv-page))
(politely-spit "build/projects/index.html" (projects-page))
(shell/sh "rsync" "-r" "resources/public/" "build")

(shutdown-agents)
