(defproject notaskinnerbox "1.0.0-SNAPSHOT"
  :description "Not a Skinner box - we respect your attention reserves"
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [org.clojure/tools.cli "0.1.0"]
                 [org.clojure/data.json "0.1.1"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [compojure "0.6.4"]
                 [hiccup "0.3.6"]
                 [lein-ring "0.4.5"]]
  :dev-dependencies []
  :ring {:handler notaskinnerbox.routes/app-handler}
  :main notaskinnerbox.core)
