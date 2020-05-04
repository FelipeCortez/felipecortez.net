(ns gen.templates.projects
  (:require [gen.templates.base :refer [document]]))

(defn projects-page []
  (document {:title "Felipe Cortez · Projects"
             :css ["/projects.css"]}
            [:div
             [:div.container.pad
              {:style "max-width: 800px;"}
              [:h1.title [:a {:href "/"} "Felipe Cortez"] " · projects"]
              [:div.projects
               [:div.project
                [:div.image-container
                 [:div.image
                  [:a
                   {:href "https://bmarks.net"}
                   [:img {:src "/img/bmarks.png", :alt "bmarks"}]]]]
                [:div.info-container
                 [:a {:href "https://bmarks.net"}]
                 [:h2 "bmarks.net"]
                 [:div.description
                  "An open-source full featured tag-based online bookmark manager inspired by "
                  [:a {:href "https://del.icio.us"} "del.icio.us"]
                  " and "
                  [:a {:href "https://pinboard.in"} "Pinboard"]
                  "."]
                 [:ul.tech
                  [:li "Python"]
                  [:li "Django"]
                  [:li "JavaScript"]
                  [:li "PostgreSQL"]]]]
               [:div.project
                [:div.image-container
                 [:div.image
                  [:a
                   {:href "https://github.com/FelipeCortez/emmn"}
                   [:img {:src "/img/emmn.png", :alt "EMMN"}]]]]
                [:div.info-container
                 [:a {:href "https://github.com/FelipeCortez/emmn"}]
                 [:h2 "emmn"]
                 [:div.description
                  "A satellite tracking desktop application for the "
                  [:a
                   {:href "http://www.inpe.br/"}
                   "National Institute for Space Research"]
                  " implemented on top of a terminal-based program."]
                 [:ul.tech [:li "C++"] [:li "Qt"] [:li "Arduino"]]]]
               [:div.project
                [:div.image-container
                 [:a
                  {:href "https://github.com/FelipeCortez/grybo"}
                  [:div.image [:img {:src "/img/grybo.png", :alt "Grybo"}]]]]
                [:div.info-container
                 [:a {:href "https://github.com/FelipeCortez/grybo"}]
                 [:h2 "grybo"]
                 [:div.description
                  "A Guitar Hero inspired game featuring Brazilian music such as samba, choro and bossa nova."]
                 [:ul.tech [:li "C++"] [:li "SDL"]]]]
               [:div.project
                [:div.image-container
                 [:div.image
                  [:a
                   {:href "/albums?u=felipesah"}
                   [:img {:src "/img/albums.png", :alt "Albums"}]]]]
                [:div.info-container
                 [:a {:href "/albums?u=felipesah"}]
                 [:h2 "last.fm album explorer"]
                 [:div.description
                  "Uses the "
                  [:a {:href "https://www.last.fm/api"} "last.fm api"]
                  " to display what records people have been listening to recently. Inspired by record store browsing and looking at beautiful album art."]
                 [:ul.tech [:li "JavaScript"]]]]
               [:div.project
                [:div.image-container
                 [:div.image
                  [:a
                   {:href "/nomes/"}
                   [:img {:src "/img/nomes.png", :alt "nomes"}]]]]
                [:div.info-container
                 [:a {:href "/nomes/"}]
                 [:h2 "nomes"]
                 [:div.description
                  "An explorable list of most Brazilian names, including similar names found via "
                  [:a
                   {:href "https://en.wikipedia.org/wiki/Levenshtein_distance"}
                   "Levenshtein distance"]
                  "."]
                 [:ul.tech [:li "Python"] [:li "Flask"] [:li "SQLite"]]]]
               [:div.project
                [:div.image-container
                 [:div.image
                  [:a
                   {:href "https://ciroeacidade.com"}
                   [:img {:src "/img/ciro.png", :alt "Ciro e a Cidade"}]]]]
                [:div.info-container
                 [:a {:href "https://ciroeacidade.com"}]
                 [:h2 "Ciro e a Cidade"]
                 [:div.description
                  "Played the guitar and keys in this band for some time, designed the website and produced "
                  [:a
                   {:href "https://www.youtube.com/watch?v=B8TbfGLbiho"}
                   " this track"]
                  "."]
                 [:ul.tech [:li "HTML"] [:li "CSS"]]]]
               [:div.project
                [:div.image-container
                 [:div.image
                  [:a
                   {:href "/camilify/"}
                   [:img {:src "/img/camilify.png", :alt "camilify"}]]]]
                [:div.info-container
                 [:a {:href "/camilify/"}]
                 [:h2 "camilify"]
                 [:div.description
                  "Simulates "
                  [:a
                   {:href "https://twitter.com/scleroticfinger"}
                   "how my friend Camile types"]
                  "."]
                 [:ul.tech [:li "JavaScript"]]]]
               [:div.project
                [:div.image-container
                 [:div.image
                  [:a
                   {:href "https://github.com/FelipeCortez/StocSynth"}
                   [:img {:src "/img/stocsynth.png", :alt "StocSynth"}]]]]
                [:div.info-container
                 [:a {:href "https://github.com/FelipeCortez/StocSynth"}]
                 [:h2 "StocSynth"]
                 [:div.description
                  "A virtual instrument plugin based on "
                  [:a
                   {:href "https://en.wikipedia.org/wiki/Additive_synthesis"}
                   "additive synthesis"]
                  " and the "
                  [:a
                   {:href "https://en.wikipedia.org/wiki/Normal_distribution"}
                   "normal distribution"]
                  "."]
                 [:ul.tech [:li "C++"] [:li "JUCE"]]]]]
              [:h1.title {:style "margin-top: 30px"} "b-sides"]
              [:ul.b-sides
               [:li
                [:a
                 {:href "https://github.com/FelipeCortez/processing_sketches"}
                 "Unknown Pleasures album cover in Processing"]]
               [:li
                [:a
                 {:href "https://telegram.me/addstickers/msnmessengermsn"}
                 "MSN Messenger emoticons for Telegram"]]
               [:li
                [:a
                 {:href "https://www.youtube.com/watch?v=AjlNCOzSdwo"}
                 "Three-handed electric piano game soundtrack cover"]]
               [:li
                [:a
                 {:href "https://www.youtube.com/watch?v=uyEfovYUZco"}
                 "A TV show theme cover"]]
               [:li
                [:a
                 {:href
                  "https://rateyourmusic.com/list/FelipeCortez/valid_unix_commands/"}
                 "A list of band names that work as UNIX commands"]]]]]))

