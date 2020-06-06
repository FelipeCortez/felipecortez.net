#!/usr/bin/env bb

(require '[babashka.pods :as pods])

(pods/load-pod "bootleg")
(require '[pod.retrogradeorbit.bootleg.utils :as utils])

(pods/load-pod "pod-babashka-filewatcher")
(require '[pod.babashka.filewatcher :as fw])

(pods/load-pod "pod-babashka-etaoin")
(require '[pod.babashka.etaoin :as eta])

(try
  (def cache (edn/read-string (slurp "cache.edn")))
  (catch Exception e (def cache {})))

(defn render [title doc]
  (utils/convert-to [:html
                     [:body
                      [:h1 title]
                      [:div doc]]]
                    :html))

(defn convert [f]
  (let [path (io/as-relative-path f)
        output-path (-> path
                        (str/replace #"^[^/]+/" "output/")
                        (str/replace #"[.][^.]+$" ".html"))
        file-hash (hash (slurp f))]
    (when-not (= file-hash (:hash (get cache path)))
      (println (get cache path))
      (io/make-parents output-path)
      (spit output-path (render "Docs"
                                (:out (shell/sh "md2html"
                                                "--github"
                                                (io/as-relative-path f))))))

    {:filename path
     :hash     file-hash}))

(defn markdown? [f]
  (str/ends-with? (io/as-relative-path f) ".md"))

(->> "playbooks"
     io/file
     file-seq
     (filter #(.isFile %))
     (filter markdown?)
     (pmap convert)
     (group-by :filename)
     (map (fn [[k v]] [k (first v)]))
     (into {})
     doall
     (spit "cache.edn"))
