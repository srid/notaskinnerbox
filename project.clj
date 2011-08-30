(defproject notaskinnerbox "1.0.0-SNAPSHOT"
  :description "Not a Skinner box - we respect your attention reserves"
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.clojure/tools.cli "0.1.0"]
                 [org.clojure/data.json "0.1.1"]
                 [compojure "0.6.4"]
                 [hiccup "0.3.6"]
                 [enlive "1.0.0"]
                 [ring "0.3.11"]]
  :dev-dependencies [[lein-ring "0.4.5"]]
  :ring {:handler notaskinnerbox.routes/app}
  :main notaskinnerbox.routes)
